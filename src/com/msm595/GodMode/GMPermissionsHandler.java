/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import org.bukkit.util.config.Configuration;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

/**
 *
 * @author Alex
 */
public class GMPermissionsHandler {
    private GodMode plugin;
    private Configuration conf;
    
    public GMPermissionsHandler(GodMode p) {
        plugin=p;
        conf=plugin.getConfiguration();
    }
    
    public void init() {
        if(plugin.getDataFolder().mkdir()) {
            conf.setProperty("#", "read the thread to see all options");
            conf.setProperty("usePermissions", false);
            conf.setProperty("default.default", new String[]{"noFire"});
            conf.setProperty("default.commands", new String[]{"noFire"});
            
            for(World w : plugin.getServer().getWorlds()) {
                conf.setProperty("worlds.nether.default", new String[]{"noFire"});
                conf.setProperty("worlds.nether.commands", new String[]{});
            }
            
            conf.save();
            plugin.log.warning("["+plugin.pdfFile.getName().toUpperCase()+"] Created a default settings file, the plugin should be configured to your settings");
        }
        
        load();
    }
    
    public void load() {
        conf.load();
    }
    
    public boolean usePermissions() {
        return conf.getBoolean("usePermissions", false);
    }
    
    private LinkedHashMap<String, Object> getWorldPerm(String world) {
        LinkedHashMap<String, Object> worlds = (LinkedHashMap)conf.getProperty("worlds");
        
        if(worlds.containsKey(world))
            return (LinkedHashMap)worlds.get(world);
        
        return (LinkedHashMap)conf.getProperty("default");
    }
    
    private boolean is(Player player, String permission) { //return if player has default setting
        if(usePermissions())
            return player.hasPermission("godmode.default."+permission);
        return ((ArrayList<String>)getWorldPerm(player.getWorld().getName()).get("default")).contains(permission);
    }
    
    private boolean can(Player player, String command) { //return if player can use commands
        if(usePermissions())
            return player.hasPermission("godmode.command."+command);
        return ((ArrayList<String>)getWorldPerm(player.getWorld().getName()).get("commands")).contains(command);
    }
    
    public boolean defaultGod(Player player) {
        return is(player, "god");
    }
    
    public boolean defaultNoFire(Player player) {
        return is(player, "noFire");
    }
    
//    public boolean defaultAirBubble(Player player) {
//        return is(player, "airBubble");
//    }
    
    public boolean canGodSelf(Player player) {
        return can(player, "godSelf");
    }
    
    public boolean canGodOther(Player player) {
        return can(player, "godOther");
    }
    
    public boolean canFire(Player player) {
        return can(player, "noFire");
    }
}
