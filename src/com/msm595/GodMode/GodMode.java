package com.msm595.GodMode;

import java.util.logging.Logger;
import java.util.HashMap;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.entity.Player;

/**
 *
 * @author Alex Epifano
 * 
 * @license Creative Commons Attribution-ShareAlike 3.0 Unported License
 * http://creativecommons.org/licenses/by-sa/3.0/
 */
public class GodMode extends JavaPlugin {
    public static Logger log = Logger.getLogger("Minecraft");
    
    public static GMPermissionsHandler perm;
    public static GMPlayerHandler playerHandler;
    public static PluginDescriptionFile pdfFile;
    public static PluginManager pm;
    
    public static GMEntityListener entityListener;
    public static GMPlayerListener playerListener;
    
    @Override
    public void onLoad() {
        perm = new GMPermissionsHandler(this);
        playerHandler = new GMPlayerHandler(this);
        pdfFile = this.getDescription();
        pm = this.getServer().getPluginManager();
        
        entityListener = new GMEntityListener(this);
        playerListener = new GMPlayerListener(this);
    }
    
    @Override
    public void onEnable() {
        perm.init();
        
        //pm.registerEvent(Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
        pm.registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.Normal, this);
        pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);
        
        log.info( "["+pdfFile.getName().toUpperCase() + "] " + pdfFile.getVersion() + " is enabled!" );
    }
    
    @Override
    public void onDisable() {
        log.info("["+pdfFile.getName().toUpperCase()+"] Disabled");
    }

}
