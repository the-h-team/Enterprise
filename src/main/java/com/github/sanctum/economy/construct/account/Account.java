package com.github.sanctum.economy.construct.account;

import com.github.sanctum.economy.construct.entity.EconomyEntity;
import com.github.sanctum.economy.construct.account.permissive.AccountType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.sanctum.economy.construct.EconomyAction;
import org.bukkit.OfflinePlayer;

/**
 * The abstract base for Bank Accounts
 */
public abstract class Account extends Balance {

	private final AccountType accountType;

	private final List<String> members;

	public Account(AccountType accountType, EconomyEntity holder, EconomyEntity... members) {
		super(holder);
		this.accountType = accountType;
		this.members = Arrays.stream(members).map(EconomyEntity::id).collect(Collectors.toList());
	}

	public AccountType getType() {
		return accountType;
	}

	public List<String> getMembers() {
		return members;
	}

	// ===== MEMBER MANAGEMENT =====

	// Is owner
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isOwner(String name);
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isOwner(String name, String world);
	public abstract EconomyAction isOwner(OfflinePlayer player);
	public abstract EconomyAction isOwner(OfflinePlayer player, String world);
	public abstract EconomyAction isOwner(UUID uuid);
	public abstract EconomyAction isOwner(UUID uuid, String world);

	// Is joint owner
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isJointOwner(String name);
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isJointOwner(String name, String world);
	public abstract EconomyAction isJointOwner(OfflinePlayer player);
	public abstract EconomyAction isJointOwner(OfflinePlayer player, String world);
	public abstract EconomyAction isJointOwner(UUID uuid);
	public abstract EconomyAction isJointOwner(UUID uuid, String world);

	// Is member
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isMember(String name);
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction isMember(String name, String world);
	public abstract EconomyAction isMember(OfflinePlayer player);
	public abstract EconomyAction isMember(OfflinePlayer player, String world);
	public abstract EconomyAction isMember(UUID uuid);
	public abstract EconomyAction isMember(UUID uuid, String world);

	// Add member
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction addMember(String name);
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction addMember(String name, String world);
	public abstract EconomyAction addMember(OfflinePlayer player);
	public abstract EconomyAction addMember(OfflinePlayer player, String world);
	public abstract EconomyAction addMember(UUID uuid);
	public abstract EconomyAction addMember(UUID uuid, String world);

	// Remove member
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction removeMember(String name);
	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	public abstract EconomyAction removeMember(String name, String world);
	public abstract EconomyAction removeMember(OfflinePlayer player);
	public abstract EconomyAction removeMember(OfflinePlayer player, String world);
	public abstract EconomyAction removeMember(UUID uuid);
	public abstract EconomyAction removeMember(UUID uuid, String world);

	@Override
	public String toString() {
		return "Account{" +
				"accountType=" + accountType +
				", members=" + members +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Account)) return false;
		Account account = (Account) o;
		return accountType == account.accountType &&
				Objects.equals(getMembers(), account.getMembers());
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountType, getMembers());
	}
}
