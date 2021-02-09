/*
 *  Copyright 2021 Sanctum <https://github.com/the-h-team>
 *  Copyright 2020 Hempfest <https://github.com/Hempfest>
 *  Copyright 2020 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of Enterprise.
 *
 *  Enterprise is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  Enterprise is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.sanctum.economy.construct.entity;

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

    /**
     * Get the name of the contained OfflinePlayer
     * @return Player name
     */
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

    /**
     * Get the OfflinePlayer's UniqueId
     * @return {@link UUID} of the player
     */
    public UUID getUniqueId() {
        return uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id().equals(((PlayerEconomyEntityBase) o).id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id());
    }
}
