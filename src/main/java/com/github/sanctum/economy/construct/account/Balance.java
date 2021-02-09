package com.github.sanctum.economy.construct.account;

import com.github.sanctum.economy.construct.account.helpers.operation.Payable;
import com.github.sanctum.economy.construct.account.helpers.operation.Drawable;
import com.github.sanctum.economy.construct.entity.EconomyEntity;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

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
	 * @param amount New amount
	 */
	public abstract void setBalance(BigDecimal amount);
	/**
	 * Set this Balance in the specific World 'world' context.
	 * {@link #exists(String)} might or might not be referenced in an
	 * implementation; it is recommended but not required.
	 * @param amount New amount
	 * @param world Name of world
	 */
	public abstract void setBalance(BigDecimal amount, String world);

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
