/*
 *   Copyright 2021 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.construct.system.exceptions;

import io.github.sanctum.economy.construct.assets.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A system exception involving a specific amount.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class AmountException extends AbstractSystemException {
    private static final long serialVersionUID = 6113315531348685535L;
    final Amount amount;

    /**
     * Constructs an exception with an amount and a message.
     *
     * @param amount an amount of an asset
     * @param message a message
     */
    public AmountException(@NotNull Amount amount, String message) {
        super(message);
        this.amount = amount;
    }

    /**
     * Constructs an exception with an amount, a message and cause.
     *
     * @param amount an amount of an asset
     * @param message a message
     * @param cause a cause throwable
     */
    public AmountException(@NotNull Amount amount, String message, Throwable cause) {
        super(message, cause);
        this.amount = amount;
    }

    /**
     * Constructs an exception with an amount and a cause.
     *
     * @param amount an amount of an asset
     * @param cause a cause throwable
     */
    public AmountException(@NotNull Amount amount, Throwable cause) {
        super(cause);
        this.amount = amount;
    }

    /**
     * Gets the amount associated with this exception.
     *
     * @return an amount of an asset
     */
    public final Amount getAmount() {
        return amount;
    }
}
