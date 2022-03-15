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
package com.github.sanctum.economy.construct.assets;

import com.github.sanctum.economy.construct.IntegralAmount;
import org.jetbrains.annotations.NotNull;

/**
 * Marks an asset which can be divided by whole number.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface IntegralAsset {
    /**
     * Get an amount for this asset.
     *
     * @param count the whole number count of the asset
     * @return a new amount object
     * @throws IllegalArgumentException if <code>count</code> is negative
     */
    @NotNull IntegralAmount getAmount(int count);
}
