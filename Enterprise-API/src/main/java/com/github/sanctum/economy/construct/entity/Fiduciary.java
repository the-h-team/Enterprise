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
 * A special type of entity that is responsible for the safekeeping
 * and/or management of others' assets.
 * <p>
 * This is akin to banks, credit unions and brokerage firms.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class Fiduciary extends EnterpriseEntity {
    /**
     * Create a fiduciary from a namespace and identifier.
     *
     * @param namespace the namespace for the entity
     * @param identity the namespace-unique identifier for the entity
     * @throws IllegalArgumentException if <code>namespace</code> does not
     * follow the pattern of {@link EnterpriseEntity#VALID_NAMESPACE} and/or
     * <code>identity</code> does not follow the pattern of
     * {@link EnterpriseEntity#VALID_IDENTIFIER}
     */
    public Fiduciary(@NotNull String namespace, @NotNull String identity) throws IllegalArgumentException {
        super(validateNamespace(namespace), validateIdentity(identity));
    }
}
