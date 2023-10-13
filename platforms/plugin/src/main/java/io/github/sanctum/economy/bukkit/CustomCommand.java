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
package io.github.sanctum.economy.bukkit;

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
     * Sends a prefixed message.
     *
     * @param sender a command sender
     * @param message a message
     */
    public void sendMessage(@NotNull CommandSender sender, String message) {
        sender.sendMessage(translateColorCodes("&f[&2" + EnterpriseBukkit.instance.getName() +"&f] " + message));
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
