package com.github.sanctum.economy.construct;

public enum EconomyPriority {
	LOW(1), HIGH(2), HIGHER(3), HIGHEST(4);

	private final int priNum;

	EconomyPriority(int priNum) {
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
