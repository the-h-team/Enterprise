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
import java.util.Objects;

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
     * Currency tokens (notes, bills or specie).
     * <p>
     * This class is designed such that you would first make your desired
     * base Asset and then pass it as the first constructor parameter;
     * this object will then mimic the Asset insofar as its group,
     * identifier and fqn.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public final class Token extends Asset {
        final BigDecimal worth;
        final String name;

        /**
         * Initialize the match, worth and name of this token.
         *
         * @param match the asset that will match this token
         * @param worth a face amount of base currency
         * @param name a name for this token
         * @implNote If <code>name</code> is null, this implementation
         * automatically produces a name with the following format:
         * "<code>normalized_worth</code> <code>currency_display_name</code>".
         * <p>
         * As an example:
         * <ul>
         *     <li><code>worth: 1E+1</code> (converted to "10")</li>
         *     <li><code>display_name: "Dollars"</code></li>
         * </ul>
         * = "10 Dollars" (concatenated, space-separated)
         */
        public Token(@NotNull Asset match, @NotNull BigDecimal worth, @Nullable String name) {
            super(match.group, match.identifier);
            this.worth = worth;
            this.name = name != null ? name : Amount.normalize(this.worth) + " " + displayName();
        }

        /**
         * Get the worth of this token.
         *
         * @return the worth of this token
         */
        public BigDecimal getWorth() {
            return worth;
        }

        /**
         * Get the name of this token.
         *
         * @return the name of this token
         */
        public String getName() {
            return name;
        }

        /**
         * Get the token-equivalent Amount in the represented currency.
         *
         * @return an equivalent Amount of the base currency
         */
        public Amount equivalentAmount() {
            return getAmount(worth);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Token token = (Token) o;
            return worth.equals(token.worth) &&
                    name.equals(token.name);
        }

        @Override
        public int hashCode() {
            return super.hashCode() ^ Objects.hash(worth, name);
        }
    }
}
