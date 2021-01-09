package com.youtube.hempfest.economy.construct.currency.normal;

import java.util.Locale;

/**
 * Define a currency object
 */
public interface EconomyCurrency { // TODO: describe methods

	String majorPlural();

	String majorSingular();

	String minorPlural();

	String minorSingular();

	Locale getLocale();

	String getWorld();

	/**
	 * Static factory method to access SpecialCurrencyLayout builder util
	 */
	static CurrencyLayout getCurrencyLayoutBuilder() {
		return new CurrencyLayout();
	}

}
