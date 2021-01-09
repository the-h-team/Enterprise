package com.youtube.hempfest.economy.construct.account;

import com.youtube.hempfest.economy.construct.entity.EconomyEntity;
import com.youtube.hempfest.economy.construct.EconomyAction;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * The base class for all Balances (Accounts and Wallets)
 */
public abstract class Balance {

	protected final EconomyEntity holder;

	protected Balance(EconomyEntity holder) {
		this.holder = holder;
	}

	public abstract void setBalance(BigDecimal amount);
	public abstract void setBalance(BigDecimal amount, String world);

	@Nullable
	public abstract BigDecimal getBalance();
	@Nullable
	public abstract BigDecimal getBalance(String world);

	public abstract boolean has(BigDecimal amount);
	public abstract boolean has(BigDecimal amount, String world);

	public abstract EconomyAction deposit(BigDecimal amount);
	public abstract EconomyAction deposit(BigDecimal amount, String world);

	public abstract EconomyAction withdraw(BigDecimal amount);
	public abstract EconomyAction withdraw(BigDecimal amount, String world);

	public EconomyEntity getHolder() {
		return holder;
	}

}
