package com.n3wham.IronMan;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.n3wham.IronMan.Events.*;

public class Main extends JavaPlugin {

	public static Permission perms = null;

	@Override
	public void onEnable() {

		loadEvents();
		loadCommands();
		
		if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			setupPermissions();
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, run, 60, 20);

	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	public void loadEvents() {
		new IronManEvents(this);
		new PlayerEvents(this);
	}

	public void loadCommands() {
		getCommand("im").setExecutor(new IronManCommand(this));
		getCommand("ironman").setExecutor(new IronManCommand(this));
	}

	public boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public String getPrefix() {
		return ChatColor.GOLD + "[" + ChatColor.RED + "Iron Man" + ChatColor.GOLD + "] " + ChatColor.GRAY;
	}

	public boolean hasPermission(Player player, String perm) {
		if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			return perms.has(player, perm);
		} else {
			if (player.isOp()) {
				return true;
			}
			
			return player.hasPermission(perm);
		}
	}

	public boolean isIronMan(Player player) {
		return getConfig().getBoolean("players." + player.getName() + ".enabled");
	}

	public void setIronMan(Player player, boolean bool) {
		getConfig().set("players." + player.getName() + ".enabled", bool);
		ItemStack air = new ItemStack(Material.AIR);
		
		ItemStack imIHelm = new ItemStack(Material.IRON_HELMET);
		ItemStack imIPlate = new ItemStack(Material.IRON_CHESTPLATE);
		ItemStack imILegs = new ItemStack(Material.IRON_LEGGINGS);
		ItemStack imIBoots = new ItemStack(Material.IRON_BOOTS);
		
		ItemStack imLHelm = new ItemStack(Material.LEATHER_HELMET);
		ItemStack imLPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack imLLegs = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack imLBoots = new ItemStack(Material.LEATHER_BOOTS);
		
		LeatherArmorMeta imLHelmMeta = (LeatherArmorMeta) imLHelm.getItemMeta();
		imLHelmMeta.setColor(Color.fromRGB(255, 215, 0));
		imLHelmMeta.setDisplayName(ChatColor.GOLD + "Iron Man Helmet");
		imLHelm.setItemMeta(imLHelmMeta);
		
		LeatherArmorMeta imLPlateMeta = (LeatherArmorMeta) imLPlate.getItemMeta();
		imLPlateMeta.setColor(Color.fromRGB(255, 0, 0));
		imLPlateMeta.setDisplayName(ChatColor.GOLD + "Iron Man Chestplate");
		imLPlate.setItemMeta(imLPlateMeta);
		
		LeatherArmorMeta imLLegsMeta = (LeatherArmorMeta) imLLegs.getItemMeta();
		imLLegsMeta.setColor(Color.fromRGB(255, 0, 0));
		imLLegsMeta.setDisplayName(ChatColor.GOLD + "Iron Man Leggings");
		imLLegs.setItemMeta(imLLegsMeta);
		
		LeatherArmorMeta imLBootsMeta = (LeatherArmorMeta) imLBoots.getItemMeta();
		imLBootsMeta.setColor(Color.fromRGB(255, 215, 0));
		imLBootsMeta.setDisplayName(ChatColor.GOLD + "Iron Man Boots");
		imLBoots.setItemMeta(imLBootsMeta);
		
		ItemMeta imIHelmMeta = imIHelm.getItemMeta();
		imIHelmMeta.setDisplayName(ChatColor.GOLD + "Iron Man Helmet");
		imIHelm.setItemMeta(imIHelmMeta);
		
		ItemMeta imIPlateMeta = imIPlate.getItemMeta();
		imIPlateMeta.setDisplayName(ChatColor.GOLD + "Iron Man Chestplate");
		imIPlate.setItemMeta(imIPlateMeta);
		
		ItemMeta imILegsMeta = imILegs.getItemMeta();
		imILegsMeta.setDisplayName(ChatColor.GOLD + "Iron Man Leggings");
		imILegs.setItemMeta(imILegsMeta);
		
		ItemMeta imIBootsMeta = imIBoots.getItemMeta();
		imIBootsMeta.setDisplayName(ChatColor.GOLD + "Iron Man Boots");
		imIBoots.setItemMeta(imIBootsMeta);
		
		if (bool) {
			PlayerInventory pI = player.getInventory();
			
			int f1 = pI.firstEmpty();
			ItemStack helm = pI.getHelmet();
			pI.setItem(f1, helm);
			if (getArmourSetting(player).equalsIgnoreCase("iron")) {
				pI.setHelmet(imIHelm);
			} else {
				pI.setHelmet(imLHelm);
			}
			if (helm != null) {
				getConfig().set("players." + player.getName() + ".helm", f1);
			} else {
				getConfig().set("players." + player.getName() + ".helm", 999);
			}
			
			int f2 = pI.firstEmpty();
			ItemStack plate = pI.getChestplate();
			pI.setItem(f2, plate);
			if (getArmourSetting(player).equalsIgnoreCase("iron")) {
				pI.setChestplate(imIPlate);
			} else {
				pI.setChestplate(imLPlate);
			}
			if (plate != null) {
				getConfig().set("players." + player.getName() + ".plate", f2);
			} else {
				getConfig().set("players." + player.getName() + ".plate", 999);
			}
			
			int f3 = pI.firstEmpty();
			ItemStack legs = pI.getLeggings();
			pI.setItem(f3, legs);
			if (getArmourSetting(player).equalsIgnoreCase("iron")) {
				pI.setLeggings(imILegs);
			} else {
				pI.setLeggings(imLLegs);
			}
			if (legs != null) {
				getConfig().set("players." + player.getName() + ".legs", f3);
			} else {
				getConfig().set("players." + player.getName() + ".legs", 999);
			}
			
			int f4 = pI.firstEmpty();
			ItemStack boots = pI.getBoots();
			pI.setItem(f4, boots);
			if (getArmourSetting(player).equalsIgnoreCase("iron")) {
				pI.setBoots(imIBoots);
			} else {
				pI.setBoots(imLBoots);
			}
			if (boots != null) {
				getConfig().set("players." + player.getName() + ".boots", f4);
			} else {
				getConfig().set("players." + player.getName() + ".boots", 999);
			}
			
			saveConfig();
			player.getInventory().setArmorContents(pI.getArmorContents());
			player.getInventory().setContents(pI.getContents());
		} else {
			PlayerInventory pI = player.getInventory();
			
			int f1 = getConfig().getInt("players." + player.getName() + ".helm");
			if (f1 == 999) {
				pI.setHelmet(air);
			} else {
				ItemStack helm = pI.getItem(f1);
				pI.setHelmet(helm);
				pI.setItem(f1, air);
			}
			getConfig().set("players." + player.getName() + ".helm", 0);

			int f2 = getConfig().getInt("players." + player.getName() + ".plate");
			if (f2 == 999) {
				pI.setChestplate(air);
			} else {
				ItemStack plate = pI.getItem(f2);
				pI.setChestplate(plate);
				pI.setItem(f2, air);
			}
			getConfig().set("players." + player.getName() + ".plate", 0);

			int f3 = getConfig().getInt("players." + player.getName() + ".legs");
			if (f3 == 999) {
				pI.setLeggings(air);
			} else {
				ItemStack legs = pI.getItem(f3);
				pI.setLeggings(legs);
				pI.setItem(f3, air);
			}
			getConfig().set("players." + player.getName() + ".legs", 0);

			int f4 = getConfig().getInt("players." + player.getName() + ".boots");
			if (f4 == 999) {
				pI.setBoots(air);
			} else {
				ItemStack boots = pI.getItem(f4);
				pI.setBoots(boots);
				pI.setItem(f4, air);
			}
			getConfig().set("players." + player.getName() + ".boots", 0);
			
			saveConfig();
			player.getInventory().setArmorContents(pI.getArmorContents());
			player.getInventory().setContents(pI.getContents());
		}
	}

	public String getArmourSetting(Player player) {
		return getConfig().getString("players." + player.getName() + ".armour");
	}

	public void setArmourSetting(Player player, String setting) {
		getConfig().set("players." + player.getName() + ".armour", setting);
	}
	
	Runnable run = new Runnable() {
		
		public void run() {
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (isIronMan(player) && player.isFlying()) {
					player.getLocation().getWorld().playEffect(player.getLocation(), Effect.SMOKE, 5);
				}
			}
			
		}
		
	};

}
