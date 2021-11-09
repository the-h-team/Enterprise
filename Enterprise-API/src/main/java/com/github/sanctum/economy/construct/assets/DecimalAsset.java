package com.github.sanctum.economy.construct.assets;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Marks an asset that can be divided by BigDecimal.
 *
 * @since 2.0.0
 */
public interface DecimalAsset {
    /**
     * Get an amount for this asset.
     *
     * @param decimal the decimal amount of the asset
     * @return a new amount object
     * @throws IllegalArgumentException if <code>decimal</code> is negative
     */
    Amount getAmount(@NotNull BigDecimal decimal);
}
