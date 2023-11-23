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

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * A service that can be attributed to a given assembly.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class AttributableService implements Comparable<AttributableService> {
    private static final Comparator<AttributableService> COMPARATOR = Comparator.comparing(AttributableService::getImplementationInfo);
    private final SystemImplementation implementationInfo;
    boolean enabled = false;

    /**
     * Constructs a new service with the given implementation info.
     *
     * @param implementationInfo the implementation info
     */
    protected AttributableService(@NotNull SystemImplementation implementationInfo) {
        this.implementationInfo = implementationInfo;
    }

    /**
     * Gets the system implementation info associated with this service.
     *
     * @return associated implementation info
     * @implSpec Return <strong>must</strong> be constant for a given instance.
     */
    public final @NotNull SystemImplementation getImplementationInfo() {
        return implementationInfo;
    }

    /**
     * Checks if this service is enabled.
     *
     * @return true if enabled
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled state of this service.
     *
     * @param enabled the new enabled state
     */
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int compareTo(@NotNull AttributableService o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        // compare their classes (must match exactly)
        if (o == null || getClass() != o.getClass()) return false;
        AttributableService that = (AttributableService) o;
        return implementationInfo.equals(that.implementationInfo);
    }

    @Override
    public final int hashCode() {
        return implementationInfo.hashCode();
    }
}
