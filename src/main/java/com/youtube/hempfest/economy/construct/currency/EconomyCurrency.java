package com.youtube.hempfest.economy.construct.currency;

import java.util.Locale;

public abstract class EconomyCurrency {

	public abstract String getPlural();

	public abstract String getSingular();

	public abstract Locale getLocale();

	public abstract String getWorld();

}
