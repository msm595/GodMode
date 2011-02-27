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
			 //System.out.println("Player damaged");
			 Player player = (Player)entity;
			 //player.sendMessage("You were damaged by " + event.getCause().name());
			 if(plugin.isGod(player)) {
				 //event.setDamage(0); //not needed?
				 event.setCancelled(true);
				 if(event.getCause().equals(DamageCause.FIRE_TICK)) {
					 player.setFireTicks(0);
				 }
			 }
		 }
	 }
	 //This method is called when ever a block is placed.
//	 public void onBlockPlace(BlockPlaceEvent event) {
//		 //Get the player doing the placing
//			Player player = event.getPlayer();
//			//Get the block that was placed
//			Block block = event.getBlockPlaced();
//			//If the block is a torch and the player has the command enabled. Do this.
//			if(block.getType() == Material.TORCH && plugin.enabled(player)){
//				//Tells the player they have placed a torch
//			player.sendMessage("You placed a torch!");
//			}
//		}
}