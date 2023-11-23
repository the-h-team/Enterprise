/*
 *  Copyright 2023 Sanctum <https://github.com/the-h-team>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.sanctum.economy.construct.system.transactions;

import io.github.sanctum.economy.construct.system.exceptions.AbstractSystemException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@ApiStatus.Internal
class ResultImpl<R> implements Result<R> {
    final R result;
    final AbstractSystemException error;

    ResultImpl(R result, AbstractSystemException error) {
        this.result = result;
        this.error = error;
    }

    @Override
    public @Nullable R get() throws AbstractSystemException {
        if (error != null) throw error;
        return result;
    }

    static class EmptyImpl extends ResultImpl<Void> implements Empty {
        EmptyImpl(AbstractSystemException error) {
            super(null, error);
        }
    }

    static class NotEmptyImpl<R> extends ResultImpl<R> implements NotEmpty<R> {
        NotEmptyImpl(@NotNull R result) {
            super(result, null);
        }

        NotEmptyImpl(@NotNull AbstractSystemException error) {
            super(null, error);
        }

        @Override
        public @NotNull R get() throws AbstractSystemException {
            if (error != null) throw error;
            return Objects.requireNonNull(result);
        }
    }

    static class LazyImpl<R> implements Result<R> {
        final Result<R> source;
        boolean complete;
        R result;
        AbstractSystemException error;

        LazyImpl(Result<R> source) {
            this.source = source;
        }

        @Override
        public @Nullable R get() throws AbstractSystemException {
            synchronized(this) {
                if (error != null) throw error;
                if (complete) return result;
                try {
                    result = source.get();
                } catch (AbstractSystemException e) {
                    error = e;
                    throw e;
                } finally {
                    complete = true;
                }
                return result;
            }
        }
    }

    static class LazyEmptyImpl extends LazyImpl<Void> implements Empty {
        LazyEmptyImpl(Result.Empty source) {
            super(source);
        }
    }

    static class LazyNotEmptyImpl<R> extends LazyImpl<R> implements NotEmpty<R> {
        LazyNotEmptyImpl(Result.NotEmpty<R> source) {
            super(source);
        }

        @Override
        public @NotNull R get() throws AbstractSystemException {
            return Objects.requireNonNull(super.get());
        }
    }
}
