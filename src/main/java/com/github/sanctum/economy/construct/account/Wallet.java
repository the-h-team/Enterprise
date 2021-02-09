package com.github.sanctum.economy.construct.account;

import com.github.sanctum.economy.construct.entity.EconomyEntity;

/**
 * The abstract base for all Wallets
 */
public abstract class Wallet extends Balance {
    protected Wallet(EconomyEntity holder) {
        super(holder);
    }
}
