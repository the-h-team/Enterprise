package com.youtube.hempfest.economy.construct.events;

import com.youtube.hempfest.economy.construct.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import java.math.BigDecimal;

public class PreTransactionEvent extends TransactionEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled = false;

    public PreTransactionEvent(Entity payer, Entity payee, BigDecimal amount) {
        super(payer, payee, amount);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
