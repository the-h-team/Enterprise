package com.youtube.hempfest.economy.construct.currency;

import java.util.Locale;

public class CurrencyLayout extends EconomyCurrency {

	private String plural;

	private String singular;

	private Locale locale;

	private String world;

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

	@Override
	public String getPlural() {
		return plural;
	}

	@Override
	public String getSingular() {
		return singular;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String getWorld() {
		return world;
	}

	public EconomyCurrency toCurrency() {
		return this;
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
