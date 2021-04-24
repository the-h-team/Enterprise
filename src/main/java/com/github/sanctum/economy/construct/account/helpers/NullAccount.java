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

import com.github.sanctum.economy.construct.EconomyAction;
import com.github.sanctum.economy.construct.account.Account;
import com.github.sanctum.economy.construct.account.permissive.AccountType;
import com.github.sanctum.economy.construct.entity.EconomyEntity;
import java.math.BigDecimal;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

/**
 * Null Object pattern -- return an object subclassing this type if no Account
 * exists for an entity in a particular context. You will need to override
 * {@link #exists()} and {@link #exists(String)} based on the situation.
 */
public abstract class NullAccount extends Account {

    protected NullAccount(AccountType accountType, EconomyEntity holder, EconomyEntity... members) {
        super(accountType, holder, members);
    }

    @Override
    public EconomyAction setBalance(BigDecimal amount) {
        return null;
    }

    @Override
    public EconomyAction setBalance(BigDecimal amount, String world) {
        return null;
    }

    @Override
    public @Nullable BigDecimal getBalance() {
        return null;
    }

    @Override
    public @Nullable BigDecimal getBalance(String world) {
        return null;
    }

    @Override
    public boolean has(BigDecimal amount) {
        return false;
    }

    @Override
    public boolean has(BigDecimal amount, String world) {
        return false;
    }

    @Override
    public EconomyAction deposit(BigDecimal amount) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction deposit(BigDecimal amount, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction withdraw(BigDecimal amount) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction withdraw(BigDecimal amount, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isOwner(String name) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isOwner(String name, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isOwner(OfflinePlayer player) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isOwner(OfflinePlayer player, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isOwner(UUID uuid) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isOwner(UUID uuid, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isJointOwner(String name) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isJointOwner(String name, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isJointOwner(OfflinePlayer player) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isJointOwner(OfflinePlayer player, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isJointOwner(UUID uuid) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isJointOwner(UUID uuid, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isMember(String name) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isMember(String name, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isMember(OfflinePlayer player) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isMember(OfflinePlayer player, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isMember(UUID uuid) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction isMember(UUID uuid, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction addMember(String name) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction addMember(String name, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction addMember(OfflinePlayer player) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction addMember(OfflinePlayer player, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction addMember(UUID uuid) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction addMember(UUID uuid, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction removeMember(String name) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction removeMember(String name, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction removeMember(OfflinePlayer player) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction removeMember(OfflinePlayer player, String world) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction removeMember(UUID uuid) {
        return new EconomyAction(holder, false, "");
    }

    @Override
    public EconomyAction removeMember(UUID uuid, String world) {
        return new EconomyAction(holder, false, "");
    }
}
