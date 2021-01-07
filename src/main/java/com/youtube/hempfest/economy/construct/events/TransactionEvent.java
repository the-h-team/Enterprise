package com.youtube.hempfest.economy.construct.events;

import com.youtube.hempfest.economy.construct.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.math.BigDecimal;

public class TransactionEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    protected final Entity payer;
    protected final Entity payee;
    protected final BigDecimal amount;

    public TransactionEvent(Entity payer, Entity payee, BigDecimal amount) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
