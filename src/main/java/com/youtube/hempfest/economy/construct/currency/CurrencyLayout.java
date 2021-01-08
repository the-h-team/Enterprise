package com.youtube.hempfest.economy.construct.currency;

import java.util.Locale;

/**
 * Fluent-api builder for an immutable EconomyCurrency
 */
public class CurrencyLayout { // builder won't implement the interface to enforce #toCurrency

	protected String plural;
	protected String singular;
	protected Locale locale;
	protected String world;

	protected CurrencyLayout() {} // Force use of EconomyCurrency static factory

	public CurrencyLayout setWorld(String world) {
		this.world = world;
		return this;
	}

	public CurrencyLayout setPlural(String plural) {
		this.plural = plural;
		return this;
	}

	public CurrencyLayout setSingular(String singular) {
		this.singular = singular;
		return this;
	}

	public CurrencyLayout setLocale(Locale locale) {
		this.locale = locale;
		return this;
	}

	/**
	 * Finish building an EconomyCurrency
	 * @return an immutable EconomyCurrency object
	 */
	public EconomyCurrency toCurrency() {
		return new ImmutableCurrencyLayout(this);
	}

	@Override
	public String toString() {
		return "CurrencyLayout{" +
				"plural='" + plural + '\'' +
				", singular='" + singular + '\'' +
				", locale=" + locale +
				'}';
	}
}
