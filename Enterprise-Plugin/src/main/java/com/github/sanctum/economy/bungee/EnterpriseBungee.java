/*
 *  Copyright 2021 Sanctum <https://github.com/the-h-team>
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
package com.github.sanctum.economy.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BungeeCord plugin implementation.
 *
 * @since 2.0.0
 * @author ms5984
 */
public final class EnterpriseBungee extends Plugin {
    static EnterpriseBungee instance;
    String name;

    @Override
    public void onLoad() {
        BungeeTransactionEvent.plugin = this;
    }

    @Override
    public void onEnable() {
        instance = this;
        name = getDescription().getName();
        getProxy().getPluginManager().registerCommand(this, new StaffCommand());
    }

    @Override
    public void onDisable() {
        getProxy().getPluginManager().unregisterCommands(this);
    }

    private class StaffCommand extends BungeeCommand {
        StaffCommand() {
            super("genterprise", "enterprise.staff", "proxyenterprise", "benterprise");
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            if (!hasPermission(sender)) {
                return;
            }
            if (args.length == 0) {
                // TODO: List registered ecos as well as any dependent plugins
                getProxy().getScheduler().runAsync(EnterpriseBungee.this, new DependencyFinder(sender)::calculateAndSend);
            }
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            return new ArrayList<>();
        }

        class DependencyFinder {
            final CommandSender sender;
            final Collection<Plugin> plugins;

            DependencyFinder(CommandSender sender) {
                this.sender = sender;
                plugins = getProxy().getPluginManager().getPlugins();
            }

            void calculateAndSend() {
                final List<String> collect = plugins.stream()
                        .filter(this::depends)
                        .map(this::simpleDescription)
                        .collect(Collectors.toList());
                if (collect.isEmpty()) {
                    sender.sendMessage(TextComponent.fromLegacyText("&cNo detected Enterprise dependencies."));
                    return;
                }
                final StringBuilder message = new StringBuilder("Detected plugins: ");
                for (String desc : collect) {
                    message.append(", ").append(desc);
                }
                sendMessage(sender, message.toString());
            }

            private boolean depends(Plugin plugin) {
                return plugin.getDescription().getDepends().contains(name) || plugin.getDescription().getSoftDepends().contains(name);
            }

            private String simpleDescription(Plugin plugin) {
                return plugin.getDescription().getName() + " "
                        + plugin.getDescription().getVersion() + " by "
                        + plugin.getDescription().getAuthor();
            }
        }
    }
}
