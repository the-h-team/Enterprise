package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A point that can be tested for the holding of assets.
 *
 * @since 2.0.0
 */
public interface Queryable {
    /**
     * Check for an amount.
     *
     * @param amount an amount of an asset
     * @return true if this point has the amount
     */
    boolean has(@NotNull Amount amount);
}
