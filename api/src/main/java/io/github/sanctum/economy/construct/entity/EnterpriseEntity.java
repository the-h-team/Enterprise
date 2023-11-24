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

import io.github.sanctum.economy.construct.system.Resolvable;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;

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
public interface EnterpriseEntity extends Resolvable {
    /**
     * Valid namespaces must start with a lowercase letter; may contain both
     * uppercase and lowercase letters, digits, underscores and hyphens between
     * the beginning and end; and must end with only a lowercase letter,
     * a digit or an underscore.
     */
    @RegExp String VALID_NAMESPACE = "^[a-z]([a-zA-Z0-9_-]*[a-z0-9_])?$";
    /**
     * Valid identities may contain both uppercase and lowercase letters;
     * digits, hash signs, forward-slashes, underscores, pluses; equals signs
     * and hyphens.
     */
    @RegExp String VALID_IDENTITY_KEY = "^[a-zA-Z0-9#/_+=-]+$";

    /**
     * A String defining an entity namespace.
     *
     * @see #VALID_NAMESPACE
     */
    @Documented
    @Pattern(VALID_NAMESPACE)
    @interface Namespace {}

    /**
     * A String defining entity identity.
     *
     * @see #VALID_IDENTITY_KEY
     */
    @Documented
    @Pattern(VALID_IDENTITY_KEY)
    @interface IdentityKey {}

    /**
     * Gets the namespace of this entity.
     * <p>
     * Namespaces describe briefly what type of participant entities
     * represent and conform to {@link #VALID_NAMESPACE}.
     *
     * @return the namespace of this entity
     */
    @Namespace @NotNull String getNamespace();

    /**
     * Gets the identity key of this entity.
     * <p>
     * An identity key is a namespace-unique identifier for this
     * entity and conforms to {@link #VALID_IDENTITY_KEY}.
     *
     * @return the namespace-unique identity key for this entity
     */
    @IdentityKey @NotNull String getIdentityKey();

    /**
     * Gets the friendly name for this entity.
     * <p>
     * <b>May be human-readable. Need not be system-unique.</b>
     *
     * @return the friendly name for this entity
     * @implSpec Prefer human-readable; does not need to be (globally) unique.
     * @implNote Defaults to {@link #getIdentityKey()}.
     */
    default @NotNull String getFriendlyName() {
        return getIdentityKey();
    }

    @Override
    default @NotNull EnterpriseEntity toEntity() {
        return this;
    }

    /**
     * A base for all custom economy actors.
     * <p>
     * This class is entirely meant for use by other economy plugins; Enterprise
     * is made with many entities pre-defined, including players (both by username
     * or by UniqueId), the local server console/proxy, custom "server accounts"
     * and custodians (a new type of entity that maintain accounts for others).
     * <p>
     * If your use case does not fall within any of the above--for instance, you
     * want to make an item shop plugin--this is likely the class to start with.
     *
     * @since 2.0.0
     * @author ms5984
     */
    abstract class Custom extends EnterpriseEntityImpl implements EnterpriseEntity {
        /**
         * Constructs a custom entity.
         *
         * @param namespace the namespace of this entity
         * @param identityKey the identity key of this entity
         * @throws IllegalArgumentException if {@code namespace} does not validate
         * against {@link #VALID_NAMESPACE} or if {@code identityKey} does not
         * validate against {@link #VALID_IDENTITY_KEY}
         */
        protected Custom(@NotNull String namespace, @NotNull String identityKey) {
            super(verifyNamespace(namespace), verifyIdentityKey(identityKey));
        }
    }

    /**
     * Verifies that a namespace is valid.
     *
     * @param namespace a namespace to verify
     * @return the namespace if valid
     * @throws IllegalArgumentException if {@code namespace} does not validate against {@link #VALID_NAMESPACE}
     */
    static @NotNull String verifyNamespace(@NotNull String namespace) throws IllegalArgumentException {
        if (!namespace.matches(VALID_NAMESPACE)) throw new IllegalArgumentException("Invalid namespace: " + namespace);
        return namespace;
    }

    /**
     * Verifies that an identity key is valid.
     *
     * @param identityKey an identity key to verify
     * @return the identity key if valid
     * @throws IllegalArgumentException if {@code identityKey} does not validate against {@link #VALID_IDENTITY_KEY}
     */
    static @NotNull String verifyIdentityKey(@NotNull String identityKey) throws IllegalArgumentException {
        if (!identityKey.matches(VALID_IDENTITY_KEY)) throw new IllegalArgumentException("Invalid identity key: " + identityKey);
        return identityKey;
    }
}
