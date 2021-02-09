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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represent players by name only. Good for quick transactions but not suitable
 * for offline transactions. Contains convenience method to grab live Player object.
 */
public final class TemporaryPlayerEntity extends PlayerEconomyEntityBase {
    private final Player player;

    public TemporaryPlayerEntity(@NotNull Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Represent this player by name
     * <p>Since player names persist for only a current session, this method
     * is best used in circumstances where transactions happen in view of the
     * player (GUIs, transactions between online players)</p>
     * @return String following "p_name=%playerName%" format
     */
    @Override
    public @NotNull String id() {
        return "p_name=".concat(offlinePlayer.getName());
    }

    /**
     * Get the active Player contained in this object
     * @return {@link Player} the online player
     */
    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    public PlayerEntity toPersistent() {
        return new PlayerEntity(player);
    }
}
