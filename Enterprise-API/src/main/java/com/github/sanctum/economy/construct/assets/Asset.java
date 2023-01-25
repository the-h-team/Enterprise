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
package com.github.sanctum.economy.construct.assets;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Identifies a specific asset that may be holdable and/or tradable.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Asset {
    /**
     * Valid groups must start with a lowercase letter; may contain lowercase
     * letters, digits, periods, underscores and hyphens between the beginning
     * and end; and must end with only a lowercase letter, a digit
     * or an underscore.
     */
    @Language("RegExp")
    String VALID_GROUP = "[a-z][a-z0-9._-]*[a-z0-9_]";
    /**
     * Valid identifiers may contain both uppercase and lowercase letters;
     * digits, hash signs, colons, forward-slashes, periods, underscores,
     * pluses; equals signs and hyphens.
     */
    @Language("RegExp")
    String VALID_IDENTIFIER = "[a-zA-Z0-9#:/._+=-]+";
    @Pattern(VALID_GROUP)
    @interface Group {}
    @Pattern(VALID_IDENTIFIER)
    @interface Identifier {}

    /**
     * Get a brief description of this asset's type.
     * <p>
     * Built-in types include:
     * <ul>
     *     <li><code>item</code> for items.</li>
     *     <li><code>currency</code> for currencies.</li>
     * </ul>
     *
     * @return a basic description of this asset's type
     */
    @NotNull @Group String getGroup();

    /**
     * Get the unique, identifying name of this specific asset
     * among its broader type.
     *
     * @return a group-unique name for this asset
     */
    @NotNull @Identifier String getIdentifier();
}
