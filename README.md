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
	    <version>1.3-pre</version>
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
	        implementation 'com.github.Hempfest:Hemponomics:1.3'
	}
```

### What is this?
``This is a standalone Economy Interface--similar in function to the Bukkit-renowned "Vault" plugin--with more extensive features!
It allows any plugin to either provide or interface with providers to execute economy operations such as transactions/account creation.``

### Why this? We already have Vault+VaultAPI.
``The power (and ideally, ease of implementation) of this interface exceeds that of Vault's present economy interface. Hemponomics allows
economy developers to implement complex logic quiently and offer far more extensive features with both currency and account support native to the API.``

### Why so similar to Vault?
``Vault had a great idea with their system: they connected economy plugins and users simply by defining a common series of operations that
existing providers could map to their internal structures, new providers could start with a template, and users don't have to worry about the
provider implementation. Hemponomics strives to do much the same while expanding functionality by inverting part of Vault's paridigm--its
cluttered monolith interface. You see, despite its length, there simply aren't even enough economy operations native to Vault's Economy class.
Hemponomics uses a lean main ``
[``interface``](https://github.com/Hempfest/Hemponomics/blob/1.3-pre/src/main/java/com/youtube/hempfest/economy/construct/implement/AdvancedEconomy.java)``
to make things simple and allow rapid implementation into other plugins with registrations and systems are similar to that of Vault for familiarity.``

### Using the interface - For developers using a provider
``Using a provider is very simple and does not requires much knowledge of the Bukkit API. Here we simply grab the registered implementation of the
interface in essentially the same way as Vault using Bukkit's ServiceProvider API.``
See [RegisteredServiceProvider](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/plugin/RegisteredServiceProvider.html) on Spigot javadocs.

_**Tip:**_ If you are outside of the main plugin class (ie do not have access to `JavaPlugin#getServer()`), note that `Bukkit.getServicesManager()` may
also be used to get a `ServicesManager` to perform the registrations lookup.

---

```JAVA
package your.plugin.information;

import com.youtube.hempfest.economy.construct.implement.AdvancedEconomy;
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
		final Collection<RegisteredServiceProvider<AdvancedEconomy>> rsps = getServer().getServicesManager().getRegistrations(AdvancedEconomy.class);
		if (rsps.isEmpty()) {
			return false;
		}
		RegisteredServiceProvider<AdvancedEconomy> service = null;
		for (RegisteredServiceProvider<AdvancedEconomy> rsp : rsps) {
			EconomyPriority priority = rsp.getProvider().getPriority();
			econ = rsp.getProvider();
			service = rsp;
			if (rsp.getProvider().getPriority().getPriNum() > priority.getPriNum()) {
				econ = rsp.getProvider();
				service = rsp;
			}
		}
		getLogger().info("- (" + rsps.size() + ") AdvancedEconomy provider(s)");
		getLogger().info("- Using provider " + service.getProvider().getPlugin().getName() + " with priority " + service.getProvider().getPriority().name());
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
			double balance = econ.getWallet(p).getBalance().doubleValue(); // Getting the players private balance
			                                           // Use getBalance(worldName) for a specific world
			EconomyAction transaction = econ.getWallet(p).deposit(new BigDecimal(20.00));
			p.sendMessage("Balance pre transaction: " + balance);
			if (transaction.isSuccess()) {
				double balanceAfter = econ.getWallet(p).getBalance().doubleValue();
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

### Providing the interface - For developers making economies
``To provide your own economy using the interface simply implement it within your class and register it from
your plugin's #onEnable(). This will in turn allow your plugin to be a Hemponomics Economy Provider.``

---

```JAVA
public class AdvancedEconomyHook { // You may create a class like this to help with registration

	private final JavaPlugin plugin;
	private AdvancedEconomy provider;

