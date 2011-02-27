package com.msm595.GodMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
* Sample plugin for Bukkit
*
* @author Dinnerbone
*/
public class GodMode extends JavaPlugin {
    private final GMPlayerListener playerListener = new GMPlayerListener(this);
    private final GMEntityListener entityListener = new GMEntityListener(this);
    private boolean godOnLogin = true;

    //Create the hashmap for gods. integer values are a binary permission system:
    //1:invincible, 2:put out fire instantly (if they are caught on fire), 4:nothing so far
    //each separate permission is a power of 2 eg: 2^0=1, 2^1=2, etc...
    //permissions can be combined simply by adding (1+2 would be invincible and fires would be put out)
    public final HashMap<Player, Integer> gods = new HashMap();  


    @Override
    //When the plugin is disabled this method is called.
    public void onDisable() {
        //Print "Basic Disabled" on the log.
        System.out.println("Basic Disabled");

    }

    @Override
    //When the plugin is enabled this method is called.
    public void onEnable() {
        //Create the pluginmanage pm.
        PluginManager pm = getServer().getPluginManager();
        //Create PlayerCommand listener
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Event.Priority.Normal, this);
        //Create BlockPlaced listener
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Event.Priority.Normal, this);
        //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Print that the plugin has been enabled!
        System.out.println( "["+pdfFile.getName().toUpperCase() + "] version " + pdfFile.getVersion() + " is enabled!" );

    }

    //The method enabled which checks to see if the player is in the hashmap basicUsers
    public boolean isGod(Player player) {
        if(!this.gods.containsKey(player)) {
            return godOnLogin;
        }  
        return ((int)this.gods.get(player) & 1) == 1;
    }

    //The method toggleVision which if the player is on the hashmap will remove the player else it will add the player.
    //Also sends user a message to notify them.
    public void toggleGodMode(Player player) {
        int perm=0;
        if(this.gods.get(player)!=null) {
            perm=this.gods.get(player);
        } else if(isGod(player)) {
            perm=1;
        }
        if ((perm&1) == 1) {
            this.gods.put(player, perm^1);
            player.sendMessage("Godmode disabled (" + (perm^1) + ")");
        } else {
            this.gods.put(player, perm|1);
            player.sendMessage("GodMode enabled (" + (perm|1) + ")");
        }
    }

}