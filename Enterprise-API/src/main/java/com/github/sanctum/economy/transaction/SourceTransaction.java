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

import com.github.sanctum.economy.construct.Amount;
import com.github.sanctum.economy.construct.entity.EnterpriseEntity;
import com.github.sanctum.economy.construct.system.Source.SupplyError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A {@link Operation#TAKE}-based transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class SourceTransaction extends MemoryTransaction {
    /**
     * Create a new take-based transaction.
     *
     * @param amount an Amount
     * @param exception a SupplyError if one has occurred
     * @param info optionally, more/custom text detail
     * @param primaries the involved entity or entities
     */
    public SourceTransaction(@NotNull Amount amount, @Nullable SupplyError exception, @Nullable String info, @NotNull EnterpriseEntity... primaries) {
        super(amount, amount.getAsset(), Operation.TAKE, exception, exception == null, info, primaries);
    }

    /**
     * Get the SupplyError for this transaction if it is unsuccessful.
     *
     * @return an Optional describing a SupplyError
     */
    @Override
    public Optional<SupplyError> getException() {
        return Optional.ofNullable((SupplyError) exception);
    }

    /**
     * Whether the point successfully supplied the amount.
     *
     * @return true if the amount was taken successfully
     */
    @Override
    public boolean isSuccess() {
        return success;
    }
}
