package com.youtube.hempfest.economy.construct.entity.concrete;

import com.sun.istack.internal.NotNull;
import com.youtube.hempfest.economy.construct.entity.AbstractPlayerEntity;
import org.bukkit.OfflinePlayer;

/**
 * Designed to represent players, even persistently. Uses UniqueId internally.
 */
public final class PlayerEntity extends AbstractPlayerEntity {
    public PlayerEntity(@NotNull OfflinePlayer offlinePlayer) {
        super(offlinePlayer);
    }

    /**
     * Represent this player by their UniqueId
     * @return String following "p_uid=%uuid%" format
     */
    @Override
    public String id() {
        return "p_uid=" + uid;
    }
}
