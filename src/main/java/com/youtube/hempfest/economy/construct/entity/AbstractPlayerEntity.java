package com.youtube.hempfest.economy.construct.entity;

import com.sun.istack.internal.NotNull;
import org.bukkit.OfflinePlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * Base class for all player-based entities
 */
public abstract class AbstractPlayerEntity implements Entity {
    protected final OfflinePlayer offlinePlayer;
    protected final UUID uid;

    protected AbstractPlayerEntity(@NotNull OfflinePlayer offlinePlayer) {
        this.offlinePlayer = Objects.requireNonNull(offlinePlayer);
        this.uid = offlinePlayer.getUniqueId();
    }

    @Override
    public String friendlyName() {
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
        return id().equals(((AbstractPlayerEntity) o).id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id(), uid);
    }
}
