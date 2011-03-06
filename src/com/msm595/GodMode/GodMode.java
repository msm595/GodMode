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
import org.bukkit.plugin.Plugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
* Sample plugin for Bukkit
*
* @author Dinnerbone
*/
public class GodMode extends JavaPlugin {
    private final GMEntityListener entityListener = new GMEntityListener(this);
    public static PluginDescriptionFile pdfFile;
    
    private boolean usePermissions = false;
    public static PermissionHandler Permissions;

    
    /*
     * Permissions code, listen in onto permissions, check if it's there
     */
    public void setupPermissions() {
	Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
	//pdfFile = this.getDescription();
		
	if (Permissions == null) {
		if (test!= null) {
			this.getServer().getPluginManager().enablePlugin(test);
			Permissions = ((Permissions) test).getHandler();
                        System.out.println("["+pdfFile.getName().toUpperCase()+"] Attached plugin to Permissions.");
                        usePermissions=true;
		}
//		else {
//			System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + "not enabled. Permissions not detected");
//			this.getServer().getPluginManager().disablePlugin(this);
//		}
	}
}

    /*
     * Create the hashmap for gods. integer values are a binary permission system:
     * 1: invincible
     * 2: can use god command on self
     * 4: can use god command on others
     * 8: put out fire instantly (if they are caught on fire)
     * 16: can toggle the no fire `perk`
     * each separate permission is a power of 2 eg: 2^0=1, 2^1=2, etc...
     * permissions can be combined simply by adding (1+8=9 would be invincible and fires would be put out)
     */
     private final HashMap<Player, Integer> gods = new HashMap();  
     private final HashMap<Player, String> worlds = new HashMap();  


    @Override
    //When the plugin is disabled this method is called.
    public void onDisable() {
        //Print "Basic Disabled" on the log.
        System.out.println("["+pdfFile.getName().toUpperCase()+"] Disabled");

    }

