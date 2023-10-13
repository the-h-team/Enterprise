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
package io.github.sanctum.economy.construct.assets;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

/**
 * Marks an asset derived from a Bukkit-native structure.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface BukkitAsset extends Asset {
    /**
     * Represents a Bukkit-native item as an ItemAsset.
     *
     * @since 2.0.0
     * @author ms5984
     */
    abstract class Item extends ItemAsset implements BukkitAsset {
        final Material material;

        Item(Material material, @Identifier String identifier) {
            super(identifier, "minecraft:" + material.name().toLowerCase());
            this.material = material;
        }

        /**
         * Gets the asset corresponding to a valid ItemStack.
         * <p>
         * Like {@link Items#of(ItemStack)}, this method is free to return
         * both cached and new objects. This is because MaterialAssets are
         * cached while MetaItemAssets are not.
         *
         * @param identifier an item asset identifier
         * @return a MaterialAsset or MetaItemAsset as appropriate
         * @throws IllegalArgumentException if the identifier cannot be
         * interpreted as a MaterialAsset nor as a MetaItemAsset.
         */
        public static Item decodeIdentifier(@NotNull @Identifier String identifier) throws IllegalArgumentException {
            final String[] split = identifier.split(MetaItemAsset.IDENTIFIER_SLUG);
            // split[0] is the material name
            final Material mat = Material.getMaterial(split[0]);
            if (mat == null) throw new IllegalArgumentException("Invalid material name: " + split[0]);
            if (split.length == 1) return Items.of(mat);
            // split[1] is the Base64 encoded ItemMeta
            final String meta = split[1];
            // verify valid meta
            try {
                return new MetaItemAsset(mat, MetaItemAsset.decodeMeta(meta));
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException("Invalid meta base64: " + meta, e);
            }
        }
    }

    /**
     * Native implementation of item-to-asset mappings.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class Items {
        static final Items INSTANCE = new Items();
        final EnumMap<Material, MaterialAsset> cache = new EnumMap<>(Material.class);

        /**
         * Gets the asset representation of a simple Material.
         *
         * @param material a given item type
         * @return the asset representation of the provided material
         * @implNote This implementation uses an internal EnumMap cache and
         * the following formula for generation:
         * <ul>
         *     <li>Group = {@link ItemAsset#GROUP}</li>
         *     <li>Identifier = "<code>material_name</code>"</li>
         * </ul>
         */
        public static MaterialAsset of(@NotNull Material material) {
            synchronized (INSTANCE.cache) {
                return INSTANCE.cache.computeIfAbsent(material, MaterialAsset::new);
            }
        }

        /**
         * Gets an asset representation of the provided ItemStack.
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
         *             <li>Group = {@link ItemAsset#GROUP}, then:</li>
         *             <ol>
         *                 <li>Serialize ItemMeta</li>
         *                 <li>Encode as base64</li>
         *             </ol>
         *             <li>Identifier =
         *             "<code>material_name</code>#bukkitmeta:<code>base64meta</code>"
         *             </li>
         *         </ul>
         *         This is not cached.
         *     </li>
         * </ul>
         */
        public static BukkitAsset.Item of(@NotNull ItemStack itemStack) {
            final Material mat = itemStack.getType();
            // return simple Material representation if possible
            if (!itemStack.hasItemMeta()) return of(mat);
            return new MetaItemAsset(mat, itemStack.getItemMeta());
        }
    }
}
