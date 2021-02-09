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
 * Represents a fixed layout for an CurrencyType
 */
public class ImmutableCurrencyLayout implements EconomyCurrency {
    private final CurrencyLayout currencyLayout;

    protected ImmutableCurrencyLayout(CurrencyLayout currencyLayout) {
        this.currencyLayout = currencyLayout;
    }

    @Override
    public String majorPlural() {
        return currencyLayout.plural;
    }

    @Override
    public String majorSingular() {
        return currencyLayout.singular;
    }

    @Override
    public String minorPlural() {
        return currencyLayout.minorPlural;
    }

    @Override
    public String minorSingular() {
        return currencyLayout.minorSingular;
    }

    @Override
    public Locale getLocale() {
        return currencyLayout.locale;
    }

    @Override
    public String getWorld() {
        return currencyLayout.world;
    }
}
