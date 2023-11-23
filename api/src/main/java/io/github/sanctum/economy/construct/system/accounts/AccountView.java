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

import io.github.sanctum.economy.construct.system.*;
import io.github.sanctum.economy.construct.system.exceptions.AbstractSystemException;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A specialization of {@link Balance} exposing {@link Account}'s functionality
 * from the perspective of a participant that is permitted access.
 *
 * @since 2.0.0
 * @author ms5984
 */
@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "RedundantThrows"})
public interface AccountView extends Balance, Contextual {
    /**
     * Checks if this view belongs to an account owner.
     *
     * @return true if the context participant is an owner
     * @throws AbstractSystemException if a system error occurs
     */
    default boolean isOwner() throws AbstractSystemException {
        return getAccessLevel().compareTo(Account.AccessLevel.CO_OWNER) >= 0;
    }

    /**
     * Checks if this view belong to one of multiple owners.
     *
     * @return true if the context participant is one of multiple owners
     * @throws AbstractSystemException if a system error occurs
     */
    default boolean isJointOwner() throws AbstractSystemException {
        return false;
    }

    /**
     * Gets the access level of this view.
     *
     * @return the access level of this view
     * @implSpec This value is constant for the lifetime of this view.
     */
    @NotNull Account.AccessLevel getAccessLevel();

    /**
     * Gets the participant whose perspective determines this view.
     *
     * @return the participant whose perspective determines this view
     * @implSpec This value is constant for the lifetime of this view.
     */
    @NotNull Resolvable getPerspective();

    /**
     * Gets the account associated with this view.
     *
     * @return the account associated with this view
     * @throws AbstractSystemException if a system error occurs
     */
    @NotNull Account getAccount() throws AbstractSystemException;

    /**
     * Attempts to add a participant to this account from the context of this view.
     *
     * @param participant a participant to add
     * @param level an initial level of access or null
     * @return {@code level} if {@code level} is not null or the default level set by the implementation
     * @throws Account.AccessDenied if the viewer does not have permission to add participants or grant {@code level}-level access
     * @throws Account.DuplicateParticipant if {@code participant} already has access to this account
     * @throws AbstractSystemException if a system error occurs
     * @implSpec Implementations are free to define a default level of access to be used if {@code level} is null.
     */
    default @Nullable Account.AccessLevel add(@NotNull Resolvable participant, @Nullable Account.AccessLevel level) throws Account.AccessDenied, Account.DuplicateParticipant, AbstractSystemException {
        if (!isOwner()) {
            throw new Account.AccessDenied(getPerspective(), "This participant cannot add other participants.");
        }
        if (level != null && level.compareTo(getAccessLevel()) >= 0) {
            throw new Account.AccessDenied(getPerspective(), "This participant cannot grant access at the requested level.");
        }
        return getAccount().add(participant, level);
    }

    /**
     * Attempts to set a participant's access from the context of this view.
     *
     * @param participant the participant whose access to edit
     * @param level a new access level
     * @return {@code participant}'s previous access level
     * @throws Account.AccessDenied if the viewer does not have permission to set access
     * @throws Account.NotAnAccountParticipant if {@code participant} is not an account participant
     * @throws AbstractSystemException if a system error occurs
     */
    default @NotNull Account.AccessLevel setAccess(@NotNull Resolvable participant, @NotNull Account.AccessLevel level) throws Account.AccessDenied, Account.NotAnAccountParticipant, AbstractSystemException {
        if (level.compareTo(getAccessLevel()) >= 0) {
            throw new Account.AccessDenied(getPerspective(), "This participant cannot edit others' access!");
        }
        throw new Account.NotAnAccountParticipant(participant, "This participant is not a participant of the account.");
    }

    /**
     * Attempts to remove a participant from the context of this view.
     *
     * @param participant the participant to remove
     * @return true if access was present and removed
     * @throws Account.AccessDenied if the viewer is not permitted to remove other participants
     * @throws Account.NotAnAccountParticipant if {@code participant} is not a participant of the account
     * @throws Account.LastOwner if removing {@code participant} would leave the account with no owner
     * @throws AbstractSystemException if a system error occurs
     */
    default boolean removeMember(@NotNull Resolvable participant) throws Account.AccessDenied, Account.NotAnAccountParticipant, Account.LastOwner, AbstractSystemException {
        if (!isOwner()) {
            throw new Account.AccessDenied(getPerspective(), "This participant cannot edit others' access!");
        }
        throw new Account.NotAnAccountParticipant(participant, "This participant is not a participant of the account.");
    }

    /**
     * Checks if this view belongs to an account owner.
     *
     * @return a pending result
     * @see #isOwner()
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Boolean>, Boolean> asyncIsOwner() {
        return PendingResult.of(Result.NotEmpty.lazy(this::isOwner));
    }

    /**
     * Checks if this view belong to one of multiple owners.
     *
     * @return a pending result
     * @see #isJointOwner()
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Boolean>, Boolean> asyncIsJointOwner() {
        return PendingResult.of(Result.NotEmpty.lazy(this::isJointOwner));
    }

    /**
     * Gets the access level of this view.
     *
     * @return a pending result
     * @implNote {@link #getAccessLevel()} should be thread-safe; this method is provided for convenience.
     * @see #getAccessLevel()
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Account.AccessLevel>, Account.AccessLevel> asyncGetAccessLevel() {
        return PendingResult.of(Result.NotEmpty.lazy(this::getAccessLevel));
    }

    /**
     * Gets the participant whose perspective determines this view.
     *
     * @return a pending result
     * @implNote {@link #getPerspective()} should be thread-safe; this method is provided for convenience.
     * @see #getPerspective()
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Resolvable>, Resolvable> asyncGetPerspective() {
        return PendingResult.of(Result.NotEmpty.lazy(this::getPerspective));
    }

    /**
     * Gets the account associated with this view.
     *
     * @return a pending result
     * @see #getAccount()
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Account>, Account> asyncGetAccount() {
        return PendingResult.of(Result.NotEmpty.lazy(this::getAccount));
    }

    /**
     * Attempts to add a participant to this account from the context of this view.
     *
     * @param participant a participant to add
     * @param level an initial level of access or null
     * @return a pending result
     * @see #add(Resolvable, Account.AccessLevel)
     */
    default @NotNull PendingResult<? extends Result<Account.AccessLevel>, Account.AccessLevel> asyncAdd(@NotNull Resolvable participant, @Nullable Account.AccessLevel level) {
        return PendingResult.of(Result.lazy(() -> add(participant, level)));
    }

    /**
     * Attempts to set a participant's access from the context of this view.
     *
     * @param participant the participant whose access to edit
     * @param level a new access level
     * @return a pending result
     * @see #setAccess(Resolvable, Account.AccessLevel)
     */
    default @NotNull PendingResult<? extends Result<Account.AccessLevel>, Account.AccessLevel> asyncSetAccess(@NotNull Resolvable participant, @NotNull Account.AccessLevel level) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> setAccess(participant, level)));
    }

    /**
     * Attempts to remove a participant from the context of this view.
     *
     * @param participant the participant to remove
     * @return a pending result
     * @see #removeMember(Resolvable)
     */
    default @NotNull PendingResult<? extends Result.NotEmpty<Boolean>, Boolean> asyncRemoveMember(@NotNull Resolvable participant) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> removeMember(participant)));
    }
}
