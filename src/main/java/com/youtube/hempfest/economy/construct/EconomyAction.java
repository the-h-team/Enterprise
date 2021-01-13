package com.youtube.hempfest.economy.construct;

import com.youtube.hempfest.economy.construct.entity.EconomyEntity;
import com.youtube.hempfest.economy.construct.events.AsyncEconomyInfoEvent;
import com.youtube.hempfest.economy.construct.events.AsyncTransactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class EconomyAction {

	private static final Plugin PLUGIN = JavaPlugin.getProvidingPlugin(EconomyAction.class);
	private static final PluginManager PM = Bukkit.getPluginManager();

	private final BigDecimal amount;

	private final boolean success;

	private boolean logged = false;

	private final String info;

	private final EconomyEntity holder;

	public EconomyAction(BigDecimal amount, EconomyEntity holder, boolean success, String transactionInfo) {
		this.amount = amount;
		this.success = success;
		this.info = transactionInfo != null ? transactionInfo : "";
		this.holder = holder;
	}

	public EconomyAction(EconomyEntity holder, boolean success, String transactionInfo) {
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
	public EconomyEntity getActiveHolder() {
		return holder;
	}

	/**
	 * Gets the exact amount involved with the transaction
	 * @return amount or null
	 */
	@Nullable
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

	public EconomyAction log() {
		if (!logged) {
			final EconomyAction economyAction = this;
			new BukkitRunnable() {
				@Override
				public void run() {
					PM.callEvent(((amount != null) ? new AsyncTransactionEvent(economyAction) :
							new AsyncEconomyInfoEvent(economyAction)));
				}
			}.runTaskAsynchronously(PLUGIN);
			logged = true;
		}
		return this;
	}
}
