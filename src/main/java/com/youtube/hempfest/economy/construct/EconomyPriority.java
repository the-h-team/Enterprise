package com.youtube.hempfest.economy.construct;

public enum EconomyPriority {
	LOW(1), HIGH(2), HIGHEST(3);

	private final int priNum;

	EconomyPriority(int priNum) {
		this.priNum = priNum;
	}

	public int getPriNum() {
		return priNum;
	}
}
