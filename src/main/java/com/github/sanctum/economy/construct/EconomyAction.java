/*
 *  Copyright 2021 Sanctum <https://github.com/the-h-team>
 *  Copyright 2020 Hempfest <https://github.com/Hempfest>
 *  Copyright 2020 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of Enterprise.
 *
 *  Enterprise is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  Enterprise is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.sanctum.economy.construct;

import com.github.sanctum.economy.construct.entity.EconomyEntity;
import com.github.sanctum.economy.construct.events.AsyncEconomyInfoEvent;
import com.github.sanctum.economy.construct.events.AsyncTransactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

/**
 * An action. May constitute a transaction or simply provide information.
 */
public class EconomyAction {

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
	 * @return The returned information from the constructor - empty if absent
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Fluid-interface method to enable log for this EconomyAction.
	 * @return this EconomyAction
	 */
	public EconomyAction log() {
		if (!logged) {
			final EconomyAction economyAction = this;
			new BukkitRunnable() {
				@Override
				public void run() {
					PM.callEvent(((amount != null) ? new AsyncTransactionEvent(economyAction) :
							new AsyncEconomyInfoEvent(economyAction)));
				}
			}.runTaskAsynchronously(JavaPlugin.getProvidingPlugin(EconomyAction.class));
			logged = true;
		}
		return this;
	}
}
