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
package com.github.sanctum.economy.construct.assets;

import com.github.sanctum.economy.construct.IntegralAmount;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Provides a fast, immutable implementation of IntegralAmount for item assets.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class ItemAmount extends IntegralAmount {
    final int amount;
    final BigDecimal bigDecimal;

    ItemAmount(int amount, @NotNull Asset asset) {
        super(asset);
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative!");
        this.amount = amount;
        bigDecimal = BigDecimal.valueOf(amount);
    }

    @Override
    public int getIntegralAmount() {
        return amount;
    }

    @Override
    public @NotNull BigDecimal getAmount() {
        return bigDecimal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ItemAmount) {
            ItemAmount itemAmount = (ItemAmount) o;
            if (amount == itemAmount.amount) {
                return asset.equals(itemAmount.asset);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        // 33554249 designed to expand amount range [0-64] to fill most of int space
        return asset.hashCode() ^ amount * 33554249;
    }
}
