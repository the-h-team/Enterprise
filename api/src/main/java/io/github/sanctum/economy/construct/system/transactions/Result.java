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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the result of an economy action.
 * <p>
 * Care should be taken if directly implementing this interface. It is possible
 * for a result to be queried multiple times. If the result is not cached, this
 * may result in multiple identical calls to the underlying system (which could
 * duplicate operations). See {@link #lazy(Result)}, {@link #lazy(NotEmpty)}.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <R> the result type
 */
public interface Result<R> {

    /**
     * A result that is always empty or always throws.
     */
    interface Empty extends Result<Void> {
        /**
         * An empty result that never throws.
         */
        Empty SUCCESS = Result.empty(null);
    }

    /**
     * A result that is always present or always throws.
     *
     * @param <R> the result type
     */
    interface NotEmpty<R> extends Result<R> {
        @Override
        @NotNull R get() throws AbstractSystemException;
    }

    /**
     * Blocks until the action is completed (may throw).
     * <p>
     * This is useful for try-catching the result.
     *
     * @return the result
     */
    @Nullable R get() throws AbstractSystemException;

    /**
     * Creates a lazy optional result.
     * <p>
     * The result will be computed exactly once when {@link #get()} is first called.
     * <p>
     * Subsequent calls to {@link #get()} will return the cached result.
     *
     * @param result a result function
     * @return a lazy result
     */
    static <R> Result<@Nullable R> lazy(@NotNull Result<@Nullable R> result) {
        return new ResultImpl.LazyImpl<>(result);
    }

    /**
     * Creates a lazy result.
     * <p>
     * The result will be computed exactly once when {@link #get()} is first called.
     * <p>
     * Subsequent calls to {@link #get()} will return the cached result.
     *
     * @param result a result function
     * @return a lazy result
     */
    static <R> Result.NotEmpty<@NotNull R> lazy(@NotNull Result.NotEmpty<@NotNull R> result) {
        return new ResultImpl.LazyNotEmptyImpl<>(result);
    }

    /**
     * Creates a result completed without error.
     *
     * @param result the result
     * @return a result
     */
    static <R> NotEmpty<@NotNull R> success(@NotNull R result) {
        return new ResultImpl.NotEmptyImpl<>(result);
    }

    /**
     * Creates a result from an error.
     *
     * @param resultType the class of the (missing) result type
     * @param error the system error that occurred
     * @return a result
     */
    static <R> NotEmpty<R> error(@NotNull Class<R> resultType, @NotNull AbstractSystemException error) {
        return new ResultImpl.NotEmptyImpl<>(error);
    }

    /**
     * Creates an empty result with an optional error.
     *
     * @param error the error
     * @return a result
     * @see Empty#SUCCESS
     */
    static Result.Empty empty(@Nullable AbstractSystemException error) {
        return new ResultImpl.EmptyImpl(error);
    }
}
