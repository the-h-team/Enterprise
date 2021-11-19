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

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An item with meta that acts as a currency token.
 *
 * @since 2.0.0
 * @author ms5984
 * @see Asset
 * @see AbstractCurrency.Token
 */
public abstract class MetaItemCurrencyToken implements AbstractCurrency.Token, Predicate<ItemStack>, Supplier<MetaItemAsset> {
    final BigDecimal worth;
    final AbstractCurrency currency;
    final ItemStack item;
    final MetaItemAsset asset;

    /**
     * Produces a complex item asset with group="complex_item",
     * identifier="<code>material_name</code>#<code>base64meta</code>".
     * <p>
     * Retaining the <code>complex_item:material_name#meta</code> format
     * first defined in {@link MetaItemAsset} allows these tokens to trade
     * just like any other item with meta.
     *
     * @param material the base material for the token item
     * @param worth the worth of this token (will be normalized)
     * @param currency the currency represented by this token
     * @implNote This constructor strives to apply {@link #name()} and
     * {@link #description()} each as item display name and lore,
     * respectively.
     */
    public MetaItemCurrencyToken(Material material, @NotNull BigDecimal worth, @NotNull AbstractCurrency currency) {
        this.worth = AbstractCurrency.Token.normalize(worth);
        this.currency = currency;
        item = new ItemStack(material);
        final ItemMeta itemMeta = item.getItemMeta();
        name().map(MetaItemCurrencyToken::applyColor).ifPresent(itemMeta::setDisplayName);
        description().map(MetaItemCurrencyToken::applyColor).map(MetaItemCurrencyToken::splitAtNewline).map(Arrays::asList).ifPresent(itemMeta::setLore);
        asset = new MetaItemAsset(item.getType(), MetaItemAsset.encodeMeta(item));
    }

    @Override
    public abstract Optional<String> description();

    @Override
    public final @NotNull BigDecimal getWorth() {
        return worth;
    }

    @Override
    public final @NotNull AbstractCurrency currency() {
        return currency;
    }

    @Override
    public final boolean test(ItemStack itemStack) {
        return item.equals(itemStack);
    }

    @Override
    public MetaItemAsset get() {
        return asset;
    }

    static String[] splitAtNewline(String s) {
        return s.split("\\\\n|\n");
    }

    static String applyColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
