package com.naturaliscraft.headtrader.listeners;

import com.naturaliscraft.headtrader.HeadTrader;
import com.naturaliscraft.headtrader.gui.CategoriesGUI;
import com.naturaliscraft.headtrader.gui.HeadsGUI;
import com.naturaliscraft.headtrader.gui.YesNoGUI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by EvanMerz on 12/18/16.
 */
public class InventoryListener implements Listener {
    private HeadTrader trader;

    public InventoryListener(HeadTrader trader){
        this.trader = trader;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null ||
                e.getCurrentItem().getType() == Material.AIR ||
                !e.getCurrentItem().hasItemMeta()) return;
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasDisplayName() || !meta.hasLore()) return;
        String title = meta.getDisplayName();
        String descriptor = ChatColor.stripColor(meta.getLore().get(0));
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();

        if(descriptor.equals("Category")){
            e.setCancelled(true);
            new HeadsGUI(trader.getConfigManager(),
                    meta.getDisplayName()).open(player);
        }

        else if(descriptor.equals("Categories")){
            e.setCancelled(true);
            new CategoriesGUI(trader.getConfigManager()).open(player);
        }

        else if(descriptor.charAt(0)=='$'){
            e.setCancelled(true);
            new YesNoGUI(item).open(player);
        }

        else if(descriptor.equals("Purchase")){
            e.setCancelled(true);
            ItemStack head = inv.getItem(4);
            player.closeInventory();
            purchase(head, player);
        }

        else if(descriptor.equals("Go Back")){
            e.setCancelled(true);
            new CategoriesGUI(trader.getConfigManager()).open(player);
        }
    }

    /**
     * @return the price of the head
     * as retrieved from the lore.
     */
    private double getPrice(ItemStack head){
        if(!head.getItemMeta().hasLore()) return 1000000000;
        String lore0 = head.getItemMeta().getLore().get(0);
        String priceString = ChatColor.stripColor(lore0).substring(1);
        return Double.parseDouble(priceString);
    }

    private void purchase(ItemStack item, Player player){
        Economy eco = HeadTrader.eco;
        if(eco.getBalance(player)<getPrice(item)){
            player.sendMessage(ChatColor.RED+"Not enough money!");
            return;
        }
        if(player.getInventory().firstEmpty()==-1){
            player.sendMessage(ChatColor.RED+"Cannot purchase, your inventory is full!");
            return;
        }
        eco.withdrawPlayer(player, getPrice(item));
        ItemMeta im = item.getItemMeta();
        im.setLore(new ArrayList<>());
        item.setItemMeta(im);
        player.getInventory().addItem(item);
        player.sendMessage(ChatColor.GREEN+"Purchased '"+
                ChatColor.stripColor(im.getDisplayName())+"' successfully!");
    }
}
