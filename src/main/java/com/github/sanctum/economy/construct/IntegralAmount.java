package com.github.sanctum.economy.construct;

import com.github.sanctum.economy.construct.assets.Asset;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Represents a simple, whole-number amount of an asset.
 *
 * @since 2.0.0
 */
public abstract class IntegralAmount extends Amount {
    protected IntegralAmount(Asset asset) {
        super(asset);
    }

    /**
     * Get the integer value of this amount.
     *
     * @return the integer value of this amount
     * @implSpec Must not return negative.
     */
    public abstract int getIntegralAmount();

    @Override
    public @NotNull BigDecimal getAmount() {
        return BigDecimal.valueOf(getIntegralAmount());
    }
}