    @Override
    //When the plugin is enabled this method is called.
    public void onEnable() {
        pdfFile = this.getDescription();
        //Create the pluginmanage pm.
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Event.Priority.Normal, this);
        setupPermissions();
        //Print that the plugin has been enabled!
        System.out.println( "["+pdfFile.getName().toUpperCase() + "] " + pdfFile.getVersion() + " is enabled!" );

    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command c, String commandLabel, String[] args) {        
        String command = c.getName().toLowerCase();
        if(!(command.equals("god") || command.equals("godmode"))) {
            return false;
        }
        
        //is it a player?
        if(sender instanceof Player) {
            Player player = (Player)sender;
            
            //god self
            if(args.length==0) {
                if(canGod(player)) {
                    toggleGodMode(player);
                    return true;
                }
                player.sendMessage(ChatColor.RED+"You don't have permission to use this command");
                return true;
            }
            
            //noFire self
            if(args.length==1 && args[0].equalsIgnoreCase("-noFire")) {
                if(canToggleFire(player)) {
                    toggleNoFire(player);
                    return true;
                }
                player.sendMessage(ChatColor.RED+"You don't have permission to use this command");
                return false;
            }
            
            //god other
            if(args.length==1) {
                if(canGodOther(player)) {
                    Player victim = getServer().getPlayer(args[0]);
                    if(victim!=null) {
                        if(toggleGodMode(player.getDisplayName(),victim)) {
                            player.sendMessage(ChatColor.GREEN+"Godmode enabled on "+args[0]);
                        } else {
                            player.sendMessage(ChatColor.RED+"Godmode disabled on "+args[0]);
                        }
                        return true;
                    }
                    player.sendMessage(ChatColor.RED+"Invalid player specified");
                    return false;
                }
                player.sendMessage(ChatColor.RED+"You don't have permission to use this command");
                return false;
            }
            
            //toggle noFire other
            if(args.length==2 && args[1].equalsIgnoreCase("-noFire")) {
                if(canGodOther(player) && canToggleFire(player)) {
                    Player victim = getServer().getPlayer(args[0]);
                    if(victim!=null) {
                        if(toggleNoFire(player.getDisplayName(),victim)) {
                            player.sendMessage(ChatColor.GREEN+"Godmode: put out fire enabled on "+args[0]);
                        } else {
                            player.sendMessage(ChatColor.RED+"Godmode: put out fire disabled on "+args[0]);
                        }
                        return true;
                    }
                    player.sendMessage(ChatColor.RED+"Invalid player specified");
                    return false;
                }
                player.sendMessage(ChatColor.RED+"You don't have permission to use this command");
                return false;
            }
            
        //was it the console?
        } else if(sender instanceof ConsoleCommandSender) {
            
            //god player
            if(args.length==1) {
                Player victim = getServer().getPlayer(args[0]);
                if(victim != null) {
                    if(toggleGodMode(ChatColor.GOLD+"Server",victim)) {
                        sender.sendMessage(ChatColor.GREEN+"Godmode enabled on "+args[0]);
                    } else {
                        sender.sendMessage(ChatColor.RED+"Godmode disabled on "+args[0]);
                    }
                    return true;
                }
            }
            
            if(args.length==2 && args[1].equalsIgnoreCase("-noFire")) {
                Player victim = getServer().getPlayer(args[0]);
                if(victim != null) {
                    if(toggleNoFire(ChatColor.GOLD+"Server",victim)) {
                        sender.sendMessage(ChatColor.GREEN+"Godmode: put out fire enabled on "+args[0]);
                    } else {
                        sender.sendMessage(ChatColor.RED+"Godmode: put out fire disabled on "+args[0]);
                    }
                    return true;
                }
            }
        }
        
        return false;
    }

    //is the player currently in godmode
    public boolean isGod(Player player) {
        return (getPerm(player) & 1) != 0;
    }

    //does the player currently have noFire enabled
    public boolean noFire(Player player) {
        return (getPerm(player) & 8) != 0;
    }
    
    //can the player toggle godmode
    public boolean canGod(Player player) {
        return (getPerm(player) & 2) != 0;
    }
    
    //can the player toggle godmode on other players
    public boolean canGodOther(Player player) {
        return (getPerm(player) & 4) != 0;
    }
    
    //can the player toggle niFire
    public boolean canToggleFire(Player player) {
        return (getPerm(player) & 16) != 0;
    }
    
    public int getPerm(Player player) {
        if(this.gods.containsKey(player)) {
            
            //is the player in the same world as when the permissions were cached?
            if(player.getWorld().getName().equals(this.worlds.get(player))) {
                return this.gods.get(player);
            }
            this.gods.remove(player);
            return getPerm(player);
        }
        
        if(usePermissions) {
            int perm=0;
            if(GodMode.Permissions.permission(player, "godmode.default.god")) {
                perm=1;
            }
            
            if(GodMode.Permissions.permission(player, "godmode.default.noFire")) {
                perm=perm|8;
            }
            
            if(GodMode.Permissions.permission(player, "godmode.command.godSelf")) {
                perm=perm|2;
            }
            
            if(GodMode.Permissions.permission(player, "godmode.command.godOther")) {
                perm=perm|4;
            }
            
            if(GodMode.Permissions.permission(player, "godmode.command.noFire")) {
                perm=perm|16;
            }
            
            this.gods.put(player, perm);
            this.worlds.put(player, player.getWorld().getName());
            return perm;
        }
        
         return 2|16; //for now if there isn't permissions let every user use god,
         //will change in next version
    }
    
    //Will turn godmode on if it is off, and off if it is on
    public boolean toggleGodMode(Player player) {
        int perm=getPerm(player);
        
        if ((perm&1) == 1) {
            this.gods.put(player, perm^1);
            player.sendMessage(ChatColor.RED+"Godmode disabled");
            return false;
        } else {
            this.gods.put(player, perm|1);
            player.sendMessage(ChatColor.GREEN+"GodMode enabled");
            return true;
        }
    }
    
    public boolean toggleGodMode(String by, Player player) {
        int perm=getPerm(player);
        
        if ((perm&1) == 1) {
            this.gods.put(player, perm^1);
            player.sendMessage(ChatColor.RED+"Godmode disabled by "+by);
            return false;
        } else {
            this.gods.put(player, perm|1);
            player.sendMessage(ChatColor.GREEN+"GodMode enabled by "+by);
            return true;
        }
    }

    public boolean toggleNoFire(Player player) {
        int perm=getPerm(player);
        
        if ((perm&8) == 8) {
            this.gods.put(player, perm^8);
            player.sendMessage(ChatColor.RED+"Godmode: put out fire disabled");
            return false;
        } else {
            this.gods.put(player, perm|8);
            player.sendMessage(ChatColor.GREEN+"GodMode: put out fire enabled");
            return true;
        }
    }
    
    public boolean toggleNoFire(String by, Player player) {
        int perm=getPerm(player);
        
        if ((perm&8) == 8) {
            this.gods.put(player, perm^8);
            player.sendMessage(ChatColor.RED+"Godmode: put out fire disabled by "+by);
            return false;
        } else {
            this.gods.put(player, perm|8);
            player.sendMessage(ChatColor.GREEN+"GodMode: put out fire enabled by "+by);
            return true;
        }
    }
    
    //reset the player's cached permissions (used when the player teleports
    public void reset(Player player) {
        if(this.gods.containsKey(player)) {
            this.gods.remove(player);
        }
    }
}