package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A point to which assets can be given.
 *
 * @since 2.0.0
 */
public interface Receiver {
    /**
     * Attempt to give an amount to this receiver.
     *
     * @param amount an amount of an asset
     */
    void give(@NotNull Amount amount);
}
