/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package io.github.sanctum.economy.construct.impl;

import io.github.sanctum.economy.construct.assets.Amount;
import io.github.sanctum.economy.construct.assets.Asset;
import io.github.sanctum.economy.construct.assets.ItemAsset;
import io.github.sanctum.economy.construct.system.Balance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * An object suitable for managing amounts of item assets.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class ItemStore implements Balance {
    protected final HashMap<String, Integer> counts = new HashMap<>();

    @Override
    public boolean has(@NotNull Amount amount) {
        if (amount instanceof ItemAsset.ItemAmount) {
            return counts.getOrDefault(amount.getAsset().getIdentifier(), 0) >= ((ItemAsset.ItemAmount) amount).getIntegralAmount();
        }
        return false;
    }

    @Override
    public @NotNull ItemStore give(@NotNull Amount amount) throws AcceptException {
        if (amount instanceof ItemAsset.ItemAmount) {
            synchronized (counts) {
                counts.merge(amount.getAsset().getIdentifier(), ((ItemAsset.ItemAmount) amount).getIntegralAmount(), Integer::sum);
            }
            return this;
        }
        throw new AcceptException(amount, "This asset is not supported.");
    }

    @Override
    public @NotNull ItemStore set(@NotNull Amount amount) throws SetException {
        if (amount instanceof ItemAsset.ItemAmount) {
            synchronized (counts) {
                counts.put(amount.getAsset().getIdentifier(), ((ItemAsset.ItemAmount) amount).getIntegralAmount());
            }
            return this;
        }
        throw new SetException(amount, "This asset is not supported.");
    }

    @Override
    public @NotNull ItemStore take(@NotNull Amount amount) throws SupplyException {
        if (amount instanceof ItemAsset.ItemAmount) {
            final int newAmount = ((ItemAsset.ItemAmount) amount).getIntegralAmount();
            final String fqn = amount.getAsset().getIdentifier();
            synchronized (counts) {
                if (counts.getOrDefault(fqn, 0) < newAmount) {
                    throw new SupplyException(amount, "Insufficient amount!");
                }
                counts.put(fqn, newAmount);
            }
            return this;
        }
        throw new SupplyException(amount, "This asset is not supported.");
    }

    @Override
    public @Nullable ItemAsset.ItemAmount total(@NotNull Asset asset) {
        if (asset instanceof ItemAsset) {
            final Integer integer = counts.get(asset.getIdentifier());
            if (integer != null) {
                return ((ItemAsset) asset).getAmount(integer);
            }
        }
        return null;
    }
}
