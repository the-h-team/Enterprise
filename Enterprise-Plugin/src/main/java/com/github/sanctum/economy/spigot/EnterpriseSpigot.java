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
package com.github.sanctum.economy.spigot;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Spigot plugin implementation.
 */
public final class EnterpriseSpigot extends JavaPlugin {

	static SimpleCommandMap COMMAND_MAP = null;
	static EnterpriseSpigot instance;

	@Override
	public void onEnable() {//
		instance = this;
		setupCommandMapField();
		AsyncTransactionEvent.plugin = this;
		new StaffCommand("enterprise", "enterprise.staff")
				.setDescription("Manage the Enterprise plugin")
				.setPermissionMessage(CustomCommand.translateColorCodes("&c&oThis is a staff-only command."))
				.register(COMMAND_MAP);
		getServer().getPluginManager().registerEvents(new LoggingListener(), this);
	}

	private void setupCommandMapField() {
		if (COMMAND_MAP != null) return;
		final Field commandMapField;
		try {
			commandMapField = getServer().getClass().getDeclaredField("commandMap");
		} catch (NoSuchFieldException e) {
			throw new IllegalStateException(e);
		}
		commandMapField.setAccessible(true);
		try {
			COMMAND_MAP = (SimpleCommandMap) commandMapField.get(getServer());
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	private class LoggingListener implements Listener {
		@EventHandler
		public void onTransactionEvent(AsyncTransactionEvent<?> e) {
			if (!e.isLogged()) return;
			getLogger().info(e::toString);
		}
	}

	private class StaffCommand extends CustomCommand {
		StaffCommand(@NotNull String label, @NotNull String permission) {
			super(label, permission);
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			if (!testPermission(sender)) {
				return true;
			}
			if (args.length == 0) {
				// TODO: List registered ecos
				final Plugin[] plugins = getServer().getPluginManager().getPlugins();
				getServer().getScheduler().runTaskAsynchronously(EnterpriseSpigot.this, new DependencyFinder(plugins, sender)::findAndSend);
				return true;
			}
			return false;
		}

		@Override
		public Plugin getPlugin() {
			return EnterpriseSpigot.this;
		}

		class DependencyFinder {
			final Plugin[] plugins;
			final CommandSender sender;

			DependencyFinder(Plugin[] plugins, CommandSender sender) {
				this.plugins = plugins;
				this.sender = sender;
			}

			void findAndSend() {
				final ArrayList<String> descriptions = new ArrayList<>();
				for (Plugin plugin : plugins) {
					if (!plugin.getDescription().getDepend().contains(EnterpriseSpigot.this.getName()) && !plugin.getDescription().getSoftDepend().contains(EnterpriseSpigot.this.getDescription().getName())) {
						continue;
					}
					if (plugin.isEnabled()) {
						descriptions.add("&a" + plugin.getDescription().getFullName() + "&7 by &f" + joinAuthors(plugin));
					} else {
						descriptions.add("&c" + plugin.getDescription().getFullName() + "&7 by &f" + joinAuthors(plugin));
					}
				}
				if (descriptions.isEmpty()) {
					sender.sendMessage(CustomCommand.translateColorCodes("&cNo detected Enterprise dependencies."));
					return;
				}
				sendMessage(sender, "Detected plugins: " + String.join("; ", descriptions));
			}

			private String joinAuthors(Plugin plugin) {
				final String[] strings = plugin.getDescription().getAuthors().toArray(new String[0]);
				if (strings.length == 0) return "?";
				if (strings.length == 1) return strings[0];
				final StringBuilder sb = new StringBuilder(strings[0]);
				for (int i = 1; i < strings.length; ++i) {
					sb.append("&7");
					if (i + 1 < strings.length) {
						sb.append(", ");
					} else {
						sb.append(" and ");
					}
					sb.append("&f").append(strings[i]);
				}
				return sb.toString();
			}
		}
	}

}
