package com.github.sanctum.economy.construct.currency.special;

import java.math.BigDecimal;
import org.bukkit.inventory.ItemStack;

public class ImmutableTokenCurrency implements TokenCurrency {

	private final TokenLayout system;

	public ImmutableTokenCurrency(TokenLayout system) {
		this.system = system;
	}


	@Override
	public BigDecimal getTokenWorth(int ordinal) {
		return system.ordinalMap.getOrDefault(ordinal, null);
	}

	@Override
	public double getTokenWorthDouble(int ordinal) {
		return system.ordinalMap.containsKey(ordinal) ? system.ordinalMap.get(ordinal).doubleValue() : 0;
	}

	@Override
	public BigDecimal getTokenWorth(ItemStack item) {
		return system.itemMap.getOrDefault(item, null);
	}

	@Override
	public double getTokenWorthDouble(ItemStack item) {
		return system.itemMap.containsKey(item) ? system.itemMap.get(item).doubleValue() : 0;
	}

}
