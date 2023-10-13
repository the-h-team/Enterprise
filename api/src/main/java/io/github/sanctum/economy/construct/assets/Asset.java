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

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;

/**
 * Things of value that are holdable and/or tradable.
 *
 * @implNote Assets objects are immutable and safe to use in collections. If
 * you would like to establish any additional properties, you need to create
 * API that exposes those properties by mapping.
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.NonExtendable
public interface Asset {
    /**
     * Valid groups must start with a lowercase letter; may contain lowercase
     * letters, digits, periods, underscores and hyphens between the beginning
     * and end; and must end with only a lowercase letter, a digit
     * or an underscore.
     */
    @RegExp String VALID_GROUP = "^[a-z][a-z0-9._-]*[a-z0-9_]$";
    /**
     * Valid identifiers may contain both uppercase and lowercase letters;
     * digits, hash signs, colons, forward-slashes, periods, underscores,
     * pluses; equals signs and hyphens.
     */
    @RegExp String VALID_IDENTIFIER = "^[a-zA-Z0-9#:/._+=-]+$";

    /**
     * A String defining an asset group.
     *
     * @see #VALID_GROUP
     */
    @Documented
    @Pattern(VALID_GROUP)
    @interface Group {}

    /**
     * A String defining an asset identifier.
     *
     * @see #VALID_IDENTIFIER
     */
    @Documented
    @Pattern(VALID_IDENTIFIER)
    @interface Identifier {}

    /**
     * Gets the asset's group.
     * <p>
     * This is usually a brief description of the asset's type.
     * <p>
     * Built-in types include:
     * <ul>
     *     <li>{@code item} for items</li>
     * </ul>
     *
     * @return this asset's group
     */
    @Group @NotNull String getGroup();

    /**
     * Gets the unique, identifying name of the asset within its group.
     *
     * @return a group-unique name for this asset
     */
    @Identifier @NotNull String getIdentifier();

    /**
     * Gets an asset object with the given group and identifier.
     * <p>
     * Assets are immutable.
     *
     * @param group a valid group
     * @param identifier a valid identifier
     * @return an asset object
     */
    static Asset of(@Group @NotNull String group, @Identifier @NotNull String identifier) {
        return new AssetImpl(group, identifier);
    }
}
