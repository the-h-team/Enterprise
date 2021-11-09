package com.github.sanctum.economy.construct.assets;

import org.bukkit.Material;

/**
 * Represents a Material as an asset.
 *
 * @since 2.0.0
 */
public final class MaterialAsset extends Asset implements IntegralAsset {
    final Material material;

    /**
     * Produce Asset as group="item", identifier="<code>material_name</code>".
     *
     * @param material a Bukkit Material
     */
    MaterialAsset(Material material) {
        super("item", material.name().toLowerCase());
        this.material = material;
    }

    /**
     * Get the Material this asset represents.
     *
     * @return the Material of this asset
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Get an Amount object for this material.
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
