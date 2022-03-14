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
package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Identifies a specific asset that may be holdable and/or tradable.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class Asset {
    /**
     * Valid groups must start with a lowercase letter; may contain lowercase
     * letters, digits, periods, underscores and hyphens between the beginning
     * and end; and must end with only a lowercase letter, a digit
     * or an underscore.
     */
    public static final Pattern VALID_GROUP = Pattern.compile("[a-z][a-z0-9._-]*[a-z0-9_]");
    /**
     * Valid identifiers may contain both uppercase and lowercase letters;
     * digits, hash signs, forward-slashes, periods, underscores, pluses;
     * equals signs and hyphens.
     */
    public static final Pattern VALID_IDENTIFIER = Pattern.compile("[a-zA-Z0-9#/._+=-]+");

    final String group;
    final String identifier;
    final String fqn;

    /**
     * Create an asset from a group and identifier.
     * <p>
     * <b>Does not perform validation. Internal use only!</b>
     *
     * @param group the group of the asset
     * @param identifier the identifier for the asset
     */
    Asset(@NotNull String group, @NotNull String identifier) {
        this.group = group;
        this.identifier = identifier;
        fqn = group + ":" + identifier;
    }

    /**
     * Get a brief description of this asset's type.
     * <p>
     * Built-in types include:
     * <ul>
     *     <li><code>item</code> for basic items.</li>
     *     <li><code>complex_item</code> for items with meta.</li>
     * </ul>
     *
     * @return a basic description of this asset's type
     */
    public final String getGroup() {
        return group;
    }

    /**
     * Get the unique, identifying name of this specific asset
     * among its broader type.
     *
     * @return a group-unique name for this asset
     */
    public final String getIdentifier() {
        return identifier;
    }

    /**
     * Get the full name of this asset as its identifier
     * qualified by its group.
     *
     * @return the full name of this asset
     * @implSpec Format is "<code>group</code>:<code>identifier</code>".
     */
    public final String getFQN() {
        return fqn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return identifier.equals(asset.identifier) &&
                fqn.equals(asset.fqn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, fqn);
    }

    @Override
    public String toString() {
        return getFQN();
    }

    static String validateGroup(String group) throws IllegalArgumentException {
        final String normalized = group.toLowerCase();
        if (!VALID_GROUP.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Group does not follow pattern: " + VALID_GROUP.pattern());
        }
        return normalized;
    }

    static String validateIdentifier(String identifier) throws IllegalArgumentException {
        if (!VALID_IDENTIFIER.matcher(identifier).matches()) {
            throw new IllegalArgumentException("Identifier does not follow pattern: " + VALID_IDENTIFIER.pattern());
        }
        return identifier;
    }
}
