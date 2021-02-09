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
