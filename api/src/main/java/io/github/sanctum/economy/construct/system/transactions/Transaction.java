/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package io.github.sanctum.economy.construct.system.transactions;

import org.jetbrains.annotations.NotNull;

/**
 * A series of actions that must all complete or fail together.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <R> the result type
 * @param <T> the result type
 */
public interface Transaction<R extends Result<T>, T> {
    /**
     * Executes the transaction.
     *
     * @return a pending result
     */
    @NotNull PendingResult<R, T> execute();

    /**
     * A transaction that can be reversed.
     *
     * @param <R> the result type
     * @param <T> the result type
     */
    interface Reversible<R extends Result<T>, T> extends Transaction<R, T> {
        /**
         * Reverses the transaction.
         *
         * @return a pending result
         */
        @NotNull PendingResult<R, T> reverse();
    }
}
