package com.youtube.hempfest.economy.construct.currency.special;

import java.math.BigDecimal;
import org.bukkit.inventory.ItemStack;

public interface TokenCurrency {

	BigDecimal getTokenWorth(int ordinal);

	double getTokenWorthDouble(int ordinal);

	BigDecimal getTokenWorth(ItemStack item);

	double getTokenWorthDouble(ItemStack item);

	static TokenLayout getCurrencyLayoutBuilder() {
		return new TokenLayout();
	}

}
