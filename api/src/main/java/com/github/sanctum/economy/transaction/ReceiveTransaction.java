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
import com.github.sanctum.economy.construct.system.Receiver.AcceptError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A {@link Operation#GIVE}-based transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class ReceiveTransaction extends MemoryTransaction {
    /**
     * Create a new give-based transaction.
     *
     * @param amount an Amount
     * @param exception an AcceptError if one has occurred
     * @param info optionally, more/custom text detail
     * @param primaries the involved entity or entities
     */
    public ReceiveTransaction(@NotNull Amount amount, @Nullable AcceptError exception, @Nullable String info, @NotNull EnterpriseEntity... primaries) {
        super(amount, amount.getAsset(), Operation.GIVE, exception, exception == null, info, primaries);
    }

    /**
     * Get the AcceptError for this transaction if it is unsuccessful.
     *
     * @return an Optional describing an AcceptError
     */
    @Override
    public @NotNull Optional<AcceptError> getException() {
        return Optional.ofNullable((AcceptError) exception);
    }

    /**
     * Whether the point successfully received the amount.
     *
     * @return true if the amount was given successfully
     */
    @Override
    public boolean isSuccess() {
        return success;
    }
}