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
import io.github.sanctum.economy.construct.system.accounts.Account.AccessLevel;
import io.github.sanctum.economy.construct.system.exceptions.ParticipantException;
import org.jetbrains.annotations.NotNull;

/**
 * Raised if a participant must be an account participant in order
 * to perform a particular action.
 * <p>
 * This exception is not specific to an {@linkplain AccessLevel access level}.
 *
 * @author ms5984
 * @since 2.0.0
 */
public class NotAnAccountParticipant extends ParticipantException {
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
