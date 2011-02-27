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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Sample plugin for Bukkit
*
* @author Dinnerbone
*/
public class GodMode extends JavaPlugin {
    public static final Logger log = Logger.getLogger("Minecraft");
    private final GMPlayerListener playerListener = new GMPlayerListener(this);
    private final GMEntityListener entityListener = new GMEntityListener(this);
    
    private boolean godOnLogin = true;
    
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
                System.out.println("[GodMode] Attached plugin to Permissions. Enjoy~");
                usePermissions=true;
            }
        }
    }

    //Create the hashmap for gods. integer values are a binary permission system:
    //1:invincible, 2:put out fire instantly (if they are caught on fire), 4:nothing so far
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
        //Create the pluginmanage pm.
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, Listener, Event.Priority.Monitor, this);
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