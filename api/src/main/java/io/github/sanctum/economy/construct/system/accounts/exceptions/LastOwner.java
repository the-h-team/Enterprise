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
package io.github.sanctum.economy.construct.system.accounts.exceptions;

import io.github.sanctum.economy.construct.system.Resolvable;
import io.github.sanctum.economy.construct.system.accounts.Account;
import io.github.sanctum.economy.construct.system.exceptions.ParticipantException;
import org.jetbrains.annotations.NotNull;

/**
 * Raised if the participant being removed from an account is the only owner.
 *
 * @author ms5984
 * @since 2.0.0
 */
public class LastOwner extends ParticipantException {
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
