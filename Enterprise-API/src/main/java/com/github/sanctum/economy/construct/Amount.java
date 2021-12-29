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
        return Objects.hash(asset, normalize(getAmount()));
    }

    /**
     * Convert BigDecimal values to the most compact and human-readable form.
     *
     * @param value a BigDecimal value to normalize
     * @return a normalized value
     * @implNote The normalization process seeks to represent BigDecimal values
     * as full, unscaled integers OR compact decimals. It looks like this:
     * <h3>For "0.50":</h3>
     * <ul>
     *     <li><code>0.50 -> 0.5</code></li>
     *     <li>{@link BigDecimal#scale()} is <code>&lt;0 (2)</code></li>
     *     <li>This indicates a decimal portion. We will try to strip
     *     trailing zeros from the value: <code>0.5</code>
     *     </li>
     *     <li>We will use this new representation as it is more compact.</li>
     * </ul>
     * <h3>For "2E+2":</h3>
     * <ul>
     *     <li><code>2E+2 -> 200</code></li>
     *     <li>Scale is negative: <code>-2</code></li>
     *     <li>This indicates a scaled whole number; we will simplify its
     *     representation by setting scale to 0: <code>200</code>
     *     </li>
     *     <li>We will use this new value as it is easier to read.</li>
     * </ul>
     */
    public static BigDecimal normalize(@NotNull BigDecimal value) {
        final int initialScale = value.scale();
        // simple whole number = fast return
        if (initialScale == 0) return value;
        // If the initial value has a decimal component
        if (initialScale > 0) {
            final BigDecimal stripped = value.stripTrailingZeros();
            // ...and strip does nothing, return original value; otherwise stripped
            return initialScale == stripped.scale() ? value : stripped;
        }
        // Number is currently negatively scaled, set scale to zero
        return value.setScale(0, BigDecimal.ROUND_UNNECESSARY);
    }
}
