package com.youtube.hempfest.economy.construct.events;

import com.youtube.hempfest.economy.construct.EconomyAction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class AsyncEconomyInfoEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final EconomyAction economyAction;

    public AsyncEconomyInfoEvent(EconomyAction economyAction) {
        super(true);
        this.economyAction = economyAction;
    }

    public EconomyAction getEconomyAction() {
        return economyAction;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
