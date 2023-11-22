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
package io.github.sanctum.economy.impl;

import io.github.sanctum.economy.system.SystemImplementation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A service that can be attributed to a given
 * {@linkplain SystemImplementation}.
 *
 * @since 2.0.0
 * @author ms5984
 */
@ApiStatus.OverrideOnly
public interface AttributableService {
    /**
     * Gets the system implementation info associated with this service.
     *
     * @return associated implementation info
     * @implSpec Return <strong>must</strong> be constant for a given instance.
     */
    @NotNull SystemImplementation getImplementationInfo();
}
