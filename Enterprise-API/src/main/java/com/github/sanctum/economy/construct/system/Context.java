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
            return new World(NAME_TYPE, validate(worldName));
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

    static String validate(@NotNull String value) {
        if (value.contains(" ")) throw new IllegalArgumentException("Value cannot contain spaces!");
        return value;
    }
}