	public AdvancedEconomyHook(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void hook() {
		provider = plugin.yourImplForInterface;
		Bukkit.getServicesManager().register(AdvancedEconomy.class, this.provider, this.plugin, ServicePriority.Normal);
		plugin.getLogger().info("- Advanced economy hooked! Now registered as a provider");
	}

	public void unhook() {
		Bukkit.getServicesManager().unregister(AdvancedEconomy.class, this.provider);
		plugin.getLogger().info("- Advanced economy un-hooked! No longer registered as a provider");
	}

}
```
##### Now let's move onto AdvancedEconomy
#### [AdvancedEconomy](https://github.com/Hempfest/Hemponomics/blob/1.3-pre/src/main/java/com/youtube/hempfest/economy/construct/implement/AdvancedEconomy.java)
You may have noticed a couple new types on the way down through. What is a Wallet? What is an account?

Let's take a look!
#### [Account](https://github.com/Hempfest/Hemponomics/blob/1.3-pre/src/main/java/com/youtube/hempfest/economy/construct/account/Account.java)

Awesome, and Wallet?
#### [Wallet](https://github.com/Hempfest/Hemponomics/blob/1.3-pre/src/main/java/com/youtube/hempfest/economy/construct/account/Wallet.java)

At this point, the keen observer may have noticed a few things. 
- `Account` and `Wallet` are both subtypes of `Balance`
- `Wallet` is further subclasses by a type called `PlayerWallet`

**Balance and its subtypes are where behaviors are defined in Hemponomics**

Instead of implementing a litany of overly-specific yet ambiguous method signatures like `#getBalanceOfSteveInWorld(String, String)`, all you need to do is
return an object which appropriately implements economy logic for the parameters provided.

Let's use the `Wallet` method`#getWallet(OfflinePlayer)`

Start by extending Wallet or an applicable subtype. In the case of players, an abstract base
([PlayerWallet](https://github.com/Hempfest/Hemponomics/blob/1.3-pre/src/main/java/com/youtube/hempfest/economy/construct/account/PlayerWallet.java)) is provided
for you with easy access to the original OfflinePlayer object as needed.
```java
package com.example.wallet;

import com.youtube.hempfest.economy.construct.EconomyAction;
import com.youtube.hempfest.economy.construct.account.PlayerWallet;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Here I've extended PlayerWallet, a subtype of Wallet with helpful player-specific
 * features. I have made a public constructor.
 */
public class ExampleWallet extends PlayerWallet {
    private static final Map<String, BigDecimal> WALLETS = new ConcurrentHashMap<>();

    public ExampleWallet(OfflinePlayer player) {
        super(player);
    }

    @Override
    public void setBalance(BigDecimal amount) {
        // example code to set global balance.
        WALLETS.put(holder.id(), amount);
    }

    @Override
    public void setBalance(BigDecimal amount, String world) {
        // code to set world-specific balance
    }

    @Override
    public boolean exists() {
        return WALLETS.containsKey(holder.id());
    }

    @Override
    public boolean exists(String world) {
        // You may return false if desired; in this case we simply return same as global
        return exists();
    }

    @Override
    public @Nullable BigDecimal getBalance() {
        return WALLETS.get(holder.id());
    }

    @Override
    public @Nullable BigDecimal getBalance(String world) {
        return getBalance();
    }

    @Override
    public boolean has(BigDecimal amount) {
        return Optional.ofNullable(getBalance()).map(bigDecimal -> bigDecimal.compareTo(amount) >= 0).orElse(false);
    }

    @Override
    public boolean has(BigDecimal amount, String world) {
        return has(amount);
    }

    @Override
    public EconomyAction deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Cannot deposit negative values");
        return Optional.ofNullable(getBalance()).map(bigDecimal -> {
            WALLETS.put(holder.id(), bigDecimal.add(amount));
            return new EconomyAction(amount, holder, true, "Successful deposit!");
        }).orElse(new EconomyAction(holder, false, "No account!"));
    }

    @Override
    public EconomyAction deposit(BigDecimal amount, String world) {
        return deposit(amount);
    }

    @Override
    public EconomyAction withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Cannot withdraw negative values");
        return Optional.ofNullable(getBalance()).map(bigDecimal -> {
            WALLETS.put(holder.id(), bigDecimal.subtract(amount));
            return new EconomyAction(amount, holder, true, "Successful withdrawal!");
        }).orElse(new EconomyAction(holder, false, "No account!"));
    }

    @Override
    public EconomyAction withdraw(BigDecimal amount, String world) {
        return withdraw(amount);
    }
}
```
