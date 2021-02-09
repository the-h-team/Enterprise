package com.github.sanctum.economy.construct.entity.types;

import com.github.sanctum.economy.construct.entity.PlayerEconomyEntityBase;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Designed to represent players, even persistently. Uses UniqueId internally.
 */
public final class PlayerEntity extends PlayerEconomyEntityBase {
    public PlayerEntity(@NotNull OfflinePlayer offlinePlayer) {
        super(offlinePlayer);
    }

    /**
     * Represent this player by their UniqueId
     * @return String following "p_uid=%uuid%" format
     */
    @Override
    public @NotNull String id() {
        return "p_uid=" + uid;
    }
}
