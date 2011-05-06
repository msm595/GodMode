/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import org.bukkit.util.config.Configuration;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 *
 * @author Alex
 */
public class GMPermissionsHandler {
    private GodMode plugin;
    private Configuration conf;
    public static PermissionHandler permissionHandler;
    
    public GMPermissionsHandler(GodMode p) {
        plugin=p;
        conf=plugin.getConfiguration();
    }
    
    public void init() {
        if(plugin.getDataFolder().mkdir()) {
            conf.setProperty("#read the thread to see all options", null);
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
        
        if(conf.getBoolean("usePermissions", false)) {
            Plugin permissionsPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");

            if (permissionHandler == null) {
                if (permissionsPlugin != null) {
                    this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                } else {
                    plugin.log.warning("["+plugin.pdfFile.getName().toUpperCase()+"] Permission system not detected, defaulting to settings file.");
                    conf.setProperty("usePermissions", false);
                }
            }
        }
    }
    
    public void load() {
        conf.load();
    }
    
    
}
