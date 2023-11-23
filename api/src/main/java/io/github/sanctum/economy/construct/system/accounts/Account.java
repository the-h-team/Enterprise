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
import io.github.sanctum.economy.construct.system.accounts.exceptions.*;
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
public abstract class Account implements Comparable<Account> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // must match subclass exactly
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(@NotNull Account o) {
        return id.compareTo(o.id);
    }

    /**
     * Defines (in general) a level of account access.
     * <p>
     * <strong>What actions are permitted by a level are <em>intentionally</em>
     * left up to implementations:)</strong>
     *
     * @since 2.0.0
     * @see SimpleAccessLevel
     * @author ms5984
     */
    public interface AccessLevel {
        /**
         * Gets the name of this access level.
         *
         * @return the name of this access level
         */
        @NotNull String getName();

        /**
         * Gets an access level with the given name.
         * <p>
         * The returned access level is comparable to other
         * access levels produced by this method.
         *
         * @param name a name
         * @return an access level
         * @implNote This method will return a {@link SimpleAccessLevel} if possible.
         */
        static @NotNull AccessLevel of(@NotNull String name) {
            final SimpleAccessLevel simpleAccessLevel = SimpleAccessLevel.of(name);
            if (simpleAccessLevel != null) return simpleAccessLevel;
            return new AccessLevelImpl(name);
        }
    }
}
