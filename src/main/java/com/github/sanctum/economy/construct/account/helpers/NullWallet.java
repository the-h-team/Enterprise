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
package com.github.sanctum.economy.construct.account.helpers;

import com.github.sanctum.economy.construct.entity.EconomyEntity;

/**
 * Null Object pattern -- return an object subclassing this type if no Wallet
 * exists for an entity in a particular context. You will need to override
 * {@link #exists()} and {@link #exists(String)} based on the situation.
 */
public abstract class NullWallet extends NullBalance {

    protected NullWallet(EconomyEntity holder) {
        super(holder);
    }

}
