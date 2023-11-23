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
package io.github.sanctum.economy.construct.system.behaviors;

import io.github.sanctum.economy.construct.assets.Amount;
import io.github.sanctum.economy.construct.system.*;
import io.github.sanctum.economy.construct.system.exceptions.*;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A point to which assets can be given.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Receiver extends Resolvable {
    /**
     * Gives an amount to this point.
     *
     * @param amount an amount of an asset
     * @return this point
     * @throws AcceptException if this point cannot accept the amount
     * @throws AbstractSystemException if a system error occurs
     */
    @Contract("_ -> this")
    @NotNull Receiver give(@NotNull Amount amount) throws AcceptException, AbstractSystemException;

    /**
     * Gives an amount to this point.
     *
     * @param amount an amount of an asset
     * @return a pending result
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> asyncGive(@NotNull Amount amount) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> give(amount)));
    }

    /**
     * Raised when a receiver is unable to accept an amount.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class AcceptException extends AmountException {
        private static final long serialVersionUID = -5318173096317818284L;

        /**
         * Constructs an exception with an Amount and a message.
         *
         * @param amount an amount of an asset
         * @param message a message
         */
        public AcceptException(@NotNull Amount amount, String message) {
            super(amount, message);
        }

        /**
         * Constructs an exception with an Amount, a message and cause.
         *
         * @param amount an amount of an asset
         * @param message a message
         * @param cause a cause throwable
         */
        public AcceptException(@NotNull Amount amount, String message, Throwable cause) {
            super(amount, message, cause);
        }

        /**
         * Constructs an exception with an Amount and a cause.
         *
         * @param amount an amount of an asset
         * @param cause a cause throwable
         */
        public AcceptException(@NotNull Amount amount, Throwable cause) {
            super(amount, cause);
        }
    }
}
