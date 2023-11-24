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

        private Server(@NotNull String identity) {
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
        public static Server identity(@IdentityKey @NotNull String identity) throws IllegalArgumentException {
            if (!identity.matches(VALID_IDENTITY_KEY)) {
                throw new IllegalArgumentException("Invalid identity: " + identity);
            }
            return new Server(identity);
        }
    }
}
