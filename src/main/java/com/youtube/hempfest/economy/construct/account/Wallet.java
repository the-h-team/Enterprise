package com.youtube.hempfest.economy.construct.account;

import com.youtube.hempfest.economy.construct.entity.EconomyEntity;

/**
 * The abstract base for all Wallets
 */
public abstract class Wallet extends Balance {
    protected Wallet(EconomyEntity holder) {
        super(holder);
    }
}
