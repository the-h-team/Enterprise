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
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * A point that is responsible for the safekeeping
 * and/or management of others' assets.
 * <p>
 * This is akin to banks, credit unions and brokerage firms.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Custodian extends Resolvable {
    /**
     * Gets the set of accounts for a participant.
     *
     * @param participant the participant
     * @return a set of accounts for the participant
     */
    default @NotNull Set<Account> getAccounts(@NotNull Resolvable participant) {
        return emptySet();
    }
}