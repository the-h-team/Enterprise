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
import com.github.sanctum.economy.construct.entity.EnterpriseEntity;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link Operation#QUERY}-based transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class QueryTransaction extends MemoryTransaction {
    /**
     * Creates a new query-based transaction object.
     *
     * @param amount an amount
     * @param primaries the involved participant or participants
     */
    public QueryTransaction(@NotNull Amount amount, @NotNull EnterpriseEntity... primaries) {
        super(amount, amount.getAsset(), Operation.QUERY, primaries);
    }

    /**
     * Gets the amount being tested by this transaction.
     *
     * @return the amount
     */
    @Override
    public @NotNull Amount getAmount() {
        return amount;
    }
}
