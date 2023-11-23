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
package io.github.sanctum.economy.construct.system.accounts;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A simple set of account access levels.
 *
 * @since 2.0.0
 * @author ms5984
 */
public enum SimpleAccessLevel implements Account.AccessLevel {
    /**
     * View-only account access.
     * <p>
     * <h2>Example permissions:</h2>
     * <ul>
     *     <li>View account balance</li>
     * </ul>
     */
    VIEWER,
    /**
     * An ordinary level of account access allowing transaction.
     * <p>
     * <h2>Example permissions:</h2>
     * <ul>
     *     <li>View account balance</li>
     *     <li>Deposit funds</li>
     *     <li>Withdraw funds</li>
     * </ul>
     */
    MEMBER,
    /**
     * An account access level with additional meta-permissions.
     * <p>
     * By default, co-owners can manage {@linkplain #VIEWER VIEWERS}
     * and {@linkplain #MEMBER MEMBERS}, but not {@linkplain #OWNER OWNERS}
     * or other co-owners.
     * <h2>Example permissions:</h2>
     * <ul>
     *     <li>View account balance</li>
     *     <li>Deposit funds</li>
     *     <li>Withdraw funds</li>
     *     <li>Manage {@linkplain #VIEWER viewers}</li>
     *     <li>Manage {@linkplain #MEMBER members}</li>
     * </ul>
     */
    CO_OWNER("co-owner"),
    /**
     * Full account access.
     * <h2>Example permissions:</h2>
     * <ul>
     *     <li>View account balance</li>
     *     <li>Deposit funds</li>
     *     <li>Withdraw funds</li>
     *     <li>Manage {@linkplain #VIEWER viewers}</li>
     *     <li>Manage {@linkplain #MEMBER members}</li>
     *     <li>Manage {@linkplain #CO_OWNER co-owners}</li>
     *     <li>Manage owners</li>
     *     <li>Close account</li>
     * </ul>
     */
    OWNER,
    ;

    public final String label;

    SimpleAccessLevel() {
        this.label = name().toLowerCase();
    }

    SimpleAccessLevel(String label) {
        this.label = label;
    }

    @Override
    public @NotNull String getName() {
        return label;
    }

    /**
     * Gets a simple access level by name, if possible.
     *
     * @param name the name of the access level
     * @return the access level or {@code null} if not found
     */
    public static @Nullable SimpleAccessLevel of(@NotNull String name) {
        for (SimpleAccessLevel value : values()) {
            if (value.getName().equalsIgnoreCase(name)) return value;
        }
        return null;
    }
}
