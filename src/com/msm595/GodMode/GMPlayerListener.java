package com.msm595.GodMode;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;


public class GMPlayerListener extends PlayerListener {
    private final GodMode plugin;
    
    public GMPlayerListener(GodMode instance) {
        plugin = instance;
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(plugin.isGod(event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.GREEN+"You are currently in godmode.");
        }
    }
}
