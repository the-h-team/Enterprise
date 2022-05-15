/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
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
package com.github.sanctum.economy.construct.impl;

import com.github.sanctum.economy.construct.Amount;
import com.github.sanctum.economy.construct.assets.*;
import com.github.sanctum.economy.construct.system.Balance;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

/**
 * An object suitable for managing amounts of item assets.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class ItemStore implements Balance {
    protected final HashMap<String, Integer> counts = new HashMap<>();

    @Override
    public boolean has(@NotNull Amount amount) {
        if (amount instanceof ItemAsset.Amount) {
            return counts.getOrDefault(amount.getAsset().getFQN(), 0) >= ((ItemAsset.Amount) amount).getIntegralAmount();
        }
        return false;
    }

    @Override
    public void give(@NotNull Amount amount) throws AcceptError {
        if (amount instanceof ItemAsset.Amount) {
            synchronized (counts) {
                counts.merge(amount.getAsset().getFQN(), ((ItemAsset.Amount) amount).getIntegralAmount(), Integer::sum);
            }
            return;
        }
        throw new AcceptError(amount, "This asset is not supported.");
    }

    @Override
    public void set(@NotNull Amount amount) throws SetError {
        if (amount instanceof ItemAsset.Amount) {
            synchronized (counts) {
                counts.put(amount.getAsset().getFQN(), ((ItemAsset.Amount) amount).getIntegralAmount());
            }
            return;
        }
        throw new SetError(amount, "This asset is not supported.");
    }

    @Override
    public void take(@NotNull Amount amount) throws SupplyError {
        if (amount instanceof ItemAsset.Amount) {
            final int newAmount = ((ItemAsset.Amount) amount).getIntegralAmount();
            final String fqn = amount.getAsset().getFQN();
            synchronized (counts) {
                if (counts.getOrDefault(fqn, 0) < newAmount) {
                    throw new SupplyError(amount, "Insufficient amount!");
                }
                counts.put(fqn, newAmount);
            }
            return;
        }
        throw new SupplyError(amount, "This asset is not supported.");
    }

    @Override
    public @NotNull Optional<Amount> total(@NotNull Asset asset) {
        if (asset instanceof ItemAsset) {
            final Integer integer = counts.get(asset.getFQN());
            if (integer != null) {
                return Optional.of(((IntegralAsset) asset).getAmount(integer));
            }
        }
        return Optional.empty();
    }
}
