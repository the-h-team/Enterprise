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
package com.github.sanctum.economy.construct;

import com.github.sanctum.economy.construct.assets.Asset;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents an amount of an asset as a BigDecimal.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class Amount {
    protected final Asset asset;

    protected Amount(Asset asset) {
        this.asset = asset;
    }

    /**
     * Get the asset being counted.
     *
     * @return the asset being counted
     */
    public final @NotNull Asset getAsset() {
        return asset;
    }

    /**
     * Get this amount as a BigDecimal.
     *
     * @return this amount as a BigDecimal
     * @implSpec Must not be negative.
     */
    public abstract @NotNull BigDecimal getAmount();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Amount) {
            Amount amount = (Amount) o;
            return asset.equals(amount.asset) && getAmount().compareTo(amount.getAmount()) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(asset, getAmount().stripTrailingZeros());
    }
}
