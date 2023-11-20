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

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Represents the pending result of an economy action.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <R> the result type
 * @param <E> the type of system error that may have occurred
 */
public abstract class PendingResult<R, E extends AbstractSystemException> {
    /**
     * Runs the given function with the result object once complete.
     *
     * @param processor a result processor
     */
    public abstract void onceComplete(@NotNull Consumer<Result<R, E>> processor);

    /**
     * Runs the given function with the result once complete.
     * <p>
     * If the action was not successful the function will not be run.
     *
     * @param consumer a result process function
     */
    public abstract void ifSuccessful(@NotNull Consumer<@Nullable R> consumer);

    /**
     * Runs the given function with the error once complete.
     * <p>
     * If the action was successful the function will not be run.
     *
     * @param consumer an error process function
     */
    public abstract void ifFailed(@NotNull Consumer<E> consumer);

    /**
     * Creates a completed pending result.
     *
     * @param result the result
     * @return a completed pending result
     */
    public static <R, E extends AbstractSystemException> @NotNull PendingResult<R, E> of(Result<@Nullable R, E> result) {
        return new PendingResultImpl<>(CompletableFuture.completedFuture(result));
    }
}
