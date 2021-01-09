package com.youtube.hempfest.economy.construct.entity;

import org.jetbrains.annotations.NotNull;

public interface EconomyEntity {
    /**
     * Define a friendly name for this EconomyEntity.
     * for instance, player's name
     * @return non-empty friendly name (defaults to #id())
     */
    default @NotNull String friendlyName() {
        return id();
    }

    /**
     * Get a string which uniquely identifies this entity
     *
     * Sample implementation:
     * (Persistent Player/OfflinePlayer) UniqueId-based player id: 'p_uid=uuid'
     * (Temporary Player) Session-based player id: 'p_name=PlayerName'
     * (NPCs) NamespacedKey-based npc id: 'npc=plugin:string-key'
     * @return String which is unique to this entity
     */
    @NotNull
    String id();
}
