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

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

/**
 * A type of asset derived from a Bukkit-native structure.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class BukkitAsset extends Asset {
    /**
     * Create an asset from a group and identifier.
     * <p>
     * <b>Does not perform validation. Internal use only!</b>
     *
     * @param group the group of the asset
     * @param identifier the identifier for the asset
     */
    BukkitAsset(@NotNull String group, @NotNull String identifier) {
        super(group, identifier);
    }

    /**
     * Native implementation of item-to-asset mappings.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class Items {
        static final Items INSTANCE = new Items();
        final EnumMap<Material, MaterialAsset> cache = new EnumMap<>(Material.class);

        /**
         * Get the asset representation of a simple Material.
         *
         * @param material a given item type
         * @return the asset representation of the provided material
         * @implNote This implementation uses an internal EnumMap cache and
         * the following formula for generation:
         * <ul>
         *     <li>Group = "item"</li>
         *     <li>Identifier = "<code>material_name</code>"</li>
         * </ul>
         */
        public static MaterialAsset of(@NotNull Material material) {
            synchronized (INSTANCE.cache) {
                return INSTANCE.cache.computeIfAbsent(material, MaterialAsset::new);
            }
        }

        /**
         * Get an asset representation of the provided ItemStack.
         *
         * @param itemStack an item
         * @return an asset representation of the provided item
         * @implSpec This method is free to return both cached and new objects.
         * @implNote This implementation uses this formula for generation:
         * <ul>
         *     <li>
         *         <b>Items without meta:</b> Delegates to {@link Items#of(Material)}.
         *         <p>
         *         This is cached.
         *     </li>
         *     <li>
         *         <b>Items with meta:</b>
         *         <ul>
         *             <li>Group = "complex_item", then:</li>
         *             <ol>
         *                 <li>Serialize ItemMeta</li>
         *                 <li>Encode as base64</li>
         *             </ol>
         *             <li>Identifier =
         *             "<code>material_name</code>#<code>base64meta</code>"
         *             </li>
         *         </ul>
         *         This is not cached.
         *     </li>
         * </ul>
         */
        public static BukkitAsset of(@NotNull ItemStack itemStack) {
            final Material mat = itemStack.getType();
            // return simple Material representation if possible
            if (!itemStack.hasItemMeta()) return of(mat);
            // Serialize and encode ItemMeta
            final String base64meta = MetaItemAsset.encodeMeta(itemStack);
            // Produce Asset as {group="complex_item",identifier="material_name#base64meta"
            return new MetaItemAsset(mat, base64meta);
        }
    }
}
