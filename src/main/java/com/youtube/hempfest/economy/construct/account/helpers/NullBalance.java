package com.youtube.hempfest.economy.construct.account.helpers;

import com.youtube.hempfest.economy.construct.EconomyAction;
import com.youtube.hempfest.economy.construct.account.Balance;
import com.youtube.hempfest.economy.construct.entity.EconomyEntity;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * Null Object pattern -- return an object subclassing this type if no Balance
 * exists for an entity in a particular context. You will need to override
 * {@link #exists()} and {@link #exists(String)} based on the situation.
 */
public abstract class NullBalance extends Balance {

    protected NullBalance(EconomyEntity holder) {
        super(holder);
    }

    @Override
    public void setBalance(BigDecimal amount) {
    }

    @Override
    public void setBalance(BigDecimal amount, String world) {
    }

    @Override
    public @Nullable BigDecimal getBalance() {
        return null;
    }

    @Override
    public @Nullable BigDecimal getBalance(String world) {
        return null;
    }

    @Override
    public boolean has(BigDecimal amount) {
        return false;
    }

    @Override
    public boolean has(BigDecimal amount, String world) {
        return false;
    }

    @Override
    public EconomyAction deposit(BigDecimal amount) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction deposit(BigDecimal amount, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction withdraw(BigDecimal amount) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction withdraw(BigDecimal amount, String world) {
        return new EconomyAction(holder, false, "");
    }
}
