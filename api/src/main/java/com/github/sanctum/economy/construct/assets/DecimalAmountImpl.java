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
package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@ApiStatus.Internal
class DecimalAmountImpl extends AmountImpl implements DecimalAmount {
    final BigDecimal amount;

    DecimalAmountImpl(@NotNull Asset asset, @NotNull BigDecimal amount) {
        super(asset);
        this.amount = amount;
    }

    @Override
    public final @NotNull BigDecimal getDecimal() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecimalAmountImpl)) return false;
        DecimalAmountImpl that = (DecimalAmountImpl) o;
        return asset.equals(that.asset) && amount.compareTo(that.amount) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 +
                DecimalAmount.normalize(amount).hashCode();
    }

    @Override
    public String toString() {
        return "DecimalAmountImpl{" +
                "amount=" + amount +
                ", asset=" + asset +
                '}';
    }
}
