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
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
final class TransferImpl extends Transfer {
    private final Source source;
    private final Receiver receiver;

    TransferImpl(@NotNull Amount amount, @NotNull Source source, @NotNull Receiver receiver) {
        super(amount);
        this.source = source;
        this.receiver = receiver;
    }

    @Override
    public @NotNull Source getSource() {
        return source;
    }

    @Override
    public @NotNull Receiver getReceiver() {
        return receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferImpl transfer = (TransferImpl) o;
        if (!getAmount().equals(transfer.getAmount())) return false;
        if (!source.equals(transfer.source)) return false;
        return receiver.equals(transfer.receiver);
    }

    @Override
    public int hashCode() {
        int result = getAmount().hashCode();
        result = 31 * result + source.toEntity().hashCode();
        result = 31 * result + receiver.toEntity().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TransferImpl{" +
                "source=" + source.toEntity() +
                ", receiver=" + receiver.toEntity() +
                '}';
    }

    static final class ReversibleImpl extends Reversible {
        private final Source originalSource;
        private final Receiver originalReceiver;

        <S extends Source & Receiver, R extends Receiver & Source> ReversibleImpl(
                @NotNull Amount amount,
                @NotNull S originalSource,
                @NotNull R originalReceiver) {
            super(amount);
            this.originalSource = originalSource;
            this.originalReceiver = originalReceiver;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <S extends Source & Receiver> @NotNull S getOriginalSource() {
            return (S) originalSource;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends Receiver & Source> @NotNull R getOriginalReceiver() {
            return (R) originalReceiver;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReversibleImpl that = (ReversibleImpl) o;
            if (!getAmount().equals(that.getAmount())) return false;
            if (!originalSource.equals(that.originalSource)) return false;
            return originalReceiver.equals(that.originalReceiver);
        }

        @Override
        public int hashCode() {
            int result = getAmount().hashCode();
            result = 31 * result + originalSource.toEntity().hashCode();
            result = 31 * result + originalReceiver.toEntity().hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "ReversibleImpl{" +
                    "originalSource=" + originalSource.toEntity() +
                    ", originalReceiver=" + originalReceiver.toEntity() +
                    '}';
        }
    }
}
