package com.naturaliscraft.headtrader.gui;

import org.bukkit.entity.Player;

/**
 * Created by EvanMerz on 12/19/16.
 */
public abstract class GUI {

    /**
     * Opens a Graphical User Interface
     * for the specified Player object
     */
    public abstract void open(Player player);

    int getInventorySize(int length){
        int size =  length + (9 - length % 9);
        return size >= 45 ? 54 : size;
    }

}
