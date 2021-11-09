package com.github.sanctum.economy.construct.entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Marks an entity that can be resolved back to a player.
 *
 * @since 2.0.0
 */
public interface PlayerEntity {
    /**
     * Get the offline player represented by this entity.
     * <p>
     * Savvy users may know--{@link Player} subclasses OfflinePlayer,
     * so it's perfectly reasonable to run an <code>instanceof
     * {@link Player}</code> check on the object to see if that's
     * the object returned here.
     *
     * @return an OfflinePlayer object
     */
    @NotNull OfflinePlayer getPlayer();

    /**
     * Attempt to resolve the entity to an online player.
     *
     * @return an online player (if possible) or null
     */
    @Nullable Player getOnline();
}
