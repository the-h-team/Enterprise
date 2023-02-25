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

import com.github.sanctum.economy.impl.SpigotWalletService;
import org.jetbrains.annotations.Nullable;

/**
 * Exposes Spigot-aware service management.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class SpigotPlatformServiceManager extends PlatformServiceManager {
    private @Nullable SpigotWalletService walletService;

    SpigotPlatformServiceManager() {
        super("Spigot");
    }

    /**
     * Gets the active Spigot wallet service.
     *
     * @return the active Spigot wallet service
     */
    @Override
    public @Nullable SpigotWalletService getWalletService() {
        return walletService;
    }

    /**
     * Sets the active Spigot wallet service.
     *
     * @param walletService the active Spigot wallet service
     */
    public void setWalletService(@Nullable SpigotWalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Gets the service manager instance for the Spigot platform.
     *
     * @return the service manager instance for Spigot
     * @throws IllegalStateException if the PlatformServiceManager singleton
     * is not a SpigotServiceManager instance
     */
    public static SpigotPlatformServiceManager getInstance() {
        try {
            return (SpigotPlatformServiceManager) instance;
        } catch (ClassCastException e) {
            throw new IllegalStateException("The platform service manager is not a SpigotServiceManager instance!");
        }
    }
}
