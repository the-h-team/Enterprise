/*
 *  Copyright 2023 Sanctum <https://github.com/the-h-team>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.sanctum.economy.system;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Stores instances of a single service type.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <S> the service type
 */
public interface ServiceRegistry<S extends AttributableService> {
    /**
     * Registers a service instance.
     *
     * @param service the service instance
     * @return the replaced service instance or null if none was replaced
     * @implSpec If a service with the same {@link S#getImplementationInfo()}
     * is already registered it will be replaced.
     */
    @Nullable S register(@NotNull S service);

    /**
     * Unregister a service instance by its defining system.
     *
     * @param implementationInfo the implementation info
     * @return true if a service was removed
     */
    boolean unregisterSystem(@NotNull SystemImplementation implementationInfo);

    /**
     * Unregister a service instance.
     * <p>
     * The provided service must be the same instance as the one that was registered.
     *
     * @param service a service instance
     * @return true if the service was removed
     */
    boolean unregisterService(@NotNull S service);

    /**
     * Gets the service instance associated with the given implementation info.
     *
     * @param implementationInfo the implementation info
     * @return the service instance or null if none is registered
     */
    @Nullable S get(@NotNull SystemImplementation implementationInfo);

    /**
     * Gets all registered service instances.
     * <p>
     * The returned set is an unmodifiable copy.
     *
     * @return all registered service instances
     */
    @NotNull Set<S> getAll();
}
