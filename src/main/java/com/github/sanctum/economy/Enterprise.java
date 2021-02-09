package com.github.sanctum.economy;

import com.github.sanctum.economy.construct.EconomyAction;
import com.github.sanctum.economy.construct.account.Wallet;
import com.github.sanctum.economy.construct.account.permissive.AccountType;
import com.github.sanctum.economy.construct.events.AsyncEconomyInfoEvent;
import com.github.sanctum.economy.construct.events.AsyncTransactionEvent;
import com.github.sanctum.economy.construct.implement.AdvancedEconomy;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Enterprise extends JavaPlugin {

	private static Enterprise instance;

	@Override
	public void onEnable() {//
		instance = this;
		registerCommand(new CommandHemponomic());
		getServer().getPluginManager().registerEvents(new LoggingListener(), this);
	}

	@Override
	public void onDisable() {//

	}

	protected static Enterprise getInstance() {
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

	private static class LoggingListener implements Listener {
		@EventHandler
		public void onInfoEvent(AsyncEconomyInfoEvent e) {
			final EconomyAction economyAction = e.getEconomyAction();
			getInstance().getLogger().info(String.format("EconomyEntity: %s [%s] Info: %s",
					economyAction.getActiveHolder().friendlyName(),
					economyAction.isSuccess(),
					economyAction.getInfo()));
		}

		@EventHandler
		public void onInfoEvent(AsyncTransactionEvent e) {
			final EconomyAction economyAction = e.getEconomyAction();
			getInstance().getLogger().info(String.format("EconomyEntity: %s [%s] Amount: %s Info: %s",
					economyAction.getActiveHolder().friendlyName(),
					economyAction.isSuccess(),
					economyAction.getAmount(),
					economyAction.getInfo()));
		}
	}

	private static class CommandHemponomic extends BukkitCommand {

		public CommandHemponomic() {
			super("enterprise");
		}

		private void sendMessage(CommandSender player, String message) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&2Hemponomics&f] " + message));
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			Collection<RegisteredServiceProvider<AdvancedEconomy>> economies = Enterprise.getInstance().getServer().getServicesManager().getRegistrations(AdvancedEconomy.class);
			if (sender instanceof Player) {
				if (!sender.hasPermission("enterprise.staff")) {
					sendMessage(sender, "&c&oThis is a staff-only command.");
					return true;
				}
			}
			if (args.length == 0) {
				String ecos = economies.stream().map(RegisteredServiceProvider::getProvider).map(AdvancedEconomy::getPlugin).map(Plugin::getName).collect(Collectors.toList()).toString();
				sendMessage(sender, "Registered Advanced Economies: " + ecos);
				return true;
			}
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("convert")) {
					if (economies == null || economies.size() < 2) {
						sendMessage(sender, "You must have at least 2 Enterprise-compatible economies loaded to convert.");
						return true;
					}
					AdvancedEconomy econ1 = economies.stream().filter(e -> e.getProvider().getPlugin().getName().equalsIgnoreCase(args[1])).findFirst().map(RegisteredServiceProvider::getProvider).orElse(null);
					if (econ1 == null) {
						String ecos = economies.stream().map(RegisteredServiceProvider::getProvider).map(AdvancedEconomy::getPlugin).map(Plugin::getName).collect(Collectors.toList()).toString();
						sendMessage(sender, "Economy " + args[1] + " was not found. Ensure you have it loaded properly.");
						sendMessage(sender, "Valid economies are: " + ecos);
						return true;
					}
					AdvancedEconomy econ2 = economies.stream().filter(e -> e.getProvider().getPlugin().getName().equalsIgnoreCase(args[2])).findFirst().map(RegisteredServiceProvider::getProvider).orElse(null);
					if (econ2 == null) {
						String ecos = economies.stream().map(RegisteredServiceProvider::getProvider).map(AdvancedEconomy::getPlugin).map(Plugin::getName).collect(Collectors.toList()).toString();
						sendMessage(sender, "Economy " + args[2] + " was not found. Ensure you have it loaded properly.");
						sendMessage(sender, "Valid economies are: " + ecos);
						return true;
					}
					sendMessage(sender, "&e&oDepending on the amount of registrations this may take a while.");
					long time = System.currentTimeMillis();
					for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()) {
						if (econ1.hasWalletAccount(op) && !econ2.hasWalletAccount(op)) {
							econ2.createAccount(AccountType.ENTITY_ACCOUNT, op);
							final Wallet wallet1 = econ1.getWallet(op);
							final Wallet wallet2 = econ2.getWallet(op);
							BigDecimal diff = Objects.requireNonNull(wallet1.getBalance()).subtract(wallet2.getBalance());
							if (diff.compareTo(BigDecimal.ZERO) > 0) { // read as "diff > ZERO"
								wallet2.deposit(diff);
							} else if (diff.compareTo(BigDecimal.ZERO) < 0) { // read as "diff < ZERO"
								wallet2.withdraw(diff.negate());
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
