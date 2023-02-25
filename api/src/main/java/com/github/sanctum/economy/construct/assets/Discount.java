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
package com.github.sanctum.economy.construct.assets;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a discount type.
 *
 * @since 2.0.0
 * @author ms5984
 */
public enum Discount {
    /**
     * A percentage-based discount.
     * <p>
     * If the beginning price is $10 and the discount is
     * <code>10 = 10%</code> the final price will be $9.
     */
    PERCENTAGE {
        /**
         * {@inheritDoc}
         * <p>
         * The final price will be calculated by multiplying the
         * beginning price by <code>(discount / 100)</code>.
         */
        @Override
        public CompiledDiscount from(@NotNull BigDecimal discount) {
            return super.from(discount);
        }

        /**
         * {@inheritDoc}
         * <p>
         * The final price will be calculated by multiplying the
         * beginning price by <code>(discount / 100)</code>.
         */
        @Override
        public CompiledDiscount from(@NotNull String discount) {
            return super.from(discount);
        }
    },
    /**
     * A decimal-based discount.
     * <p>
     * If the beginning price is $10 and the discount is
     * <code>0.10 = 10%</code> the final price will be $9.
     */
    DECIMAL {
        /**
         * {@inheritDoc}
         * <p>
         * The final price will be calculated by multiplying
         * the beginning price by <code>1 - discount</code>.
         */
        @Override
        public CompiledDiscount from(@NotNull BigDecimal discount) {
            return super.from(discount);
        }

        /**
         * {@inheritDoc}
         * <p>
         * The final price will be calculated by multiplying
         * the beginning price by <code>1 - discount</code>.
         */
        @Override
        public CompiledDiscount from(@NotNull String discount) {
            return super.from(discount);
        }
    },
    /**
     * An absolute discount.
     * <p>
     * If the beginning price is $10 and the discount
     * is <code>1</code> the final price will be $9.
     */
    FLAT {
        /**
         * {@inheritDoc}
         * <p>
         * The final price will be calculated by subtracting
         * <code>discount</code> from the beginning price.
         */
        @Override
        public CompiledDiscount from(@NotNull BigDecimal discount) {
            return super.from(discount);
        }

        /**
         * {@inheritDoc}
         * <p>
         * The final price will be calculated by subtracting
         * <code>discount</code> from the beginning price.
         */
        @Override
        public CompiledDiscount from(@NotNull String discount) {
            return super.from(discount);
        }
    }
    ;

    /**
     * Produces a compiled discount.
     *
     * @param discount the discount amount
     * @return a new compiled discount
     */
    public CompiledDiscount from(@NotNull BigDecimal discount) {
        return new CompiledDiscount(discount);
    }

    /**
     * Produces a compiled discount.
     *
     * @param discount the discount as a valid BigDecimal string representation
     * @return a new compiled discount
     * @throws NumberFormatException if <code>discount</code>
     * cannot be parsed as a valid BigDecimal
     */
    public CompiledDiscount from(@NotNull String discount) {
        return from(new BigDecimal(discount));
    }

    /**
     * A compiled discount ready for use.
     *
     * @since 2.0.0
     */
    public class CompiledDiscount {
        final BigDecimal discount;

        CompiledDiscount(BigDecimal discount) {
            this.discount = discount;
        }

        /**
         * Produces a new decimal amount having the discount applied.
         *
         * @param amount an existing decimal amount
         * @return a new amount with the discount applied
         */
        public DecimalAmount calculate(@NotNull DecimalAmount amount) {
            final BigDecimal finalPrice;
            switch (Discount.this) {
                case PERCENTAGE:
                    finalPrice = discount.multiply(amount.getDecimal()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    break;
                case DECIMAL:
                    finalPrice = BigDecimal.ONE.subtract(discount).multiply(amount.getDecimal());
                    break;
                case FLAT:
                    finalPrice = amount.getDecimal().subtract(discount);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return new DecimalAmountImpl(amount.getAsset(), finalPrice);
        }

        @Override
        public String toString() {
            switch (Discount.this) {
                case PERCENTAGE:
                    return "-" + discount + "%";
                case DECIMAL:
                    return "-" + discount.setScale(2, RoundingMode.HALF_UP) + "x";
                case FLAT:
                    return "-" + discount.setScale(2, RoundingMode.HALF_UP);
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
