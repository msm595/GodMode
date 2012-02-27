/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 *
 * @author Alex
 */
public class GMEntityListener implements Listener {
    GodMode plugin;
    
    public GMEntityListener(GodMode p) {
        plugin = p;
    }
    
    
//    @Override
//    public void onEntityDeath(EntityDeathEvent event) {
//        if(!(event.getEntity() instanceof Player)) return;
//        
//        Player player = (Player) event.getEntity();
//        System.out.println(event.getDrops().toString());
//        System.out.println(player.getInventory().getContents()[0]);
//    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player)event.getEntity();
        
        if(plugin.playerHandler.isGod(player)) {
            event.setCancelled(true);
            event.setDamage(0);
        }
        
        if(plugin.playerHandler.noFire(player)) {
            player.setFireTicks(0);
            if(event.getCause().equals(DamageCause.FIRE) || event.getCause().equals(DamageCause.FIRE_TICK))
                event.setCancelled(true);
        }
        
//        if(plugin.playerHandler.airBubble(player)) {
//            player.setRemainingAir(player.getMaximumAir());
//        }
        
        //event.
    }
}
