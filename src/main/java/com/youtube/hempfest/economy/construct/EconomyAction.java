package com.youtube.hempfest.economy.construct;

public class EconomyAction {

	private double amount;

	private final boolean success;

	private final String info;

	private final String holder;

	public EconomyAction(double amount, String holder, boolean success, String transactionInfo) {
		this.amount = amount;
		this.success = success;
		this.info = transactionInfo != null ? transactionInfo : "";
		this.holder = holder;
	}

	public EconomyAction(String holder, boolean success, String transactionInfo) {
		this.success = success;
		this.info = transactionInfo != null ? transactionInfo : "";
		this.holder = holder;
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
	public String getActiveHolder() {
		return holder;
	}

	/**
	 * Gets the exact amount involved with the transaction
	 * @return amount or null
	 */
	public double getAmount() {
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
