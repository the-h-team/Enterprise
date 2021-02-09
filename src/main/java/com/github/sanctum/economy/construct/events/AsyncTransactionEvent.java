package com.github.sanctum.economy.construct.events;

import com.github.sanctum.economy.construct.EconomyAction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class AsyncTransactionEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final EconomyAction economyAction;

    public AsyncTransactionEvent(EconomyAction economyAction) {
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
