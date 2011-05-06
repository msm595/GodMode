/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author Alex
 */
public class GMEntityListener extends EntityListener {
    
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        
        Player player = (Player) event.getEntity();
        System.out.println(event.getDrops().toString());
        System.out.println(player.getInventory().getContents()[0]);
    }
    
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        
        event.
    }
}
