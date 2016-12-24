package com.naturaliscraft.headtrader.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvanMerz on 12/21/16.
 */
public class YesNoGUI extends GUI{
    private ItemStack head;

    public YesNoGUI(ItemStack head){
        this.head = head;
    }

    /**
     * @param player opens inventory.
     * inherited from GUI superclass.
     */
    public void open(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9,
                ChatColor.GREEN+"Are you sure?");
        ItemStack yes = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemStack no = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta yesMeta = yes.getItemMeta();
        ItemMeta noMeta = no.getItemMeta();
        List<String> yesLore = new ArrayList<String>();
        List<String> noLore = new ArrayList<String>();
        yesLore.add(ChatColor.GRAY+"Purchase");
        noLore.add(ChatColor.GRAY+"Go Back");
        yesMeta.setLore(yesLore);
        noMeta.setLore(noLore);
        yesMeta.setDisplayName(ChatColor.GREEN+"YES");
        noMeta.setDisplayName(ChatColor.RED+"NO");
        yes.setItemMeta(yesMeta);
        no.setItemMeta(noMeta);
        inventory.setItem(4, head);
        inventory.setItem(3, yes);
        inventory.setItem(5, no);
        player.openInventory(inventory);
    }
}
