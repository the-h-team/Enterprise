package com.youtube.hempfest.economy.construct;

import com.sun.istack.internal.Nullable;
import com.youtube.hempfest.economy.construct.account.Account;
import com.youtube.hempfest.economy.construct.account.Wallet;
import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import com.youtube.hempfest.economy.construct.currency.EconomyCurrency;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public interface AdvancedEconomy {

	Plugin getPlugin();

	String getVersion();

	EconomyCurrency getCurrency();

	EconomyCurrency getCurrency(String world);

	EconomyPriority getPriority();

	String format(double amount);

	String format(double amount, Locale locale);

	double getMaxWalletSize();

	boolean isMultiWorld();

	boolean isMultiCurrency();

	boolean isAccountsLimited();

	boolean hasMultiAccountSupport();

	boolean hasWalletSizeLimit();

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	default boolean hasWalletAccount(String name) {
		return getWallet(name) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	default boolean hasWalletAccount(String name, String world) {
		return getWallet(name, world) != null;
	}

	default boolean hasWalletAccount(OfflinePlayer player) {
		return getWallet(player) != null;
	}

	default boolean hasWalletAccount(OfflinePlayer player, String world) {
		return getWallet(player, world) != null;
	}

	default boolean hasWalletAccount(UUID uuid) {
		return getWallet(uuid) != null;
	}

	default boolean hasWalletAccount(UUID uuid, String world) {
		return getWallet(uuid, world) != null;
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

	default boolean hasAccount(OfflinePlayer player, String world) {
		return getAccount(player, world) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	default boolean hasAccount(String name, String world) {
		return getAccount(name, world) != null;
	}

	default boolean hasAccount(UUID uuid) {
		return getAccount(uuid) != null;
	}

	default boolean hasAccount(UUID uuid, String world) {
		return getAccount(uuid, world) != null;
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
	Account getAccount(String name, String world);

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
	Account getAccount(String name, AccountType type, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Account getAccount(String accountId, String name, String world);

	@Nullable
	Account getAccount(OfflinePlayer player, AccountType type);

	@Nullable
	Account getAccount(OfflinePlayer player, AccountType type, String world);

	@Nullable
	Account getAccount(OfflinePlayer player);

	@Nullable
	Account getAccount(OfflinePlayer player, String world);

	@Nullable
	Account getAccount(String accountId, OfflinePlayer player, String world);

	@Nullable
	Account getAccount(UUID uuid);

	@Nullable
	Account getAccount(UUID uuid, String world);

	@Nullable
	Account getAccount(UUID uuid, AccountType type);

	@Nullable
	Account getAccount(UUID uuid, AccountType type, String world);

	@Nullable
	Account getAccount(String accountId, UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Wallet getWallet(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	Wallet getWallet(String name, String world);

	@Nullable
	Wallet getWallet(OfflinePlayer player);

	@Nullable
	Wallet getWallet(OfflinePlayer player, String world);

	@Nullable
	Wallet getWallet(UUID uuid);

	@Nullable
	Wallet getWallet(UUID uuid, String world);

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
	EconomyAction createAccount(AccountType type, String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId, String world, double amount);

	EconomyAction createAccount(AccountType type, OfflinePlayer player);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, double amount);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world, double amount);

	EconomyAction createAccount(AccountType type, UUID uuid);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId);

	EconomyAction createAccount(AccountType type, UUID uuid, double amount);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world, double amount);

	EconomyAction deleteWalletAccount(Wallet wallet);

	EconomyAction deleteAccount(String accountID); // double-check these types of methods

	EconomyAction deleteAccount(String accountID, String world); // ^^

	EconomyAction deleteAccount(Account account);

	List<Account> getAccounts();

	List<String> getAccountList();

}
