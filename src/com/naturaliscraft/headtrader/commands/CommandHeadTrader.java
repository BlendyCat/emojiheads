package com.naturaliscraft.headtrader.commands;

import com.naturaliscraft.headtrader.HeadTrader;
import com.naturaliscraft.headtrader.gui.CategoriesGUI;
import com.naturaliscraft.headtrader.utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by EvanMerz on 12/18/16.
 */
public class CommandHeadTrader implements CommandExecutor {
    private HeadTrader trader;
    private ConfigManager manager;

    public CommandHeadTrader(HeadTrader ht){
        trader = ht;
        manager = new ConfigManager(trader.getConfig());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        //Player exclusive Commands
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0)
                new CategoriesGUI(manager).open(player);
        }

        //Non Player Commands
        if(args.length == 1 && args[0].equalsIgnoreCase("reload") &&
                sender.hasPermission("headtrader.reload")){
            trader.reload();
            manager = trader.getConfigManager();
        }

        return true;
    }
}
