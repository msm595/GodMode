package com.msm595.GodMode;

import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.entity.Player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.ChatColor;

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

    public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
        String command = c.getName().toLowerCase();
        if(!(command.equals("god") || command.equals("godmode"))) {
            return false;
        }
        
        if(sender instanceof ConsoleCommandSender) {
            if(args.length==0) return false;
            
            Player victim = getServer().getPlayer(args[0]);
            
            if(victim == null || !playerHandler.isPlayer(victim)) {
                sender.sendMessage("No such player exists.");
                return false;
            }
                
            if(args.length>1 && args[1].equalsIgnoreCase("-noFire")) {
                String way="";
                if(playerHandler.toggleFire(victim)) {
                    way = "enabled";
                } else {
                    way = "disabled";
                }
                sender.sendMessage("NoFire "+way+" on "+victim.getName());
                victim.sendMessage("Server "+way+" NoFire for you.");
                return true;
            } else if(args.length==1) {
                String way="";
                if(playerHandler.toggleGod(victim)) {
                    way = "enabled";
                } else {
                    way = "disabled";
                }
                sender.sendMessage("GodMode "+way+" on "+victim.getName());
                victim.sendMessage("Server "+way+" GodMode for you.");
                return true;
            }
            
            return false;
        }
        
        if(!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        
        if(args.length==0) { //godSelf
            if(perm.canGodSelf(p)) {
                String message=ChatColor.RED+"GodMode disabled.";
                if(playerHandler.toggleGod(p))
                    message=ChatColor.GREEN+"GodMode enabled.";
                
                p.sendMessage(message);
                return true;
            }
            p.sendMessage(ChatColor.RED+"You don't have permission to use this command.");
            return false;
        }
        
        if(args.length==1 && args[0].equalsIgnoreCase("-noFire")) { //FireSelf
            if(perm.canFire(p)) {
                String message=ChatColor.RED+"NoFire disabled.";
                if(playerHandler.toggleFire(p))
                    message=ChatColor.GREEN+"NoFire enabled.";
                
                p.sendMessage(message);
                return true;
            }
            p.sendMessage(ChatColor.RED+"You don't have permission to use this command.");
            return false;
        }
        
        if(args.length==1 && !args[0].startsWith("-")) { //god other
            if(perm.canGodOther(p)) {
                Player victim = getServer().getPlayer(args[0]);
                if(victim == null || !playerHandler.isPlayer(victim)) {
                    sender.sendMessage(ChatColor.RED+"No such player exists.");
                    return false;
                }
                
                String toSender=ChatColor.RED+"GodMode disabled on "+victim.getName()+".";
                String message=ChatColor.RED+p.getName()+" disabled GodMode for you.";
                if(playerHandler.toggleGod(victim)) {
                    message=ChatColor.GREEN+p.getName()+" enabled GodMode for you.";
                    toSender=ChatColor.GREEN+"GodMode enabled on "+victim.getName()+".";
                }
                
                victim.sendMessage(message);
                p.sendMessage(toSender);
                return true;
            }
            p.sendMessage(ChatColor.RED+"You don't have permission to use this command.");
            return false;
        }
        
        if(args.length==2 && !args[0].startsWith("-") && args[1].equalsIgnoreCase("-noFire")) { //fire other
            if(perm.canGodOther(p)) {
                Player victim = getServer().getPlayer(args[0]);
                if(victim == null || !playerHandler.isPlayer(victim)) {
                    sender.sendMessage(ChatColor.RED+"No such player exists.");
                    return false;
                }
                
                String toSender=ChatColor.RED+"NoFire disabled on "+victim.getName()+".";
                String message=ChatColor.RED+p.getName()+" disabled NoFire for you.";
                if(playerHandler.toggleFire(victim)) {
                    message=ChatColor.GREEN+p.getName()+" enabled NoFire for you.";
                    toSender=ChatColor.GREEN+"NoFire enabled on "+victim.getName()+".";
                }
                
                victim.sendMessage(message);
                p.sendMessage(toSender);
                return true;
            }
            p.sendMessage(ChatColor.RED+"You don't have permission to use this command.");
            return false;
        }
            
        return false;
    }
    
}
