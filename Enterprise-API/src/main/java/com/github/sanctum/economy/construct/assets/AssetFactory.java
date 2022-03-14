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

import java.util.function.BiFunction;

/**
 * Generate new assets using a common, normalized group.
 *
 * @since 2.0.0
 * @author ms5984
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
        this.group = Asset.validateGroup(group);
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
        return constructor.apply(group, Asset.validateIdentifier(identifier));
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
