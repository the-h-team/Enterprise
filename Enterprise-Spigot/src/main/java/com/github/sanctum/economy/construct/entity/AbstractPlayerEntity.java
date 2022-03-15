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
package com.github.sanctum.economy.construct.entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implements {@link EnterpriseEntity} for players.
 *
 * @since 2.0.0
 * @author ms5984
 * @see EnterpriseEntity
 * @param <T> the type of the identifying property
 */
public abstract class AbstractPlayerEntity<T> extends BukkitEntity implements EnterpriseEntity.PlayerEntity<T> {
    transient final OfflinePlayer player;
    final T identifyingProperty;

    AbstractPlayerEntity(String namespace, T identifyingProperty, OfflinePlayer player) {
        super(namespace, identifyingProperty.toString());
        this.identifyingProperty = identifyingProperty;
        this.player = player;
    }

    /**
     * Get the offline player represented by this entity.
     * <p>
     * Savvy users may know--{@link Player} subclasses OfflinePlayer,
     * so it's perfectly reasonable to run an <code>instanceof</code>
     * check to see if the object being returned here is in fact a
     * {@linkplain Player}.
     *
     * @return an OfflinePlayer object
     */
    public @NotNull OfflinePlayer getPlayer() {
        return player;
    }

    /**
     * Attempt to resolve this entity to an online player.
     *
     * @return an online player (if possible) or null
     */
    public @Nullable Player getOnline() {
        return player.getPlayer();
    }

    @Override
    public @NotNull String friendlyName() {
        final String playerName = player.getName();
        if (playerName != null) {
            return playerName;
        }
        return "Player:" + player.getUniqueId();
    }

    @Override
    public final @NotNull T getIdentifyingProperty() {
        return identifyingProperty;
    }
}
