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
