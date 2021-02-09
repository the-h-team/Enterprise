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

import org.jetbrains.annotations.NotNull;

public interface EconomyEntity {
    /**
     * Define a friendly name for this EconomyEntity.
     * for instance, player's name
     * @return non-empty friendly name (defaults to #id())
     */
    default @NotNull String friendlyName() {
        return id();
    }

    /**
     * Get a string which uniquely identifies this entity
     *
     * Sample implementation:
     * (Persistent Player/OfflinePlayer) UniqueId-based player id: 'p_uid=uuid'
     * (Temporary Player) Session-based player id: 'p_name=PlayerName'
     * (NPCs) NamespacedKey-based npc id: 'npc=plugin:string-key'
     * @return String which is unique to this entity
     */
    @NotNull
    String id();

    default String[] splitId() {
        return id().split("=", 2);
    }
}
