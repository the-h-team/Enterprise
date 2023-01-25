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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * Represents an ItemStack with ItemMeta as an asset.
 *
 * @since 2.0.0
 * @author ms5984
 * @see Asset
 */
public final class MetaItemAsset extends BukkitAsset.Item {
    /**
     * The text added to an identifier after the material
     * name but before the serialized ItemMeta Base64.
     */
    public static final String IDENTIFIER_SLUG = "#bukkitmeta:";
    final String meta;

    /**
     * Produce item asset as
     * identifier="<code>material_name</code>#bukkitmeta:<code>base64meta</code>".
     *
     * @param material a Bukkit Material
     * @param meta a Base64 Bukkit ItemMeta representation
     */
    MetaItemAsset(Material material, String meta) {
        super(material, material.name().toLowerCase() + IDENTIFIER_SLUG + meta);
        this.meta = meta;
    }

    MetaItemAsset(Material material, ItemMeta meta) {
        this(material, encodeMeta(meta));
    }

    /**
     * Reconstruct a copy of the item that this asset represents.
     * <p>
     * <b>This method is not cached.</b> Use sparingly.
     *
     * @return a copy of the original item
     * @throws IllegalStateException if the item could not be reconstructed
     */
    public ItemStack getItem() {
        final ItemMeta meta = decodeMeta(this.meta);
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
    public @NotNull ItemAsset.Amount getAmount(@Range(from = 0, to = Integer.MAX_VALUE) int count) throws IllegalArgumentException {
        return new Amount(count, this);
    }

    static String encodeMeta(ItemMeta meta) {
        // Serialize and encode ItemMeta
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final Map<String, Object> serialized = meta.serialize();
        try (BukkitObjectOutputStream boos = new BukkitObjectOutputStream(os)) {
            boos.writeObject(serialized);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }

    static ItemMeta decodeMeta(String base64) {
        final ItemMeta meta;
        final byte[] bytes = Base64.getDecoder().decode(base64);
        try (BukkitObjectInputStream inputStream = new BukkitObjectInputStream(new ByteArrayInputStream(bytes))) {
            meta = (ItemMeta) inputStream.readObject();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return meta;
    }
}
