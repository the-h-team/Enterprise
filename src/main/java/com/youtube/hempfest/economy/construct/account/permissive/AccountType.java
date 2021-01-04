package com.youtube.hempfest.economy.construct.account.permissive;

public enum AccountType {

	BANK_ACCOUNT(AccountPriority.HIGH), ENTITY_ACCOUNT(AccountPriority.LOW), SERVER_ACCOUNT(AccountPriority.HIGHEST);

	private final AccountPriority priority;

	AccountType(AccountPriority priority) {
		this.priority = priority;
	}

	public AccountPriority getPriority() {
		return priority;
	}

}