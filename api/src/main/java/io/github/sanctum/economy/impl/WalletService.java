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
package io.github.sanctum.economy.impl;

import io.github.sanctum.economy.construct.entity.PlayerHandle;
import io.github.sanctum.economy.construct.system.Context;
import io.github.sanctum.economy.construct.system.Wallet;
import org.jetbrains.annotations.NotNull;

/**
 * Provides Wallets for system players.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <N> the player-by-username handle for this implementation
 * @param <U> the player-by-uniqueId handle for this implementation
 */
public interface WalletService<N extends PlayerHandle.ByUsername, U extends PlayerHandle.ByUniqueId> extends AttributableService {
    /**
     * Gets a wallet object for a player by username.
     *
     * @param player the player object
     * @return the wallet object for the player, identified by username
     */
    Wallet<N> username(@NotNull N player);

    /**
     * Gets a contextual wallet object for a player by username.
     *
     * @param player the player object
     * @param contexts the contexts to apply
     * @return the wallet object for the player, identified by username
     */
    Wallet<N> username(@NotNull N player, @NotNull Context... contexts);

    /**
     * Gets a wallet object for a player by UniqueId.
     *
     * @param player the player object
     * @return the wallet object for the player, identified by UniqueId
     */
    Wallet<U> uniqueId(U player);

    /**
     * Gets a contextual wallet object for a player by UniqueId.
     *
     * @param player the player object
     * @param contexts the contexts to apply
     * @return the wallet object for the player, identified by UniqueId
     */
    Wallet<U> uniqueId(@NotNull U player, @NotNull Context... contexts);
}