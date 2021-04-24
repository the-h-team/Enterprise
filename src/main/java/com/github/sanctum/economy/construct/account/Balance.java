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
package com.github.sanctum.economy.construct.account;

import com.github.sanctum.economy.construct.EconomyAction;
import com.github.sanctum.economy.construct.account.helpers.operation.Drawable;
import com.github.sanctum.economy.construct.account.helpers.operation.Payable;
import com.github.sanctum.economy.construct.entity.EconomyEntity;
import java.math.BigDecimal;
import org.jetbrains.annotations.Nullable;

/**
 * The base class for all Balances (Accounts and Wallets)
 */
public abstract class Balance implements Payable, Drawable {

	protected final EconomyEntity holder;

	protected Balance(EconomyEntity holder) {
		this.holder = holder;
	}

	/**
	 * Set this Balance in a general context. {@link #exists()} might or
	 * might not be referenced in an implementation; it is recommended
	 * but not required.
	 *
	 * @param amount New amount
	 * @return An economy action retaining information.
	 */
	public abstract EconomyAction setBalance(BigDecimal amount);

	/**
	 * Set this Balance in the specific World 'world' context.
	 * {@link #exists(String)} might or might not be referenced in an
	 * implementation; it is recommended but not required.
	 *
	 * @param amount New amount
	 * @param world  Name of world
	 * @return An economy action retaining information.
	 */
	public abstract EconomyAction setBalance(BigDecimal amount, String world);

	/**
	 * Check if this Balance exists in a general context. Realistically,
	 * this will return True for most Wallet implementations and thus
	 * will be of most use when working with Account subtypes.
	 * @return true if exists, false otherwise
	 */
	public abstract boolean exists();
	/**
	 * Check if this Balance exists in the specific World 'world' context.
	 * Realistically, this will return True for most Wallet implementations
	 * and thus will be of most use when working with Account subtypes.
	 * @param world Name of world
	 * @return true if exists, false otherwise
	 */
	public abstract boolean exists(String world);

	/**
	 * Get the value of this Balance object in a general context.
	 * @return value as a {@link BigDecimal} if present or null
	 */
	@Nullable
	public abstract BigDecimal getBalance();
	/**
	 * Get the value of this Balance object in the specific
	 * World 'world' context.
	 * @param world Name of world
	 * @return value as a {@link BigDecimal} if present or null
	 */
	@Nullable
	public abstract BigDecimal getBalance(String world);

	/**
	 * Test if this Balance is greater than or equal to an amount
	 * in a general context.
	 * @param amount amount to test
	 * @return true if balance >= to amount, false otherwise
	 */
	public abstract boolean has(BigDecimal amount);
	/**
	 * Test if this Balance is greater than or equal to an amount
	 * in the context of world 'world'.
	 * @param amount amount to test
	 * @return true if balance >= to amount, false otherwise
	 */
	public abstract boolean has(BigDecimal amount, String world);

	/**
	 * Get the EconomyEntity associated with this Balance.
	 * @return entity
	 */
	public EconomyEntity getHolder() {
		return holder;
	}

}
