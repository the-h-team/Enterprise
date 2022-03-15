/*
 *   Copyright 2021 Sanctum <https://github.com/the-h-team>
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
package com.github.sanctum.economy.bungee;

import com.github.sanctum.economy.transaction.*;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.AsyncEvent;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A transaction event for BungeeCord plugins.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <T> the transaction type
 */
public final class BungeeTransactionEvent<T extends MemoryTransaction> extends AsyncEvent<BungeeTransactionEvent<T>> {
    static Plugin plugin;
    final T transactionInfo;
    final AtomicBoolean logged = new AtomicBoolean(false);

    /**
     * Create a new event from a transaction and a final callback.
     * <p>
     * Note: this constructor does not automatically call the event;
     * if that is desired you must do so separately.
     *
     * @param transactionInfo a transaction
     * @param callback a function to call after all intents process
     * @see #call(QueryTransaction)
     * @see #call(ReceiveTransaction)
     * @see #call(SetTransaction)
     * @see #call(SourceTransaction)
     * @see #call(TotalTransaction)
     */
    public BungeeTransactionEvent(@NotNull T transactionInfo, @NotNull Callback<BungeeTransactionEvent<T>> callback) {
        super(callback);
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

    private void log() {
        plugin.getLogger().info(this::toString);
    }

    BungeeTransactionEvent<T> call() {
        ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> ProxyServer.getInstance().getPluginManager().callEvent(BungeeTransactionEvent.this));
        return this;
    }

    static void scheduleLogIfSet(BungeeTransactionEvent<? extends MemoryTransaction> event, Throwable ex) {
        if (!event.isLogged()) return;
        ProxyServer.getInstance().getScheduler().runAsync(plugin, event::log);
    }

    /**
     * Create and call an event for a query operation.
     * <p>
     * Uses callback to log to console.
     *
     * @param query the query transaction
     * @return the new event registered to runAsync
     * @implNote Sets up callback to log to console if set.
     */
    public static BungeeTransactionEvent<QueryTransaction> call(@NotNull QueryTransaction query) {
        return new BungeeTransactionEvent<>(query, BungeeTransactionEvent::scheduleLogIfSet).call();
    }

    /**
     * Create and call an event for a receive operation.
     *
     * @param give the give transaction
     * @return the new event registered to runAsync
     * @implNote Sets up callback to log to console if set.
     */
    public static BungeeTransactionEvent<ReceiveTransaction> call(@NotNull ReceiveTransaction give) {
        return new BungeeTransactionEvent<>(give, BungeeTransactionEvent::scheduleLogIfSet).call();
    }

    /**
     * Create and call an event for a set operation.
     *
     * @param set the set transaction
     * @return the new event registered to runAsync
     * @implNote Sets up callback to log to console if set.
     */
    public static BungeeTransactionEvent<SetTransaction> call(@NotNull SetTransaction set) {
        return new BungeeTransactionEvent<>(set, BungeeTransactionEvent::scheduleLogIfSet).call();
    }

    /**
     * Create and call an event for a source operation.
     *
     * @param take the take transaction
     * @return the new event registered to runAsync
     * @implNote Sets up callback to log to console if set.
     */
    public static BungeeTransactionEvent<SourceTransaction> call(@NotNull SourceTransaction take) {
        return new BungeeTransactionEvent<>(take, BungeeTransactionEvent::scheduleLogIfSet).call();
    }

    /**
     * Create and call an event for a total operation.
     *
     * @param total the total transaction
     * @return the new event registered to runAsync
     * @implNote Sets up callback to log to console if set.
     */
    public static BungeeTransactionEvent<TotalTransaction> call(@NotNull TotalTransaction total) {
        return new BungeeTransactionEvent<>(total, BungeeTransactionEvent::scheduleLogIfSet).call();
    }
}
