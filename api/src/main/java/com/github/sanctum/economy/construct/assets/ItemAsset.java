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

import org.jetbrains.annotations.NotNull;

/**
 * An asset which represents an item.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class ItemAsset extends AssetImpl {
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
     */
    public final @NotNull ItemAsset.ItemAmount getAmount(int count) {
        return new ItemAmount(count, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemAsset itemAsset = (ItemAsset) o;

        return itemId.equals(itemAsset.itemId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + itemId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ItemAsset{" +
                "itemId='" + itemId + '\'' +
                ", group='" + group + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }

    /**
     * Provides a fast, immutable implementation of IntegralAmount for item assets.
     *
     * @since 2.0.0
     */
    public static final class ItemAmount extends AmountImpl implements IntegralAmount {
        final int amount;

        ItemAmount(int amount, @NotNull ItemAsset asset) {
            super(asset);
            this.amount = amount;
        }

        @Override
        public @NotNull ItemAsset getAsset() {
            return (ItemAsset) asset;
        }

        @Override
        public int getIntegralAmount() {
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof ItemAmount) {
                ItemAmount that = (ItemAmount) o;
                if (this.amount == that.amount) {
                    return asset.equals(that.asset);
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

        @Override
        public String toString() {
            return "ItemAmount{" +
                    "amount=" + amount +
                    ", asset=" + asset +
                    '}';
        }
    }
}
