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
import org.jetbrains.annotations.NotNull;

/**
 * A system exception involving a particular Entity.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class EntityException extends AbstractSystemException {
    private static final long serialVersionUID = 7000692155774178638L;
    protected final EnterpriseEntity enterpriseEntity;

    /**
     * Constructs an exception with an Entity and a message.
     *
     * @param entity an entity
     * @param message a message
     */
    EntityException(@NotNull EnterpriseEntity entity, String message) {
        super(message);
        enterpriseEntity = entity;
    }

    /**
     * Constructs an exception with an Entity, a message and cause.
     *
     * @param entity an entity
     * @param message a message
     * @param cause a cause throwable
     */
    EntityException(@NotNull EnterpriseEntity entity, String message, Throwable cause) {
        super(message, cause);
        enterpriseEntity = entity;
    }

    /**
     * Constructs an exception with an Entity and a cause.
     *
     * @param entity an entity
     * @param cause a cause throwable
     */
    EntityException(@NotNull EnterpriseEntity entity, Throwable cause) {
        super(cause);
        enterpriseEntity = entity;
    }

    /**
     * Gets the entity associated with this exception.
     *
     * @return an entity
     */
    public final EnterpriseEntity getEntity() {
        return enterpriseEntity;
    }
}
