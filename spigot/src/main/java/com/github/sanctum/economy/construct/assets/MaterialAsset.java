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
package com.github.sanctum.economy.construct.assets;

import org.bukkit.Material;

/**
 * Represents a Material as an asset.
 *
 * @since 2.0.0
 * @author ms5984
 * @see Asset
 */
public final class MaterialAsset extends BukkitAsset.Item {

    MaterialAsset(Material material) {
        super(material, material.name().toLowerCase());
    }

    /**
     * Gets the Material this asset represents.
     *
     * @return the Material of this asset
     */
    public Material getMaterial() {
        return material;
    }
}
