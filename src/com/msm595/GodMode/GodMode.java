package com.msm595.GodMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListener;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
* Sample plugin for Bukkit
*
* @author Dinnerbone
*/
public class GodMode extends JavaPlugin {
    private final GMPlayerListener playerListener = new GMPlayerListener(this);
    private final GMEntityListener entityListener = new GMEntityListener(this);
    public static PluginDescriptionFile pdfFile;
    
    private boolean usePermissions = false;
    public static PermissionHandler Permissions;
    private Listener Listener = new Listener();

    private class Listener extends ServerListener {

        public Listener() {
        }

        @Override
        public void onPluginEnabled(PluginEvent event) {
            if(event.getPlugin().getDescription().getName().equals("Permissions")) {
                GodMode.Permissions = ((Permissions)event.getPlugin()).Security;
                System.out.println("["+pdfFile.getName().toUpperCase()+"] Attached plugin to Permissions.");
                usePermissions=true;
            }
        }
    }

    //Create the hashmap for gods. integer values are a binary permission system:
    //1:invincible, 2:can use god command, 4:put out fire instantly (if they are caught on fire), 8:nothing so far
    //each separate permission is a power of 2 eg: 2^0=1, 2^1=2, etc...
    //permissions can be combined simply by adding (1+2=3 would be invincible and fires would be put out)
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
        pdfFile = this.getDescription();
        //Create the pluginmanage pm.
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, Listener, Event.Priority.Monitor, this);
        //Print that the plugin has been enabled!
        System.out.println( "["+pdfFile.getName().toUpperCase() + "] " + pdfFile.getVersion() + " is enabled!" );

    }

    //The method enabled which checks to see if the player is in the hashmap basicUsers
    public boolean isGod(Player player) {
        return (getPerm(player) & 1) != 0;
    }

    public boolean noFire(Player player) {
        return (getPerm(player) & 4) != 0;
    }
    
    public boolean canGod(Player player) {
        return (getPerm(player) & 2) != 0;
    }
    
    public int getPerm(Player player) {
        if(this.gods.get(player)!=null) {
            return this.gods.get(player);
        }
        
        if(usePermissions) {
            int perm=0;
            if(GodMode.Permissions.permission(player, "godmode.godByDefault")) {
                perm=1;
            }
            
            if(GodMode.Permissions.permission(player, "godmode.godSelf")) {
                perm=perm|2;
            }
            
            if(GodMode.Permissions.permission(player, "godmode.neverOnFire")) {
                perm=perm|4;
            }
            
            return perm;
        }
        
         return 2; //for now if there isn't permissions let every user use god,
         //will change in next version
    }
    
    //The method toggleVision which if the player is on the hashmap will remove the player else it will add the player.
    //Also sends user a message to notify them.
    public void toggleGodMode(Player player) {     
        int perm=getPerm(player);
        
        if ((perm&1) == 1) {
            this.gods.put(player, perm^1);
            player.sendMessage("Godmode disabled (" + (perm^1) + ")");
        } else {
            this.gods.put(player, perm|1);
            player.sendMessage("GodMode enabled (" + (perm|1) + ")");
        }
    }

}