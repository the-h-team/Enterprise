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
package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A point from which assets can be taken.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Source extends Resolvable {
    /**
     * Attempt to take an amount from this source.
     *
     * @param amount an amount of an asset
     * @throws SupplyError if this point cannot produce the amount
     */
    void take(@NotNull Amount amount) throws SupplyError;

    /**
     * Raised when a source is unable to provide an amount.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class SupplyError extends AmountException {
        private static final long serialVersionUID = 1018956534551717937L;

        /**
         * Construct an exception with an Amount and a message.
         *
         * @param amount an amount of an asset
         * @param message a message
         */
        public SupplyError(@NotNull Amount amount, String message) {
            super(amount, message);
        }

        /**
         * Construct an exception with an Amount, a message and cause.
         *
         * @param amount an amount of an asset
         * @param message a message
         * @param cause a cause throwable
         */
        public SupplyError(@NotNull Amount amount, String message, Throwable cause) {
            super(amount, message, cause);
        }

        /**
         * Construct an exception with an Amount and a cause.
         *
         * @param amount an amount of an asset
         * @param cause a cause throwable
         */
        public SupplyError(@NotNull Amount amount, Throwable cause) {
            super(amount, cause);
        }
    }
}
