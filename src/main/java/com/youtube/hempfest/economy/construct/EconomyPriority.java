package com.youtube.hempfest.economy.construct;

public enum EconomyPriority {
	LOW(1), HIGH(2), HIGHEST(3);

	private final int priNum;

	EconomyPriority(int priNum) {
		this.priNum = priNum;
	}

	/* consider leaving methods like this out, enums are designed so you don't need to manually set magic numbers
	 by all means tho feel free to use friendly names like "low", "high", "highest" i.e. things less likely to
	 change when adding/removing from this list. if you need a temporary int representation use #ordinal()
	 */
	public int getPriNum() {
		return priNum;
	}
}
