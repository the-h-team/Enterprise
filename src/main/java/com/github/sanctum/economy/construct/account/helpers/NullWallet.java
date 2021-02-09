package com.github.sanctum.economy.construct.account.helpers;

import com.github.sanctum.economy.construct.entity.EconomyEntity;

/**
 * Null Object pattern -- return an object subclassing this type if no Wallet
 * exists for an entity in a particular context. You will need to override
 * {@link #exists()} and {@link #exists(String)} based on the situation.
 */
public abstract class NullWallet extends NullBalance {

    protected NullWallet(EconomyEntity holder) {
        super(holder);
    }

}
