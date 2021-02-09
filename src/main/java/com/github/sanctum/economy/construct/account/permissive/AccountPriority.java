package com.github.sanctum.economy.construct.account.permissive;

public enum AccountPriority {
	LOW(1), HIGH(2), HIGHEST(3);

	private final int priNum;

	AccountPriority(int priNum) {
		this.priNum = priNum;
	}

	/**
	 * Get's the persistent hierarchy of the enum value.
	 * @return Position in hierarchy
	 */
	public int getPriNum() {
		return priNum;
	}

}
