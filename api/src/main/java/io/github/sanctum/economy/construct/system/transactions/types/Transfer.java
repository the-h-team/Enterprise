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
import io.github.sanctum.economy.construct.system.behaviors.Receiver;
import io.github.sanctum.economy.construct.system.behaviors.Source;
import io.github.sanctum.economy.construct.system.transactions.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

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

    @Override
    public @NotNull PendingResult<Result.NotEmpty<Amount>, Amount> execute() {
        final Source source = getSource();
        final Receiver receiver = getReceiver();
        final CompletableFuture<Result.NotEmpty<Amount>> cf = new CompletableFuture<>();
        final PendingResult<? extends Result.NotEmpty<Source>, Source> sourcePendingResult = source.asyncTake(amount);
        sourcePendingResult.ifSuccessful(s -> {
            final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> receiverPendingResult = receiver.asyncGive(amount);
            receiverPendingResult.ifSuccessful(r -> cf.complete(Result.success(amount)));
            receiverPendingResult.ifFailed(cf::completeExceptionally);
        });
        sourcePendingResult.ifFailed(cf::completeExceptionally);
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

        @Override
        public @NotNull PendingResult<Result.NotEmpty<Amount>, Amount> execute() {
            final Source originalSource = getOriginalSource();
            final Receiver originalReceiver = getOriginalReceiver();
            final CompletableFuture<Result.NotEmpty<Amount>> cf = new CompletableFuture<>();
            final PendingResult<? extends Result.NotEmpty<Source>, Source> sourcePendingResult = originalSource.asyncTake(getAmount());
            sourcePendingResult.ifSuccessful(s -> {
                final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> receiverPendingResult = originalReceiver.asyncGive(getAmount());
                receiverPendingResult.ifSuccessful(r -> cf.complete(Result.success(getAmount())));
                receiverPendingResult.ifFailed(cf::completeExceptionally);
                receiverPendingResult.ifFailed(r -> getOriginalSource().asyncGive(getAmount())); // best effort to refund the source
            });
            sourcePendingResult.ifFailed(cf::completeExceptionally);
            return PendingResult.of(cf);
        }

        @Override
        public @NotNull PendingResult<Result.NotEmpty<Amount>, Amount> reverse() {
            final Source originalReceiver = getOriginalReceiver();
            final Receiver originalSource = getOriginalSource();
            final CompletableFuture<Result.NotEmpty<Amount>> cf = new CompletableFuture<>();
            final PendingResult<? extends Result.NotEmpty<Source>, Source> sourcePendingResult = originalReceiver.asyncTake(getAmount());
            sourcePendingResult.ifSuccessful(s -> {
                final PendingResult<? extends Result.NotEmpty<Receiver>, Receiver> receiverPendingResult = originalSource.asyncGive(getAmount());
                receiverPendingResult.ifSuccessful(r -> cf.complete(Result.success(getAmount())));
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
}
