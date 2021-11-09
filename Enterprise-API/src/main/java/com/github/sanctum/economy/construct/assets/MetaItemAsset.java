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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Represents an ItemStack with meta as an asset.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class MetaItemAsset extends Asset implements IntegralAsset {
    final Material material;
    final String meta;

    /**
     * Produce Asset as group="complex_item",
     * identifier="<code>material_name</code>#<code>base64meta</code>".
     *
     * @param material a Bukkit Material
     * @param meta a Base64 ItemMeta representation
     */
    MetaItemAsset(Material material, String meta) {
        super("complex_item", material.name().toLowerCase() + "#" + meta);
        this.material = material;
        this.meta = meta;
    }

    /**
     * Reconstruct a copy of the item that this asset represents.
     * <p>
     * <b>This method is not cached.</b> Use sparingly.
     *
     * @return a copy of the original item
     */
    public ItemStack getItem() {
        final ItemMeta meta;
        final byte[] bytes = Base64.getDecoder().decode(this.meta);
        try (BukkitObjectInputStream inputStream = new BukkitObjectInputStream(new ByteArrayInputStream(bytes))) {
            meta = (ItemMeta) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        final ItemStack item = new ItemStack(material);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Get an Amount object for this asset's item.
     *
     * @param count the number of items
     * @return a new amount object
     * @throws IllegalArgumentException if <code>count</code> is negative
     */
    @Override
    public ItemAmount getAmount(int count) throws IllegalArgumentException {
        return new ItemAmount(count, this);
    }
}
