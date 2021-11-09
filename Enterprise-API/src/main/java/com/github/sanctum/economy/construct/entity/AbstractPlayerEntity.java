package com.github.sanctum.economy.construct.entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractPlayerEntity extends EnterpriseEntity implements PlayerEntity {
    transient final OfflinePlayer player;

    AbstractPlayerEntity(String namespace, String identity, OfflinePlayer player) {
        super(namespace, identity);
        this.player = player;
    }

    @Override
    public @NotNull OfflinePlayer getPlayer() {
        return player;
    }

    @Override
    public @Nullable Player getOnline() {
        return player.getPlayer();
    }
}
