//The package
package com.msm595.GodMode;

//All the imports
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent;

//Start the class BasicBlockListener
public class GMEntityListener extends EntityListener{
     private final GodMode plugin;
     public GMEntityListener(GodMode instance) {
     plugin = instance;
    }

     public void onEntityDamage(EntityDamageEvent event) {
         Entity entity = event.getEntity();
         if(entity instanceof Player) {
             Player player = (Player)entity;

             //player.sendMessage("You were damaged by " + event.getCause().name());
             if(plugin.isGod(player)) {
                 //event.setDamage(0); //not needed?
                 event.setCancelled(true);
                 
             }
             if(plugin.noFire(player) &&
                         event.getCause().equals(DamageCause.FIRE_TICK)) {
                     player.setFireTicks(0);
                     //System.out.println("Stop fire");
                 }
         }
     }
}