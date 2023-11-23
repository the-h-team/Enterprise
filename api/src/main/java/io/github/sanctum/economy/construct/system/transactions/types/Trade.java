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
package io.github.sanctum.economy.construct.system.transactions.types;

import io.github.sanctum.economy.construct.assets.Amount;
import io.github.sanctum.economy.construct.system.behaviors.*;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a trade of two assets between two participants.
 * <p>
 * A trade is essentially two {@linkplain Transfer transfers} of different
 * asset type between two participants.
 *
 * @since 2.0.0
 * @see Transfer
 * @author ms5984
 */
public abstract class Trade implements Transaction<Result.NotEmpty<AmountPair>, AmountPair> {
    private final AmountPair amounts;

    /**
     * Creates a new trade of the given amounts.
     *
     * @param first the first amount
     * @param second the second amount
     */
    public Trade(@NotNull Amount first, @NotNull Amount second) {
        this(new AmountPair(first, second));
    }

    /**
     * Creates a new trade of the given amounts.
     *
     * @param amounts the amounts to trade
     */
    public Trade(@NotNull AmountPair amounts) {
        this.amounts = amounts;
    }

    /**
     * Gets the first participant in this trade.
     *
     * @return the first participant
     */
    public abstract <P1 extends Source & Receiver> @NotNull P1 getFirstParticipant();

    /**
     * Gets the second participant in this trade.
     *
     * @return the second participant
     */
    public abstract <P2 extends Receiver & Source> @NotNull P2 getSecondParticipant();

    /**
     * Gets the amounts involved in this trade.
     *
     * @return the amounts
     */
    public final @NotNull AmountPair getAmounts() {
        return amounts;
    }
}
