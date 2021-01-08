package com.youtube.hempfest.economy.construct.entity;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

/**
 * Base class for NPCs (recommended over String)
 */
public class NpcEntity implements Entity {

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
    public String friendlyName() {
        return friendlyName;
    }

    @Override
    public String id() {
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
