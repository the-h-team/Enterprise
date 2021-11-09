package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Generate new assets using a common, normalized group.
 *
 * @since 2.0.0
 */
public class AssetFactory<T extends Asset> {
    final String group;
    final BiFunction<String, String, T> constructor;

    /**
     * Create a factory for the provided asset group and base class.
     *
     * @param group asset group name
     * @param constructor a base class constructor
     * @throws IllegalArgumentException if <code>group</code>
     * does not follow the pattern of {@link Asset#VALID_GROUP} and/or
     * cannot be normalized to match via {@link String#toLowerCase()}
     */
    protected AssetFactory(@NotNull String group, @NotNull BiFunction<String, String, T> constructor) throws IllegalArgumentException {
        this.group = group.toLowerCase();
        if (!Asset.VALID_GROUP.matcher(this.group).matches()) {
            throw new IllegalArgumentException("Group does not follow pattern: " + Asset.VALID_GROUP.pattern());
        }
        this.constructor = constructor;
    }

    /**
     * Get the group string of this factory.
     *
     * @return this factory's group string
     */
    public final String getGroup() {
        return group;
    }

    /**
     * Create an asset with this factory's group,
     * constructor and the provided identifier.
     *
     * @param identifier asset identifier
     * @return a new asset with the given identifier
     * @throws IllegalArgumentException if <code>identifier</code>
     * does not follow the pattern of {@link Asset#VALID_IDENTIFIER}
     */
    public T fromIdentifier(@NotNull String identifier) throws IllegalArgumentException {
        if (!Asset.VALID_IDENTIFIER.matcher(identifier).matches()) {
            throw new IllegalArgumentException("Identifier does not follow pattern: " + Asset.VALID_IDENTIFIER.pattern());
        }
        return constructor.apply(group, identifier);
    }

    /**
     * Create a factory for the provided asset group.
     *
     * @param group asset group name
     * @throws IllegalArgumentException if <code>group</code>
     * does not follow the pattern of {@link Asset#VALID_GROUP} and/or
     * cannot be normalized to match via {@link String#toLowerCase()}
     */
    public static AssetFactory<Asset> forGroup(@NotNull String group) throws IllegalArgumentException {
        return new AssetFactory<>(group, Asset::new);
    }

    /**
     * Create a factory for the provided asset group using the
     * selected base class constructor function.
     *
     * @param group asset group name
     * @param constructor the constructor function to use
     * @throws IllegalArgumentException if <code>group</code>
     * does not follow the pattern of {@link Asset#VALID_GROUP} and/or
     * cannot be normalized to match via {@link String#toLowerCase()}
     */
    public static <T extends AbstractAsset> AssetFactory<T> forGroup(@NotNull String group, @NotNull BiFunction<String, String, T> constructor) throws IllegalArgumentException {
        return new AssetFactory<>(group, constructor);
    }
}
