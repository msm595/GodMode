//The Package
package com.msm595.GodMode;
//All the imports
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
//Starts the class BasicPlayer listener

public class GMPlayerListener extends PlayerListener{
	private final GodMode plugin;
	public GMPlayerListener(GodMode instance) {
		plugin = instance;
	}
	  
	//This method is called whenever a player uses a command.
	public void onPlayerCommand(PlayerChatEvent event) {
		//Make the message a string.
		String[] split = event.getMessage().split(" ");
		//Get the player that talked.
		Player player = event.getPlayer();
		//If the first part of the string is /basic or /b then do this.
		if ((split[0].equalsIgnoreCase("/godmode"))
				|| (split[0].equalsIgnoreCase("/god"))) {
			//Run the method toggleVision for player
			plugin.toggleGodMode(player);
			event.setCancelled(true);
		}
	}
}