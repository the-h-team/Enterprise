package com.github.sanctum.economy.construct.currency.normal;

import java.util.Locale;

/**
 * Fluent-api builder for an immutable CurrencyType
 */
public class CurrencyLayout { // builder won't implement the interface to enforce #toCurrency

	protected String plural;
	protected String singular;
	protected String minorPlural;
	protected String minorSingular;
	protected Locale locale;
	protected String world;

	protected CurrencyLayout() {} // Force use of CurrencyType static factory

	public CurrencyLayout setWorld(String world) {
		this.world = world;
		return this;
	}

	public CurrencyLayout setMajorPlural(String plural) {
		this.plural = plural;
		return this;
	}

	public CurrencyLayout setMajorSingular(String singular) {
		this.singular = singular;
		return this;
	}

	public CurrencyLayout setMinorPlural(String plural) {
		this.minorPlural = plural;
		return this;
	}

	public CurrencyLayout setMinorSingular(String singular) {
		this.minorSingular = singular;
		return this;
	}

	public CurrencyLayout setLocale(Locale locale) {
		this.locale = locale;
		return this;
	}

	/**
	 * Finish building an CurrencyType
	 * @return an immutable CurrencyType object
	 */
	public EconomyCurrency toCurrency() {
		return new ImmutableCurrencyLayout(this);
	}

	@Override
	public String toString() {
		return "SpecialCurrencyLayout{" +
				"plural='" + plural + '\'' +
				", singular='" + singular + '\'' +
				", locale=" + locale +
				'}';
	}
}
