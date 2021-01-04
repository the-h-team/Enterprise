package com.youtube.hempfest.economy.construct.account;

import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.NamespacedKey;

public class Account extends Balance {

	private final AccountType accountType;

	private final List<String> members;

	public Account(AccountType accountType, String holder, double balance, String world, String... members) {
		super(holder, world, balance);
		this.accountType = accountType;
		this.members = Arrays.asList(members);
	}

	public Account(AccountType accountType, NamespacedKey holder, double balance, String world, String... members) {
		super(holder, world, balance);
		this.accountType = accountType;
		this.members = Arrays.asList(members);
	}

	public AccountType getType() {
		return accountType;
	}

	public List<String> getMembers() {
		return members;
	}

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
