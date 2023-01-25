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

import java.util.UUID;

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
    @RegExp String VALID_TYPE = "[a-z]([\\w.]*[a-zA-Z\\d])?";
    /**
     * Valid values may contain only letters, numbers, underscores, periods,
     * spaces and hyphens; but must not start or end with whitespace.
     */
    @RegExp String VALID_VALUE = "[\\w.-][\\w. -]*[\\w.-]?";
    @Pattern(VALID_TYPE)
    @interface Type {}
    @Pattern(VALID_VALUE)
    @interface Value {}

    /**
     * Get the type component of this context.
     *
     * @return the type of this context
     */
    @Type String getType();

    /**
     * Get the value component of this context.
     *
     * @return the value component
     */
    @Value String getValue();

    /**
     * Represent a world as a context.
     *
     * @since 2.0.0
     * @author ms5984
     */
    @ApiStatus.NonExtendable
    interface World extends Context {
        /**
         * The type string for worlds by name.
         */
        @Type String NAME_TYPE = "world";
        /**
         * The type string for world by UID.
         */
        @Type String UID_TYPE = "world_uid";

        /**
         * Represents a world by its name.
         */
        @ApiStatus.NonExtendable
        interface Name extends World {
            @Override
            default @Type String getType() {
                return NAME_TYPE;
            }
        }

        /**
         * Represents a world by its UID.
         */
        @ApiStatus.NonExtendable
        interface UID extends World {
            @Override
            default @Type String getType() {
                return UID_TYPE;
            }
        }

        /**
         * Represents a world by its name.
         *
         * @param worldName the name of the world
         * @return a new world context object
         */
        static Name byName(@Value @NotNull String worldName) {
            return () -> worldName;
        }

        /**
         * Represents a world by its UID.
         *
         * @param worldUID the UID of the world
         * @return a new world context object
         */
        static UID byUID(@NotNull UUID worldUID) {
            return worldUID::toString;
        }
    }

    /**
     * Represent a permission as a context.
     *
     * @since 2.0.0
     * @author ms5984
     */
    @ApiStatus.NonExtendable
    interface Permission extends Context {
        /**
         * The type string for a permission.
         */
        @Type String PERMISSION_TYPE = "perm";

        @Override
        default @Type String getType() {
            return PERMISSION_TYPE;
        }

        /**
         * Represents a permission as its string form.
         *
         * @param permission the permission
         * @return a new permission context object
         */
        static Permission of(@Value @NotNull String permission) {
            return () -> permission;
        }
    }

    /**
     * Represents a custom context produced by a
     * {@link CustomTypeBuilder} instance.
     *
     * @since 2.0.0
     * @author ms5984
     * @see CustomTypeBuilder
     */
    class Custom implements Context {
        final @Type String type;
        final @Value String value;

        private Custom(@Type String type, @Value String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public @Type String getType() {
            return type;
        }

        @Override
        public @Value String getValue() {
            return null;
        }
    }

    /**
     * Represent custom contexts via a typed builder.
     *
     * @since 2.0.0
     * @author ms5984
     * @see Custom
     */
    final class CustomTypeBuilder {
        final @Type String type;

        /**
         * Define contexts with a type key.
         *
         * @param type the context type
         * @throws IllegalArgumentException if {@code type}
         * represents an internally defined type
         * @see World#NAME_TYPE
         * @see World#UID_TYPE
         * @see Permission#PERMISSION_TYPE
         */
        public CustomTypeBuilder(@Type @NotNull String type) throws IllegalArgumentException {
            this.type = validateType(type);
        }

        /**
         * Get the type key of this builder.
         *
         * @return the context type
         */
        public @Type String getType() {
            return type;
        }

        /**
         * Define a context of this builder's type with a value string.
         *
         * @param value the value for the type
         */
        public Custom getContext(@Value @NotNull String value) {
            return new Custom(type, value);
        }
    }

    /**
     * Get a context builder with a defined type key.
     *
     * @param type the context type
     * @throws IllegalArgumentException if {@code type} contains
     * spaces or represents an internally defined type
     * @see World#NAME_TYPE
     * @see World#UID_TYPE
     * @see Permission#PERMISSION_TYPE
     */
    static CustomTypeBuilder factory(@Type @NotNull String type) throws IllegalArgumentException {
        return new CustomTypeBuilder(type);
    }

    static String validateType(@NotNull String type) throws IllegalArgumentException {
        switch (type) {
            case World.NAME_TYPE:
            case World.UID_TYPE:
            case Permission.PERMISSION_TYPE:
                throw new IllegalArgumentException("Cannot use protected internal type.");
            default:
                return type;
        }
    }
}
