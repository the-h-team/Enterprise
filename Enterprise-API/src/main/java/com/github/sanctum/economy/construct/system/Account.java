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

import com.github.sanctum.economy.construct.entity.EnterpriseEntity;
import com.github.sanctum.economy.construct.entity.Fiduciary;
import org.jetbrains.annotations.NotNull;

/**
 * Manage assets on account with a custodian.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Account {
    /**
     * Get the custodian for this account.
     *
     * @return the fiduciary responsible for this account
     */
    @NotNull Fiduciary getCustodian();

    /**
     * Access this account from the context of the provided entity.
     *
     * @param entity an entity
     * @return an object with the level of access desired for the entity
     * @throws AccessDenied if the entity is not allowed access
     */
    @NotNull AccountView accessAs(@NotNull EnterpriseEntity entity) throws AccessDenied;

    /**
     * Raised if an entity is not permitted access to an account.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class AccessDenied extends EntityException {
        private static final long serialVersionUID = 1113963021969850967L;

        /**
         * Construct an exception with an Entity and a message.
         *
         * @param entity the denied entity
         * @param message a message
         */
        public AccessDenied(@NotNull EnterpriseEntity entity, String message) {
            super(entity, message);
        }

        /**
         * Construct an exception with an Entity, a message and cause.
         *
         * @param entity the denied entity
         * @param message a message
         * @param cause a cause throwable
         */
        public AccessDenied(@NotNull EnterpriseEntity entity, String message, Throwable cause) {
            super(entity, message, cause);
        }

        /**
         * Construct an exception with an Entity and a cause.
         *
         * @param entity the denied entity
         * @param cause a cause throwable
         */
        public AccessDenied(@NotNull EnterpriseEntity entity, Throwable cause) {
            super(entity, cause);
        }
    }
}
