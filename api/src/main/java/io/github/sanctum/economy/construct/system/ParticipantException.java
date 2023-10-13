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
package io.github.sanctum.economy.construct.system;

import org.jetbrains.annotations.NotNull;

/**
 * A system exception involving a particular participant.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class ParticipantException extends AbstractSystemException {
    private static final long serialVersionUID = 7000692155774178638L;
    protected final Resolvable participant;

    /**
     * Constructs an exception with a participant and a message.
     *
     * @param participant a resolvable participant
     * @param message a message
     */
    ParticipantException(@NotNull Resolvable participant, String message) {
        super(message);
        this.participant = participant;
    }

    /**
     * Constructs an exception with an Entity, a message and cause.
     *
     * @param participant a resolvable participant
     * @param message a message
     * @param cause a cause throwable
     */
    ParticipantException(@NotNull Resolvable participant, String message, Throwable cause) {
        super(message, cause);
        this.participant = participant;
    }

    /**
     * Constructs an exception with a participant and a cause.
     *
     * @param participant a resolvable participant
     * @param cause a cause throwable
     */
    ParticipantException(@NotNull Resolvable participant, Throwable cause) {
        super(cause);
        this.participant = participant;
    }

    /**
     * Gets the participant associated with this exception.
     *
     * @return a participant
     */
    public final @NotNull Resolvable getParticipant() {
        return participant;
    }
}
