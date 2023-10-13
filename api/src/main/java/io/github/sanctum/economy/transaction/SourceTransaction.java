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
package io.github.sanctum.economy.transaction;

import io.github.sanctum.economy.construct.assets.Amount;
import io.github.sanctum.economy.construct.entity.EnterpriseEntity;
import io.github.sanctum.economy.construct.system.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * An {@link Operation#TAKE}-based transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class SourceTransaction extends MemoryTransaction {
    /**
     * Creates a new take-based transaction.
     *
     * @param amount an amount
     * @param primaries the involved entity or entities
     */
    public SourceTransaction(@NotNull Amount amount, @NotNull EnterpriseEntity... primaries) {
        super(amount, amount.getAsset(), Operation.TAKE, primaries);
    }

    /**
     * Gets the amount being taken by this transaction.
     *
     * @return the amount
     */
    @Override
    public @Nullable Amount getAmount() {
        return amount;
    }

    /**
     * Represents the result of a take transaction.
     *
     * @since 2.0.0
     */
    static final class Result extends io.github.sanctum.economy.transaction.Result<SourceTransaction, Source.SupplyError> {
        /**
         * Creates a result from a UUID, transaction spec, error and success.
         *
         * @param uuid the unique identifier of this transaction execution
         * @param transaction the original transaction specification
         * @param error the error, if any
         * @param success whether the transaction was "successful"
         * @implSpec Success is an implementation-specific concept.
         */
        public Result(@NotNull UUID uuid, @NotNull SourceTransaction transaction, @Nullable Source.SupplyError error, boolean success) {
            super(uuid, transaction, error, success);
        }
    }
}
