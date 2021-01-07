package com.youtube.hempfest.economy.construct.account;

import com.youtube.hempfest.economy.construct.account.permissive.AccountType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.youtube.hempfest.economy.construct.entity.Entity;
import org.bukkit.World;

public class Account extends Balance {

	private final AccountType accountType;

	private final List<String> members;

	public Account(AccountType accountType, Entity holder, BigDecimal balance, World world, Entity... members) {
		super(holder, world, balance);
		this.accountType = accountType;
		this.members = Arrays.stream(members).map(Entity::id).collect(Collectors.toList());
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
