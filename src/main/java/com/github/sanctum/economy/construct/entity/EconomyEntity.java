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

/**
 * Describes an entity engaged in economy transactions
 */
public interface EconomyEntity {
    /**
     * Define a friendly name for this EconomyEntity.
     * <p>For instance, a player's name, or an organization.</p>
     * @return non-empty friendly name (defaults to #id())
     */
    default @NotNull String friendlyName() {
        return id();
    }

    /**
     * Get a string which uniquely identifies this entity.
     * <p>Uses the following format:</p>
     * <p>'type'='identity'</p>
     *
     * <p>Sample implementations:</p>
     * <p>(Persistent Player/OfflinePlayer) UniqueId-based player id: 'p_uid=uuid'</p>
     * <p>(Temporary Player) Session-based player id: 'p_name=PlayerName'</p>
     * <p>(NPCs) NamespacedKey-based npc id: 'npc=plugin:string-key'</p>
     * @return String which is unique to this entity
     */
    @NotNull
    String id();

    /**
     * Separate the 'type' and 'identity' components of {@link #id()}.
     * @return array of length 2 where element one is type and two is identity
     */
    default String[] splitId() {
        return id().split("=", 2);
    }
}
