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
package io.github.sanctum.economy.construct.system.behaviors;

import io.github.sanctum.economy.construct.assets.Amount;
import io.github.sanctum.economy.construct.system.*;
import io.github.sanctum.economy.construct.system.exceptions.AbstractSystemException;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.NotNull;

/**
 * A point that can be tested for the holding of assets.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Queryable extends Resolvable {
    /**
     * Checks for an amount on this point.
     *
     * @param amount an amount of an asset
     * @return true if this point has the amount
     * @throws AbstractSystemException if a system error occurs
     */
    boolean has(@NotNull Amount amount) throws AbstractSystemException;

    /**
     * Checks for an amount on this point.
     *
     * @param amount an amount of an asset
     * @return a pending result
     */
    default @NotNull PendingResult<Result.NotEmpty<Boolean>, Boolean> asyncHas(@NotNull Amount amount) {
        return PendingResult.of(() -> has(amount));
    }
}
