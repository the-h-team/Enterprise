/*
 *   Copyright 2021-2023 Sanctum <https://github.com/the-h-team>
 *   Copyright 2020 Hempfest <https://github.com/Hempfest>
 *   Copyright 2020 ms5984 (Matt) <https://github.com/ms5984>
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

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.github.sanctum.economy.system.PluginPlatformLoader;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit plugin implementation.
 */
public final class EnterpriseBukkit extends JavaPlugin {

	static SimpleCommandMap COMMAND_MAP = null;
	static EnterpriseBukkit instance;

	@Override
	public void onEnable() {//
		instance = this;
		setupCommandMapField();
		TransactionEvent.plugin = this;
		PluginPlatformLoader.SPIGOT.initialize();
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
		public void onTransactionEvent(TransactionEvent<?> e) {
			if (!e.isLogged()) return;
			getLogger().info(e::toString);
		}
	}

	private class StaffCommand extends CustomCommand {
		StaffCommand(@NotNull String label, @NotNull String permission) {
			super(label, permission);
		}

		@Override
		public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
			if (!testPermission(sender)) {
				return true;
			}
			if (args.length == 0) {
				// TODO: List registered ecos
				final Plugin[] plugins = getServer().getPluginManager().getPlugins();
				getServer().getScheduler().runTaskAsynchronously(EnterpriseBukkit.this, new DependencyFinder(plugins, sender)::findAndSend);
				return true;
			}
			return false;
		}

		@Override
		public @NotNull Plugin getPlugin() {
			return EnterpriseBukkit.this;
		}

		class DependencyFinder {
			final Plugin[] plugins;
			final CommandSender sender;

			DependencyFinder(Plugin[] plugins, CommandSender sender) {
				this.plugins = plugins;
				this.sender = sender;
			}

			void findAndSend() {
				final ArrayList<String> pluginList = new ArrayList<>();
				final String enterprisePluginName = EnterpriseBukkit.this.getName();
				for (Plugin plugin : plugins) {
					if (!plugin.getDescription().getDepend().contains(enterprisePluginName) && !plugin.getDescription().getSoftDepend().contains(enterprisePluginName)) {
						continue;
					}
					if (plugin.isEnabled()) {
						pluginList.add("&a" + plugin.getDescription().getFullName() + "&7 by &f" + joinAuthors(plugin));
					} else {
						pluginList.add("&c" + plugin.getDescription().getFullName() + "&7 by &f" + joinAuthors(plugin));
					}
				}
				if (pluginList.isEmpty()) {
					sender.sendMessage(translateColorCodes("&cNo detected Enterprise dependencies."));
					return;
				}
				sendMessage(sender, "Detected plugins:");
				for (String s : pluginList) {
					sendMessage(sender, translateColorCodes(s));
				}
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
