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
 * A point from which assets can be taken.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Source extends Resolvable {
    /**
     * Takes an amount from this point.
     *
     * @param amount an amount of an asset
     * @return this point
     * @throws SupplyError if this point cannot produce the amount
     * @throws AbstractSystemException if a system error occurs
     */
    @Contract("_ -> this")
    @NotNull Source take(@NotNull Amount amount) throws SupplyError, AbstractSystemException;

    /**
     * Takes an amount from this point.
     *
     * @param amount an amount of an asset
     * @return a pending result
     */
    default @NotNull PendingResult<Result.NotEmpty<Source>, Source> asyncTake(@NotNull Amount amount) {
        return PendingResult.of(() -> take(amount));
    }

    /**
     * Raised when a source is unable to provide an amount.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class SupplyError extends AmountException {
        private static final long serialVersionUID = 1018956534551717937L;

        /**
         * Constructs an exception with an Amount and a message.
         *
         * @param amount an amount of an asset
         * @param message a message
         */
        public SupplyError(@NotNull Amount amount, String message) {
            super(amount, message);
        }

        /**
         * Constructs an exception with an Amount, a message and cause.
         *
         * @param amount an amount of an asset
         * @param message a message
         * @param cause a cause throwable
         */
        public SupplyError(@NotNull Amount amount, String message, Throwable cause) {
            super(amount, message, cause);
        }

        /**
         * Constructs an exception with an Amount and a cause.
         *
         * @param amount an amount of an asset
         * @param cause a cause throwable
         */
        public SupplyError(@NotNull Amount amount, Throwable cause) {
            super(amount, cause);
        }
    }
}
