package com.youtube.hempfest.economy.construct;

import com.youtube.hempfest.economy.construct.account.Account;
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
	boolean hasWalletAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasWalletAccount(String name, String world);

	boolean hasWalletAccount(OfflinePlayer player);

	boolean hasWalletAccount(OfflinePlayer player, String world);

	boolean hasWalletAccount(UUID uuid);

	boolean hasWalletAccount(UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasAccount(String name);

	boolean hasAccount(OfflinePlayer player);

	boolean hasAccount(OfflinePlayer player, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasAccount(String name, String world);

	boolean hasAccount(UUID uuid);

	boolean hasAccount(UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean walletHasAmount(String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean walletHasAmount(String name, String world, double amount);

	boolean walletHasAmount(OfflinePlayer player, double amount);

	boolean walletHasAmount(OfflinePlayer player, String world, double amount);

	boolean walletHasAmount(UUID uuid, double amount);

	boolean walletHasAmount(UUID uuid, String world, double amount);

	boolean accountHasAmount(String accountID, double amount);

	boolean accountHasAmount(Account account, double amount);

	boolean accountHasAmount(Account account, String world, double amount);

	boolean accountHasAmount(String accountID, String world, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	double getWalletBalance(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	double getWalletBalance(String name, String world);

	double getWalletBalance(OfflinePlayer player);

	double getWalletBalance(OfflinePlayer player, String world);

	double getWalletBalance(UUID uuid);

	double getWalletBalance(UUID uuid, String world);

	double getAccountBalance(String accountID);

	double getAccountBalance(String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, AccountType type);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, AccountType type, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String accountId, String name, String world);

	Account getAccount(OfflinePlayer player, AccountType type);

	Account getAccount(OfflinePlayer player, AccountType type, String world);

	Account getAccount(OfflinePlayer player);

	Account getAccount(OfflinePlayer player, String world);

	Account getAccount(String accountId, OfflinePlayer player, String world);

	Account getAccount(UUID uuid);

	Account getAccount(UUID uuid, String world);

	Account getAccount(UUID uuid, AccountType type);

	Account getAccount(UUID uuid, AccountType type, String world);

	Account getAccount(String accountId, UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletWithdraw(String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletWithdraw(String name, String world, double amount);

	EconomyAction walletWithdraw(OfflinePlayer player, double amount);

	EconomyAction walletWithdraw(OfflinePlayer player, String world, double amount);

	EconomyAction walletWithdraw(UUID uuid, double amount);

	EconomyAction walletWithdraw(UUID uuid, String world, double amount);

	EconomyAction accountWithdraw(Account account, double amount);

	EconomyAction accountWithdraw(Account account, String world, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletDeposit(String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletDeposit(String name, String world, double amount);

	EconomyAction walletDeposit(OfflinePlayer player, double amount);

	EconomyAction walletDeposit(OfflinePlayer player, String world, double amount);

	EconomyAction walletDeposit(UUID uuid, double amount);

	EconomyAction walletDeposit(UUID uuid, String world, double amount);

	EconomyAction accountDeposit(Account account, double amount);

	EconomyAction accountDeposit(Account account, String world, double amount);

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

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction deleteWalletAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction deleteWalletAccount(String name, String world);

	EconomyAction deleteWalletAccount(OfflinePlayer player);

	EconomyAction deleteWalletAccount(OfflinePlayer player, String world);

	EconomyAction deleteWalletAccount(UUID uuid);

	EconomyAction deleteWalletAccount(UUID uuid, String world);

	EconomyAction deleteAccount(String accountID);

	EconomyAction deleteAccount(String accountID, String world);

	EconomyAction deleteAccount(Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountOwner(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountOwner(String name, Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountOwner(String name, String accountID, String world);

	EconomyAction isAccountOwner(OfflinePlayer player, String accountID);

	EconomyAction isAccountOwner(OfflinePlayer player, Account account);

	EconomyAction isAccountOwner(OfflinePlayer player, String accountID, String world);

	EconomyAction isAccountOwner(UUID uuid, String accountID);

	EconomyAction isAccountOwner(UUID uuid, Account account);

	EconomyAction isAccountOwner(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountMember(String name, Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountMember(String name, String accountID, String world);

	EconomyAction isAccountMember(OfflinePlayer player, String accountID);

	EconomyAction isAccountMember(OfflinePlayer player, Account account);

	EconomyAction isAccountMember(OfflinePlayer player, String accountID, String world);

	EconomyAction isAccountMember(UUID uuid, String accountID);

	EconomyAction isAccountMember(UUID uuid, Account account);

	EconomyAction isAccountMember(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction addAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction addAccountMember(String name, String accountID, String world);

	EconomyAction addAccountMember(OfflinePlayer player, String accountID);

	EconomyAction addAccountMember(OfflinePlayer player, String accountID, String world);

	EconomyAction addAccountMember(UUID uuid, String accountID);

	EconomyAction addAccountMember(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction removeAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction removeAccountMember(String name, String accountID, String world);

	EconomyAction removeAccountMember(OfflinePlayer player, String accountID);

	EconomyAction removeAccountMember(OfflinePlayer player, String accountID, String world);

	EconomyAction removeAccountMember(UUID uuid, String accountID);

	EconomyAction removeAccountMember(UUID uuid, String accountID, String world);

	List<Account> getAccounts();

	List<String> getAccountList();

}
