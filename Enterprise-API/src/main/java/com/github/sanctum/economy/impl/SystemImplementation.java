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
package com.github.sanctum.economy.impl;

import org.jetbrains.annotations.NotNull;

/**
 * Get information about a system implementation, such as by a plugin.
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface SystemImplementation {
    /**
     * Get the name of the assembly.
     *
     * @return the name of the implementing assembly
     */
    @NotNull String getName();

    /**
     * Get the version of the implementation.
     * <p>
     * Defaults to empty string.
     *
     * @return the version of the implementation
     */
    default @NotNull String getVersion() {
        return "";
    }
}
