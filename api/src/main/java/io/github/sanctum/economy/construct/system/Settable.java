/*
 *   Copyright 2021 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.construct.system;

import io.github.sanctum.economy.construct.assets.Amount;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A point whose amounts can be set.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Settable extends Resolvable {
    /**
     * Sets an amount.
     *
     * @param amount an amount of an asset
     * @return this point
     * @throws SetError if {@code amount} cannot be set for this point
     */
    @Contract("_ -> this")
    @NotNull Settable set(@NotNull Amount amount) throws SetError;

    /**
     * Sets an amount.
     *
     * @param amount an amount of an asset
     * @return a pending result
     */
    @NotNull PendingResult<Settable, SetError> asyncSet(@NotNull Amount amount);

    /**
     * Raised when an amount cannot be set for a point.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class SetError extends AmountException {
        private static final long serialVersionUID = 5857783323258413601L;

        /**
         * Constructs an exception with an Amount and a message.
         *
         * @param amount an amount of an asset
         * @param message a message
         */
        public SetError(@NotNull Amount amount, String message) {
            super(amount, message);
        }

        /**
         * Constructs an exception with an Amount, a message and cause.
         *
         * @param amount an amount of an asset
         * @param message a message
         * @param cause a cause throwable
         */
        public SetError(@NotNull Amount amount, String message, Throwable cause) {
            super(amount, message, cause);
        }

        /**
         * Constructs an exception with an Amount and a cause.
         *
         * @param amount an amount of an asset
         * @param cause a cause throwable
         */
        public SetError(@NotNull Amount amount, Throwable cause) {
            super(amount, cause);
        }
    }
}
