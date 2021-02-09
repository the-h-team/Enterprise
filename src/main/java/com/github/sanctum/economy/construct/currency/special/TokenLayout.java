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
package com.github.sanctum.economy.construct.currency.special;

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
