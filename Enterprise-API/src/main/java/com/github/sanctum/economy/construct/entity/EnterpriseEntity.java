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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Identifies an economically-involved entity.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class EnterpriseEntity {
    /**
     * Valid namespaces must start with a lowercase letter; may contain both
     * uppercase and lowercase letters, digits, underscores and hyphens between
     * the beginning and end; and must end with only a lowercase letter,
     * a digit or an underscore.
     */
    public static final Pattern VALID_NAMESPACE = Pattern.compile("[a-z][a-zA-Z0-9_-]*[a-z0-9_]");
    /**
     * Valid identifiers may contain both uppercase and lowercase letters;
     * digits, hash signs, forward-slashes, underscores, pluses; equals signs
     * and hyphens.
     */
    public static final Pattern VALID_IDENTIFIER = Pattern.compile("[a-zA-Z0-9#/_+=-]+");

    final String namespace;
    final String identity;
    final String fqn;

    /**
     * Create an entity from a namespace and identifier.
     * <p>
     * <b>Does not perform validation. Internal use only!</b>
     *
     * @param namespace the namespace for the entity
     * @param identity the namespace-unique identifier for the entity
     */
    EnterpriseEntity(@NotNull String namespace, @NotNull String identity) {
        this.namespace = namespace;
        this.identity = identity;
        fqn = namespace + ":" + identity;
    }

    /**
     * Get this entity's namespace.
     * <p>
     * This typically describes briefly what type of entity
     * this is and conforms to {@link #VALID_NAMESPACE}.
     *
     * @return the namespace of this entity
     */
    public final String getNamespace() {
        return namespace;
    }

    /**
     * Get this entity's identity key.
     * <p>
     * This is a namespace-unique identifier for this entity
     * and conforms to {@link #VALID_IDENTIFIER}.
     *
     * @return the namespace-unique identity key for this entity
     */
    public final String getIdentity() {
        return identity;
    }

    /**
     * Get the full name of this entity as its identity qualified by its group.
     * <p>
     * <b>FQNs are designed to be system-unique.</b>
     *
     * @return the full name of this entity
     * @implSpec Format is "<code>namespace</code>:<code>identity</code>".
     */
    public final String getFQN() {
        return fqn;
    }

    /**
     * A friendly name for this entity.
     * <p>
     * <b>May be human-readable. Does not need be system-unique.</b>
     *
     * @return a friendly name for this entity
     * @implSpec Prefer human-readable; does not need to be unique.
     * @implNote Defaults to {@link #identity}.
     */
    public @NotNull String friendlyName() {
        return identity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnterpriseEntity that = (EnterpriseEntity) o;
        return identity.equals(that.identity) &&
                fqn.equals(that.fqn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity, fqn);
    }

    @Override
    public String toString() {
        return getFQN();
    }

    /**
     * Marks an entity that represents a player using a typed property.
     *
     * @since 2.0.0
     * @author ms5984
     * @param <T> the type of the identifying property
     */
    public interface PlayerEntity<T> {
        /**
         * Get the identifying property of the player.
         *
         * @return an identifying property of the player
         */
        @NotNull T getIdentifyingProperty();

        /**
         * Access this object as its EnterpriseEntity form.
         *
         * @return this object as an EnterpriseEntity
         */
        EnterpriseEntity asEntity();

        /**
         * Marks an entity that represents a player using their username.
         *
         * @implSpec All implementations must use {@link ByUsername#NAMESPACE}.
         * @since 2.0.0
         * @author ms5984
         */
        interface ByUsername extends PlayerEntity<String> {
            /**
             * All entity implementations should use
             * this value as their namespace.
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
             * All entity implementations should use
             * this value as their namespace.
             */
            String NAMESPACE = "p_uid";
        }
    }

    static String validateNamespace(String namespace) {
        if (!VALID_NAMESPACE.matcher(namespace).matches()) {
            throw new IllegalArgumentException("Namespace does not follow pattern: " + VALID_NAMESPACE.pattern());
        }
        return namespace;
    }

    static String validateIdentity(String identity) {
        if (!VALID_IDENTIFIER.matcher(identity).matches()) {
            throw new IllegalArgumentException("Identity does not follow pattern: " + VALID_IDENTIFIER.pattern());
        }
        return identity;
    }
}
