/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A type of entity derived from a Bukkit-native structure.
 *
 * @since 2.0.0
 * @author ms5984
 */
// FIXME pull impl
public class BukkitEntity extends EnterpriseEntityImpl {
    /**
     * Creates an entity from a namespace and identifier.
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
    public static class PlayerByUUID extends AbstractPlayerEntity<UUID> implements PlayerEntity.ByUniqueId {

        PlayerByUUID(OfflinePlayer player) {
            super(NAMESPACE, player.getUniqueId(), player);
        }

        /**
         * Gets an entity for an online player which uses their UniqueId.
         *
         * @param player an online player
         * @return a new player entity
         */
        public static PlayerByUUID online(@NotNull Player player) {
            return new PlayerByUUID(player);
        }

        /**
         * Gets an entity for an offline player which uses their UniqueId.
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
    public static class PlayerByUsername extends AbstractPlayerEntity<String> implements PlayerEntity.ByUsername {

        PlayerByUsername(String username, OfflinePlayer player) {
            super(NAMESPACE, username, player);
        }

        /**
         * Gets an entity for an online player which uses their username.
         *
         * @param player an online player
         * @return a new player entity
         */
        public static PlayerByUsername online(@NotNull Player player) {
            return new PlayerByUsername(player.getName(), player);
        }

        /**
         * Gets an entity for an offline player which uses their username.
         *
         * @param offlinePlayer an offline player
         * @return a new player entity
         * @throws IllegalArgumentException if we have
         * not yet seen a name for the player
         */
        public static PlayerByUsername offline(@NotNull OfflinePlayer offlinePlayer) throws IllegalArgumentException {
            return new PlayerByUsername(checkUsername(offlinePlayer), offlinePlayer);
        }

        // validate that we have a username for the (potentially never yet online) player
        static String checkUsername(OfflinePlayer offlinePlayer) {
            if (offlinePlayer instanceof Player) return ((Player) offlinePlayer).getName();
            final String name = offlinePlayer.getName();
            if (name == null) throw new IllegalArgumentException("Offline player has no name");
            return name;
        }
    }

    /**
     * Native implementation for the local server.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class Server extends BukkitEntity {
        /**
         * All implementations of this class use this value as their namespace.
         */
        public static final String NAMESPACE = "server";

        Server(@NotNull String identity) {
            super(NAMESPACE, identity);
        }

        /**
         * Gets an entity for the server that specifically
         * identifies the console command sender.
         *
         * @param console the console command sender instance
         * @return a new server entity
         * @implNote Uses {@link ConsoleCommandSender#getName()}
         * as the value for <code>identity</code>.
         */
        public static Server console(@NotNull ConsoleCommandSender console) {
            return new Server(console.getName());
        }

        /**
         * Gets an entity associated with the server with
         * an arbitrary identifier component.
         *
         * @param identity a custom identifier
         * @return a new, custom server entity
         * @throws IllegalArgumentException if <code>identity</code> does not
         * meet the format of {@link EnterpriseEntity#VALID_IDENTITY_KEY}.
         */
        public static Server identity(@NotNull @IdentityKey String identity) throws IllegalArgumentException {
            if (!identity.matches(VALID_IDENTITY_KEY)) {
                throw new IllegalArgumentException("Invalid identity: " + identity);
            }
            return new Server(identity);
        }
    }
}
