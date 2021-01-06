package com.youtube.hempfest.economy.construct.currency;

import java.util.Locale;

/**
 * Define a currency object
 */
public interface EconomyCurrency { // TODO: describe methods

	String getPlural();

	String getSingular();

	Locale getLocale();

	String getWorld();

	/**
	 * Static factory method to access CurrencyLayout builder util
	 */
	static CurrencyLayout getCurrencyLayoutBuilder() {
		return new CurrencyLayout();
	}

}
