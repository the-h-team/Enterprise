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
package com.github.sanctum.economy.construct.account.helpers.operation;

import com.github.sanctum.economy.construct.EconomyAction;

import java.math.BigDecimal;

public interface Payable {
    /**
     * Attempt to deposit an amount
     * @param amount {@link BigDecimal} amount
     */
    EconomyAction deposit(BigDecimal amount);
    /**
     * Attempt to deposit an amount in the world 'world'
     * @param amount {@link BigDecimal} amount
     * @param world Name of world
     */
    EconomyAction deposit(BigDecimal amount, String world);
}
