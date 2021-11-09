package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A point whose amounts can be set.
 *
 * @since 2.0.0
 */
public interface Settable {
    /**
     * Set an amount.
     *
     * @param amount an amount of an asset
     */
    void set(@NotNull Amount amount);
}
