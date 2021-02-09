/*
 *  Copyright 2021 Sanctum <https://github.com/the-h-team>
 *  Copyright 2020 Hempfest <https://github.com/Hempfest>
 *  Copyright 2020 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of Enterprise.
 *
 *  Enterprise is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  Enterprise is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.sanctum.economy.construct.entity.types;

import com.github.sanctum.economy.construct.entity.EconomyEntity;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Base class for NPCs (recommended over String)
 */
public class NpcEntity implements EconomyEntity {

    protected final NamespacedKey namespacedKey;
    protected final String internalName;
    protected String friendlyName;
    protected final String pluginName;

    /**
     * The recommended constructor for NPC entities.
     * Enables retrieval of the plugin's name.
     * <p>The plugin reference is used to create a
     * NamespacecdKey internally.</p>
     * @param plugin the plugin which is responsible for this NPC
     * @param name a String name for this npc
     */
    public NpcEntity(@NotNull Plugin plugin, @NotNull String name) {
        this.internalName = name;
        this.friendlyName = name;
        this.namespacedKey = new NamespacedKey(plugin, name);
        this.pluginName = plugin.getName();
    }

    /**
     * Get the name of this NPC's plugin if a reference was stored
     * @return Plugin's name if stored, or null
     */
    @Nullable
    public String getPluginName() {
        return pluginName;
    }

    /**
     * Set the new name of this NPC. Requires similar Plugin reference
     * to prevent overwriting by any plugin.
     * @param plugin original plugin that created this NPC
     * @param friendlyName new name of this NPC
     */
    public void setFriendlyName(Plugin plugin, String friendlyName) {
        final NamespacedKey newKey = new NamespacedKey(plugin, internalName);
        if (namespacedKey.equals(newKey)) {
            this.friendlyName = friendlyName;
        }
    }

    @Override
    public @NotNull String friendlyName() {
        return friendlyName;
    }

    @Override
    public @NotNull String id() {
        return "npc=" + namespacedKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NpcEntity npcEntity = (NpcEntity) o;
        return namespacedKey.equals(npcEntity.namespacedKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespacedKey, internalName);
    }
}
