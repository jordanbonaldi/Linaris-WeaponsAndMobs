package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenotteTmpl extends WeaponTmpl {
	public MenotteTmpl(String name, Material material) {
		super(name, material, 0.0D);
	}
	
	public void onRightClickPlayer(final Player clicker, Player clicked) {
		if(!PluginMain.getPluginInstance().hasPerm(clicker, "flic.menotte")) return;
		
		final ItemStack itemUnMenotte = new ItemStack(Material.STONE_HOE, 1);
		ItemMeta meta = itemUnMenotte.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Prisonnier : " + clicked.getName());
		itemUnMenotte.setItemMeta(meta);
		
		Entity top = clicker;
		while(top.getPassenger() != null) top = top.getPassenger();
		
		top.setPassenger(clicked);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPluginInstance(), new Runnable() {
			@Override
			public void run() {
				clicker.setItemInHand(itemUnMenotte);
			}
		}, 10L);
	}
}
