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
package com.github.sanctum.economy.transaction;

import com.github.sanctum.economy.construct.assets.Amount;
import com.github.sanctum.economy.construct.entity.EnterpriseEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * @param has whether the point had the amount
     * @param info optionally, more/custom text detail
     * @param primaries the involved entity or entities
     */
    public QueryTransaction(@NotNull Amount amount, boolean has, @Nullable String info, @NotNull EnterpriseEntity... primaries) {
        super(amount, amount.getAsset(), Operation.QUERY, null, has, info, primaries);
    }

    /**
     * Whether the point has the amount.
     *
     * @return true if the amount is present
     */
    @Override
    public boolean isSuccess() {
        return success;
    }
}
