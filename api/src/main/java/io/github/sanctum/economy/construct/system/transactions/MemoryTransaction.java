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

import io.github.sanctum.economy.construct.assets.Amount;
import io.github.sanctum.economy.construct.assets.Asset;
import io.github.sanctum.economy.construct.system.Resolvable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds transaction information in memory.
 *
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.NonExtendable
public class MemoryTransaction {
    final Amount amount;
    final Asset asset;
    final Resolvable[] primaries;
    final Operation operation;

    /**
     * Creates a new transaction object from provided information.
     *
     * @param amount an amount (if present, must be an amount of {@code asset})
     * @param asset an asset
     * @param operation a transaction type
     * @param primaries the involved participant or participants
     */
    protected MemoryTransaction(@Nullable Amount amount,
                                @NotNull Asset asset,
                                @NotNull Operation operation,
                                @NotNull Resolvable... primaries) {
        if (amount != null && !amount.getAsset().equals(asset)) {
            throw new IllegalArgumentException("Amount asset must match transaction asset");
        }
        this.amount = amount;
        this.asset = asset;
        this.operation = operation;
        this.primaries = primaries;
    }

    /**
     * Gets the amount for this transaction, if present.
     *
     * @return an amount or null
     */
    public @Nullable Amount getAmount() {
        return amount;
    }

    /**
     * Gets the asset associated with this transaction.
     *
     * @return the associated asset
     */
    public final @NotNull Asset getAsset() {
        return asset;
    }

    /**
     * Gets the operation (transaction type).
     *
     * @return the transaction type
     */
    public final @NotNull Operation getOperation() {
        return operation;
    }

    /**
     * Gets the primary actor or actors in this transaction.
     *
     * @return the primary actor or actors
     */
    public final @NotNull Resolvable @NotNull [] getPrimaries() {
        return primaries;
    }
}
