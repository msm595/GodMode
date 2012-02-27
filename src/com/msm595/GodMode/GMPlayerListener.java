/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
/**
 *
 * @author Alex
 */
public class GMPlayerListener implements Listener {
    GodMode plugin;
    
    public GMPlayerListener(GodMode p) {
        plugin = p;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        plugin.playerHandler.join(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.playerHandler.join(event.getPlayer());
    }
}
