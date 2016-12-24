package com.naturaliscraft.headtrader.gui;

import com.naturaliscraft.headtrader.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvanMerz on 12/21/16.
 */
public class HeadsGUI extends GUI {
    private ConfigManager configManager;
    private ConfigurationSection section;

    public HeadsGUI(ConfigManager manager, String section){
        configManager = manager;
        this.section = configManager.getSection(section);
    }

    /**
     * Opens Heads GUI for player
     */
    public void open(Player player){
        ItemStack[] heads = configManager.getHeads(section);
        Inventory inventory = Bukkit.createInventory(null, getInventorySize(heads.length+1),
                ChatColor.DARK_AQUA+"Select a head to purchase...");
        ItemStack i = new ItemStack(Material.COMPASS);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(ChatColor.AQUA+"Return...");
        List<String> l = new ArrayList<String>();
        l.add(ChatColor.GRAY+"Categories");
        m.setLore(l);
        i.setItemMeta(m);
        inventory.addItem(i);
        inventory.addItem(heads);
        player.openInventory(inventory);
    }
}
