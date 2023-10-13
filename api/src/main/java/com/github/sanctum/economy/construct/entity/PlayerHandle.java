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
package com.github.sanctum.economy.construct.entity;

import com.github.sanctum.economy.construct.system.Resolvable;
import org.jetbrains.annotations.NotNull;

/**
 * Marks a player handle.
 *
 * @author ms5984
 * @since 2.0.0
 */
public interface PlayerHandle extends Resolvable {
    /**
     * Marks a player handle identified by a username.
     *
     * @since 2.0.0
     */
    interface ByUsername extends PlayerHandle {
        @Override
        @NotNull PlayerEntity.ByUsername toEntity();
    }

    /**
     * Marks a player handle identified by a UniqueId.
     *
     * @since 2.0.0
     */
    interface ByUniqueId extends PlayerHandle {
        @Override
        @NotNull PlayerEntity.ByUniqueId toEntity();
    }
}
