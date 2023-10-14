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
package io.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an amount of an asset.
 *
 * @implNote Amount objects are immutable and safe to use in collections.
 * @since 2.0.0
 * @see DecimalAmount
 * @see IntegralAmount
 * @author ms5984
 */
@ApiStatus.NonExtendable
public interface Amount {
    /**
     * Gets the base asset of this amount.
     *
     * @return the base asset
     */
    @NotNull Asset getAsset();
}