package com.youtube.hempfest.economy.construct.implement;

import com.youtube.hempfest.economy.construct.EconomyAction;
import com.youtube.hempfest.economy.construct.EconomyPriority;
import com.youtube.hempfest.economy.construct.account.Account;
import com.youtube.hempfest.economy.construct.account.Wallet;
import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import com.youtube.hempfest.economy.construct.currency.normal.EconomyCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public interface AdvancedEconomy {

	Plugin getPlugin();

	String getVersion();

	EconomyCurrency getCurrency();

	EconomyCurrency getCurrency(String world);

	EconomyPriority getPriority();

	String format(BigDecimal amount);

	String format(BigDecimal amount, Locale locale);

	BigDecimal getMaxWalletSize();

	boolean isMultiWorld();

	boolean isMultiCurrency();

	boolean hasMultiAccountSupport();

	boolean hasWalletSizeLimit();

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	default boolean hasWalletAccount(String name) {
		return getWallet(name) != null;
	}

	default boolean hasWalletAccount(OfflinePlayer player) {
		return getWallet(player) != null;
	}

	default boolean hasWalletAccount(UUID uuid) {
		return getWallet(uuid) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	default boolean hasAccount(String name) {
		return getAccount(name) != null;
	}

	default boolean hasAccount(OfflinePlayer player) {
		return getAccount(player) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	default boolean hasAccount(String accountId, String name) {
		return getAccount(accountId, name) != null;
	}

	default boolean hasAccount(UUID uuid) {
		return getAccount(uuid) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Account getAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Account getAccount(String name, AccountType type);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Account getAccount(String accountId, String name);

	@Nullable
	Account getAccount(OfflinePlayer player, AccountType type);

	@Nullable
	Account getAccount(OfflinePlayer player);

	@Nullable
	Account getAccount(String accountId, OfflinePlayer player);

	@Nullable
	Account getAccount(UUID uuid);

	@Nullable
	Account getAccount(UUID uuid, AccountType type);

	@Nullable
	Account getAccount(String accountId, UUID uuid);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Wallet getWallet(String name);

	@Nullable
	Wallet getWallet(OfflinePlayer player);

	@Nullable
	Wallet getWallet(UUID uuid);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, BigDecimal startingAmount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId, String world, BigDecimal startingAmount);

	EconomyAction createAccount(AccountType type, OfflinePlayer player);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, BigDecimal startingAmount);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world, BigDecimal startingAmount);

	EconomyAction createAccount(AccountType type, UUID uuid);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId);

	EconomyAction createAccount(AccountType type, UUID uuid, BigDecimal startingAmount);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world, BigDecimal startingAmount);

	EconomyAction deleteWalletAccount(Wallet wallet);

	EconomyAction deleteWalletAccount(Wallet wallet, String world);

	EconomyAction deleteAccount(String accountID); // double-check these types of methods

	EconomyAction deleteAccount(String accountID, String world); // ^^

	EconomyAction deleteAccount(Account account);

	EconomyAction deleteAccount(Account account, String world);

	List<Account> getAccounts();

	List<String> getAccountList();

}
