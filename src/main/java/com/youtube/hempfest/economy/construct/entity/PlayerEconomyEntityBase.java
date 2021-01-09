package com.youtube.hempfest.economy.construct.entity;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * Base class for all player-based entities
 */
public abstract class PlayerEconomyEntityBase implements EconomyEntity {
    protected final OfflinePlayer offlinePlayer;
    protected final UUID uid;

    protected PlayerEconomyEntityBase(@NotNull OfflinePlayer offlinePlayer) {
        this.offlinePlayer = Objects.requireNonNull(offlinePlayer);
        this.uid = offlinePlayer.getUniqueId();
    }

    @Override
    public @NotNull String friendlyName() {
        return offlinePlayer.getName();
    }

    /**
     * Get the OfflinePlayer contained in this object
     * @return {@link OfflinePlayer}
     */
    public OfflinePlayer getPlayer() {
        return offlinePlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id().equals(((PlayerEconomyEntityBase) o).id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id(), uid);
    }
}
