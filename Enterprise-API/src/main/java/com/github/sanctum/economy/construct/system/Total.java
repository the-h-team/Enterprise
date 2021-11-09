package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import com.github.sanctum.economy.construct.assets.Asset;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A point that can be queried for its totals of assets.
 *
 * @since 2.0.0
 */
public interface Total {
    /**
     * Query for the amount of this asset held.
     *
     * @param asset the asset to total
     * @return an Optional describing the amount, if present
     */
    @NotNull Optional<Amount> total(@NotNull Asset asset);
}
