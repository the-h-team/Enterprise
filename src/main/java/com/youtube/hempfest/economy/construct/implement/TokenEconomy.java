package com.youtube.hempfest.economy.construct.implement;

import com.youtube.hempfest.economy.construct.EconomyAction;
import com.youtube.hempfest.economy.construct.EconomyPriority;
import com.youtube.hempfest.economy.construct.account.Account;
import com.youtube.hempfest.economy.construct.account.Wallet;
import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import com.youtube.hempfest.economy.construct.currency.special.TokenCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public interface TokenEconomy {

	Plugin getPlugin();

	String getVersion();

	EconomyPriority getPriority();

	TokenCurrency getCurrency();

	TokenCurrency getCurrency(String world);

	String format(BigDecimal amount);

	BigDecimal getMaxWalletSize();

	boolean isMultiWorld();

	boolean isMultiCurrency();

	boolean hasMultiAccountSupport();

	boolean hasWalletSizeLimit();

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasAccount(String name, String world);

	boolean hasAccount(OfflinePlayer player);

	boolean hasAccount(OfflinePlayer player, String world);

	boolean hasAccount(UUID uuid);

	boolean hasAccount(UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasWallet(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasWallet(String name, String world);

	boolean hasWallet(OfflinePlayer player);

	boolean hasWallet(OfflinePlayer player, String world);

	boolean hasWallet(UUID uuid);

	boolean hasWallet(UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Wallet getWallet(String name);

	Wallet getWallet(OfflinePlayer player);

	Wallet getWallet(UUID uuid);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, AccountType type);

	Account getAccount(OfflinePlayer player);

	Account getAccount(OfflinePlayer player, AccountType type);

	Account getAccount(UUID uuid);

	Account getAccount(UUID uuid, AccountType type);

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