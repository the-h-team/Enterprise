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

import org.jetbrains.annotations.NotNull;

/**
 * The base for custom asset classes.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class AbstractAsset extends Asset {
    /**
     * Create an asset from a group and an identifier.
     *
     * @param group the group of the asset
     * @param identifier the identifier for the asset
     * @throws IllegalArgumentException if <code>group</code>
     * does not follow the pattern of {@link Asset#VALID_GROUP} and/or
     * cannot be normalized to match via {@link String#toLowerCase()}
     * and/or if <code>identifier</code> does not follow the pattern
     * of {@link Asset#VALID_IDENTIFIER}
     */
    protected AbstractAsset(@NotNull String group, @NotNull String identifier) throws IllegalArgumentException {
        super(validateGroup(group), validateIdentifier(identifier));
    }
}
