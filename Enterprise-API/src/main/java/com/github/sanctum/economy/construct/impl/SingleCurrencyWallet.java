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
 */
public class SingleCurrencyWallet<T extends AbstractCurrency> extends AbstractWallet {
    final T asset;
    BigDecimal amount;

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
    public void give(@NotNull Amount amount) {
        if (amount.getAsset().equals(asset)) {
            this.amount = this.amount.add(amount.getAmount());
        }
        // TODO: throw on wrong asset
    }

    @Override
    public void set(@NotNull Amount amount) {
        if (amount.getAsset().equals(asset)) {
            this.amount = amount.getAmount();
        }
        // TODO: throw on wrong asset
    }

    @Override
    public void take(@NotNull Amount amount) {
        if (amount.getAsset().equals(asset)) {
            this.amount = this.amount.add(amount.getAmount());
        }
        // TODO: throw on wrong asset
    }

    @Override
    public @NotNull Optional<Amount> total(@NotNull Asset asset) {
        if (asset.equals(this.asset)) {
            return Optional.of(this.asset.getAmount(amount));
        }
        return Optional.empty();
    }
}
