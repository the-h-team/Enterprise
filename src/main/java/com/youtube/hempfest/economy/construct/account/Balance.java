package com.youtube.hempfest.economy.construct.account;

import org.bukkit.NamespacedKey;

public class Balance {


	private String holder;

	private NamespacedKey key;

	private final String world;

	private final double balance;

	protected Balance(String holder, String world, double balance) {
		this.holder = holder;
		this.world = world;
		this.balance = balance;
	}

	protected Balance(NamespacedKey holder, String world, double balance) {
		this.key = holder;
		this.world = world;
		this.balance = balance;
	}

	public String getWorld() {
		return world;
	}

	public double getBalance() {
		return balance;
	}

	public String getHolder() {
		return holder;
	}

	public NamespacedKey getNPC() {
		return key;
	}

}
