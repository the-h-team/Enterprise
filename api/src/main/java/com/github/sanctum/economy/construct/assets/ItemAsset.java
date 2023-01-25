/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
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
 * An asset which represents an item.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class ItemAsset extends AssetImpl implements IntegralAsset {
    /**
     * The common group for all item-based assets.
     */
    public static final @Group String GROUP = "item";
    private final String itemId;

    /**
     * Creates a new item asset.
     *
     * @param identifier an identifier
     * @param itemId the item's id
     */
    protected ItemAsset(@Identifier @NotNull String identifier, @NotNull String itemId) {
        super(GROUP, identifier);
        this.itemId = itemId;
    }

    /**
     * Gets the namespaced ID of the item.
     * <p>
     * For example, {@code minecraft:diamond}.
     *
     * @return the item's namespaced ID
     */
    public final @NotNull String getItemId() {
        return itemId;
    }

    /**
     * Gets an amount of this item asset.
     *
     * @param count the whole number count of the asset
     * @return a new amount object
     * @throws IllegalArgumentException if {@code count} is negative
     */
    @Override
    public final @NotNull IntegralAmount getAmount(int count) {
        return new Amount(count, this);
    }

    /**
     * Provides a fast, immutable implementation of IntegralAmount for item assets.
     *
     * @since 2.0.0
     */
    public static final class Amount extends IntegralAmount {
        final int amount;
        final BigDecimal bigDecimal;

        Amount(int amount, @NotNull ItemAsset asset) {
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
            if (o instanceof Amount) {
                Amount amount = (Amount) o;
                if (this.amount == amount.amount) {
                    return asset.equals(amount.asset);
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            // 524309 designed to expand values from [0-4096] to fill up int space
            /*
            Larger values roll over; semi-arbitrary range chosen based on the max
            contents count of a double-chest inventory: 9*6=54; 54*64 = 3456 total
             */
            return asset.hashCode() ^ amount * 524309;
        }
    }
}
