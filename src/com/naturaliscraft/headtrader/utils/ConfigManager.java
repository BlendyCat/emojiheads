package com.naturaliscraft.headtrader.utils;


import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    private ItemStack[] getHeads(ConfigurationSection section){
        HeadCreator hc = new HeadCreator();
        List<ItemStack> heads = new ArrayList<>();
        ConfigurationSection headSection = section.getConfigurationSection("heads");
        for (String key : headSection.getKeys(false)) {
            double price = headSection.getDouble(key + ".price");
            ItemStack head = hc.createHead((String) headSection.get(key + ".texture"),
                    (String) headSection.get(key + ".title"), price);
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


    private int getPages(ConfigurationSection section){
        ConfigurationSection headSection = section.getConfigurationSection("heads");
        int size = headSection.getKeys(false).size();
        return (size + (45 - size % 45))/45;
    }

    public ItemStack[] getPage(ConfigurationSection section, int index){
        int from = index * 44;
        int to = from+44;
        ItemStack[] heads = getHeads(section);
        to = heads.length > to ? to : heads.length;
        ItemStack[] newHeads = new ItemStack[to-from];
        System.arraycopy(heads, from, newHeads, 0, to-from);
        return newHeads;
    }

    public ItemStack[] getControls(int page, ConfigurationSection section){
        HeadCreator hc = new HeadCreator();
        ItemStack control0;
        ItemStack control1;

        if(page == 0){
            control0 = new ItemStack(Material.AIR);
        }else{
            control0 = hc.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOI" +
                            "jp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3J" +
                            "hZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlO" +
                            "TU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJ" +
                            "hOTdkNzk4ZDVmMWEyMyJ9fX0=",
                    "&3Page Back",
                    0);
            ItemMeta meta = control0.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY+"<<<");
            meta.setLore(lore);
            control0.setItemMeta(meta);
        }
        if(page+1 == getPages(section)){
            control1 = new ItemStack(Material.AIR);
        }else{
            control1 = hc.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7I" +
                            "nVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV" +
                            "0L3RleHR1cmUvMWI2ZjFhMjViNmJjMTk5OTQ2NDcyYWVkY" +
                            "jM3MDUyMjU4NGZmNmY0ZTgzMjIxZTU5NDZiZDJlNDFiNWN" +
                            "hMTNiIn19fQ==",
                    "&3Page Forward",
                    0);
            ItemMeta meta = control1.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY+">>>");
            meta.setLore(lore);
            control1.setItemMeta(meta);

        }
        ItemStack info = hc.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOI" +
                        "jp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQub" +
                        "mV0L3RleHR1cmUvYTBlOWQyYmViODRiMzJlM2YxNWUzODBjY" +
                        "zJjNTUxMDY0MjkxMWE1MTIxMDVmYTJlYzY3OWJjNTQwZmQ4M" +
                        "Tg0In19fQ==",
                ChatColor.translateAlternateColorCodes('&', section.getString("title")),
                0);
        ItemMeta meta = info.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Page "+(page+1)+"/"+getPages(section));
        meta.setLore(lore);
        info.setItemMeta(meta);

        return new ItemStack[] {control0, info, control1};
    }
}