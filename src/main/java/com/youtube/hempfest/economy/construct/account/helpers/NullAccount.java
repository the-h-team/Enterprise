package com.youtube.hempfest.economy.construct.account.helpers;

import com.youtube.hempfest.economy.construct.EconomyAction;
import com.youtube.hempfest.economy.construct.account.Account;
import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import com.youtube.hempfest.economy.construct.entity.EconomyEntity;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Null Object pattern -- return this type if no Account
 * exists for an entity in a particular context
 */
public abstract class NullAccount extends Account {

    protected NullAccount(AccountType accountType, EconomyEntity holder, EconomyEntity... members) {
        super(accountType, holder, members);
    }

    @Override
    public void setBalance(BigDecimal amount) {
    }

    @Override
    public void setBalance(BigDecimal amount, String world) {
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
