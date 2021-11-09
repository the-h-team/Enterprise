package com.github.sanctum.economy.construct.assets;

import com.github.sanctum.economy.construct.IntegralAmount;

/**
 * Marks an asset which can be divided by whole number.
 *
 * @since 2.0.0
 */
public interface IntegralAsset {
    /**
     * Get an amount for this asset.
     *
     * @param count the whole number count of the asset
     * @return a new amount object
     * @throws IllegalArgumentException if <code>count</code> is negative
     */
    IntegralAmount getAmount(int count);
}
