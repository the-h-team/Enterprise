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

import java.util.Optional;

/**
 * Manage assets on account with a custodian.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Account {
    /**
     * Gets the custodian for this account.
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
     * Allows an entity to access this account.
     *
     * @param entity an entity
     * @param level an initial level of access
     * @throws DuplicateEntity if <code>entity</code> is already on the account
     */
    void addEntity(@NotNull EnterpriseEntity entity, AccessLevel level) throws DuplicateEntity;

    /**
     * Sets the access level of another entity.
     *
     * @param entity another entity
     * @param level a level of access
     * @return an Optional describing the previous level of access, if present
     */
    @NotNull Optional<AccessLevel> setEntityAccess(@NotNull EnterpriseEntity entity, AccessLevel level);

    /**
     * Removes an entity from this account.
     *
     * @param entity an entity
     * @return true if access was present and removed
     * @throws LastOwner if removing <code>entity</code>
     * would leave the account with no owner
     */
    boolean removeEntity(@NotNull EnterpriseEntity entity) throws LastOwner;

    /**
     * Describes (in general) a level of account access.
     * <p>
     * <b>What action(s) are permitted by a level is
     * left up to implementations:)</b>
     *
     * @since 2.0.0
     * @author ms5984
     */
    enum AccessLevel {
        /**
         * Can check the account.
         */
        VIEWER,
        /**
         * Ordinary member.
         */
        MEMBER,
        /**
         * Can manage Viewers and Members but not Owners.
         */
        CO_OWNER,
        /**
         * Full account access.
         */
        OWNER
    }

    /**
     * Raised if an entity is not permitted access to an account.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class AccessDenied extends EntityException {
        private static final long serialVersionUID = 1113963021969850967L;

        /**
         * Constructs an exception with an Entity and a message.
         *
         * @param entity the denied entity
         * @param message a message
         */
        public AccessDenied(@NotNull EnterpriseEntity entity, String message) {
            super(entity, message);
        }

        /**
         * Constructs an exception with an Entity, a message and cause.
         *
         * @param entity the denied entity
         * @param message a message
         * @param cause a cause throwable
         */
        public AccessDenied(@NotNull EnterpriseEntity entity, String message, Throwable cause) {
            super(entity, message, cause);
        }

        /**
         * Constructs an exception with an Entity and a cause.
         *
         * @param entity the denied entity
         * @param cause a cause throwable
         */
        public AccessDenied(@NotNull EnterpriseEntity entity, Throwable cause) {
            super(entity, cause);
        }
    }

    /**
     * Raised if an entity is already a member of an account.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class DuplicateEntity extends EntityException {
        private static final long serialVersionUID = -6017977336788103629L;

        /**
         * Constructs an exception with an Entity and a message.
         *
         * @param entity the duplicated entity
         * @param message a message
         */
        public DuplicateEntity(@NotNull EnterpriseEntity entity, String message) {
            super(entity, message);
        }

        /**
         * Constructs an exception with an Entity, a message and cause.
         *
         * @param entity the duplicated entity
         * @param message a message
         * @param cause a cause throwable
         */
        public DuplicateEntity(@NotNull EnterpriseEntity entity, String message, Throwable cause) {
            super(entity, message, cause);
        }

        /**
         * Constructs an exception with an Entity and a cause.
         *
         * @param entity the duplicated entity
         * @param cause a cause throwable
         */
        public DuplicateEntity(@NotNull EnterpriseEntity entity, Throwable cause) {
            super(entity, cause);
        }
    }

    /**
     * Raised if the entity being removed from an account is the only owner.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class LastOwner extends EntityException {
        private static final long serialVersionUID = -3459768126506047552L;
        protected final Account account;

        /**
         * Constructs an exception with an Entity, an Account and a message.
         *
         * @param owner the entity
         * @param account the account
         * @param message a message
         */
        public LastOwner(@NotNull EnterpriseEntity owner, @NotNull Account account, String message) {
            super(owner, message);
            this.account = account;
        }

        /**
         * Constructs an exception with an Entity,
         * an Account, a message and cause.
         *
         * @param owner the entity
         * @param account the account
         * @param message a message
         * @param cause a cause throwable
         */
        public LastOwner(@NotNull EnterpriseEntity owner, @NotNull Account account, String message, Throwable cause) {
            super(owner, message, cause);
            this.account = account;
        }

        /**
         * Constructs an exception with an Entity, an Account and a cause.
         *
         * @param owner the entity
         * @param account the account
         * @param cause a cause throwable
         */
        public LastOwner(@NotNull EnterpriseEntity owner, @NotNull Account account, Throwable cause) {
            super(owner, cause);
            this.account = account;
        }

        /**
         * Gets the account associated with this exception.
         *
         * @return the account associated with this exception
         */
        public final Account getAccount() {
            return account;
        }
    }

    /**
     * Raised if an entity must be an existing account member.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class NotAMember extends EntityException {
        private static final long serialVersionUID = 2094168851736743929L;

        /**
         * Constructs an exception with an Entity and a message.
         *
         * @param entity the denied entity
         * @param message a message
         */
        public NotAMember(@NotNull EnterpriseEntity entity, String message) {
            super(entity, message);
        }

        /**
         * Constructs an exception with an Entity, a message and cause.
         *
         * @param entity the denied entity
         * @param message a message
         * @param cause a cause throwable
         */
        public NotAMember(@NotNull EnterpriseEntity entity, String message, Throwable cause) {
            super(entity, message, cause);
        }

        /**
         * Constructs an exception with an Entity and a cause.
         *
         * @param entity the denied entity
         * @param cause a cause throwable
         */
        public NotAMember(@NotNull EnterpriseEntity entity, Throwable cause) {
            super(entity, cause);
        }
    }
}
