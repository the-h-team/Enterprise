package com.github.sanctum.economy.construct.assets;

import com.github.sanctum.economy.construct.IntegralAmount;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * Provides a fast, immutable implementation of IntegralAmount for item assets.
 *
 * @since 2.0.0
 */
public class ItemAmount extends IntegralAmount {
    final int amount;
    final BigDecimal bigDecimal;

    ItemAmount(int amount, Asset asset) {
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
}
