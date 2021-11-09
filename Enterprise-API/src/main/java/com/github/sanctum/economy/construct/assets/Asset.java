package com.github.sanctum.economy.construct.assets;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Identifies a specific asset that may be holdable and/or tradable.
 *
 * @since 2.0.0
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

    /**
     * Native implementation of item-to-asset mappings.
     *
     * @since 2.0.0
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
        public static Asset of(@NotNull ItemStack itemStack) {
            final Material mat = itemStack.getType();
            // return simple Material representation if possible
            if (!itemStack.hasItemMeta()) return of(mat);
            // Serialize and encode ItemMeta
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            final Map<String, Object> serialized = itemStack.getItemMeta().serialize();
            try (BukkitObjectOutputStream boos = new BukkitObjectOutputStream(os)) {
                boos.writeObject(serialized);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            final String base64meta = Base64.getEncoder().encodeToString(os.toByteArray());
            // Produce Asset as {group="complex_item",identifier="material_name#base64meta"
            return new MetaItemAsset(mat, base64meta);
        }
    }
}
