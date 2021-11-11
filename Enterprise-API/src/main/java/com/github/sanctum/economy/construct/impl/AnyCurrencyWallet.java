/*
 *   Copyright 2021 Sanctum <https://github.com/the-h-team>
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
import com.github.sanctum.economy.construct.assets.AbstractCurrency;
import com.github.sanctum.economy.construct.assets.Asset;
import com.github.sanctum.economy.construct.system.Wallet;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

/**
 * A relatively simple wallet implementation that only supports currency.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class AnyCurrencyWallet implements Wallet {
    final HashMap<String, BigDecimal> amounts = new HashMap<>();

    @Override
    public boolean has(@NotNull Amount amount) {
        final Asset theAsset = amount.getAsset();
        if (theAsset instanceof AbstractCurrency) {
            final BigDecimal ourAmount = amounts.get(theAsset.getFQN());
            return (ourAmount != null) && ourAmount.compareTo(amount.getAmount()) >= 0;
        }
        return false;
    }

    @Override
    public void give(@NotNull Amount amount) throws AcceptError {
        final Asset theAsset = amount.getAsset();
        if (theAsset instanceof AbstractCurrency) {
            final String fqn = theAsset.getFQN();
            synchronized (amounts) {
                amounts.merge(fqn, amount.getAmount(), BigDecimal::add);
            }
            return;
        }
        throw new AcceptError(amount, "This wallet only supports currency.");
    }

    @Override
    public void set(@NotNull Amount amount) throws SetError {
        final Asset theAsset = amount.getAsset();
        if (theAsset instanceof AbstractCurrency) {
            final String fqn = theAsset.getFQN();
            synchronized (amounts) {
                amounts.put(fqn, amount.getAmount());
            }
            return;
        }
        throw new SetError(amount, "This wallet only supports currency.");
    }

    @Override
    public void take(@NotNull Amount amount) throws SupplyError {
        final Asset theAsset = amount.getAsset();
        if (theAsset instanceof AbstractCurrency) {
            final String fqn = theAsset.getFQN();
            synchronized (amounts) {
                final BigDecimal ourAmount = amounts.get(fqn);
                final BigDecimal theAmount = amount.getAmount();
                if (ourAmount == null || ourAmount.compareTo(theAmount) < 0) {
                    throw new SupplyError(amount, "Insufficient funds!");
                }
                amounts.merge(fqn, theAmount, BigDecimal::subtract);
            }
            return;
        }
        throw new SupplyError(amount, "This wallet only supports currency.");
    }

    @Override
    public @NotNull Optional<Amount> total(@NotNull Asset asset) {
        return Optional.of(asset)
                .filter(AbstractCurrency.class::isInstance)
                .map(AbstractCurrency.class::cast)
                .map(this::getAmountOfCurrency);
    }

    private Amount getAmountOfCurrency(AbstractCurrency currency) {
        final BigDecimal ourAmount = amounts.get(currency.getFQN());
        if (ourAmount != null) return currency.getAmount(ourAmount);
        return null;
    }
}
