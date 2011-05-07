/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
/**
 *
 * @author Alex
 */
public class GMPlayerListener extends PlayerListener {
    GodMode plugin;
    
    public GMPlayerListener(GodMode p) {
        plugin = p;
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.playerHandler.join(event.getPlayer());
    }
    
    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        plugin.playerHandler.teleport(event.getPlayer());
    }
}
