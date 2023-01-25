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
package com.github.sanctum.economy.transaction;

import com.github.sanctum.economy.construct.Amount;
import com.github.sanctum.economy.construct.assets.Asset;
import com.github.sanctum.economy.construct.entity.EnterpriseEntity;
import com.github.sanctum.economy.construct.system.AbstractSystemException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Holds transaction information in memory for reference.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class MemoryTransaction {
    transient final Amount amount;
    transient final Asset asset;
    transient final AbstractSystemException exception;
    transient final EnterpriseEntity[] primaries;
    final boolean success;
    final String info;
    final Operation operation;
    final String description;

    /**
     * Create a new transaction from provided information.
     *
     * @param amount an Amount (of the asset provided below)
     * @param asset an asset (must match <code>amount</code>)
     * @param operation a transaction type
     * @param exception a system exception if one has occurred
     * @param success a general success/failure status
     * @param info custom detail for the transaction
     * @param primaries the involved entity or entities
     */
    protected MemoryTransaction(@Nullable Amount amount,
                                @NotNull Asset asset,
                                @NotNull Operation operation,
                                @Nullable AbstractSystemException exception,
                                boolean success,
                                @Nullable String info,
                                @NotNull EnterpriseEntity... primaries) {
        this.amount = amount;
        this.asset = asset;
        this.operation = operation;
        this.exception = exception;
        this.success = success;
        this.info = info;
        this.primaries = primaries;
        this.description = info != null ? info : operation.getTemplate().map(this::doReplacements).orElseGet(this::defaultReplacements);
    }

    /**
     * Get the original Amount object for this transaction, if applicable.
     *
     * @return an Optional describing the original Amount
     */
    public @NotNull Optional<Amount> getAmount() {
        return Optional.ofNullable(amount);
    }

    /**
     * Get asset of the transaction.
     *
     * @return the asset
     */
    public final Asset getAsset() {
        return asset;
    }

    /**
     * Get the operation (transaction type).
     *
     * @return the transaction type
     */
    public final Operation getOperation() {
        return operation;
    }

    /**
     * Get the exception associated with this transaction, if applicable.
     *
     * @return an Optional describing a system exception
     */
    public @NotNull Optional<? extends AbstractSystemException> getException() {
        return Optional.ofNullable(exception);
    }

    /**
     * Whether the transaction in general was a success.
     *
     * @return true on successful transactions
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Get additional detail about the transaction (if provided).
     *
     * @return an Optional describing transaction detail
     */
    public final Optional<String> getInfo() {
        return Optional.ofNullable(info);
    }

    /**
     * Get a written description of this transaction.
     * <p>
     * If {@link #getInfo()} is present, it is returned preferentially.
     * The next fallback is {@link Operation#getTemplate()}, followed
     * by {@link Operation#defaultTemplate}.
     *
     * @return a simple, non-empty description for this transaction
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Get the primary actor or actors in this transaction.
     *
     * @return the primary actor or actors
     */
    public final EnterpriseEntity[] getPrimaries() {
        return primaries;
    }

    @NotNull String doReplacements(@NotNull String template) {
        if (!template.contains("{") || !template.contains("}")) return template;
        if (template.contains("{0}")) {
            template = template.replace("{0}", String.valueOf(amount));
        }
        if (template.contains("{1}")) {
            template = template.replace("{1}", listPrimaries());
        }
        if (template.contains("{2}")) {
            template = template.replace("{2}", String.valueOf(asset));
        }
        if (template.contains("{3}")) {
            template = template.replace("{3}", String.valueOf(success));
        }
        if (template.contains("{4}")) {
            template = template.replace("{4}", String.valueOf(operation));
        }
        if (template.contains("{5}")) {
            template = template.replace("{5}", String.valueOf(exception));
        }
        return template;
    }

    String defaultReplacements() {
        return doReplacements(operation.defaultTemplate);
    }

    String listPrimaries() {
        if (primaries.length == 0) {
            return "[none]"; // I guess
        }
        final String firstName = primaries[0].getFriendlyName();
        if (primaries.length == 1) return firstName;
        final StringBuilder sb = new StringBuilder(firstName);
        for (int i = 1; i < primaries.length; ++i) {
            final String friendlyName = primaries[i].getFriendlyName();
            if (i + 1 < primaries.length) {
                if (friendlyName.contains(",")) {
                    // complex list
                    sb.append("; ");
                } else {
                    sb.append(", ");
                }
            } else {
                sb.append(" and ");
            }
            sb.append(friendlyName);
        }
        return sb.toString();
    }
}
