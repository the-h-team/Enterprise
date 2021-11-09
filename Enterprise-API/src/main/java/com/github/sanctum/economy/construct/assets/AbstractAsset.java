package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.NotNull;

/**
 * The base for custom asset classes.
 *
 * @since 2.0.0
 */
public class AbstractAsset extends Asset {
    /**
     * Create an asset from a group and an identifier.
     *
     * @param group the group of the asset
     * @param identifier the identifier for the asset
     * @throws IllegalArgumentException if <code>group</code>
     * does not follow the pattern of {@link Asset#VALID_GROUP} and/or
     * cannot be normalized to match via {@link String#toLowerCase()}
     * and/or if <code>identifier</code> does not follow the pattern
     * of {@link Asset#VALID_IDENTIFIER}
     */
    protected AbstractAsset(@NotNull String group, @NotNull String identifier) throws IllegalArgumentException {
        super(validateGroup(group), validateIdentifier(identifier));
    }

    static String validateGroup(String group) throws IllegalArgumentException {
        final String normalized = group.toLowerCase();
        if (!Asset.VALID_GROUP.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Group does not follow pattern: " + Asset.VALID_GROUP.pattern());
        }
        return normalized;
    }

    static String validateIdentifier(String identifier) throws IllegalArgumentException {
        if (!Asset.VALID_IDENTIFIER.matcher(identifier).matches()) {
            throw new IllegalArgumentException("Identifier does not follow pattern: " + Asset.VALID_IDENTIFIER.pattern());
        }
        return identifier;
    }
}
