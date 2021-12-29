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
package com.github.sanctum.economy.construct.entity;

import org.jetbrains.annotations.NotNull;

/**
 * A base for all custom economy actors.
 * <p>
 * This class is entirely meant for use by other economy plugins; Enterprise
 * is made with many entities pre-defined, including players (both by username
 * or by UniqueId), the local server console/proxy, custom "server accounts"
 * and fiduciaries (a new type of entity that maintain accounts for others).
 * <p>
 * If your use case does not fall within any of the above--for instance, you
 * want to make an item shop plugin--this is likely the class to start with.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class CustomEntity extends EnterpriseEntity {
    /**
     * Create an entity from a namespace and identifier.
     *
     * @param namespace the namespace for the entity
     * @param identity  the namespace-unique identifier for the entity
     * @throws IllegalArgumentException if <code>namespace</code> does not
     * follow the pattern of {@link EnterpriseEntity#VALID_NAMESPACE} and/or
     * <code>identity</code> does not follow the pattern of
     * {@link EnterpriseEntity#VALID_IDENTIFIER}
     */
    public CustomEntity(@NotNull String namespace, @NotNull String identity) throws IllegalArgumentException {
        super(validateNamespace(namespace), validateIdentity(identity));
    }
}
