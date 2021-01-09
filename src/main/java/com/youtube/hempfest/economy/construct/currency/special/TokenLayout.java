package com.youtube.hempfest.economy.construct.currency.special;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class TokenLayout {

	protected final Map<Integer, BigDecimal> ordinalMap = new HashMap<>();

	protected final Map<ItemStack, BigDecimal> itemMap = new HashMap<>();

	protected TokenLayout() {}

	/**
	 * If you have your own way of defining item types or use digital currency
	 * the ordinal map may be your selection.
	 * @param ordinal the delimiter for the worth
	 * @param worth the worth of the delimiter
	 * @return A token currency layout
	 */
	public TokenLayout setWorth(int ordinal, BigDecimal worth) {
		this.ordinalMap.put(ordinal, worth);
		return this;
	}

	/**
	 * Here this method is used if you depend on a specific item for currency
	 * @param item The item to appraise
	 * @param worth The item's worth
	 * @return A token currency layout
	 */
	public TokenLayout setWorth(ItemStack item, BigDecimal worth) {
		this.itemMap.put(item, worth);
		return this;
	}

	public ImmutableTokenCurrency toSystem() {
		return new ImmutableTokenCurrency(this);
	}

}
