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

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ConnectedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A type of entity derived from a BungeeCord-native structure.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class BungeeEntity extends EnterpriseEntity {
    /**
     * Represents any Bungeecord proxy instance.
     *
     * @since 2.0.0
     */
    public static final BungeeEntity BUNGEECORD_PROXY = new BungeeEntity("proxy", "bungeecord");

    /**
     * Create an entity from a namespace and identifier.
     * <p>
     * <b>Does not perform validation. Internal use only!</b>
     *
     * @param namespace the namespace for the entity
     * @param identity the namespace-unique identifier for the entity
     */
    BungeeEntity(@NotNull String namespace, @NotNull String identity) {
        super(namespace, identity);
    }

    /**
     * Native implementation for proxy players by UniqueId.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class ProxyPlayerByUUID extends AbstractProxyPlayerEntity<UUID> implements PlayerEntity.ByUniqueId {

        ProxyPlayerByUUID(ProxiedPlayer player) {
            super(NAMESPACE, player.getUniqueId(), player);
        }

        @Override
        public @NotNull ProxyPlayerByUUID asEntity() {
            return this;
        }

        /**
         * Get an entity for a proxied player that uses their UniqueId.
         *
         * @param player a proxied player
         * @return a new player entity
         */
        public static ProxyPlayerByUUID proxied(@NotNull ProxiedPlayer player) {
            return new ProxyPlayerByUUID(player);
        }

        /**
         * Get an entity for a connected player that uses their UniqueId.
         *
         * @param connectedPlayer a connected player
         * @return a new player entity
         */
        public static ProxyPlayerByUUID connected(@NotNull ConnectedPlayer connectedPlayer) {
            return new ProxyPlayerByUUID(connectedPlayer);
        }
    }

    /**
     * Native implementation for proxy players by username.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class ProxyPlayerByUsername extends AbstractProxyPlayerEntity<String> implements PlayerEntity.ByUsername {

        ProxyPlayerByUsername(ProxiedPlayer player) {
            super(NAMESPACE, player.getName(), player);
        }

        @Override
        public @NotNull ProxyPlayerByUsername asEntity() {
            return this;
        }

        /**
         * Get an entity for a proxied player that uses their username.
         *
         * @param player a proxied player
         * @return a new player entity
         */
        public static ProxyPlayerByUsername proxied(@NotNull ProxiedPlayer player) {
            return new ProxyPlayerByUsername(player);
        }

        /**
         * Get an entity for a connected player that uses their username.
         *
         * @param connectedPlayer a connected player
         * @return a new player entity
         */
        public static ProxyPlayerByUsername connected(@NotNull ConnectedPlayer connectedPlayer) {
            return new ProxyPlayerByUsername(connectedPlayer);
        }
    }

    /**
     * Native implementation for backend servers.
     *
     * @implSpec Namespaces take the general form of
     * <code>proxied_server_[identity_type]</code>.
     * @since 2.0.0
     * @author ms5984
     */
    public static class BackendServer extends BungeeEntity {

        BackendServer(String namespace, String identity) {
            super(namespace, identity);
        }

        /**
         * Get an entity for a backend server that uses its configured name.
         *
         * @param serverInfo a backend server info object
         * @return a new backend server entity
         * @implNote Uses the namespace <code>proxied_server_name</code>.
         */
        public static BackendServer name(@NotNull ServerInfo serverInfo) {
            return new BackendServer("proxied_server_name", serverInfo.getName());
        }
    }
}
