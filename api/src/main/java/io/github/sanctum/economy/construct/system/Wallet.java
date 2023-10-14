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
package io.github.sanctum.economy.construct.system;

import io.github.sanctum.economy.construct.entity.PlayerEntity;
import io.github.sanctum.economy.construct.entity.PlayerHandle;
import org.jetbrains.annotations.NotNull;

/**
 * Marks an object able to manage limited amounts of assets
 * being held by a player on their person/in hammerspace.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Wallet<T extends PlayerHandle> extends Balance, Contextual {
    /**
     * Gets the player handle for this wallet.
     *
     * @return the player handle for this wallet
     */
    T getPlayerHandle();

    /**
     * Gets the player entity for this wallet.
     *
     * @return the player entity for this wallet
     */
    @Override
    @NotNull PlayerEntity<?> toEntity();
}