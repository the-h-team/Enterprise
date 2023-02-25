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

import net.md_5.bungee.api.connection.ConnectedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implements {@link EnterpriseEntity} for proxied players.
 *
 * @since 2.0.0
 * @author ms5984
 * @see EnterpriseEntity
 * @param <T> the type of the identifying property
 */
@ApiStatus.NonExtendable
public abstract class AbstractProxyPlayerEntity<T> extends BungeeEntityImpl implements EnterpriseEntity.PlayerEntity<T> {
    transient final ProxiedPlayer player;
    final T identifyingProperty;

    AbstractProxyPlayerEntity(String namespace, T identifyingProperty, ProxiedPlayer player) {
        super(namespace, identifyingProperty.toString());
        this.identifyingProperty = identifyingProperty;
        this.player = player;
    }

    /**
     * Gets the proxied player represented by this entity.
     *
     * @return a ProxiedPlayer object
     */
    public @NotNull ProxiedPlayer getPlayer() {
        return player;
    }

    /**
     * Attempts to resolve this entity to a connected player.
     *
     * @return a connected player (if possible) or null
     */
    public @Nullable ConnectedPlayer getConnectedPlayer() {
        if (player instanceof ConnectedPlayer) return (ConnectedPlayer) player;
        return null;
    }

    @Override
    public @NotNull String getFriendlyName() {
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
