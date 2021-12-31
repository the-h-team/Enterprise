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
package com.github.sanctum.economy.impl;

import com.github.sanctum.economy.construct.entity.EnterpriseEntity;
import com.github.sanctum.economy.construct.system.Context;
import com.github.sanctum.economy.construct.system.Wallet;
import org.jetbrains.annotations.NotNull;

/**
 * Provides Wallets for system players.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface WalletService {
    /**
     * Get the system implementation info associated with this service.
     *
     * @return associated implementation info
     */
    SystemImplementation getImplementationInfo();

    /**
     * Get a wallet object for a player by username.
     *
     * @param player the player object
     * @param <T> the inferred type bound (this is used by the wallet system)
     * @return the wallet object for the player, identified by username
     */
    <T extends EnterpriseEntity & EnterpriseEntity.PlayerEntity.ByUsername> Wallet<T> username(@NotNull T player);

    /**
     * Get a contextual wallet object for a player by username.
     *
     * @param player the player object
     * @param contexts the contexts to apply
     * @param <T> the inferred type bound (this is used by the wallet system)
     * @return the wallet object for the player, identified by username
     */
    <T extends EnterpriseEntity & EnterpriseEntity.PlayerEntity.ByUsername> Wallet<T> username(@NotNull T player, @NotNull Context... contexts);

    /**
     * Get a wallet object for a player by UniqueId.
     *
     * @param player the player object
     * @param <T> the inferred type bound (this is used by the wallet system)
     * @return the wallet object for the player, identified by UniqueId
     */
    <T extends EnterpriseEntity & EnterpriseEntity.PlayerEntity.ByUniqueId> Wallet<T> uniqueId(T player);

    /**
     * Get a contextual wallet object for a player by UniqueId.
     *
     * @param player the player object
     * @param contexts the contexts to apply
     * @param <T> the inferred type bound (this is used by the wallet system)
     * @return the wallet object for the player, identified by UniqueId
     */
    <T extends EnterpriseEntity & EnterpriseEntity.PlayerEntity.ByUniqueId> Wallet<T> uniqueId(@NotNull T player, @NotNull Context... contexts);
}
