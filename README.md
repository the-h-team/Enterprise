# Hemponomics


A more advanced vault like economy interface.

---

[![](https://jitpack.io/v/Hempfest/Hemponomics.svg)](https://jitpack.io/#Hempfest/Hemponomics)
### Importing with maven
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  	<dependency>
	    <groupId>com.github.Hempfest</groupId>
	    <artifactId>Hemponomics</artifactId>
	    <version>1.0</version>
	</dependency>
```
### Importing with gradle
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.Hempfest:Hemponomics:1.0'
	}
```

### What is this?
``This is an economy interface standalone. Much similar to the game renound "Vault" plugin but with more extensive features. It allows any plugin to either provide or hook into
its interface to execute economy operations such as transactions.``

### Why this? I have vault.
``The opportunity this interface presents exceeds that of vaults economy interface. This alterntive allows the user overriding to implement
far more extensive features with both currency and money account support.``

### Why so similar to vault?
``Vault had a great idea with their system. There just sadly isnt enough economy operations native to the interface. To keep things simple and easiest to implement
into other plugins the registration and systems are similar to that of Vault primarily for familiarity.``

### Using the interface
``Doing this is very simple and requires very little knowledge on bukkit API. 
You simply grab the registered implementation of the interface from cache the same way as Vault using Bukkit services api``

---

```JAVA
package your.plugin.information;

import com.youtube.hempfest.economy.construct.AdvancedEconomy;
import com.youtube.hempfest.economy.construct.EconomyAction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ExamplePlugin extends JavaPlugin {

	private static AdvancedEconomy econ = null;

	@Override
	public void onEnable() {
		if (!setupEconomy() ) {
			getLogger().severe("- Disabled due to no Hemponomic dependency found!");
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {

	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Hemponomics") == null) {
			return false;
		}
		RegisteredServiceProvider<AdvancedEconomy> rsp = getServer().getServicesManager().getRegistration(AdvancedEconomy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return true;
	}

	public static AdvancedEconomy getEconomy() {
		return econ;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			// perform your code on the console level
			return true;
		}

		Player p = (Player) sender;

		if (command.getLabel().equalsIgnoreCase("freemoney")) {
			double balance = econ.getWalletBalance(p); // Getting the players private balance
			                                           // Use (p, worldName) for a specific world
			EconomyAction transaction = econ.walletDeposit(p, 20.00);
			p.sendMessage("Balance pre transaction: " + balance);
			if (transaction.isSuccess()) {
				double balanceAfter = econ.getWalletBalance(p);
				p.sendMessage(transaction.getInfo());                      // Send the user the transaction information
				p.sendMessage("Balance pro transaction: " + balanceAfter); // Or send the user your own message.
			} else {
				p.sendMessage("Something went wrong. Transaction un-successful");
				return true;
			}
			return true;
		}

		return false;
	}
}
```

### Providing the interface
``To provide your own economy using the interface simply implement it within your class and register it within
your servers onEnable this will in turn allow your plugin to be a Hemponomics Economy provider.``

---

```JAVA
public class AdvancedEconomyHook {

	private final JavaPlugin plugin;
	private AdvancedEconomy provider;

	public AdvancedEconomyHook(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void hook() {
		provider = plugin.advancedEconomyInstance;
		Bukkit.getServicesManager().register(AdvancedEconomy.class, this.provider, this.plugin, ServicePriority.Normal);
		plugin.getLogger().info("- Advanced economy hooked! Now registered as a provider");
	}

	public void unhook() {
		Bukkit.getServicesManager().unregister(AdvancedEconomy.class, this.provider);
		plugin.getLogger().info("- Advanced economy un-hooked! No longer registered as a provider");
	}

}
```
String methods are reserved for system/npc economy operations while UUID and OfflinePlayer methods are reserved for players.
```JAVA
public interface AdvancedEconomy {

	Plugin getPlugin();

	String getVersion();

	EconomyCurrency getCurrency();

	EconomyCurrency getCurrency(String world);

	EconomyPriority getPriority();

	String format(double amount);

	String format(double amount, Locale locale);

	double getMaxWalletSize();

	boolean isMultiWorld();

	boolean isMultiCurrency();

	boolean isAccountsLimited();

	boolean hasMultiAccountSupport();

	boolean hasWalletSizeLimit();

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasWalletAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasWalletAccount(String name, String world);

	boolean hasWalletAccount(OfflinePlayer player);

	boolean hasWalletAccount(OfflinePlayer player, String world);

	boolean hasWalletAccount(UUID uuid);

	boolean hasWalletAccount(UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasAccount(String name);

	boolean hasAccount(OfflinePlayer player);

	boolean hasAccount(OfflinePlayer player, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean hasAccount(String name, String world);

	boolean hasAccount(UUID uuid);

	boolean hasAccount(UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean walletHasAmount(String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	boolean walletHasAmount(String name, String world, double amount);

	boolean walletHasAmount(OfflinePlayer player, double amount);

	boolean walletHasAmount(OfflinePlayer player, String world, double amount);

	boolean walletHasAmount(UUID uuid, double amount);

	boolean walletHasAmount(UUID uuid, String world, double amount);

	boolean accountHasAmount(String accountID, double amount);

	boolean accountHasAmount(Account account, double amount);

	boolean accountHasAmount(Account account, String world, double amount);

	boolean accountHasAmount(String accountID, String world, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	double getWalletBalance(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	double getWalletBalance(String name, String world);

	double getWalletBalance(OfflinePlayer player);

	double getWalletBalance(OfflinePlayer player, String world);

	double getWalletBalance(UUID uuid);

	double getWalletBalance(UUID uuid, String world);

	double getAccountBalance(String accountID);

	double getAccountBalance(String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, AccountType type);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String name, AccountType type, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	Account getAccount(String accountId, String name, String world);

	Account getAccount(OfflinePlayer player, AccountType type);

	Account getAccount(OfflinePlayer player, AccountType type, String world);

	Account getAccount(OfflinePlayer player);

	Account getAccount(OfflinePlayer player, String world);

	Account getAccount(String accountId, OfflinePlayer player, String world);

	Account getAccount(UUID uuid);

	Account getAccount(UUID uuid, String world);

	Account getAccount(UUID uuid, AccountType type);

	Account getAccount(UUID uuid, AccountType type, String world);

	Account getAccount(String accountId, UUID uuid, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletWithdraw(String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletWithdraw(String name, String world, double amount);

	EconomyAction walletWithdraw(OfflinePlayer player, double amount);

	EconomyAction walletWithdraw(OfflinePlayer player, String world, double amount);

	EconomyAction walletWithdraw(UUID uuid, double amount);

	EconomyAction walletWithdraw(UUID uuid, String world, double amount);

	EconomyAction accountWithdraw(Account account, double amount);

	EconomyAction accountWithdraw(Account account, String world, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletDeposit(String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction walletDeposit(String name, String world, double amount);

	EconomyAction walletDeposit(OfflinePlayer player, double amount);

	EconomyAction walletDeposit(OfflinePlayer player, String world, double amount);

	EconomyAction walletDeposit(UUID uuid, double amount);

	EconomyAction walletDeposit(UUID uuid, String world, double amount);

	EconomyAction accountDeposit(Account account, double amount);

	EconomyAction accountDeposit(Account account, String world, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction createAccount(AccountType type, String name, String accountId, String world, double amount);

	EconomyAction createAccount(AccountType type, OfflinePlayer player);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, double amount);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world);

	EconomyAction createAccount(AccountType type, OfflinePlayer player, String accountId, String world, double amount);

	EconomyAction createAccount(AccountType type, UUID uuid);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId);

	EconomyAction createAccount(AccountType type, UUID uuid, double amount);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world);

	EconomyAction createAccount(AccountType type, UUID uuid, String accountId, String world, double amount);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction deleteWalletAccount(String name);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction deleteWalletAccount(String name, String world);

	EconomyAction deleteWalletAccount(OfflinePlayer player);

	EconomyAction deleteWalletAccount(OfflinePlayer player, String world);

	EconomyAction deleteWalletAccount(UUID uuid);

	EconomyAction deleteWalletAccount(UUID uuid, String world);

	EconomyAction deleteAccount(String accountID);

	EconomyAction deleteAccount(String accountID, String world);

	EconomyAction deleteAccount(Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountOwner(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountOwner(String name, Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountOwner(String name, String accountID, String world);

	EconomyAction isAccountOwner(OfflinePlayer player, String accountID);

	EconomyAction isAccountOwner(OfflinePlayer player, Account account);

	EconomyAction isAccountOwner(OfflinePlayer player, String accountID, String world);

	EconomyAction isAccountOwner(UUID uuid, String accountID);

	EconomyAction isAccountOwner(UUID uuid, Account account);

	EconomyAction isAccountOwner(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountMember(String name, Account account);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction isAccountMember(String name, String accountID, String world);

	EconomyAction isAccountMember(OfflinePlayer player, String accountID);

	EconomyAction isAccountMember(OfflinePlayer player, Account account);

	EconomyAction isAccountMember(OfflinePlayer player, String accountID, String world);

	EconomyAction isAccountMember(UUID uuid, String accountID);

	EconomyAction isAccountMember(UUID uuid, Account account);

	EconomyAction isAccountMember(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction addAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction addAccountMember(String name, String accountID, String world);

	EconomyAction addAccountMember(OfflinePlayer player, String accountID);

	EconomyAction addAccountMember(OfflinePlayer player, String accountID, String world);

	EconomyAction addAccountMember(UUID uuid, String accountID);

	EconomyAction addAccountMember(UUID uuid, String accountID, String world);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction removeAccountMember(String name, String accountID);

	/**
	 * @deprecated String method dedicated to system/npc
	 */
	@Deprecated
	EconomyAction removeAccountMember(String name, String accountID, String world);

	EconomyAction removeAccountMember(OfflinePlayer player, String accountID);

	EconomyAction removeAccountMember(OfflinePlayer player, String accountID, String world);

	EconomyAction removeAccountMember(UUID uuid, String accountID);

	EconomyAction removeAccountMember(UUID uuid, String accountID, String world);

	List<Account> getAccounts();

	List<String> getAccountList();

}
```
