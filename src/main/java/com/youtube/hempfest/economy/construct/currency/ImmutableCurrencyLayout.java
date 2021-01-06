package com.youtube.hempfest.economy.construct.currency;

import java.util.Locale;

/**
 * Represents a fixed layout for an EconomyCurrency
 */
public class ImmutableCurrencyLayout implements EconomyCurrency {
    private final String plural;
    private final String singular;
    private final Locale locale;
    private final String world;

    protected ImmutableCurrencyLayout(CurrencyLayout currencyLayout) {
        this.plural = currencyLayout.plural;
        this.singular = currencyLayout.singular;
        this.locale = currencyLayout.locale;
        this.world = currencyLayout.world;
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
}
