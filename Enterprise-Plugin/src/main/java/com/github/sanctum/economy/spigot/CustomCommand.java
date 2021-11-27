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
package com.github.sanctum.economy.spigot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Basic dynamic command class with utilities.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class CustomCommand extends Command implements PluginIdentifiableCommand {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#((?:\\d|[A-F]|[a-f]){6})");
    static final boolean SUPPORTS_HEX;

    static {
        // Detect 1.16+
        SUPPORTS_HEX = Pattern.compile("1\\.(1[6-9]|[2-9][0-9]).*").matcher(Bukkit.getVersion()).find();
    }

    CustomCommand(@NotNull String label, String permission) {
        super(label);
        setPermission(permission);
    }

    /**
     * Send a prefixed message.
     *
     * @param sender a command sender
     * @param message a message
     */
    public void sendMessage(@NotNull CommandSender sender, String message) {
        sender.sendMessage(translateColorCodes("&f[&2" + EnterpriseSpigot.instance.getName() +"&f] " + message));
    }

    /**
     * Translate ampersand-based color codes in text.
     *
     * @param ampText ampersand-formatted text
     * @return translated text
     */
    static String translateColorCodes(String ampText) {
        if (ampText == null) return null;
        ampText = ChatColor.translateAlternateColorCodes('&', ampText);
        if (SUPPORTS_HEX) {
            final Matcher matcher = HEX_PATTERN.matcher(ampText);
            final StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                final String colorGroup = matcher.group().toLowerCase();
                final StringBuilder hexColor = new StringBuilder(ChatColor.COLOR_CHAR + "x");
                for (int i = 0; i < 6; ++i) {
                    hexColor.append(ChatColor.COLOR_CHAR).append(colorGroup.charAt(i));
                }
                matcher.appendReplacement(sb, hexColor.toString());
            }
            matcher.appendTail(sb);
            return sb.toString();
        }
        return ampText;
    }
}
