package com.n3wham.IronMan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.n3wham.IronMan.CustomEvents.*;

public class IronManCommand implements CommandExecutor {
	
	public Main plugin;
	public String prefix;
	
	public IronManCommand(Main plugin) {
		
		this.plugin = plugin;
		this.prefix = plugin.getPrefix();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			final Player player = (Player) sender;
			
			if (plugin.hasPermission(player, "ironman.use")) {
				
				if (args.length == 0) {
					
					if (!plugin.isIronMan(player)) {
						
						EnableIronManEvent enableIM = new EnableIronManEvent(plugin, player, false);
						plugin.getServer().getPluginManager().callEvent(enableIM);
						
					} else {
						
						DisableIronManEvent disableIM = new DisableIronManEvent(plugin, player, false);
						plugin.getServer().getPluginManager().callEvent(disableIM);
						
					}
					
				} else if (args.length == 1) {
					
					if (args[0].equalsIgnoreCase("toggle")) {
						
						String setting = plugin.getArmourSetting(player);
						if (setting.equalsIgnoreCase("iron")) {
							plugin.setArmourSetting(player, "leather");
							player.sendMessage(prefix + "You have selected the Dyed Leather Armour Suit.");
							
							if (plugin.isIronMan(player)) {
								DisableIronManEvent disableIM = new DisableIronManEvent(plugin, player, true);
								plugin.getServer().getPluginManager().callEvent(disableIM);
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									
									public void run() {
										EnableIronManEvent enableIM = new EnableIronManEvent(plugin, player, true);
										plugin.getServer().getPluginManager().callEvent(enableIM);
									}
									
								}, 2);
							}
						} else {
							plugin.setArmourSetting(player, "iron");
							player.sendMessage(prefix + "You have selected the Iron Armour Suit.");
							
							if (plugin.isIronMan(player)) {
								DisableIronManEvent disableIM = new DisableIronManEvent(plugin, player, true);
								plugin.getServer().getPluginManager().callEvent(disableIM);
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									
									public void run() {
										EnableIronManEvent enableIM = new EnableIronManEvent(plugin, player, true);
										plugin.getServer().getPluginManager().callEvent(enableIM);
									}
									
								}, 2);
							}
						}
						
					} else {
						
						player.sendMessage(prefix + ChatColor.RED + "Incorrect argument supplied.");
						
					}
					
				} else {
					
					player.sendMessage(prefix + ChatColor.RED + "Incorrect number arguments supplied.");
					
				}
				
			} else {
				
				player.sendMessage(prefix + "You don't have the power of Iron Man!");
				
			}
			
		} else {
			
			sender.sendMessage(plugin.getPrefix() + "Iron Man is for players only.");
			
		}
		
		return true;
	}
	
}
