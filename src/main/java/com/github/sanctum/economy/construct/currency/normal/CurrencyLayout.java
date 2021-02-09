/*
 *  Copyright 2021 Sanctum <https://github.com/the-h-team>
 *  Copyright 2020 Hempfest <https://github.com/Hempfest>
 *  Copyright 2020 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of Enterprise.
 *
 *  Enterprise is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  Enterprise is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
