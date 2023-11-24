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
import io.github.sanctum.economy.construct.system.behaviors.Receiver;
import io.github.sanctum.economy.construct.system.behaviors.Source;
import io.github.sanctum.economy.construct.system.exceptions.ParticipantException;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static io.github.sanctum.economy.construct.system.transactions.types.Transfer.ExceptionalOutcome.*;

/**
 * Represents the transfer of a single asset between two participants.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class Transfer implements Transaction<Result.NotEmpty<Amount>, Amount> {
    private final Amount amount;

    /**
     * Creates a new transfer of a given amount.
     *
     * @param amount an amount
     */
    public Transfer(@NotNull Amount amount) {
        this.amount = amount;
    }

    /**
     * Gets the participant that will provide the amount.
     *
     * @return the source participant
     */
    public abstract @NotNull Source getSource();

    /**
     * Gets the participant that will receive the amount.
     *
     * @return the recipient participant
     */
    public abstract @NotNull Receiver getReceiver();

    /**
     * {@inheritDoc}
     * <p>
     * This method will first attempt to take the amount from the source
     * and then attempt to give it to the receiver.
     * <p>
     * If either action fails, the transaction will fail.
     *
     * @return a pending result
     * @implNote This implementation may make a best-effort attempt to reverse
     * the {@code take} operation if the {@code give} operation fails, relying
     * on the source's ability to be the target of a {@code give} operation.
     * @see ExceptionalOutcome#GIVE_FAILED_TAKE_REVERSED
     */
    @Override
    public @NotNull PendingResult<Result.NotEmpty<Amount>, Amount> execute() {
        final Source source = getSource();
        final Receiver receiver = getReceiver();
        final CompletableFuture<Result.NotEmpty<Amount>> cf = new CompletableFuture<>();
        final PendingResult<? extends Result.NotEmpty<Source>, Source> sourcePendingResult = source.asyncTake(amount);
        sourcePendingResult.ifSuccessful(() -> {
            final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> receiverPendingResult = receiver.asyncGive(amount);
            receiverPendingResult.ifSuccessful(() -> cf.complete(Result.success(amount)));
            receiverPendingResult.ifFailed(giveError -> {
                // attempt to refund source
                if (source instanceof Receiver) {
                    final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> sourceAsReceiver = ((Receiver) source).asyncGive(amount);
                    sourceAsReceiver.ifSuccessful(() -> cf.completeExceptionally(new Exception(receiver, GIVE_FAILED_TAKE_REVERSED, giveError)));
                    sourceAsReceiver.ifFailed(ignored -> cf.completeExceptionally(new Exception(receiver, GIVE_FAILED_TAKE_HOLDS, giveError)));
                } else {
                    cf.completeExceptionally(new Exception(receiver, GIVE_FAILED, giveError));
                }
            });
        });
        sourcePendingResult.ifFailed(e -> cf.completeExceptionally(new Exception(source, TAKE_FAILED, e)));
        return PendingResult.of(cf);
    }

    /**
     * Gets the amount being transferred.
     *
     * @return the amount
     */
    public final @NotNull Amount getAmount() {
        return amount;
    }

    public static abstract class Reversible extends Transfer implements Transaction.Reversible<Result.NotEmpty<Amount>, Amount> {
        /**
         * Creates a new reversible transfer of a given amount.
         *
         * @param amount an amount
         */
        public Reversible(@NotNull Amount amount) {
            super(amount);
        }

        /**
         * {@inheritDoc}
         * <p>
         * This method will first attempt to take the amount from the source
         * and then attempt to give it to the receiver.
         * <p>
         * If either action fails, the transaction will fail.
         *
         * @return a pending result
         * @implNote If the {@code take} operation succeeds but the {@code give}
         * operation fails this method will make an attempt to reverse the
         * {@code take} operation; in either case, the transaction will fail.
         * @see ExceptionalOutcome#GIVE_FAILED_TAKE_REVERSED
         * @see ExceptionalOutcome#GIVE_FAILED_TAKE_HOLDS
         */
        @Override
        public @NotNull PendingResult<Result.NotEmpty<Amount>, Amount> execute() {
            final Source source = getOriginalSource();
            final Receiver receiver = getOriginalReceiver();
            final CompletableFuture<Result.NotEmpty<Amount>> cf = new CompletableFuture<>();
            final PendingResult<? extends Result.NotEmpty<Source>, Source> sourcePendingResult = source.asyncTake(getAmount());
            sourcePendingResult.ifSuccessful(() -> {
                final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> receiverPendingResult = receiver.asyncGive(getAmount());
                receiverPendingResult.ifSuccessful(() -> cf.complete(Result.success(getAmount())));
                receiverPendingResult.ifFailed(giveError -> {
                    // attempt to refund source
                    final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> sourceAsReceiver = getOriginalSource().asyncGive(getAmount());
                    sourceAsReceiver.ifSuccessful(() -> cf.completeExceptionally(new Exception(receiver, GIVE_FAILED_TAKE_REVERSED, giveError)));
                    sourceAsReceiver.ifFailed(ignored -> cf.completeExceptionally(new Exception(receiver, GIVE_FAILED_TAKE_HOLDS, giveError)));
                });
            });
            sourcePendingResult.ifFailed(e -> cf.completeExceptionally(new Exception(source, TAKE_FAILED, e)));
            return PendingResult.of(cf);
        }

        @Override
        public @NotNull PendingResult<Result.NotEmpty<Amount>, Amount> reverse() {
            final Source originalReceiver = getOriginalReceiver();
            final Receiver originalSource = getOriginalSource();
            final CompletableFuture<Result.NotEmpty<Amount>> cf = new CompletableFuture<>();
            final PendingResult<? extends Result.NotEmpty<Source>, Source> sourcePendingResult = originalReceiver.asyncTake(getAmount());
            sourcePendingResult.ifSuccessful(() -> {
                final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> receiverPendingResult = originalSource.asyncGive(getAmount());
                receiverPendingResult.ifSuccessful(() -> cf.complete(Result.success(getAmount())));
                receiverPendingResult.ifFailed(cf::completeExceptionally);
                receiverPendingResult.ifFailed(r -> getOriginalReceiver().asyncGive(getAmount())); // best effort to refund the original receiver
            });
            sourcePendingResult.ifFailed(cf::completeExceptionally);
            return PendingResult.of(cf);
        }

        /**
         * Gets the participant that will provide the amount.
         * <p>
         * This participant will receive the amount if the transaction is reversed.
         *
         * @return a participant
         */
        public abstract <S extends Source & Receiver> @NotNull S getOriginalSource();

        /**
         * Gets the participant that will receive the amount.
         * <p>
         * This participant will provide the amount if the transaction is reversed.
         *
         * @return a participant
         */
        public abstract  <R extends Receiver & Source> @NotNull R getOriginalReceiver();

        /**
         * {@inheritDoc}
         * @deprecated see {@link #getOriginalSource()}
         */
        @Override
        @Deprecated
        public final @NotNull Source getSource() {
            return getOriginalSource();
        }

        /**
         * {@inheritDoc}
         * @deprecated see {@link #getOriginalReceiver()}
         */
        @Override
        @Deprecated
        public final @NotNull Receiver getReceiver() {
            return getOriginalReceiver();
        }
    }

    /**
     * The possible exceptional outcomes of a transfer.
     * <p>
     * Note that {@link #GIVE_FAILED_TAKE_REVERSED} is only possible if
     * the sender supports receiving or the transfer is reversible.
     *
     * @since 2.0.0
     */
    public enum ExceptionalOutcome {
        TAKE_FAILED("not sent"),
        GIVE_FAILED("sent but not received"),
        GIVE_FAILED_TAKE_REVERSED("sent but not received; sender refunded"),
        GIVE_FAILED_TAKE_HOLDS("sent but not received; sender not refunded"),
        ;

        private final String message;

        ExceptionalOutcome(String message) {
            this.message = message;
        }
    }

    /**
     * A system exception involving a transaction.
     *
     * @since 2.0.0
     * @see ParticipantException
     * @see Throwable#getCause()
     */
    public static final class Exception extends ParticipantException {
        private static final long serialVersionUID = 5627984397988688466L;
        private final ExceptionalOutcome outcome;

        /**
         * Constructs a new exception with the given outcome and cause.
         *
         * @param participant the participant
         * @param outcome an exceptional outcome
         * @param cause the cause
         */
        private Exception(@NotNull Resolvable participant, ExceptionalOutcome outcome, Throwable cause) {
            super(participant, "Transaction failed: " + outcome.message, cause);
            this.outcome = outcome;
        }

        /**
         * Gets the outcome.
         * <p>
         * This will reflect the state of the transaction at the time of the error.
         * <p>
         * As such, it will be any of the following:
         * <ul>
         *     <li>{@link ExceptionalOutcome#TAKE_FAILED}</li>
         *     <li>{@link ExceptionalOutcome#GIVE_FAILED}</li>
         *     <li>{@link ExceptionalOutcome#GIVE_FAILED_TAKE_REVERSED}</li>
         *     <li>{@link ExceptionalOutcome#GIVE_FAILED_TAKE_HOLDS}</li>
         * </ul>
         *
         * @return an outcome
         */
        public @NotNull Transfer.ExceptionalOutcome getOutcome() {
            return outcome;
        }
    }
}
