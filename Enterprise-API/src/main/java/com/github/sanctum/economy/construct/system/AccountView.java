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
package com.github.sanctum.economy.construct.system;

/**
 * A specialization of {@link Balance}, exposing its functionality
 * from the context of an entity that is permitted access.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface AccountView extends Balance {
    /**
     * Does this view belong to an account owner?
     *
     * @return true if the context entity is an owner
     */
    default boolean isOwner() {
        return true;
    }

    /**
     * Does this view belong to one of multiple owners?
     *
     * @return true if the context entity a co-owner
     * @implSpec If this method returns <code>true</code>,
     * {@link #isOwner()} must also return <code>true</code>.
     */
    default boolean isJointOwner() {
        return false;
    }
}
