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
import io.github.sanctum.economy.construct.system.Resolvable;
import io.github.sanctum.economy.construct.system.behaviors.*;
import io.github.sanctum.economy.construct.system.exceptions.ParticipantException;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static io.github.sanctum.economy.construct.system.transactions.types.Trade.ExceptionalOutcome.match;

/**
 * Represents a trade of two assets between two participants.
 * <p>
 * A trade is essentially two {@linkplain Transfer transfers} of different
 * asset type between two participants and is implemented as such.
 * <p>
 * Trades are naturally {@linkplain Transaction.Reversible reversible}, making
 * use of {@link AmountPair#swap()} to swap the order and thus direction of the
 * resulting transfers.
 *
 * @since 2.0.0
 * @see Transfer
 * @author ms5984
 */
public abstract class Trade implements Transaction<Result.NotEmpty<AmountPair>, AmountPair>, Transaction.Reversible<Result.NotEmpty<AmountPair>, AmountPair> {
    private final AmountPair amounts;

    /**
     * Creates a new trade of the given amounts.
     * <p>
     * {@code first} will be taken from the first participant and given to the
     * second participant, while {@code second} will be taken from the second
     * participant and given to the first participant.
     *
     * @param first the first amount
     * @param second the second amount
     */
    public Trade(@NotNull Amount first, @NotNull Amount second) {
        this(new AmountPair(first, second));
    }

    /**
     * Creates a new trade of the given amounts.
     * <p>
     * The first amount of the pair will be taken from the first participant
     * and given to the second participant, while the second amount will be
     * taken from the second participant and given to the first participant.
     *
     * @param amounts the amounts to trade
     * @see AmountPair
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

    @Override
    public @NotNull PendingResult<Result.NotEmpty<AmountPair>, AmountPair> execute() {
        final CompletableFuture<Result.NotEmpty<AmountPair>> cf = new CompletableFuture<>();
        final PendingResult<Result.NotEmpty<Amount>, Amount> firstTransfer = new TransferImpl(amounts.getFirst(), getFirstParticipant(), getSecondParticipant()).execute();
        firstTransfer.ifSuccessful(() -> {
            final PendingResult<Result.NotEmpty<Amount>, Amount> secondTransfer = new TransferImpl(amounts.getSecond(), getSecondParticipant(), getFirstParticipant()).execute();
            secondTransfer.ifSuccessful(() -> cf.complete(Result.success(amounts)));
            secondTransfer.ifFailed(e -> cf.completeExceptionally(new Exception(getSecondParticipant(), match(true, ((Transfer.Exception) e).getOutcome()), e)));
        });
        firstTransfer.ifFailed(e -> cf.completeExceptionally(new Exception(getFirstParticipant(), match(false, ((Transfer.Exception) e).getOutcome()), e)));
        return PendingResult.of(cf);
    }

    @Override
    public @NotNull PendingResult<Result.NotEmpty<AmountPair>, AmountPair> reverse() {
        final CompletableFuture<Result.NotEmpty<AmountPair>> cf = new CompletableFuture<>();
        final PendingResult<Result.NotEmpty<Amount>, Amount> firstTransfer = new TransferImpl(amounts.getSecond(), getFirstParticipant(), getSecondParticipant()).execute();
        firstTransfer.ifSuccessful(() -> {
            final PendingResult<Result.NotEmpty<Amount>, Amount> secondTransfer = new TransferImpl(amounts.getFirst(), getSecondParticipant(), getFirstParticipant()).execute();
            secondTransfer.ifSuccessful(() -> cf.complete(Result.success(amounts)));
            secondTransfer.ifFailed(e -> cf.completeExceptionally(new Exception(getSecondParticipant(), match(true, ((Transfer.Exception) e).getOutcome()), e)));
        });
        firstTransfer.ifFailed(e -> cf.completeExceptionally(new Exception(getFirstParticipant(), match(false, ((Transfer.Exception) e).getOutcome()), e)));
        return PendingResult.of(cf);
    }

    /**
     * The possible exceptional outcomes of a trade.
     *
     * @since 2.0.0
     */
    public enum ExceptionalOutcome {
        FIRST_ASSET_TAKE_FAILED("first asset not sent"),
        FIRST_ASSET_GIVE_FAILED("first asset sent but not received"),
        FIRST_ASSET_GIVE_FAILED_TAKE_REVERSED("first asset sent but not received; first participant refunded"),
        FIRST_ASSET_GIVE_FAILED_TAKE_HOLDS("first asset sent but not received; first participant not refunded"),
        SECOND_ASSET_TAKE_FAILED("second asset not sent"),
        SECOND_ASSET_GIVE_FAILED("second asset sent but not received"),
        SECOND_ASSET_GIVE_FAILED_TAKE_REVERSED("second asset sent but not received; second participant refunded"),
        SECOND_ASSET_GIVE_FAILED_TAKE_HOLDS("second asset sent but not received; second participant not refunded"),
        ;

        private final String message;

        ExceptionalOutcome(String message) {
            this.message = message;
        }

        static ExceptionalOutcome match(boolean second, Transfer.ExceptionalOutcome internalOutcome) {
            switch (internalOutcome) {
                case TAKE_FAILED:
                    return second ? SECOND_ASSET_TAKE_FAILED : FIRST_ASSET_TAKE_FAILED;
                case GIVE_FAILED:
                    return second ? SECOND_ASSET_GIVE_FAILED : FIRST_ASSET_GIVE_FAILED;
                case GIVE_FAILED_TAKE_REVERSED:
                    return second ? SECOND_ASSET_GIVE_FAILED_TAKE_REVERSED : FIRST_ASSET_GIVE_FAILED_TAKE_REVERSED;
                case GIVE_FAILED_TAKE_HOLDS:
                    return second ? SECOND_ASSET_GIVE_FAILED_TAKE_HOLDS : FIRST_ASSET_GIVE_FAILED_TAKE_HOLDS;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + internalOutcome);
            }
        }
    }

    /**
     * A system exception involving a trade.
     *
     * @since 2.0.0
     * @see ParticipantException
     * @see Transfer.ExceptionalOutcome
     * @see Throwable#getCause()
     */
    public static final class Exception extends ParticipantException {
        private static final long serialVersionUID = -5318655531280771951L;
        private final ExceptionalOutcome outcome;

        /**
         * Constructs a new exception with the given outcome and cause.
         *
         * @param participant the participant
         * @param outcome an exceptional outcome
         * @param cause the cause
         */
        private Exception(@NotNull Resolvable participant, @NotNull ExceptionalOutcome outcome, @NotNull Throwable cause) {
            super(participant, "Trade failed: " + outcome.message, cause);
            this.outcome = outcome;
        }

        /**
         * Gets the outcome.
         * <p>
         * This will reflect the state of the trade at the time of the error.
         *
         * @return an outcome
         * @see ExceptionalOutcome
         */
        public @NotNull ExceptionalOutcome getOutcome() {
            return outcome;
        }
    }
}
