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
package com.github.sanctum.economy.transaction;

import com.github.sanctum.economy.construct.assets.Amount;
import com.github.sanctum.economy.construct.system.Receiver;
import com.github.sanctum.economy.construct.system.Resolvable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * An {@link Operation#GIVE}-based transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class ReceiveTransaction extends MemoryTransaction {
    /**
     * Creates a new give-based transaction.
     *
     * @param amount an amount
     * @param primaries the involved actor or actors
     */
    public ReceiveTransaction(@NotNull Amount amount, @NotNull Resolvable... primaries) {
        super(amount, amount.getAsset(), Operation.GIVE, primaries);
    }

    /**
     * Gets the amount being given by this transaction.
     *
     * @return the amount
     */
    @Override
    public @NotNull Amount getAmount() {
        return amount;
    }

    /**
     * Represents the result of a receive transaction.
     *
     * @since 2.0.0
     */
    static final class Result extends com.github.sanctum.economy.transaction.Result<ReceiveTransaction, Receiver.AcceptError> {
        /**
         * Creates a result from a UUID, transaction spec, error and success.
         *
         * @param uuid the unique identifier of this transaction execution
         * @param transaction the original transaction specification
         * @param error the error, if any
         * @param success whether the transaction was "successful"
         * @implSpec Success is an implementation-specific concept.
         */
        public Result(@NotNull UUID uuid, @NotNull ReceiveTransaction transaction, @Nullable Receiver.AcceptError error, boolean success) {
            super(uuid, transaction, error, success);
        }
    }
}
