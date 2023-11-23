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

import io.github.sanctum.economy.construct.system.Resolvable;
import io.github.sanctum.economy.construct.system.exceptions.*;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Documented;

/**
 * Manage assets on account with a custodian.
 *
 * @since 2.0.0
 * @see Custodian
 * @author ms5984
 */
public abstract class Account {
    /**
     * The format of an account ID.
     * <p>
     * Generally, account IDs can be any non-empty string without whitespace.
     * Implementations may choose to enforce stricter formats.
     */
    public static final @RegExp String ID_FORMAT = "^\\S+$";

    /**
     * A String representing an account ID.
     *
     * @see #ID_FORMAT
     */
    @Documented
    @Pattern(ID_FORMAT)
    @interface Id {}

    private final @Id String id;

    /**
     * Constructs an account with an ID.
     *
     * @param id an ID
     * @throws IllegalArgumentException if {@code id} is not a valid account ID
     */
    protected Account(@NotNull String id) {
        if (!id.matches(ID_FORMAT)) throw new IllegalArgumentException("Invalid account ID: " + id);
        this.id = id;
    }

    /**
     * Gets the ID of this account.
     *
     * @return the ID of this account
     */
    @Id
    public final @NotNull String getId() {
        return id;
    }

    /**
     * Gets the custodian that is responsible for this account.
     *
     * @return the custodian for this account
     * @throws AbstractSystemException if a system error occurs
     */
    public abstract @NotNull Custodian getCustodian() throws AbstractSystemException;

    /**
     * Accesses this account from the context of the provided participant.
     *
     * @param participant a participant
     * @return a view with the level of access allowed to the participant
     * @throws AccessDenied if the participant is not allowed access
     * @throws AbstractSystemException if a system error occurs
     */
    public abstract @NotNull AccountView accessAs(@NotNull Resolvable participant) throws AccessDenied, AbstractSystemException;

    /**
     * Allows a participant to access this account.
     * <p>
     * A default level of access is used.
     *
     * @param participant a participant
     * @return the default level of access set by the implementation
     * @throws DuplicateParticipant if {@code participant} is already on the account
     * @throws AbstractSystemException if a system error occurs
     * @implNote This method is equivalent to {@code add(participant, null)}.
     */
    public @NotNull AccessLevel add(@NotNull Resolvable participant) throws DuplicateParticipant, AbstractSystemException {
        return add(participant, null);
    }

    /**
     * Allows a participant to access this account.
     *
     * @param participant a participant
     * @param level an initial level of access or null for a system-default
     * @return {@code level} (if not {@code null}) or the default level
     * set by the implementation
     * @throws DuplicateParticipant if {@code participant} is already on the account
     * @throws AbstractSystemException if a system error occurs
     * @implSpec Implementations are free to define a default level of access
     * to be used whenever {@code level} is null.
     */
    @Contract("_, !null -> param2")
    public abstract @NotNull AccessLevel add(@NotNull Resolvable participant, @Nullable AccessLevel level) throws DuplicateParticipant, AbstractSystemException;

    /**
     * Removes a participant from this account.
     *
     * @param participant a participant
     * @return true if access was present and removed
     * @throws LastOwner if removing {@code participant} would leave this account without owner
     * @throws AbstractSystemException if a system error occurs
     */
    public abstract boolean remove(@NotNull Resolvable participant) throws LastOwner, AbstractSystemException;

    /**
     * Gets the access level of a participant.
     *
     * @param participant a participant
     * @return the level of access, if any, or null
     * @throws AbstractSystemException if a system error occurs
     */
    public abstract @Nullable AccessLevel getAccessLevel(@NotNull Resolvable participant) throws AbstractSystemException;

