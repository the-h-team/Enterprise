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
 * Generate new assets with a common group.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class AssetFactory<T extends Asset> {
    private final @Asset.Group String group;
    private final BiFunction<String, String, T> constructor;

    /**
     * Create a factory for the provided asset group and base class.
     *
     * @param group asset group name
     * @param constructor a base class constructor
     */
    protected AssetFactory(@NotNull @Asset.Group String group, @NotNull BiFunction<String, String, T> constructor) {
        this.group = group;
        this.constructor = constructor;
    }

    /**
     * Get the group string of this factory.
     *
     * @return this factory's group string
     */
    public final @Asset.Group String getGroup() {
        return group;
    }

    /**
     * Create an asset with this factory's group,
     * constructor and the provided identifier.
     *
     * @param identifier asset identifier
     * @return a new asset with the given identifier
     */
    public T fromIdentifier(@NotNull @Asset.Identifier String identifier) {
        return constructor.apply(group, identifier);
    }

    /**
     * Create a factory for the provided asset group.
     *
     * @param group asset group name
     */
    public static AssetFactory<Asset> forGroup(@NotNull @Asset.Group String group) {
        return new AssetFactory<>(group, AssetImpl::new);
    }

    /**
     * Create a factory for the provided asset group using the
     * selected base class constructor function.
     *
     * @param group asset group name
     * @param constructor the constructor function to use
     */
    public static <T extends Asset> AssetFactory<T> forGroup(@NotNull @Asset.Group String group, @NotNull BiFunction<String, String, T> constructor) {
        return new AssetFactory<>(group, constructor);
    }
}
