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
public class Context {
    final String type;
    final String value;
    final String delimited;

    /**
     * Create a new context from a type key and value.
     *
     * @param type the context type
     * @param value the value
     * @implSpec Both {@code type} and {@code value}
     * must not contain spaces.
     */
    Context(@NotNull String type, @NotNull String value) {
        this.type = type;
        this.value = value;
        this.delimited = type + "=" + value;
    }

    /**
     * Get the type component of this context.
     *
     * @return the type of this context
     */
    public final String getType() {
        return type;
    }

    /**
     * Get the value component of this context.
     *
     * @return the value component
     */
    public final String getValue() {
        return value;
    }

    @Override
    public final String toString() {
        return delimited;
    }

    /**
     * Represent a world as a context.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class World extends Context {
        /**
         * The type string for worlds by name.
         */
        public static final String NAME_TYPE = "world";
        /**
         * The type string for world by UID.
         */
        public static final String UID_TYPE = "world_uid";

        World(@NotNull String type, @NotNull String value) {
            super(type, value);
        }

        /**
         * Represents a world by its name.
         *
         * @param worldName the name of the world
         * @return a new world context object
         * @throws IllegalArgumentException if
         * {@code worldName} contains spaces
         */
        public static World byName(@NotNull String worldName) throws IllegalArgumentException {
            return new World(NAME_TYPE, validateValue(worldName));
        }

        /**
         * Represents a world by its UID.
         *
         * @param worldUID the UID of the world
         * @return a new world context object
         */
        public static World byUID(@NotNull UUID worldUID) {
            return new World(UID_TYPE, worldUID.toString());
        }
    }

    /**
     * Represent a permission as a context.
     *
     * @since 2.0.0
     * @author ms5984
     */
    public static class Permission extends Context {
        /**
         * The type string for a permission.
         */
        public static final String PERMISSION_TYPE = "perm";

        Permission(@NotNull String permission) {
            super(PERMISSION_TYPE, permission);
        }

        /**
         * Represents a permission as its string form.
         *
         * @param permission the permission
         * @return a new permission context object
         * @throws IllegalArgumentException if
         * {@code permission} contains spaces
         */
        public static Permission of(@NotNull String permission) throws IllegalArgumentException {
            return new Permission(validateValue(permission));
        }
    }

    /**
     * Represents custom contexts produced by
     * a {@link CustomTypeBuilder} instance.
     *
     * @since 2.0.0
     * @author ms5984
     * @see CustomTypeBuilder
     */
    public static class Custom extends Context {
        Custom(@NotNull String type, @NotNull String value) {
            super(type, validateValue(value));
        }
    }

    /**
     * Represent custom contexts via a typed builder.
     *
     * @since 2.0.0
     * @author ms5984
     * @see Custom
     */
    public static final class CustomTypeBuilder {
        final String type;

        /**
         * Define contexts with a type key.
         *
         * @param type the context type
         * @throws IllegalArgumentException if {@code type} contains
         * spaces or represents an internally defined type
         * @see World#NAME_TYPE
         * @see World#UID_TYPE
         * @see Permission#PERMISSION_TYPE
         */
        public CustomTypeBuilder(@NotNull String type) throws IllegalArgumentException {
            this.type = validateType(type);
        }

        /**
         * Get the type key of this builder.
         *
         * @return the context type
         */
        public String getType() {
            return type;
        }

        /**
         * Define a context of this builder's type with a value string.
         *
         * @param value the value for the type
         */
        public Custom getContext(@NotNull String value) {
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
    public static CustomTypeBuilder factory(@NotNull String type) throws IllegalArgumentException {
        return new CustomTypeBuilder(type);
    }

    static String validateType(@NotNull String type) throws IllegalArgumentException {
        if (type.contains(" ")) throw new IllegalArgumentException("Type cannot contain spaces!");
        switch (type) {
            case World.NAME_TYPE:
            case World.UID_TYPE:
            case Permission.PERMISSION_TYPE:
                throw new IllegalArgumentException("Cannot use protected internal type.");
            default:
                return type;
        }
    }

    static String validateValue(@NotNull String value) throws IllegalArgumentException {
        if (value.contains(" ")) throw new IllegalArgumentException("Value cannot contain spaces!");
        return value;
    }
}