    /**
     * Sets the access level of a participant.
     * <p>
     * Note that setting a participant's access level to null does
     * <strong>not</strong> remove the participant from the account.
     *
     * @param participant a participant
     * @param level a level of access or null to reset to system-default
     * @return the participant's new level of access
     * @throws NotAnAccountParticipant if {@code participant} is not an account participant
     * @throws AbstractSystemException if a system error occurs
     * @implSpec Implementations are free to define a default level of access
     * to be used whenever {@code level} is null.
     */
    public abstract @NotNull AccessLevel setAccessLevel(@NotNull Resolvable participant, @Nullable AccessLevel level) throws NotAnAccountParticipant, AbstractSystemException;

    /**
     * Gets the ID of this account.
     *
     * @return a pending result
     * @implNote {@link #getId()} is thread-safe; this method is provided for convenience.
     * @see #getId()
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<String>, String> asyncGetId() {
        return PendingResult.of(Result.success(id));
    }

    /**
     * Gets the custodian that is responsible for this account.
     *
     * @return a pending result
     * @see #getCustodian()
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<Custodian>, Custodian> asyncGetCustodian() {
        return PendingResult.of(Result.NotEmpty.lazy(this::getCustodian));
    }

    /**
     * Accesses this account from the context of the provided participant.
     *
     * @param participant a participant
     * @return a pending result
     * @see #accessAs(Resolvable)
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<AccountView>, AccountView> asyncAccessAs(@NotNull Resolvable participant) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> accessAs(participant)));
    }

    /**
     * Allows a participant to access this account.
     * <p>
     * A default level of access is used.
     *
     * @param participant a participant
     * @return a pending result
     * @see #add(Resolvable)
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<AccessLevel>, AccessLevel> asyncAdd(@NotNull Resolvable participant) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> add(participant)));
    }

    /**
     * Allows a participant to access this account.
     *
     * @param participant a participant
     * @param level an initial level of access or null for a system-default
     * @return a pending result
     * @see #add(Resolvable, AccessLevel)
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<AccessLevel>, AccessLevel> asyncAdd(@NotNull Resolvable participant, @Nullable AccessLevel level) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> add(participant, level)));
    }

    /**
     * Removes a participant from this account.
     *
     * @param participant a participant
     * @return a pending result
     * @see #remove(Resolvable)
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<Boolean>, Boolean> asyncRemove(@NotNull Resolvable participant) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> remove(participant)));
    }

    /**
     * Gets the access level of a participant.
     *
     * @param participant a participant
     * @return a pending result
     * @see #getAccessLevel(Resolvable)
     */
    public @NotNull PendingResult<? extends Result<AccessLevel>, AccessLevel> asyncGetAccessLevel(@NotNull Resolvable participant) {
        return PendingResult.of(Result.lazy(() -> getAccessLevel(participant)));
    }

    /**
     * Sets the access level of a participant.
     * <p>
     * Note that setting a participant's access level to null does
     * <strong>not</strong> remove the participant from the account.
     *
     * @param participant a participant
     * @param level a level of access or null to reset to system-default
     * @return a pending result
     * @see #setAccessLevel(Resolvable, AccessLevel)
     */
    public @NotNull PendingResult<? extends Result.NotEmpty<AccessLevel>, AccessLevel> asyncSetAccessLevel(@NotNull Resolvable participant, @Nullable AccessLevel level) {
        return PendingResult.of(Result.NotEmpty.lazy(() -> setAccessLevel(participant, level)));
    }

    /**
     * Describes (in general) a level of account access.
     * <p>
     * <strong>What actions are permitted by a level are <em>intentionally</em>
     * left up to implementations:)</strong>
     *
     * @since 2.0.0
     * @author ms5984
     */
    public enum AccessLevel {
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
        CO_OWNER,
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
        OWNER
    }

    /**
     * Raised if a participant is not permitted access to an account.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class AccessDenied extends ParticipantException {
        private static final long serialVersionUID = 1113963021969850967L;

        /**
         * Constructs an exception with a participant and a message.
         *
         * @param participant the denied participant
         * @param message a message
         */
        public AccessDenied(@NotNull Resolvable participant, String message) {
            super(participant, message);
        }

