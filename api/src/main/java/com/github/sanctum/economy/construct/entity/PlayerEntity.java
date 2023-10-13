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
package com.github.sanctum.economy.construct.entity;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a player as an entity using a typed identifying property.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <T> the type of the identifying property
 */
public interface PlayerEntity<T> extends EnterpriseEntity {
    /**
     * The namespace of native entities identifying a player by username.
     *
     * @see PlayerEntity
     */
    @Namespace String BY_USERNAME = "pl_username";
    /**
     * The namespace of all entities identifying a player by UniqueId.
     *
     * @see PlayerEntity
     */
    @Namespace String BY_UNIQUE_ID = "pl_uuid";

    /**
     * Represents a player as an entity using their username.
     *
     * @author ms5984
     * @see #BY_USERNAME
     * @since 2.0.0
     */
    final class ByUsername extends PlayerEntityImpl<String> implements PlayerEntity<String> {
        /**
         * Constructs an entity from a player's username.
         *
         * @param username the username of the player
         */
        public ByUsername(@IdentityKey @NotNull String username) {
            super(BY_USERNAME, username);
        }
    }

    /**
     * Represents a player as an entity using their UniqueId.
     *
     * @author ms5984
     * @see #BY_UNIQUE_ID
     * @since 2.0.0
     */
    final class ByUniqueId extends PlayerEntityImpl<UUID> implements PlayerEntity<UUID> {
        /**
         * Constructs an entity from a player's UniqueId.
         *
         * @param uuid the UniqueId of the player
         */
        public ByUniqueId(@NotNull UUID uuid) {
            super(BY_UNIQUE_ID, uuid);
        }
    }

    /**
     * Gets the identifying property of the player.
     *
     * @return an identifying property of the player
     */
    @NotNull T getIdentifyingProperty();
}
