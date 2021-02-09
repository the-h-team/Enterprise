/*
 *  Copyright 2021 Sanctum <https://github.com/the-h-team>
 *  Copyright 2020 Hempfest <https://github.com/Hempfest>
 *  Copyright 2020 ms5984 (Matt) <https://github.com/ms5984>
 *
 *  This file is part of Enterprise.
 *
 *  Enterprise is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  Enterprise is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.sanctum.economy.construct.account;

import com.github.sanctum.economy.construct.entity.types.PlayerEntity;
import org.bukkit.OfflinePlayer;

/**
 * Base class for player-based wallets.
 * <p>Easily accepts OfflinePlayer and handles entity formation and
 * provide convenience method to retrieve reference to the player</p>
 */
public abstract class PlayerWallet extends Wallet {
    protected PlayerWallet(OfflinePlayer player) {
        super(new PlayerEntity(player));
    }

    @Override
    public PlayerEntity getHolder() {
        return (PlayerEntity) super.getHolder();
    }

    public OfflinePlayer getPlayer() {
        return getHolder().getPlayer();
    }
}
