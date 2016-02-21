package com.cjburkey.plugin.ushop;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.plugin.ushop.cmd.Shop;
import com.cjburkey.plugin.ushop.gui.ClickEvent;
import com.cjburkey.plugin.ushop.io.IO;
import com.cjburkey.plugin.ushop.stat.Metrics;
import net.milkbowl.vault.economy.Economy;

public class UltraShop extends JavaPlugin {
	
	private static UltraShop plugin;
	private static Economy econ = null;
	
	public static final UltraShop getPlugin() { return plugin; }
	public static final Economy getEcon() { return econ; }
	
	public void onEnable() {
		plugin = this;
		
		try {
			Metrics m = new Metrics(this);
			m.start();
		} catch(Exception e) {
			Util.log("Failed to submit stats :(");
		}
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getCommand("shop").setExecutor(new Shop());
		
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		
		if(!setupEconomy()) {
			Util.log("&4Econ couldn't be setup!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			Util.log("&2Econ setup!");
		}
		
		IO.getDataDir().mkdirs();
	}
	
	public void onDisable() {  }
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
}