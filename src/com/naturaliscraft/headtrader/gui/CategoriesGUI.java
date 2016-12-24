package com.naturaliscraft.headtrader.gui;

import com.naturaliscraft.headtrader.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by EvanMerz on 12/19/16.
 */
public class CategoriesGUI extends GUI {
    private ConfigManager manager;

    public CategoriesGUI(ConfigManager manager){
        this.manager = manager;
    }

    public void open(Player player){
        ConfigurationSection[] categories = manager.getSections();
        Inventory inventory = Bukkit.createInventory(null, getInventorySize(categories.length),
                ChatColor.DARK_GREEN+"Select a Category...");
        for(int i = 0; i < categories.length; i++){
            ConfigurationSection section = categories[i];
            ItemStack icon = manager.getIcon(section);
            inventory.setItem(i,icon);
        }
        player.openInventory(inventory);
    }
}
