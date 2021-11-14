/*
 *   Copyright 2021 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.github.sanctum.economy.construct.entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A type of entity derived from a Bukkit-native structure.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class BukkitEntity extends EnterpriseEntity {
    /**
     * Create an entity from a namespace and identifier.
     * <p>
     * <b>Does not perform validation. Internal use only!</b>
     *
     * @param namespace the namespace for the entity
     * @param identity the namespace-unique identifier for the entity
     */
    BukkitEntity(@NotNull String namespace, @NotNull String identity) {
        super(namespace, identity);
    }

    /**
     * Native implementation for players by UniqueId.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class PlayerByUUID extends AbstractPlayerEntity {

        PlayerByUUID(OfflinePlayer player) {
            super("p_uid", player.getUniqueId().toString(), player);
        }

        /**
         * Get an entity for an online player which uses their UniqueId.
         *
         * @param player an online player
         * @return a new player entity
         */
        public static PlayerByUUID online(@NotNull Player player) {
            return new PlayerByUUID(player);
        }

        /**
         * Get an entity for an offline player which uses their UniqueId.
         *
         * @param offlinePlayer an offline player
         * @return a new player entity
         */
        public static PlayerByUUID offline(@NotNull OfflinePlayer offlinePlayer) {
            return new PlayerByUUID(offlinePlayer);
        }
    }

    /**
     * Native implementation for players by username.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class PlayerByUsername extends AbstractPlayerEntity {

        PlayerByUsername(OfflinePlayer player) {
            super("p_username", player.getName(), player);
        }

        /**
         * Get an entity for an online player which uses their username.
         *
         * @param player an online player
         * @return a new player entity
         */
        public static PlayerByUsername online(@NotNull Player player) {
            return new PlayerByUsername(player);
        }

        /**
         * Get an entity for an offline player which uses their username.
         *
         * @param offlinePlayer an offline player
         * @return a new player entity
         */
        public static PlayerByUsername offline(@NotNull OfflinePlayer offlinePlayer) {
            return new PlayerByUsername(offlinePlayer);
        }
    }
}
