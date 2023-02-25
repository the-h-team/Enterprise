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

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Documented;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * An abstract base for all currencies.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface AbstractCurrency extends DecimalAsset, Asset {

    /**
     * Valid alpha codes mimic ISO 4217.
     * <p>
     * See <a href="https://en.wikipedia.org/wiki/ISO_4217">ISO 4217</a>.
     */
    @RegExp String VALID_ALPHA_CODE = "^[A-Z]{3}$";

    /**
     * A String defining a currency alpha code.
     *
     * @see #VALID_ALPHA_CODE
     * @see #getAlphaCode()
     */
    @Documented
    @Pattern(VALID_ALPHA_CODE)
    @interface AlphaCode {}

    /**
     * Gets the alpha code for this currency.
     * <p>
     * This would be used at exchanges or in foreign trade.
     *
     * @return the short alpha code for this currency
     * @implSpec Like the real world, use three-letter, uppercase strings,
     * possibly even following existing ISO 4217 strategies:
     * {@code (two-letter country code)+...}
     * <ul>
     * <li>{@code (initial letter of currency name)}</li>
     * <li>a mnemonic continuation of the name itself ({@code R} for EURo)
     * </li>
     * <li>{@code "N"} as in "new" following a devaluation</li>
     * </ul>
     * <p>
     * The alpha code for U.S. Dollars is {@code USD}.
     */
    @NotNull @AlphaCode String getAlphaCode();

    /**
     * Gets the formal name of this currency.
     * <p>
     * This often includes the name of the country or region.
     * <p>
     * For USD this would be {@code "U.S. Dollars"}.
     *
     * @return the formal name of this currency
     */
    default @NotNull String getFormalName() {
        return getUnitNamePlural();
    }

    /**
     * Gets the plural form of this currency's major unit.
     * <p>
     * For USD this would be {@code "Dollars"}.
     *
     * @return the plural form of this currency's major unit
     */
    @NotNull String getUnitNamePlural();

    /**
     * Gets the singular form of this currency's major units.
     * <p>
     * For USD this would be {@code "Dollar"}.
     *
     * @return the singular form of this currency's major unit
     */
    @NotNull String getUnitNameSingular();

    // FIXME overhaul token system
    /**
     * Currency tokens (notes, bills or specie).
     * <p>
     * This class is designed such that you would first make your desired base
     * asset and then pass it as the first constructor parameter; this object
     * will then mimic the Asset insofar as group and identifier.
     *
     * @since 2.0.0
     * @author ms5984
     */
    abstract class Token implements Asset {
        final Asset match;
        final BigDecimal worth;
        final String name;

        /**
         * Initializes the match asset, worth and name of this token.
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
            this.match = match;
            this.worth = worth;
            this.name = name != null ? name : DecimalAmount.normalize(this.worth) + " " + getBaseCurrency().getFormalName();
        }

        @Override
        public @NotNull @Group String getGroup() {
            return match.getGroup();
        }

        @Override
        public @NotNull @Identifier String getIdentifier() {
            return match.getIdentifier();
        }

        /**
         * Gets the currency represented by this token.
         *
         * @return the currency this token represents
         */
        public abstract @NotNull AbstractCurrency getBaseCurrency();

        /**
         * Gets the worth of this token.
         *
         * @return the worth of this token
         */
        public BigDecimal getWorth() {
            return worth;
        }

        /**
         * Gets the name of this token.
         *
         * @return the name of this token
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the token-equivalent Amount in the represented currency.
         *
         * @return an equivalent Amount of the base currency
         */
        public Amount toEquivalentAmount() {
            return getBaseCurrency().getAmount(worth);
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
