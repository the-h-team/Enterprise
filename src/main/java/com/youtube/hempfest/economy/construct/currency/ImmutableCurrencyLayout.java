package com.youtube.hempfest.economy.construct.currency;

import java.util.Locale;

/**
 * Represents a fixed layout for an EconomyCurrency
 */
public class ImmutableCurrencyLayout implements EconomyCurrency {
    private final CurrencyLayout currencyLayout;

    protected ImmutableCurrencyLayout(CurrencyLayout currencyLayout) {
        this.currencyLayout = currencyLayout;
    }

    @Override
    public String getPlural() {
        return currencyLayout.plural;
    }

    @Override
    public String getSingular() {
        return currencyLayout.singular;
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
