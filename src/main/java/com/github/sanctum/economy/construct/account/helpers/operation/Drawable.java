package com.github.sanctum.economy.construct.account.helpers.operation;

import com.github.sanctum.economy.construct.EconomyAction;

import java.math.BigDecimal;

public interface Drawable {
    /**
     * Attempt to withdraw an amount
     * @param amount {@link BigDecimal} amount
     */
    EconomyAction withdraw(BigDecimal amount);
    /**
     * Attempt to withdraw an amount in the world 'world'
     * @param amount {@link BigDecimal} amount
     * @param world Name of world
     */
    EconomyAction withdraw(BigDecimal amount, String world);
}
