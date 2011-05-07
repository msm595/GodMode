/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

/**
 *
 * @author Alex
 */
public class GMPlayerHandler {
    public GodMode plugin;
    
    private HashMap<Player,God> players;
    
    public GMPlayerHandler(GodMode p) {
        plugin = p;
        players = new HashMap<Player, God>();
    }
    
    public void command(Player p, String c) {
        
    }
    
    public void teleport(Player player) {
        if(!players.containsKey(player) || 
               !player.getWorld().getName().equals(players.get(player).getWorld())) {
            join(player);
            return;
        }
    }
    
    public void join(Player player) {
        God g;
        if(players.containsKey(player)) {
            g = players.get(player);
            g.isGod(plugin.perm.defaultGod(player));
            g.noFire(plugin.perm.defaultNoFire(player));
            g.setWorld(player.getWorld().getName());
        } else {
            g = new God();
            g.isGod(plugin.perm.defaultGod(player));
            g.noFire(plugin.perm.defaultNoFire(player));
            g.setWorld(player.getWorld().getName());
            players.put(player, g);
        }
        
        String message;
        
        if(g.isGod()) {
            message=ChatColor.GREEN + "Welcome to '"+player.getWorld().getName()+"'. Godmode is enabled. NoFire is ";
        } else {
            message=ChatColor.RED + "Welcome to '"+player.getWorld().getName()+"'. Godmode is disabled. NoFire is ";
        }
        
        if(g.noFire()) {
            message+="enabled.";
        } else {
            message+="disabled.";
        }
        
        player.sendMessage(message);
    }
    
    public boolean isGod(Player p) {
        return players.get(p).isGod();
    }
    
    public boolean noFire(Player p) {
        return players.get(p).noFire();
    }
    
//    public boolean airBubble(Player p) {
//        return players.get(p).airBubble();
//    }
    
    public boolean isPlayer(Player p) {
        return players.containsKey(p);
    }
    
    public boolean toggleGod(Player p) {
        return players.get(p).toggleGod();
    }
    
    public boolean toggleFire(Player p) {
        return players.get(p).toggleFire();
    }
}
