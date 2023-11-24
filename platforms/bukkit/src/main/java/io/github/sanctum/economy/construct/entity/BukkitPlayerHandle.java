/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.construct.entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bukkit-platform player handle implementations.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class BukkitPlayerHandle implements PlayerHandle, BukkitEntity.PlayerHandle {
    /**
     * A player handle identified by username.
     *
     * @since 2.0.0
     */
    public static class ByUsername extends BukkitPlayerHandle implements PlayerHandle.ByUsername {
        final PlayerEntity.ByUsername entity;

        /**
         * Creates a player handle by username from the respective player entity.
         *
         * @param entity a player entity by username
         */
        public ByUsername(@NotNull PlayerEntity.ByUsername entity) {
            this.entity = entity;
        }

        @SuppressWarnings("deprecation")
        @Override
        public @Nullable OfflinePlayer getOfflinePlayer() {
            final Player player = getPlayer();
            if (player != null) return player;
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entity.identifyingProperty);
            return sanityCheckOfflinePlayer(offlinePlayer);
        }

        @Override
        public @Nullable Player getPlayer() {
            return Bukkit.getPlayerExact(entity.identifyingProperty);
        }

        @Override
        public final @NotNull PlayerEntity.ByUsername toEntity() {
            return entity;
        }
    }

    /**
     * A player handle identified by the player's UniqueId.
     *
     * @since 2.0.0
     */
    public static class ByUniqueId extends BukkitPlayerHandle implements PlayerHandle.ByUniqueId {
        final PlayerEntity.ByUniqueId entity;

        /**
         * Creates a player handle by UniqueId from the respective player entity.
         *
         * @param entity a player entity by UniqueId
         */
        public ByUniqueId(@NotNull PlayerEntity.ByUniqueId entity) {
            this.entity = entity;
        }

        @Override
        public @Nullable OfflinePlayer getOfflinePlayer() {
            return sanityCheckOfflinePlayer(Bukkit.getOfflinePlayer(entity.identifyingProperty));
        }

        @Override
        public final @NotNull PlayerEntity.ByUniqueId toEntity() {
            return entity;
        }
    }

    /**
     * Gets the player associated with this handle.
     *
     * @return the player associated with this handle
     */
    public @Nullable Player getPlayer() {
        final OfflinePlayer offlinePlayer = getOfflinePlayer();
        if (offlinePlayer != null) return offlinePlayer.getPlayer();
        return null;
    }

    /**
     * Gets the offline player associated with this handle.
     *
     * @return the offline player associated with this handle
     */
    public abstract @Nullable OfflinePlayer getOfflinePlayer();

    private static @Nullable OfflinePlayer sanityCheckOfflinePlayer(@NotNull OfflinePlayer offlinePlayer) {
        if (offlinePlayer.getName() != null) return offlinePlayer;
        return null;
    }
}
