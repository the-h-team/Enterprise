package com.youtube.hempfest.economy.construct.account;

import com.youtube.hempfest.economy.construct.entity.Entity;
import org.bukkit.World;

import java.math.BigDecimal;

/**
 * The abstract base for all Wallets
 */
public abstract class Wallet extends Balance {
    protected Wallet(Entity holder, World world, BigDecimal balance) {
        super(holder, world, balance);
    }
}
