package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A point from which assets can be taken.
 *
 * @since 2.0.0
 */
public interface Source {
    /**
     * Attempt to take an amount from this source.
     *
     * @param amount an amount of an asset
     */
    void take(@NotNull Amount amount);
}