        /**
         * Constructs an exception with a participant, a message and cause.
         *
         * @param participant the denied participant
         * @param message a message
         * @param cause a cause throwable
         */
        public AccessDenied(@NotNull Resolvable participant, String message, Throwable cause) {
            super(participant, message, cause);
        }

        /**
         * Constructs an exception with a participant and a cause.
         *
         * @param participant the denied participant
         * @param cause a cause throwable
         */
        public AccessDenied(@NotNull Resolvable participant, Throwable cause) {
            super(participant, cause);
        }
    }

    /**
     * Raised if a participant is already a participant of an account.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class DuplicateParticipant extends ParticipantException {
        private static final long serialVersionUID = -6017977336788103629L;

        /**
         * Constructs an exception with a participant and a message.
         *
         * @param participant the duplicated participant
         * @param message a message
         */
        public DuplicateParticipant(@NotNull Resolvable participant, String message) {
            super(participant, message);
        }

        /**
         * Constructs an exception with a participant, a message and cause.
         *
         * @param participant the duplicated participant
         * @param message a message
         * @param cause a cause throwable
         */
        public DuplicateParticipant(@NotNull Resolvable participant, String message, Throwable cause) {
            super(participant, message, cause);
        }

        /**
         * Constructs an exception with a participant and a cause.
         *
         * @param participant the duplicated participant
         * @param cause a cause throwable
         */
        public DuplicateParticipant(@NotNull Resolvable participant, Throwable cause) {
            super(participant, cause);
        }
    }

    /**
     * Raised if the participant being removed from an account is the only owner.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class LastOwner extends ParticipantException {
        private static final long serialVersionUID = -3459768126506047552L;
        protected final Account account;

        /**
         * Constructs an exception with a participant, an account and a message.
         *
         * @param owner the last owning participant
         * @param account the account
         * @param message a message
         */
        public LastOwner(@NotNull Resolvable owner, @NotNull Account account, String message) {
            super(owner, message);
            this.account = account;
        }

        /**
         * Constructs an exception with a participant, an account,
         * a message and cause.
         *
         * @param owner the last owning participant
         * @param account the account
         * @param message a message
         * @param cause a cause throwable
         */
        public LastOwner(@NotNull Resolvable owner, @NotNull Account account, String message, Throwable cause) {
            super(owner, message, cause);
            this.account = account;
        }

        /**
         * Constructs an exception with a participant, an account and a cause.
         *
         * @param owner the last owning participant
         * @param account the account
         * @param cause a cause throwable
         */
        public LastOwner(@NotNull Resolvable owner, @NotNull Account account, Throwable cause) {
            super(owner, cause);
            this.account = account;
        }

        /**
         * Gets the account associated with this exception.
         *
         * @return the account associated with this exception
         */
        public final @NotNull Account getAccount() {
            return account;
        }
    }

    /**
     * Raised if a participant must be an account participant in order
     * to perform a particular action.
     * <p>
     * This exception is not specific to an access level.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class NotAnAccountParticipant extends ParticipantException {
        private static final long serialVersionUID = 2094168851736743929L;

        /**
         * Constructs an exception with a participant and a message.
         *
         * @param participant the denied participant
         * @param message a message
         */
        public NotAnAccountParticipant(@NotNull Resolvable participant, String message) {
            super(participant, message);
        }

        /**
         * Constructs an exception with a participant, a message and cause.
         *
         * @param participant the denied participant
         * @param message a message
         * @param cause a cause throwable
         */
        public NotAnAccountParticipant(@NotNull Resolvable participant, String message, Throwable cause) {
            super(participant, message, cause);
        }

        /**
         * Constructs an exception with a participant and a cause.
         *
         * @param participant the denied participant
         * @param cause a cause throwable
         */
        public NotAnAccountParticipant(@NotNull Resolvable participant, Throwable cause) {
            super(participant, cause);
        }
    }
}
