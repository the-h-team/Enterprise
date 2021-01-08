package com.youtube.hempfest.economy.construct.account;

import com.sun.istack.internal.Nullable;
import com.youtube.hempfest.economy.construct.entity.Entity;
import com.youtube.hempfest.economy.construct.EconomyAction;
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

	protected Balance(Entity holder, World world, BigDecimal balance) {
		this.holder = holder;
		this.worldUid = world.getUID();
		setBalance(balance);
	}

	@Nullable
	public World getWorld() {
		return Bukkit.getWorld(worldUid);
	}

	public abstract void setBalance(BigDecimal amount);

	@Nullable
	public abstract BigDecimal getBalance();

	public abstract boolean has(BigDecimal amount);

	public abstract EconomyAction deposit(BigDecimal amount);

	public abstract EconomyAction withdraw(BigDecimal amount);

	public Entity getHolder() {
		return holder;
	}

}
