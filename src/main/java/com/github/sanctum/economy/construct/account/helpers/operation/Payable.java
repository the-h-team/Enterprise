package com.github.sanctum.economy.construct.account.helpers.operation;

import com.github.sanctum.economy.construct.EconomyAction;

import java.math.BigDecimal;

public interface Payable {
    /**
     * Attempt to deposit an amount
     * @param amount {@link BigDecimal} amount
     */
    EconomyAction deposit(BigDecimal amount);
    /**
     * Attempt to deposit an amount in the world 'world'
     * @param amount {@link BigDecimal} amount
     * @param world Name of world
     */
    EconomyAction deposit(BigDecimal amount, String world);
}
