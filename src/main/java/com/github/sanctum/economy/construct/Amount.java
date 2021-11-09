package com.github.sanctum.economy.construct;

import com.github.sanctum.economy.construct.assets.Asset;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Represents an amount of an asset as a BigDecimal.
 *
 * @since 2.0.0
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
}
