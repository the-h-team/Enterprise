/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.github.sanctum.economy.spigot;

import com.github.sanctum.economy.transaction.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A transaction event for Spigot plugins.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <T> the transaction type
 */
public final class AsyncTransactionEvent<T extends MemoryTransaction> extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    static JavaPlugin plugin;
    final T transactionInfo;
    final AtomicBoolean logged = new AtomicBoolean(false);

    /**
     * Directly create a new transaction event.
     * <p>
     * Note: this constructor does not automatically call the event;
     * if that is desired you must do so separately.
     *
     * @param transactionInfo a transaction
     * @see #call(QueryTransaction)
     * @see #call(ReceiveTransaction)
     * @see #call(SetTransaction)
     * @see #call(SourceTransaction)
     * @see #call(TotalTransaction)
     */
    public AsyncTransactionEvent(@NotNull T transactionInfo) {
        super(true);
        this.transactionInfo = transactionInfo;
    }

    /**
     * Get the transaction in this event.
     *
     * @return the transaction in this event
     */
    public T getTransactionInfo() {
        return transactionInfo;
    }

    /**
     * Whether this event should be logged to console.
     *
     * @return true if the event should be logged
     */
    public boolean isLogged() {
        return logged.get();
    }

    /**
     * Set whether this event should be logged to console.
     *
     * @param log whether to log
     */
    public void setLogged(boolean log) {
        logged.set(log);
    }

    @Override
    public String toString() {
        return "Transaction: " + transactionInfo.getDescription();
    }

    AsyncTransactionEvent<T> call() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(AsyncTransactionEvent.this));
        return this;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    // for fevents contract
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Create and call an event for a query operation.
     *
     * @param query a query transaction
     * @return the scheduled-to-be-called event
     */
    public static AsyncTransactionEvent<QueryTransaction> call(@NotNull QueryTransaction query) {
        return new AsyncTransactionEvent<>(query).call();
    }

    /**
     * Create and call an event for a receive operation.
     *
     * @param give a give transaction
     * @return the scheduled-to-be-called event
     */
    public static AsyncTransactionEvent<ReceiveTransaction> call(@NotNull ReceiveTransaction give) {
        return new AsyncTransactionEvent<>(give).call();
    }

    /**
     * Create and call an event for a set operation.
     *
     * @param set a set transaction
     * @return the scheduled-to-be-called event
     */
    public static AsyncTransactionEvent<SetTransaction> call(@NotNull SetTransaction set) {
        return new AsyncTransactionEvent<>(set).call();
    }

    /**
     * Create and call an event for a source operation.
     *
     * @param take a take transaction
     * @return the scheduled-to-be-called event
     */
    public static AsyncTransactionEvent<SourceTransaction> call(@NotNull SourceTransaction take) {
        return new AsyncTransactionEvent<>(take).call();
    }

    /**
     * Create and call an event for a total operation.
     *
     * @param total a total transaction
     * @return the scheduled-to-be-called event
     */
    public static AsyncTransactionEvent<TotalTransaction> call(@NotNull TotalTransaction total) {
        return new AsyncTransactionEvent<>(total).call();
    }
}
