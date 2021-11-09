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
 * Implements {@link EnterpriseEntity} for players while including
 * {@link PlayerEntity}'s function.
 *
 * @since 2.0.0
 * @author ms5984
 * @see EnterpriseEntity
 * @see PlayerEntity
 */
public abstract class AbstractPlayerEntity extends EnterpriseEntity implements PlayerEntity {
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
