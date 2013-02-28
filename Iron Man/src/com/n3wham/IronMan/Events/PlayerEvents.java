package com.n3wham.IronMan.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.n3wham.IronMan.Main;
import com.n3wham.IronMan.CustomEvents.DisableIronManEvent;

public class PlayerEvents implements Listener {
	
	public Main plugin;
	public String prefix;
	
	public PlayerEvents(Main plugin) {
		this.plugin = plugin;
		this.prefix = plugin.getPrefix();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		if (plugin.getArmourSetting(event.getPlayer()) == null) {
			plugin.setArmourSetting(event.getPlayer(), "leather");
		}
		
		if (plugin.isIronMan(event.getPlayer()) && plugin.hasPermission(event.getPlayer(), "ironman.use")) {
			event.getPlayer().sendMessage(prefix + "You're still wearing your Iron Man suit!");
		} else if (plugin.isIronMan(event.getPlayer()) && !plugin.hasPermission(event.getPlayer(), "ironman.use")) {
			DisableIronManEvent disableIM = new DisableIronManEvent(plugin, event.getPlayer(), true);
			plugin.getServer().getPluginManager().callEvent(disableIM);
		}
		
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		
		Entity entity = event.getEntity();
		
		if (entity instanceof Player) {
			
			Player player = (Player) entity;
			
			if (plugin.isIronMan(player)) {
				event.setCancelled(true);
			}
			
		}
		
	}
	
}
