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
    private int page;

    public HeadsGUI(ConfigManager manager, String section, int page){
        configManager = manager;
        this.section = configManager.getSection(section);
        this.page = page;
    }

    /**
     * Opens Heads GUI for player
     */
    public void open(Player player) {
        ItemStack[] heads = configManager.getPage(section, page);
        int size = getInventorySize(heads.length) == 54 ? 54 : getInventorySize(heads.length) + 9;
        Inventory inventory = Bukkit.createInventory(null, size,
                ChatColor.DARK_AQUA + "Select a head to purchase...");
        ItemStack i = new ItemStack(Material.COMPASS);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName(ChatColor.AQUA + "Return...");
        List<String> l = new ArrayList<>();
        l.add(ChatColor.GRAY + "Categories");
        m.setLore(l);
        i.setItemMeta(m);
        inventory.addItem(i);
        inventory.addItem(heads);
        player.openInventory(inventory);
        ItemStack[] controls = configManager.getControls(page, section);
        inventory.setItem(size-6, controls[0]);
        inventory.setItem(size-5, controls[1]);
        inventory.setItem(size-4, controls[2]);
    }
}
