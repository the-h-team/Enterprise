package com.youtube.hempfest.economy.construct.currency.special;

import java.math.BigDecimal;
import org.bukkit.inventory.ItemStack;

public class ImmutableTokenCurrency implements TokenCurrency {

	private final TokenLayout system;

	public ImmutableTokenCurrency(TokenLayout system) {
		this.system = system;
	}


	@Override
	public BigDecimal getTokenWorth(int ordinal) {
		return system.ordinalMap.get(ordinal);
	}

	@Override
	public double getTokenWorthDouble(int ordinal) {
		return system.ordinalMap.get(ordinal).doubleValue();
	}

	@Override
	public BigDecimal getTokenWorth(ItemStack item) {
		return system.itemMap.get(item);
	}

	@Override
	public double getTokenWorthDouble(ItemStack item) {
		return system.itemMap.get(item).doubleValue();
	}

}
