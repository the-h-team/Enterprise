package com.youtube.hempfest.economy.construct.account.helpers;

import com.youtube.hempfest.economy.construct.entity.EconomyEntity;

/**
 * Null Object pattern -- return this type if no Wallet
 * exists for an entity in a particular context
 */
public abstract class NullWallet extends NullBalance {

    protected NullWallet(EconomyEntity holder) {
        super(holder);
    }

}
