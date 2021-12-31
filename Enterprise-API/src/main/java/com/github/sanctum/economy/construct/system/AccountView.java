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
import com.github.sanctum.economy.construct.system.Account.*;
import org.jetbrains.annotations.NotNull;

/**
 * A specialization of {@link Balance} exposing {@link Account}'s functionality
 * from the perspective of an entity that is permitted access.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface AccountView extends Balance, Contextual {
    /**
     * Does this view belong to an account owner?
     *
     * @return true if the context entity is an owner
     */
    default boolean isOwner() {
        return accessLevel() == AccessLevel.OWNER;
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

    /**
     * Get the access level of this view.
     *
     * @return the access level of this view
     */
    @NotNull AccessLevel accessLevel();

    /**
     * Get the entity whose perspective determines this view.
     *
     * @return the entity whose perspective determines this view
     */
    @NotNull EnterpriseEntity viewer();

    /**
     * Get the account associated with this view.
     *
     * @return the account associated with this view
     */
    @NotNull Account account();

    /**
     * Attempts to add a member to this account from the context of this view.
     *
     * @param entity an entity to add
     * @param level an initial level of access
     * @throws AccessDenied if viewer does not have permission to add members
     * @throws DuplicateEntity if <code>entity</code> is already on the account
     */
    default void addMember(@NotNull EnterpriseEntity entity, AccessLevel level) throws AccessDenied, DuplicateEntity {
        if (!isOwner()) {
            throw new AccessDenied(viewer(), "This entity cannot add members.");
        }
        account().addEntity(entity, level);
    }

    /**
     * Attempts to set a member's access from the context of this view.
     *
     * @param member the member to edit
     * @param level a new access level
     * @return <code>member</code>'s previous access level
     * @throws AccessDenied if viewer does not have permission to set access
     * @throws NotAMember if <code>member</code> is not yet on the account
     */
    default @NotNull AccessLevel setAccess(@NotNull EnterpriseEntity member, AccessLevel level) throws AccessDenied, NotAMember {
        if (!isOwner()) {
            throw new AccessDenied(viewer(), "This entity cannot edit others' access!");
        }
        throw new NotAMember(member, "This entity is not a member of the account.");
    }

    /**
     * Attempts to remove a member from the context of this view.
     *
     * @param member the member to remove
     * @return true if access was present and removed
     * @throws AccessDenied if viewer is not permitted to remove
     * @throws NotAMember if <code>member</code> is not yet on the account
     * @throws LastOwner if removing <code>member</code> would leave the
     * account with no owner
     */
    @SuppressWarnings("RedundantThrows")
    default boolean removeMember(@NotNull EnterpriseEntity member) throws AccessDenied, NotAMember, LastOwner {
        if (!isOwner()) {
            throw new AccessDenied(viewer(), "This entity cannot edit others' access!");
        }
        throw new NotAMember(member, "This entity is not a member of the account.");
    }
}
