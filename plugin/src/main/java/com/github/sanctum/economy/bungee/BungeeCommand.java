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
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * A dynamic command class with a text utility.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class BungeeCommand extends Command implements TabExecutor {
    BungeeCommand(@NotNull String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    /**
     * Sends a prefixed message.
     *
     * @param sender a command sender
     * @param message a message
     */
    public void sendMessage(@NotNull CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText("&f[&2" + EnterpriseBungee.instance.name +"&f] " + message));
    }
}
