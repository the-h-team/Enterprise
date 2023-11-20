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
import io.github.sanctum.economy.construct.entity.EnterpriseEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An {@link Operation#TOTAL}-based transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class TotalTransaction extends MemoryTransaction {
    /**
     * Creates a new total-based transaction.
     *
     * @param amount the amount possessed by the point, if any
     * @param asset the asset being totalled
     * @param primaries the involved entity or entities
     */
    public TotalTransaction(@Nullable Amount amount, @NotNull Asset asset, @NotNull EnterpriseEntity... primaries) {
        super(amount, asset, Operation.TOTAL, primaries);
    }

    /**
     * Gets the total amount of {@link #getAsset()} possessed by this point.
     *
     * @return the amount if present or null
     */
    @Override
    public @Nullable Amount getAmount() {
        return amount;
    }
}
