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
package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.assets.Amount;
import com.github.sanctum.economy.construct.assets.Asset;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A point that can be queried for its totals of assets.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Total extends Resolvable {
    /**
     * Query for the amount of this asset held.
     *
     * @param asset the asset to total
     * @return an Optional describing the amount, if present
     */
    @NotNull Optional<? extends Amount> total(@NotNull Asset asset);
}
