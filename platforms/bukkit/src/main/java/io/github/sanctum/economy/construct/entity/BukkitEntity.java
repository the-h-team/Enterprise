/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.construct.entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A type of entity derived from a Bukkit-native structure.
 *
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.NonExtendable
public interface BukkitEntity extends EnterpriseEntity {
    /**
     * Marker interface for Bukkit-native player handles.
     *
     * @since 2.0.0
     */
    interface PlayerHandle {
        /**
         * Gets a username-based player handle from a Bukkit player object.
         * <p>
         * Supports online and offline players.
         *
         * @param player the player
         * @return a new player handle
         * @throws IllegalArgumentException if {@link OfflinePlayer#getName()} returns null
         */
        static @NotNull BukkitPlayerHandle.ByUsername byUsername(@NotNull OfflinePlayer player) {
            final String name = player.getName();
            if (name == null) throw new IllegalArgumentException("player#getName() returned null");
            return new BukkitPlayerHandle.ByUsername(new PlayerEntity.ByUsername(name));
        }

        /**
         * Gets a UniqueId-based player handle from a Bukkit player object.
         * <p>
         * Supports online and offline players.
         *
         * @param player the player
         * @return a new player handle
         */
        static @NotNull BukkitPlayerHandle.ByUniqueId byUniqueId(@NotNull OfflinePlayer player) {
            return new BukkitPlayerHandle.ByUniqueId(new PlayerEntity.ByUniqueId(player.getUniqueId()));
        }
    }

    /**
     * Native implementation for the local server.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class Server extends EnterpriseEntityImpl implements BukkitEntity {
        /**
         * All implementations of this class use this value as their namespace.
         */
        public static final @Namespace String NAMESPACE = "server";

        private Server(@NotNull String identityKey) {
            super(NAMESPACE, EnterpriseEntity.verifyIdentityKey(identityKey));
        }

        /**
         * Gets an entity for the server that specifically
         * identifies the console command sender.
         *
         * @return a new server entity
         * @implNote Uses {@link Bukkit#getConsoleSender()} and
         * {@link ConsoleCommandSender#getName()} as the value for
         * {@code identityKey}.
         */
        public static Server console() {
            try {
                return new Server(Bukkit.getConsoleSender().getName());
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Unexpected value for ConsoleCommandSender#getName()", e);
            }
        }

        /**
         * Gets a server-associated entity with an arbitrary identity key.
         *
         * @param identityKey an identity key
         * @return a new server-associated entity
         * @throws IllegalArgumentException if {@code identityKey} does not
         * meet the format of {@link EnterpriseEntity#VALID_IDENTITY_KEY}.
         */
        public static Server identityKey(@NotNull String identityKey) throws IllegalArgumentException {
            return new Server(identityKey);
        }
    }
}
