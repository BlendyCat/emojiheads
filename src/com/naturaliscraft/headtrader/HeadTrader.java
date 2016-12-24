package com.naturaliscraft.headtrader;

import com.naturaliscraft.headtrader.commands.CommandHeadTrader;
import com.naturaliscraft.headtrader.listeners.InventoryListener;
import com.naturaliscraft.headtrader.listeners.PlayerListener;
import com.naturaliscraft.headtrader.utils.ConfigManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by EvanMerz on 12/18/16.
 */
public class HeadTrader extends JavaPlugin {
    private ConfigManager configManager;

    public static Economy eco = null;

    @Override
    public void onEnable(){
        if (!setupEconomy() ) {
            getLogger().info("disabled due to vault not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        configManager = new ConfigManager(getConfig());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
        getCommand("headtrader").setExecutor(new CommandHeadTrader(this));
    }

    @Override
    public void onDisable(){
        saveConfig();
    }

    public void reload(){
        reloadConfig();
        configManager = new ConfigManager(getConfig());
    }

    public ConfigManager getConfigManager(){
        return configManager;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }
}
