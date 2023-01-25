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

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;

/**
 * Represents a particular context using a String
 * key-value pair delimited by equals (=).
 * <p>
 * Example:
 * <ul>
 *     <li>world=world</li>
 * </ul>
 *
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.NonExtendable
public interface Context {
    /**
     * Valid types must start with a lowercase letter; may contain upper and
     * lowercase letters, numbers, periods and underscores between beginning
     * and end; and must end with only an upper or lowercase letter or
     * a number.
     */
    @RegExp String VALID_TYPE = "^[a-z]([\\w.]*[a-zA-Z\\d])?$";
    /**
     * Valid values may contain only letters, numbers, underscores, periods,
     * colons, curly braces, spaces and hyphens; but must not start or end with
     * whitespace.
     */
    @RegExp String VALID_VALUE = "^[\\w.:{}-](?:[\\w.:{} -]*[\\w.:{}-])?$";

    /**
     * A String defining a context type.
     *
     * @see #VALID_TYPE
     */
    @Documented
    @Pattern(VALID_TYPE)
    @interface Type {}

    /**
     * A String defining a context value.
     *
     * @see #VALID_VALUE
     */
    @Documented
    @Pattern(VALID_VALUE)
    @interface Value {}

    /**
     * Gets the type component of this context.
     *
     * @return the type of this context
     */
    @Type @NotNull String getType();

    /**
     * Gets the value component of this context.
     *
     * @return the value of this context
     */
    @Value @NotNull String getValue();

    /**
     * Represents a world as a context.
     *
     * @since 2.0.0
     */
    class World extends ContextImpl {
        /**
         * The type string for every world context.
         */
        public static final String TYPE = "world";

        /**
         * Creates a new world context.
         *
         * @param worldRepresentation a suitable identifier for the world
         */
        protected World(@Value @NotNull String worldRepresentation) {
            super(TYPE, worldRepresentation);
        }

        /**
         * Creates a new world context from a world name.
         * <p>
         * The world name is used as the value component.
         *
         * @param worldName the name of the world
         * @return a new world context
         */
        public static World name(@NotNull String worldName) {
            if (!worldName.matches(VALID_VALUE)) {
                throw new IllegalArgumentException("Invalid world name: " + worldName);
            }
            return new World(worldName);
        }
    }

    /**
     * Represents a custom context.
     *
     * @since 2.0.0
     * @author ms5984
     * @see #of(String, String)
     * @see #factory(String)
     */
    final class Custom extends ContextImpl {
        private Custom(@Type String type, @Value String value) {
            super(type, value);
        }
    }

    /**
     * Creates custom contexts via a typed builder.
     *
     * @since 2.0.0
     * @author ms5984
     * @see Custom
     */
    final class CustomTypeBuilder {
        final @Type String type;

        CustomTypeBuilder(@Type @NotNull String type) {
            this.type = validateCustomType(type);
        }

        /**
         * Gets the type key of this builder.
         *
         * @return the context type
         */
        public @Type String getType() {
            return type;
        }

        /**
         * Defines a context of this builder's type.
         *
         * @param value the value for the context
         */
        public Custom getContext(@Value @NotNull String value) {
            return new Custom(type, value);
        }
    }

    /**
     * Gets a builder for contexts with a predefined, shared type.
     *
     * @param type the context type
     * @throws IllegalArgumentException if {@code type} contains
     * spaces or represents an internally defined type
     * @see World#TYPE
     */
    static CustomTypeBuilder factory(@Type @NotNull String type) {
        return new CustomTypeBuilder(type);
    }

    /**
     * Gets an arbitrary context.
     * <p>
     * The given type must not match any of the internally defined types.
     *
     * @param type the context type
     * @param value the context value
     * @return a new context
     * @throws IllegalArgumentException if {@code type} matches an
     * internally defined type
     * @see World#TYPE
     */
    static Custom of(@Type @NotNull String type, @Value @NotNull String value) throws IllegalArgumentException {
        return new Custom(validateCustomType(type), value);
    }

    /**
     * Checks to see if a custom type is valid.
     * <p>
     * Custom types must not match any of the internally defined types.
     *
     * @param type a context type
     * @return the type if valid
     * @throws IllegalArgumentException if {@code type} matches an
     * internally defined type
     */
    static String validateCustomType(@NotNull String type) throws IllegalArgumentException {
        //noinspection SwitchStatementWithTooFewBranches
        switch (type) {
            case World.TYPE:
                throw new IllegalArgumentException("Cannot use protected internal type.");
            default:
                return type;
        }
    }
}
