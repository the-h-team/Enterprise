package com.youtube.hempfest.economy.construct.internal;

import com.youtube.hempfest.economy.construct.entity.Entity;

import java.math.BigDecimal;

public class EconomyAction {

	private final BigDecimal amount;

	private final boolean success;

	private final String info;

	private final Entity holder;

	public EconomyAction(BigDecimal amount, Entity holder, boolean success, String transactionInfo) {
		this.amount = amount;
		this.success = success;
		this.info = transactionInfo != null ? transactionInfo : "";
		this.holder = holder;
	}

	public EconomyAction(Entity holder, boolean success, String transactionInfo) {
		this(null, holder, success, transactionInfo);
	}

	/**
	 * Get the end result of the transaction
	 * @return false is transaction failed
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Get the entity involved with the transaction
	 * @return holder in transaction
	 */
	public Entity getActiveHolder() {
		return holder;
	}

	/**
	 * Gets the exact amount involved with the transaction
	 * @return amount or null
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Gets the transaction result information.
	 * @return The returned information from the constructor -
	 * empty if absent
	 */
	public String getInfo() {
		return info;
	}
}
