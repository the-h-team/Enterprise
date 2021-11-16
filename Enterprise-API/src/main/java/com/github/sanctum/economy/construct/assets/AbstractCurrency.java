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
package com.github.sanctum.economy.construct.assets;

import com.github.sanctum.economy.construct.Amount;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * An abstract base for all currencies.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class AbstractCurrency extends AbstractAsset implements DecimalAsset {
    /**
     * Create a currency from an identifier.
     *
     * @param identifier the internal identifier for the currency
     * @throws IllegalArgumentException if <code>identifier</code> does not
     * follow the pattern of {@link Asset#VALID_IDENTIFIER}
     */
    protected AbstractCurrency(@NotNull String identifier) throws IllegalArgumentException {
        super("currency", identifier);
    }

    /**
     * Get the short symbol for this currency.
     * <p>
     * This would be used at exchanges or in foreign trade.
     *
     * @return the short symbol for this currency
     * @implSpec Like the real world, prefer three-letter, uppercase strings,
     * possibly even of the usual real-world format: <code>(two-letter country
     * code)+(initial letter of currency name)</code>.
     * <p>
     * As a reminder, the symbol for U.S. Dollars is <code>USD</code>.
     */
    public abstract @NotNull String symbol();

    /**
     * Get the formal display name of this currency.
     *
     * @return the formal display name of this currency
     * @implSpec E.g., for USD, this could be <code>"U.S. Dollars"</code>.
     * @implNote Defaults to {@link #majorNamePlural()}.
     */
    public @NotNull String displayName() {
        return majorNamePlural();
    }

    /**
     * Get the plural form of this currency's major units.
     *
     * @return the plural form of this currency's major units
     * @implSpec E.g., for USD, this could be <code>"Dollars"</code>.
     */
    public abstract @NotNull String majorNamePlural();

    /**
     * Get the singular form of this currency's major units.
     *
     * @return the singular form of this currency's major units
     * @implSpec E.g., for USD, this could be <code>"Dollar"</code>.
     */
    public abstract @NotNull String majorNameSingular();

    /**
     * Get the plural form of this currency's minor units.
     *
     * @return the plural form of this currency's minor units
     * @implSpec E.g., for USD, this could be <code>"Cents"</code>.
     */
    public abstract @Nullable String minorNamePlural();

    /**
     * Get the singular form of this currency's minor units.
     *
     * @return the singular form of this currency's minor units
     * @implSpec E.g., for USD, this could be <code>"Cent"</code>.
     */
    public abstract @Nullable String minorNameSingular();

    @Override
    public @NotNull Amount getAmount(@NotNull BigDecimal decimal) {
        return new CurrencyAmount(decimal);
    }

    /**
     * An immutable implementation of Amount for currencies.
     *
     * @since 2.0.0
     * @author ms5984
     */
    class CurrencyAmount extends Amount {
        final BigDecimal bigDecimal;

        CurrencyAmount(BigDecimal bigDecimal) {
            super(AbstractCurrency.this);
            this.bigDecimal = bigDecimal;
        }

        @Override
        public @NotNull BigDecimal getAmount() {
            return bigDecimal;
        }
    }

    /**
     * The contract of currency tokens (notes, bills or specie).
     *
     * @since 2.0.0
     * @author ms5984
     */
    public interface Token {
        /**
         * Get the worth of this token.
         *
         * @return the worth of this token
         */
        @NotNull BigDecimal getWorth();

        /**
         * Get the currency of this token.
         *
         * @return the currency represented by this token
         */
        @NotNull AbstractCurrency currency();

        /**
         * Get the token-equivalent Amount in the represented currency.
         *
         * @return an equivalent Amount of {@link #currency()}
         */
        default @NotNull Amount equivalentAmount() {
            return currency().new CurrencyAmount(getWorth());
        }

        /**
         * Get the display name for this token, if applicable.
         *
         * @return an Optional describing the display name for this token
         * @implNote This default implementation produces a display name
         * with the following format:
         * "<code>normalized_worth</code> <code>currency_display_name</code>".
         * <p>
         * As an example:
         * <ul>
         *     <li><code>worth: 1E+1</code> (converted to "10")</li>
         *     <li><code>display_name: "Dollars"</code></li>
         * </ul>
         * = "10 Dollars" (concatenated, space-separated)
         */
        default Optional<String> name() {
            return Optional.of(normalize(getWorth()) + " " + currency().displayName());
        }

        /**
         * Get the description for this token, if applicable.
         *
         * @return an Optional describing the description for this token
         */
        default Optional<String> description() {
            return Optional.empty();
        }

        /**
         * Convert values to the most compact but human-readable form.
         *
         * @param value a value to normalize
         * @return a normalized value
         * @implNote The normalization process seeks to represent values as
         * full, unscaled integers OR compact decimals. It looks like this:
         * <h3>For "0.50":</h3>
         * <ul>
         *     <li><code>0.50 -> 0.5</code></li>
         *     <li>{@link BigDecimal#scale()} is <code>&lt;0</code></li>
         *     <li>This indicates a decimal portion. We will try to strip
         *     trailing zeros from the value: <code>0.5</code>
         *     </li>
         *     <li>This new value has a smaller scale, so we will use it.</li>
         * </ul>
         * <h3>For "200":</h3>
         * <ul>
         *     <li><code>2E+2 -> 200</code></li>
         *     <li>Scale is negative: <code>-2</code></li>
         *     <li>This indicates a scaled whole number; we will simply
         *     the value by setting its scale to 0: <code>200</code>
         *     </li>
         *     <li>This value is more human-readable, so we will use it.</li>
         * </ul>
         */
        static BigDecimal normalize(@NotNull BigDecimal value) {
            final int initialScale = value.scale();
            // If the initial value has a decimal component
            if (initialScale > 0) {
                final BigDecimal stripped = value.stripTrailingZeros();
                // ...and strip does nothing, return original value; otherwise stripped
                return initialScale == stripped.scale() ? value : stripped;
            }
            // Number is whole, simply set scale to zero
            return value.setScale(0, BigDecimal.ROUND_UNNECESSARY);
        }
    }
}
