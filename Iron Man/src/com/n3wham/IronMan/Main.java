package com.n3wham.IronMan;

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
import org.bukkit.plugin.java.JavaPlugin;

import com.n3wham.IronMan.Events.*;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		loadEvents();
		loadCommands();
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

	public String getPrefix() {
		return ChatColor.GOLD + "[" + ChatColor.RED + "Iron Man"
				+ ChatColor.GOLD + "] " + ChatColor.GRAY;
	}

	public boolean hasPermission(Player player, String perm) {
		return player.hasPermission(perm);
	}

	public boolean isIronMan(Player player) {
		return getConfig().getBoolean(
				"players." + player.getName() + ".enabled");
	}

	public void setIronMan(Player player, boolean bool) {
		getConfig().set("players." + player.getName() + ".enabled", bool);
		ItemStack air = new ItemStack(Material.AIR);

		ItemStack imIHelm = this.getArmourWithMeta(Material.IRON_HELMET, null, "Iron Man Helmet");
		ItemStack imIPlate = this.getArmourWithMeta(Material.IRON_CHESTPLATE, null, "Iron Man Chestplate");
		ItemStack imILegs = this.getArmourWithMeta(Material.IRON_LEGGINGS, null, "Iron Man Leggings");
		ItemStack imIBoots = this.getArmourWithMeta(Material.IRON_BOOTS, null, "Iron Man Boots");

		ItemStack imLHelm = this.getArmourWithMeta(Material.LEATHER_HELMET, Color.fromRGB(255, 215, 0), "Iron Man Helmet");
		ItemStack imLPlate = this.getArmourWithMeta(Material.LEATHER_CHESTPLATE,Color.fromRGB(255, 0, 0),"Iron Man Chestplate");
		ItemStack imLLegs = this.getArmourWithMeta(Material.LEATHER_LEGGINGS,Color.fromRGB(255, 0, 0),"Iron Man Leggings");
		ItemStack imLBoots = this.getArmourWithMeta(Material.LEATHER_BOOTS,Color.fromRGB(255, 215, 0),"Iron Man Boots");

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

			int f1 = getConfig()
					.getInt("players." + player.getName() + ".helm");
			if (f1 == 999) {
				pI.setHelmet(air);
			} else {
				ItemStack helm = pI.getItem(f1);
				pI.setHelmet(helm);
				pI.setItem(f1, air);
			}
			getConfig().set("players." + player.getName() + ".helm", 0);

			int f2 = getConfig().getInt(
					"players." + player.getName() + ".plate");
			if (f2 == 999) {
				pI.setChestplate(air);
			} else {
				ItemStack plate = pI.getItem(f2);
				pI.setChestplate(plate);
				pI.setItem(f2, air);
			}
			getConfig().set("players." + player.getName() + ".plate", 0);

			int f3 = getConfig()
					.getInt("players." + player.getName() + ".legs");
			if (f3 == 999) {
				pI.setLeggings(air);
			} else {
				ItemStack legs = pI.getItem(f3);
				pI.setLeggings(legs);
				pI.setItem(f3, air);
			}
			getConfig().set("players." + player.getName() + ".legs", 0);

			int f4 = getConfig().getInt(
					"players." + player.getName() + ".boots");
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

	private ItemStack getArmourWithMeta(Material m, Color c, String s) {
		ItemStack is = new ItemStack(m);
		ItemMeta armMeta = is.getItemMeta();
		armMeta.setDisplayName(ChatColor.GOLD + s);
		if (m.equals(Material.LEATHER_BOOTS)
				|| m.equals(Material.LEATHER_CHESTPLATE)
				|| m.equals(Material.LEATHER_HELMET)
				|| m.equals(Material.LEATHER_LEGGINGS)) {
			((LeatherArmorMeta) armMeta).setColor(c);
		}
		is.setItemMeta(armMeta);
		return is;
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
					player.getLocation().getWorld()
							.playEffect(player.getLocation(), Effect.SMOKE, 5);
				}
			}

		}

	};

}
