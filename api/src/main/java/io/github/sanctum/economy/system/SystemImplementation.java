/*
 *   Copyright 2023 Sanctum <https://github.com/the-h-team>
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
package io.github.sanctum.economy.system;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Exposes information about a system implementation, such as by a plugin.
 *
 * @implNote Objects are immutable and safe to use in collections.
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.NonExtendable
public interface SystemImplementation {
    /**
     * The default version string.
     */
    @NotNull String DEFAULT_VERSION = "unspecified";

    /**
     * Gets the name of the assembly.
     *
     * @return the name of the implementing assembly
     */
    @NotNull String getName();

    /**
     * Gets the version of the implementation.
     *
     * @return the version of the implementing assembly
     * @see #DEFAULT_VERSION
     */
    @NotNull String getVersion();

    /**
     * Creates a new object with the given name and version.
     *
     * @param name the name of the implementing assembly
     * @param version the version of the implementing assembly
     * @return a new object
     */
    static @NotNull SystemImplementation of(@NotNull String name, @NotNull String version) {
        return new SystemImplementationImpl(name, version);
    }

    /**
     * Creates a new object with the given name and the default version.
     *
     * @param name the name of the implementing assembly
     * @return a new object with the default version
     * @see #DEFAULT_VERSION
     */
    static @NotNull SystemImplementation of(@NotNull String name) {
        return new SystemImplementationImpl(name, DEFAULT_VERSION);
    }
}
