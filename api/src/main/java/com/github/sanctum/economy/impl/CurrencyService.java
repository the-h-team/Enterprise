/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
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

import com.github.sanctum.economy.construct.assets.AbstractCurrency;
import com.github.sanctum.economy.construct.system.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Provides system currency data.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface CurrencyService extends AttributableService {
    /**
     * Gets the main currency for this system.
     * <p>
     * If you only have one global currency, reference it here.
     *
     * @return an Optional describing the main system currency
     */
    @NotNull Optional<? extends AbstractCurrency> getMainCurrency();

    /**
     * Gets the main currency for a particular world.
     *
     * @param world a world context
     * @return an Optional describing <code>world</code>'s main currency
     * @implSpec Defaults to empty; prefer to override only if
     * you really want to support multiple worlds separately.
     */
    default @NotNull Optional<? extends AbstractCurrency> getWorldCurrency(@NotNull Context.World world) {
        return Optional.empty();
    }
}
