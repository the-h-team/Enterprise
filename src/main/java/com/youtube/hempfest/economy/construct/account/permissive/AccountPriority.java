package com.youtube.hempfest.economy.construct.account.permissive;

public enum AccountPriority {
	LOW(1), HIGH(2), HIGHEST(3);

	private final int priNum;

	AccountPriority(int priNum) {
		this.priNum = priNum;
	}

	public int getPriNum() {
		return priNum;
	}
}
