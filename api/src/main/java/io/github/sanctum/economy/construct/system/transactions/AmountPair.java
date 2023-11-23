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
package io.github.sanctum.economy.construct.system.transactions;

import io.github.sanctum.economy.construct.assets.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a pair of amounts.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class AmountPair {
    private final Amount first;
    private final Amount second;

    /**
     * Constructs a new amount pair.
     *
     * @param first the first amount
     * @param second the second amount
     */
    public AmountPair(@NotNull Amount first, @NotNull Amount second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first amount.
     *
     * @return the first amount
     */
    public @NotNull Amount getFirst() {
        return first;
    }

    /**
     * Gets the second amount.
     *
     * @return the second amount
     */
    public @NotNull Amount getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountPair that = (AmountPair) o;
        if (!first.equals(that.first)) return false;
        return second.equals(that.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AmountPair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
