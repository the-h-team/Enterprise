package com.youtube.hempfest.economy;

import com.youtube.hempfest.economy.construct.AdvancedEconomy;
import com.youtube.hempfest.economy.construct.account.permissive.AccountType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hemponomics extends JavaPlugin {

	private static Hemponomics instance;

	@Override
	public void onEnable() {
		// Plugin startup logic
		this.instance = this;
		registerCommand(new CommandInformation());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	protected static Hemponomics getInstance() {
		return instance;
	}

	public void registerCommand(BukkitCommand command) {
		try {

			final Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);

			final CommandMap commandMap = (CommandMap) commandMapField.get(getServer());
			commandMap.register(command.getLabel(), command);

		} catch (final Exception e) {
			e.printStackTrace();
		}


	}

	private static class CommandInformation extends BukkitCommand {

		public CommandInformation() {
			super("hemponomic");
		}

		private void sendMessage(CommandSender player, String message) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&2Hemponomics&f] " + message));
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {

			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("convert")) {
					Collection<RegisteredServiceProvider<AdvancedEconomy>> economies = Hemponomics.getInstance().getServer().getServicesManager().getRegistrations(AdvancedEconomy.class);
					if (economies == null || economies.size() < 2) {
						sendMessage(sender, "You must have at least 2 Hemponomics compatible economies loaded to convert.");
						return true;
					}
					AdvancedEconomy econ1 = economies.stream().filter(e -> e.getProvider().getPlugin().getName().equalsIgnoreCase(args[1])).findFirst().map(RegisteredServiceProvider::getProvider).orElse(null);
					AdvancedEconomy econ2 = economies.stream().filter(e -> e.getProvider().getPlugin().getName().equalsIgnoreCase(args[2])).findFirst().map(RegisteredServiceProvider::getProvider).orElse(null);
					if (econ1 == null) {
						String ecos = economies.stream().map(RegisteredServiceProvider::getProvider).map(AdvancedEconomy::getPlugin).map(Plugin::getName).collect(Collectors.toList()).toString();
						sendMessage(sender, "Economy " + args[1] + " was not found. Ensure you have it loaded properly.");
						sendMessage(sender, "Valid economies are: " + ecos);
						return true;
					}
					if (econ2 == null) {
						String ecos = economies.stream().map(RegisteredServiceProvider::getProvider).map(AdvancedEconomy::getPlugin).map(Plugin::getName).collect(Collectors.toList()).toString();
						sendMessage(sender, "Economy " + args[2] + " was not found. Ensure you have it loaded properly.");
						sendMessage(sender, "Valid economies are: " + ecos);
						return true;
					}
					sendMessage(sender, "&e&oDepending on the amount of registrations this may take a while.");
					long time = System.currentTimeMillis();
					for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()) {
						if (econ1.hasAccount(op) && !econ2.hasAccount(op)) {
							econ2.createAccount(AccountType.ENTITY_ACCOUNT, op);
							double diff = econ1.getWalletBalance(op) - econ2.getWalletBalance(op);
							if (diff > 0.0D) {
								econ2.walletDeposit(op, diff);
							} else if (diff < 0.0D) {
								econ2.walletDeposit(op, -diff);
							}
						}
					}
					long complete = (System.currentTimeMillis() - time) / 1000;
					int second = Integer.parseInt(String.valueOf(complete));
					sendMessage(sender, "&aConversion completed in " + second + " seconds.");
					return true;
				}
				return true;
			}
			return false;
		}
	}

}
