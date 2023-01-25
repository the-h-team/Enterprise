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

/**
 * A point with contextual metadata.
 * <p>
 * Examples:
 * <ul>
 *     <li>current world (by name, uuid)</li>
 *     <li>player permissions</li>
 *     <li>custom contexts</li>
 * </ul>
 *
 * @since 2.0.0
 * @author ms5984
 */
public interface Contextual {
    /**
     * Get all contexts applied to this point.
     *
     * @return all contexts applied to this point
     */
    default @NotNull Context[] contexts() {
        return new Context[0];
    }
}
