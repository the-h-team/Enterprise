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
package io.github.sanctum.economy.construct.system;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the result of an economy action.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <R> the result type
 * @param <E> the type of system error that may have occurred
 */
public interface Result<R, E extends AbstractSystemException> {

    /**
     * Blocks until the action is completed (may throw).
     * <p>
     * This is useful for try-catching the result.
     *
     * @return the result
     */
    @Nullable R get() throws E;

    /**
     * Creates an empty result with an error.
     *
     * @param error the error
     * @return a result
     */
    static <E extends AbstractSystemException> Result<Void, E> empty(@NotNull E error) {
        return new ResultImpl<>(null, error);
    }

    /**
     * Creates a result completed without error.
     *
     * @param result the result
     * @return a result
     */
    static <R> Result<@Nullable R, AbstractSystemException> success(@Nullable R result) {
        return new ResultImpl<>(result, null);
    }
}
