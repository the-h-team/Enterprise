package com.youtube.hempfest.economy.construct.account;

import com.youtube.hempfest.economy.construct.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The base class for all Balances (Accounts and Wallets)
 */
public abstract class Balance {

	protected final Entity holder;

	protected final UUID worldUid;

	protected BigDecimal balance;

	protected Balance(Entity holder, World world, BigDecimal balance) {
		this.holder = holder;
		this.worldUid = world.getUID();
		this.balance = balance;
	}

	public World getWorld() {
		return Bukkit.getWorld(worldUid);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public Entity getHolder() {
		return holder;
	}

}
