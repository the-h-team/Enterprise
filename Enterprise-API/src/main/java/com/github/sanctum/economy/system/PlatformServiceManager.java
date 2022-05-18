/*
 *   Copyright 2022 Sanctum <https://github.com/the-h-team>
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
package com.github.sanctum.economy.system;

import com.github.sanctum.economy.impl.CurrencyService;
import com.github.sanctum.economy.impl.WalletService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exposes available economy services with agnostic base API.
 * <p>
 * Each platform has a specialization of this class. Instances
 * are maintained by the platform itself.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class PlatformServiceManager {
    /**
     * The manager singleton.
     */
    protected static PlatformServiceManager instance;
    /**
     * The active currency service.
     */
    protected CurrencyService currencyService;
    final String name;

    /**
     * Create a new service manager instance for a particular platform.
     *
     * @param name the name of the platform
     */
    protected PlatformServiceManager(@NotNull String name) {
        this.name = name;
    }

    /**
     * Get the name of the platform.
     *
     * @return the name of the platform
     */
    public final @NotNull String getPlatformName() {
        return name;
    }

    /**
     * Get the active currency service.
     *
     * @return the active currency service, if any
     */
    public final @Nullable CurrencyService getCurrencyService() {
        return currencyService;
    }

    /**
     * Set the active currency service.
     *
     * @param currencyService a currency service
     */
    public final void setCurrencyService(@Nullable CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Get the active wallet service.
     *
     * @return the active wallet service, if any
     */
    public abstract @Nullable WalletService<?, ?> getWalletService();

    /**
     * Get the service manager instance.
     *
     * @return the service manager instance
     * @throws IllegalStateException if the platform service manager is not set
     */
    public static PlatformServiceManager getInstance() {
        if (instance == null) throw new IllegalStateException("No platform service manager instance found.");
        return instance;
    }
}
