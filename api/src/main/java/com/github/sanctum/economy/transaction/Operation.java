/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
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

import com.github.sanctum.economy.construct.system.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents the action performed in a transaction.
 *
 * @since 2.0.0
 * @author ms5984
 */
public enum Operation {
    /**
     * A point is checked for an amount (true or false only).
     */
    QUERY(Queryable.class, "{1} has {0}"),
    /**
     * A point is given an amount.
     */
    GIVE(Receiver.class, "{1} was given {0}"),
    /**
     * An amount is set for a point.
     */
    SET(Settable.class, "Asset {2} was set to {0} for {1}"),
    /**
     * An amount is taken from a point.
     */
    TAKE(Source.class, "{0} was taken from {1}"),
    /**
     * A point is asked for its total of an asset.
     */
    TOTAL(Total.class, "{1} has {0} of {2}"),
    ;

    /**
     * The system class that represents this operation.
     */
    public final Class<?> interfaceClass;
    /**
     * The default template string for
     * {@link MemoryTransaction#getDescription()}.
     * <p>
     * The format is as such:
     * <ol start="0">
     *     <li>Amount</li>
     *     <li>Primary/Primaries</li>
     *     <li>Asset</li>
     *     <li>Success</li>
     *     <li>Operation</li>
     *     <li>Exception</li>
     * </ol>
     */
    public final String defaultTemplate;
    final AtomicReference<String> template;

    Operation(Class<?> interfaceClass, String defaultTemplate) {
        this.interfaceClass = interfaceClass;
        this.defaultTemplate = defaultTemplate;
        this.template = new AtomicReference<>(defaultTemplate);
    }

    /**
     * Get the user-configured description template for this action.
     *
     * @return an Optional describing an alternate description template
     * @see #defaultTemplate
     */
    public Optional<String> getTemplate() {
        return Optional.ofNullable(template.get());
    }

    /**
     * Set a custom description template for this action.
     *
     * @param template a new template
     * @see #defaultTemplate
     */
    public void setTemplate(@Nullable String template) {
        this.template.set(template);
    }
}
