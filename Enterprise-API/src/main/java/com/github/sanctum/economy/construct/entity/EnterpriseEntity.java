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

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Identifies an economically-involved actor.
 * <p>
 * In Enterprise, the identity of economy actors and their abilities are
 * separated into API for defining identity and API that can define and
 * redefine actors' abilities. This class (and all of its subclasses)
 * are particularly focused on that first component--defining identity
 * in format and functionality.
 *
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.NonExtendable
public interface EnterpriseEntity {
    /**
     * Valid namespaces must start with a lowercase letter; may contain both
     * uppercase and lowercase letters, digits, underscores and hyphens between
     * the beginning and end; and must end with only a lowercase letter,
     * a digit or an underscore.
     */
    @RegExp String VALID_NAMESPACE = "[a-z]([a-zA-Z0-9_-]*[a-z0-9_])?";
    /**
     * Valid identities may contain both uppercase and lowercase letters;
     * digits, hash signs, forward-slashes, underscores, pluses; equals signs
     * and hyphens.
     */
    @RegExp String VALID_IDENTITY = "[a-zA-Z0-9#/_+=-]+";

    @Pattern(VALID_NAMESPACE)
    @interface Namespace {}
    @Pattern(VALID_IDENTITY)
    @interface Identity {}

    /**
     * Get this entity's namespace.
     * <p>
     * Namespaces describe briefly what type of participant entities
     * represent and conform to {@link #VALID_NAMESPACE}.
     *
     * @return the namespace of this entity
     */
    @Namespace @NotNull String getNamespace();

    /**
     * Get this entity's identity key.
     * <p>
     * Identity is a namespace-unique identifier for this
     * entity and conforms to {@link #VALID_IDENTITY}.
     *
     * @return the namespace-unique identity key for this entity
     */
    @Identity @NotNull String getIdentity();

    /**
     * A friendly name for this entity.
     * <p>
     * <b>May be human-readable. Need not be system-unique.</b>
     *
     * @return a friendly name for this entity
     * @implSpec Prefer human-readable; does not need to be unique.
     * @implNote Defaults to {@link #getIdentity()}.
     */
    default @NotNull String getFriendlyName() {
        return getIdentity();
    }

    /**
     * Marks an entity that represents a player using a typed property.
     *
     * @since 2.0.0
     * @author ms5984
     * @param <T> the type of the identifying property
     */
    interface PlayerEntity<T> extends EnterpriseEntity {
        /**
         * Get the identifying property of the player.
         *
         * @return an identifying property of the player
         */
        @NotNull T getIdentifyingProperty();

        /**
         * Marks an entity that represents a player using their username.
         *
         * @implSpec All implementations must use {@link ByUsername#NAMESPACE}.
         * @since 2.0.0
         * @author ms5984
         */
        interface ByUsername extends PlayerEntity<String> {
            /**
             * The namespace of all entities identifying a player by username.
             */
            String NAMESPACE = "p_username";
        }

        /**
         * Marks an entity that represents a player using their UniqueId.
         *
         * @implSpec All implementations must use {@link ByUniqueId#NAMESPACE}.
         * @since 2.0.0
         * @author ms5984
         */
        interface ByUniqueId extends PlayerEntity<UUID> {
            /**
             * The namespace of all entities identifying a player by UniqueId.
             */
            String NAMESPACE = "p_uid";
        }
    }

    /**
     * A base for all custom economy actors.
     * <p>
     * This class is entirely meant for use by other economy plugins; Enterprise
     * is made with many entities pre-defined, including players (both by username
     * or by UniqueId), the local server console/proxy, custom "server accounts"
     * and fiduciaries (a new type of entity that maintain accounts for others).
     * <p>
     * If your use case does not fall within any of the above--for instance, you
     * want to make an item shop plugin--this is likely the class to start with.
     *
     * @since 2.0.0
     * @author ms5984
     */
    abstract class Custom implements EnterpriseEntity {
        protected final @Namespace String namespace;
        protected final @Identity String identity;

        protected Custom(@Namespace @NotNull String namespace, @Identity @NotNull String identity) {
            this.namespace = namespace;
            this.identity = identity;
        }

        @Override
        public @Namespace @NotNull String getNamespace() {
            return namespace;
        }

        @Override
        public @Identity @NotNull String getIdentity() {
            return identity;
        }
    }
}
