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

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A basic implementation of {@link ServiceRegistry}.
 *
 * @since 2.0.0
 * @author ms5984
 * @param <S> the service type
 */
public abstract class AbstractServiceRegistry<S extends AttributableService> implements ServiceRegistry<S> {
    /**
     * Gets the backing map.
     * <p>
     * This map should not be exposed to consumers.
     *
     * @return the backing map
     * @implSpec The returned map must be mutable and preferably final.
     */
    protected abstract @NotNull Map<SystemImplementation, S> registryMap();

    @Override
    public @Nullable S register(@NotNull S service) {
        final Map<SystemImplementation, S> map = registryMap();
        synchronized (map) {
            return map.put(service.getImplementationInfo(), service);
        }
    }

    @Override
    public boolean unregisterSystem(@NotNull SystemImplementation implementationInfo) {
        final Map<SystemImplementation, S> map = registryMap();
        synchronized (map) {
            return map.remove(implementationInfo) != null;
        }
    }

    @Override
    public boolean unregisterService(@NotNull S service) {
        final Map<SystemImplementation, S> map = registryMap();
        synchronized (map) {
            return map.remove(service.getImplementationInfo(), service);
        }
    }

    @Override
    public @Nullable S get(@NotNull SystemImplementation implementationInfo) {
        final Map<SystemImplementation, S> map = registryMap();
        synchronized (map) {
            return map.get(implementationInfo);
        }
    }

    @Override
    public @NotNull Set<S> getAll() {
        final Map<SystemImplementation, S> map = registryMap();
        synchronized (map) {
            return Collections.unmodifiableSet(new HashSet<>(map.values()));
        }
    }
}
