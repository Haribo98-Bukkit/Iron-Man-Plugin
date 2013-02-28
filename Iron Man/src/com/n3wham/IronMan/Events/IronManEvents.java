package com.n3wham.IronMan.Events;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.n3wham.IronMan.Main;
import com.n3wham.IronMan.CustomEvents.*;

public class IronManEvents implements Listener {
	
	public Main plugin;
	public String prefix;
	
	public IronManEvents(Main plugin) {
		this.plugin = plugin;
		this.prefix = plugin.getPrefix();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEnable(EnableIronManEvent event) {
		Player player = event.getPlayer();
		final Location pL = player.getLocation();
		
		if (!event.isMuted()) {
			player.sendMessage(prefix + "Iron Man mode activated!");
			pL.getWorld().createExplosion(pL, 0);
			pL.getWorld().playEffect(pL, Effect.SMOKE, 2);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					pL.getWorld().playEffect(pL, Effect.SMOKE, 2);
				}
				
			}, 10);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					pL.getWorld().playEffect(pL, Effect.SMOKE, 2);
				}
				
			}, 20);
		}
		
		plugin.setIronMan(player, true);
		
		player.setAllowFlight(true);
		player.setFlying(true);
		
		
	}

	@EventHandler
	public void onDisble(DisableIronManEvent event) {
		Player player = event.getPlayer();
		
		if (!event.isMuted()) {
			player.sendMessage(prefix + "Iron Man mode de-activated!");
		}
		
		plugin.setIronMan(player, false);
		
		if (!player.getGameMode().equals(GameMode.CREATIVE)) {
			player.setAllowFlight(false);
		}
		
		player.setFallDistance(0);
		
	}
	
}
