/*
 *  Copyright 2023 Sanctum <https://github.com/the-h-team>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.sanctum.economy.transaction;

import com.github.sanctum.economy.construct.system.AbstractSystemException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents the result of a transaction in memory.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <T> the type of transaction
 * @param <E> the type of system error that may have occurred
 */
public class Result<T extends MemoryTransaction, E extends AbstractSystemException> {
    final UUID uuid;
    final T transaction;
    final E error;
    final boolean success;

    Result(@NotNull UUID uuid, @NotNull T transaction, boolean success) {
        this(uuid, transaction, null, success);
    }

    protected Result(@NotNull UUID uuid, @NotNull T transaction, @Nullable E error, boolean success) {
        this.uuid = uuid;
        this.transaction = transaction;
        this.error = error;
        this.success = success;
    }

    /**
     * Gets the unique identifier of this transaction execution.
     *
     * @return the unique identifier
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the transaction.
     *
     * @return the transaction
     */
    public @NotNull T getTransaction() {
        return transaction;
    }

    /**
     * Gets the error that occurred during the transaction, if any.
     *
     * @return the error or null
     */
    public @Nullable E getError() {
        return null;
    }

    /**
     * In general, indicates if a transaction was successful.
     *
     * @return true if successful
     * @implNote Success is an implementation-specific concept.
     */
    public boolean wasSuccess() {
        return success;
    }

    /**
     * Creates a result from a UUID, transaction spec and success state.
     *
     * @param uuid the unique identifier of this transaction execution
     * @param transaction the original transaction specification
     * @param success whether the transaction was "successful"
     * @return a new result
     * @implSpec Success is an implementation-specific concept.
     */
    public static <T extends MemoryTransaction> Result<T, ?> of(@NotNull UUID uuid, @NotNull T transaction, boolean success) {
        return new Result<>(uuid, transaction, success);
    }

    /**
     * Creates a result from a UUID, transaction spec, error and success state.
     *
     * @param uuid the unique identifier of this transaction execution
     * @param transaction the original transaction specification
     * @param error the error, if any
     * @param success whether the transaction was "successful"
     * @implSpec Success is an implementation-specific concept.
     */
    public static <T extends MemoryTransaction, E extends AbstractSystemException> Result<T, E> of(@NotNull UUID uuid, @NotNull T transaction, @Nullable E error, boolean success) {
        return new Result<>(uuid, transaction, error, success);
    }
}
