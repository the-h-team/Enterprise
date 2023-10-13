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

import io.github.sanctum.economy.impl.BukkitWalletService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exposes Bukkit-aware service management.
 *
 * @since 2.0.0
 * @author ms5984
 */
public class BukkitPlatformServiceManager extends PlatformServiceManager {
    private @Nullable BukkitWalletService walletService;

    BukkitPlatformServiceManager() {
        super("Bukkit");
    }

    /**
     * Gets the active wallet service.
     *
     * @return the active wallet service
     */
    @Override
    public @NotNull BukkitWalletService getWalletService() {
        if (walletService == null) {
            throw new IllegalStateException("The Bukkit wallet service is not initialized!");
        }
        return walletService;
    }

    /**
     * Sets the active Bukkit wallet service.
     *
     * @param walletService the active Bukkit wallet service
     */
    public void setWalletService(@NotNull BukkitWalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Gets the service manager instance for the Bukkit platform.
     *
     * @return the service manager instance for Bukkit
     * @throws IllegalStateException if the PlatformServiceManager singleton
     * is not a SpigotServiceManager instance
     */
    public static @NotNull BukkitPlatformServiceManager getInstance() {
        try {
            return (BukkitPlatformServiceManager) instance;
        } catch (ClassCastException e) {
            throw new IllegalStateException("The platform service manager is not a BukkitPlatformServiceManager instance!");
        }
    }
}
