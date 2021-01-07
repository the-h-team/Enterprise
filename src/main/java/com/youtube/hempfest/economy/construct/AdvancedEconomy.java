package com.youtube.hempfest.economy.construct;

import com.sun.istack.internal.Nullable;
import com.youtube.hempfest.economy.construct.account.Account;
import com.youtube.hempfest.economy.construct.account.Wallet;
import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import com.youtube.hempfest.economy.construct.currency.EconomyCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.youtube.hempfest.economy.construct.entity.NpcEntity;
import com.youtube.hempfest.economy.construct.internal.EconomyAction;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class AdvancedEconomy {

	public abstract Plugin getPlugin();

	public abstract String getVersion();

	public abstract EconomyCurrency getCurrency();

	public abstract EconomyCurrency getCurrency(String world);

	public abstract EconomyPriority getPriority();

	public abstract String format(BigDecimal amount);

	public abstract String format(BigDecimal amount, Locale locale);

	public abstract BigDecimal getMaxWalletSize();

	public abstract boolean isMultiWorld();

	public abstract boolean isMultiCurrency();

	public abstract boolean isAccountsLimited();

	public abstract boolean hasMultiAccountSupport();

	public abstract boolean hasWalletSizeLimit();

	public abstract EconomyInternals backing();

	public interface EconomyInternals {
		@Nullable
		Wallet getWallet(String string);
		@Nullable
		Wallet getWallet(String string, String world);
		@Nullable
		Wallet getWallet(OfflinePlayer player);
		@Nullable
		Wallet getWallet(OfflinePlayer player, World world);
		@Nullable
		Wallet getWallet(OfflinePlayer player, String world);
		@Nullable
		@Deprecated
		default Account getAccount(String name) { // default/npc
			return null;
		}
		@Nullable
		Account getAccount(OfflinePlayer player);
		@Nullable
		Account getAccount(OfflinePlayer player, World world);
		@Nullable
		Account getAccount(OfflinePlayer player, String world);
		@Nullable
		@Deprecated
		default Account getAccount(String name, String world) { // default/npc
			return null;
		}
		@Nullable
		Account getAccount(UUID uuid);
		@Nullable
		Account getAccount(UUID uuid, String world);
		@Nullable
		Wallet getWallet(NpcEntity npc, String world);
		@Nullable
		Wallet getWallet(UUID uuid);
		@Nullable
		Wallet getWallet(UUID uuid, String world);
		@Nullable
		Account getAccountByType(String name, AccountType type);
		@Nullable
		Account getAccountByType(String name, AccountType type, String world);
		@Nullable
		Account getAccountByType(OfflinePlayer player, AccountType type);
		@Nullable
		Account getAccountByType(OfflinePlayer player, AccountType type, String world);
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public boolean hasWalletAccount(String name) {
		return backing().getWallet(name) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public boolean hasWalletAccount(String name, String world) {
		return backing().getWallet(name, world) != null;
	}

	/**
	 * Method meant for online players, use for instances
	 * where it makes sense to allow economy to infer/detect world
	 * automatically
	 * @param onlinePlayer a {@link Player} object
	 */
	public boolean hasWalletAccountDetectWorld(Player onlinePlayer) {
		return hasWalletAccount(onlinePlayer, onlinePlayer.getWorld());
	}

	public boolean hasWalletAccount(OfflinePlayer player) {
		return backing().getWallet(player) != null;
	}

	public boolean hasWalletAccount(OfflinePlayer player, World world) {
		return backing().getWallet(player, world) != null;
	}

	public boolean hasWalletAccount(OfflinePlayer player, String worldName) {
		return backing().getWallet(player, worldName) != null;
	}

	// removed UUID methods in favor of player/NPC rails

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public boolean hasAccount(String name) {
		return backing().getAccount(name) != null;
	}

	/**
	 * Method meant for online players, use for instances
	 * where it makes sense to allow economy to infer/detect world
	 * automatically
	 * @param onlinePlayer a {@link Player} object
	 */
	public boolean hasAccountDetectWorld(Player onlinePlayer) {
		return backing().getAccount(onlinePlayer, onlinePlayer.getWorld()) != null;
	}

	public boolean hasAccount(OfflinePlayer player) {
		return backing().getAccount(player) != null;
	}

	public boolean hasAccount(OfflinePlayer player, World world) {
		return backing().getAccount(player, world) != null;
	}

	public boolean hasAccount(OfflinePlayer player, String world) {
		return backing().getAccount(player, world) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public boolean hasAccount(String name, String world) {
		return backing().getAccount(name, world) != null;
	}

	public boolean hasAccount(UUID uuid) {
		return backing().getAccount(uuid) != null;
	}

	public boolean hasAccount(UUID uuid, String world) {
		return backing().getAccount(uuid, world) != null;
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public boolean walletHasAmount(String name, BigDecimal amount) {
		final Wallet wallet = backing().getWallet(name);
		if (wallet == null) return false;
		return wallet.getBalance().compareTo(amount) >= 0;
	}

	public boolean walletHasAmount(Plugin plugin, String name, String world, BigDecimal amount) {
		final Wallet wallet = backing().getWallet(new NpcEntity(plugin, name), world);
		if (wallet == null) return false;
		return wallet.getBalance().compareTo(amount) >= 0;
	}

	public boolean walletHasAmount(OfflinePlayer player, BigDecimal amount) {
		final Wallet wallet = backing().getWallet(player);
		if (wallet == null) return false;
		return wallet.getBalance().compareTo(amount) >= 0;
	}

	public boolean walletHasAmount(OfflinePlayer player, String world, BigDecimal amount) {
		final Wallet wallet = backing().getWallet(player, world);
		if (wallet == null) return false;
		return wallet.getBalance().compareTo(amount) >= 0;
	}

	public boolean walletHasAmount(UUID uuid, BigDecimal amount) {
		final Wallet wallet = backing().getWallet(uuid);
		if (wallet == null) return false;
		return wallet.getBalance().compareTo(amount) >= 0;
	}

	public boolean walletHasAmount(UUID uuid, String world, BigDecimal amount) {
		final Wallet wallet = backing().getWallet(uuid, world);
		if (wallet == null) return false;
		return wallet.getBalance().compareTo(amount) >= 0;
	}

/*	public boolean accountHasAmount(String accountID, BigDecimal amount) { TODO: refactor in favor of getById mechanism
		final Account account = backing().getAccount(accountID, world);
		if (account == null) return false;
		return account.getBalance().compareTo(amount) >= 0;
	}*/

/*	public boolean accountHasAmount(Account account, BigDecimal amount) { // they can just use Account#getBalance, right?
		return false;
	}

	public boolean accountHasAmount(Account account, String world, BigDecimal amount) {
		return false;
	}*/

/*	public boolean accountHasAmount(String accountID, String world, BigDecimal amount) { // TODO: refactor in favor of getById
		return false;
	}*/

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	public BigDecimal getWalletBalance(String name) {
		final Wallet wallet = backing().getWallet(name);
		if (wallet == null) return null;
		return wallet.getBalance();
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Nullable
	@Deprecated
	public BigDecimal getWalletBalance(String name, String world) {
		final Wallet wallet = backing().getWallet(name, world);
		if (wallet == null) return null;
		return wallet.getBalance();
	}

	@Nullable
	public BigDecimal getWalletBalance(OfflinePlayer player) {
		final Wallet wallet = backing().getWallet(player);
		if (wallet == null) return null;
		return wallet.getBalance();
	}

	@Nullable
	public BigDecimal getWalletBalance(OfflinePlayer player, String world) {
		final Wallet wallet = backing().getWallet(player, world);
		if (wallet == null) return null;
		return wallet.getBalance();
	}

	@Nullable
	public BigDecimal getWalletBalance(UUID uuid){
		final Wallet wallet = backing().getWallet(uuid);
		if (wallet == null) return null;
		return wallet.getBalance();
	}

	@Nullable
	public BigDecimal getWalletBalance(UUID uuid, String world) {
		final Wallet wallet = backing().getWallet(uuid, world);
		if (wallet == null) return null;
		return wallet.getBalance();
	}

/*	@Nullable // TODO: Refactor in getter for Account by id
	public abstract BigDecimal getAccountBalance(String accountID);

	@Nullable
	public abstract BigDecimal getAccountBalance(String accountID, String world);*/

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public Account getAccount(String name) {
		return backing().getAccount(name);
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public Account getAccount(String name, String world) {
		return backing().getAccount(name, world);
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public Account getAccount(String name, AccountType type) {
		return backing().getAccountByType(name, type);
	}

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public Account getAccount(String name, AccountType type, String world) {
		return backing().getAccountByType(name, type, world);
	}

//	/**
//	 * @deprecated String method dedicated to system/npc
//	 */
//	@Deprecated
//	public abstract Account getAccount(String accountId, String name, String world); // TODO: Replace with getter for Account by id

	public Account getAccount(OfflinePlayer player, AccountType type) {
		return backing().getAccountByType(player, type);
	}

	public Account getAccount(OfflinePlayer player, AccountType type, String world) {
		return null;
	}

	public Account getAccount(OfflinePlayer player) {
		return null;
	}

	public Account getAccount(OfflinePlayer player, String world) {
		return null;
	}

	public Account getAccount(String accountId, OfflinePlayer player, String world) {
		return null;
	}

/*	public Account getAccount(UUID uuid) { // prevent misuse of this method by forcing world+type reference
		return null;
	}

	public Account getAccount(UUID uuid, String world) {
		return null;
	}

	public Account getAccount(UUID uuid, AccountType type) {
		return null;
	}*/

	public Account getAccount(UUID uuid, AccountType type, String world) {
		return null;
	}

//	public Account getAccount(String accountId, UUID uuid, String world); TODO: Refactor to getter by accountId

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction walletWithdraw(String name, BigDecimal amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction walletWithdraw(String name, String world, BigDecimal amount);

	public abstract EconomyAction walletWithdraw(OfflinePlayer player, BigDecimal amount);

	public abstract EconomyAction walletWithdraw(OfflinePlayer player, String world, BigDecimal amount);

	public abstract EconomyAction walletWithdraw(UUID uuid, BigDecimal amount);

	public abstract EconomyAction walletWithdraw(UUID uuid, String world, BigDecimal amount);

	public abstract EconomyAction accountWithdraw(Account account, BigDecimal amount);

	public abstract EconomyAction accountWithdraw(Account account, String world, BigDecimal amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction walletDeposit(String name, BigDecimal amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction walletDeposit(String name, String world, BigDecimal amount);

	public abstract EconomyAction walletDeposit(OfflinePlayer player, BigDecimal amount);

	public abstract EconomyAction walletDeposit(OfflinePlayer player, String world, BigDecimal amount);

	public abstract EconomyAction walletDeposit(UUID uuid, BigDecimal amount);

	public abstract EconomyAction walletDeposit(UUID uuid, String world, BigDecimal amount);

	public abstract EconomyAction accountDeposit(Account account, BigDecimal amount);

	public abstract EconomyAction accountDeposit(Account account, String world, BigDecimal amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction createAccount(AccountType type, String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction createAccount(AccountType type, String name, String accountId);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction createAccount(AccountType type, String name, BigDecimal amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction createAccount(AccountType type, String name, String accountId, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction createAccount(AccountType type, String name, String accountId, String world, BigDecimal amount);

	public abstract EconomyAction createAccount(AccountType type, OfflinePlayer player);

	public abstract EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId);

	public abstract EconomyAction createAccount(AccountType type, OfflinePlayer player, BigDecimal amount);

	public abstract EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world);

	public abstract EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world, BigDecimal amount);

	public abstract EconomyAction createAccount(AccountType type, UUID uuid);

	public abstract EconomyAction createAccount(AccountType type, UUID uuid, String accountId);

	public abstract EconomyAction createAccount(AccountType type, UUID uuid, BigDecimal amount);

	public abstract EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world);

	public abstract EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world, BigDecimal amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction deleteWalletAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction deleteWalletAccount(String name, String world);

	public abstract EconomyAction deleteWalletAccount(OfflinePlayer player);

	public abstract EconomyAction deleteWalletAccount(OfflinePlayer player, String world);

	public abstract EconomyAction deleteWalletAccount(UUID uuid);

	public abstract EconomyAction deleteWalletAccount(UUID uuid, String world);

	public abstract EconomyAction deleteAccount(String accountID);

	public abstract EconomyAction deleteAccount(String accountID, String world);

	public abstract EconomyAction deleteAccount(Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isAccountOwner(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isAccountOwner(String name, Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isAccountOwner(String name, String accountID, String world);

	public abstract EconomyAction isAccountOwner(OfflinePlayer player, String accountID);

	public abstract EconomyAction isAccountOwner(OfflinePlayer player, Account account);

	public abstract EconomyAction isAccountOwner(OfflinePlayer player, String accountID, String world);

	public abstract EconomyAction isAccountOwner(UUID uuid, String accountID);

	public abstract EconomyAction isAccountOwner(UUID uuid, Account account);

	public abstract EconomyAction isAccountOwner(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isAccountMember(String name, Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isAccountMember(String name, String accountID, String world);

	public abstract EconomyAction isAccountMember(OfflinePlayer player, String accountID);

	public abstract EconomyAction isAccountMember(OfflinePlayer player, Account account);

	public abstract EconomyAction isAccountMember(OfflinePlayer player, String accountID, String world);

	public abstract EconomyAction isAccountMember(UUID uuid, String accountID);

	public abstract EconomyAction isAccountMember(UUID uuid, Account account);

	public abstract EconomyAction isAccountMember(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction addAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction addAccountMember(String name, String accountID, String world);

	/**
	 * Method meant for online players, use for instances
	 * where it makes sense to allow economy to infer/detect world
	 * automatically
	 * @param onlinePlayer a {@link Player} object
	 */
	public abstract EconomyAction addAccountMember(Player onlinePlayer, String accountID);

	public abstract EconomyAction addAccountMember(OfflinePlayer player, String accountID);

	public abstract EconomyAction addAccountMember(OfflinePlayer player, String accountID, String world);

	public abstract EconomyAction addAccountMember(UUID uuid, String accountID);

	public abstract EconomyAction addAccountMember(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction removeAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction removeAccountMember(String name, String accountID, String world);

	/**
	 * Method meant for online players, use for instances
	 * where it makes sense to allow economy to infer/detect world
	 * automatically
	 * @param onlinePlayer a {@link Player} object
	 */
	public abstract EconomyAction removeAccountMember(Player onlinePlayer, String accountID);

	public abstract EconomyAction removeAccountMember(OfflinePlayer player, String accountID);

	public abstract EconomyAction removeAccountMember(OfflinePlayer player, String accountID, String world);

	public abstract EconomyAction removeAccountMember(UUID uuid, String accountID);

	public abstract EconomyAction removeAccountMember(UUID uuid, String accountID, String world);

	public abstract List<Account> getAccounts();

	public abstract List<String> getAccountList();

}
