package com.naturaliscraft.headtrader.utils;


import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvanMerz on 12/18/16.
 * Last Modified by EvanMerz 12/19/16
 */
public class ConfigManager {
    private FileConfiguration config;

    public ConfigManager(FileConfiguration configuration){
        config = configuration;
    }

    public ConfigurationSection[] getSections(){
        List<ConfigurationSection> sections = new ArrayList<>();
        for(String key : config.getKeys(false))
            sections.add(config.getConfigurationSection(key));
        return sections.toArray(new ConfigurationSection[sections.size()]);
    }

    /**
     * @return the head ItemStacks from this
     * section. Used for GUI creation to list
     * heads.
     */
    public ItemStack[] getHeads(ConfigurationSection section){
        HeadCreator hc = new HeadCreator();
        List<ItemStack> heads = new ArrayList<>();
        ConfigurationSection headSection = section.getConfigurationSection("heads");
        for(String key : headSection.getKeys(false)){
            double price = headSection.getDouble(key + ".price");
            ItemStack head = hc.createHead((String) headSection.get(key+".texture"),
                    (String) headSection.get(key+".title"), price);
            heads.add(head);

        }
        return heads.toArray(new ItemStack[heads.size()]);
    }

    /**
     * @return the first head in the getHeads()
     * ItemStack array therefore making the icon
     * for that section a preview of the heads
     * it contains.
     */
    public ItemStack getIcon(ConfigurationSection section){
        ItemStack icon = getHeads(section)[0];
        ItemMeta meta = icon.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+ "Category");
        meta.setLore(lore);
        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes('&',
                section.getString("title")));
        icon.setItemMeta(meta);
        return icon;
    }

    /**
     * @return the section associated with this
     * title. Loops through sections and finds first
     * matching title.
     */
    public ConfigurationSection getSection(String title){
        for(ConfigurationSection section : getSections()){
            if(ChatColor.stripColor(ChatColor
                    .translateAlternateColorCodes('&',
                    section.getString("title")))
                    .equals(ChatColor.stripColor(title))){
                return section;
            }
        }
        return null;
    }
}