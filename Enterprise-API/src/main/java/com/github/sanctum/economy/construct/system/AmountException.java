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
package com.github.sanctum.economy.construct.system;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;

/**
 * A system exception involving a specific Amount.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class AmountException extends AbstractSystemException {
    private static final long serialVersionUID = 6113315531348685535L;
    protected final Amount amount;

    /**
     * Construct an exception with an Amount and a message.
     *
     * @param amount an amount of an asset
     * @param message a message
     */
    AmountException(@NotNull Amount amount, String message) {
        super(message);
        this.amount = amount;
    }

    /**
     * Construct an exception with an Amount, a message and cause.
     *
     * @param amount an amount of an asset
     * @param message a message
     * @param cause a cause throwable
     */
    AmountException(@NotNull Amount amount, String message, Throwable cause) {
        super(message, cause);
        this.amount = amount;
    }

    /**
     * Construct an exception with an Amount and a cause.
     *
     * @param amount an amount of an asset
     * @param cause a cause throwable
     */
    AmountException(@NotNull Amount amount, Throwable cause) {
        super(cause);
        this.amount = amount;
    }

    /**
     * Get the Amount associated with this exception.
     *
     * @return an amount of an asset
     */
    public final Amount getAmount() {
        return amount;
    }
}
