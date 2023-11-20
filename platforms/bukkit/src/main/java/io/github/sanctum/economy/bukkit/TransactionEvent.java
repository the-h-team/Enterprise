/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.bukkit;

import io.github.sanctum.economy.construct.system.transactions.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A transaction event for Spigot plugins.
 * <p>
 * This event is called ahead of transaction finalization. This allows you to
 * write your own transaction handling logic to alter or cancel processing as
 * desired.
 * <p>
 * This event also allows you to replace the transaction prototype. Since
 * prototypes are immutable, this is the proper way to change details before it
 * is finalized.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <T> the transaction type
 */
public final class TransactionEvent<T extends MemoryTransaction> extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    static JavaPlugin plugin;
    final LinkedList<T> prototypes = new LinkedList<>();
    boolean cancelled = false;
    boolean logged;

    /**
     * Directly creates a new transaction event.
     * <p>
     * Note: this constructor does not automatically call the event;
     * if that is desired you must do so separately.
     *
     * @param transaction a transaction
     * @see #call(QueryTransaction)
     * @see #call(ReceiveTransaction)
     * @see #call(SetTransaction)
     * @see #call(SourceTransaction)
     * @see #call(TotalTransaction)
     */
    public TransactionEvent(@NotNull T transaction) {
        super(false);
        this.prototypes.add(transaction);
    }

    /**
     * Gets the transaction specified in this event.
     *
     * @return a transaction prototype
     */
    public @NotNull T getPrototype() {
        return prototypes.getLast();
    }

    /**
     * Sets a new transaction prototype.
     *
     * @param transaction a new transaction prototype
     * @return the last transaction prototype
     */
    public @NotNull T setPrototype(@NotNull T transaction) {
        final T last = prototypes.getLast();
        prototypes.addLast(transaction);
        return last;
    }

    /**
     * Whether this event should be logged to console.
     *
     * @return true if the event should be logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * Sets whether this event should be logged to console.
     *
     * @param logged whether to log
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        final Iterator<T> iterator = prototypes.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().toString());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return "TransactionEvent{" +
                "prototypes=" + sb +
                ", lastPrototype=" + prototypes.getLast() +
                ", cancelled=" + cancelled +
                ", logged=" + logged +
                '}';
    }

    TransactionEvent<T> call() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(TransactionEvent.this));
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
     * Creates and calls an event for a query operation.
     *
     * @param query a query transaction
     * @return the scheduled-to-be-called event
     */
    public static TransactionEvent<QueryTransaction> call(@NotNull QueryTransaction query) {
        return new TransactionEvent<>(query).call();
    }

    /**
     * Creates and calls an event for a receive operation.
     *
     * @param give a give transaction
     * @return the scheduled-to-be-called event
     */
    public static TransactionEvent<ReceiveTransaction> call(@NotNull ReceiveTransaction give) {
        return new TransactionEvent<>(give).call();
    }

    /**
     * Creates and calls an event for a set operation.
     *
     * @param set a set transaction
     * @return the scheduled-to-be-called event
     */
    public static TransactionEvent<SetTransaction> call(@NotNull SetTransaction set) {
        return new TransactionEvent<>(set).call();
    }

    /**
     * Creates and calls an event for a source operation.
     *
     * @param take a take transaction
     * @return the scheduled-to-be-called event
     */
    public static TransactionEvent<SourceTransaction> call(@NotNull SourceTransaction take) {
        return new TransactionEvent<>(take).call();
    }

    /**
     * Creates and calls an event for a total operation.
     *
     * @param total a total transaction
     * @return the scheduled-to-be-called event
     */
    public static TransactionEvent<TotalTransaction> call(@NotNull TotalTransaction total) {
        return new TransactionEvent<>(total).call();
    }
}
