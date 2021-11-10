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
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * A very simple wallet implementation which only supports one currency.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class SingleCurrencyWallet<T extends AbstractCurrency> extends AbstractWallet {
    final T asset;
    BigDecimal amount = BigDecimal.ZERO; // TODO: configurable default?

    protected SingleCurrencyWallet(@NotNull T currency) {
        this.asset = currency;
    }

    @Override
    public boolean has(@NotNull Amount amount) {
        if (amount.getAsset().equals(asset)) {
            return this.amount.compareTo(amount.getAmount()) >= 0;
        }
        return false;
    }

    @Override
    public void give(@NotNull Amount amount) throws AcceptError {
        if (amount.getAsset().equals(asset)) {
            this.amount = this.amount.add(amount.getAmount());
            return;
        }
        throw new AcceptError(amount, "This wallet does not support this asset!");
    }

    @Override
    public void set(@NotNull Amount amount) throws SetError {
        if (amount.getAsset().equals(asset)) {
            this.amount = amount.getAmount();
            return;
        }
        throw new SetError(amount, "This wallet does not support this asset!");
    }

    @Override
    public void take(@NotNull Amount amount) throws SupplyError {
        if (amount.getAsset().equals(asset)) {
            final BigDecimal theAmount = amount.getAmount();
            if (this.amount.compareTo(theAmount) < 0) {
                throw new SupplyError(amount, "Insufficient funds!");
            }
            this.amount = this.amount.subtract(theAmount);
            return;
        }
        throw new SupplyError(amount, "This wallet does not support this asset!");
    }

    @Override
    public @NotNull Optional<Amount> total(@NotNull Asset asset) {
        if (asset.equals(this.asset)) {
            return Optional.of(this.asset.getAmount(amount));
        }
        return Optional.empty();
    }
}
