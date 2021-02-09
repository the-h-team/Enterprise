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
