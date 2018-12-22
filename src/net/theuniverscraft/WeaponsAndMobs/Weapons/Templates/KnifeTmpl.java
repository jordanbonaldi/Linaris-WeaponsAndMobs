package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class KnifeTmpl extends CacWeaponTmpl {
	public KnifeTmpl(String name, Material material) {
		super(name, material, 10D);
	}
	
	@Override
	public void onCac(EntityDamageByEntityEvent event, Player damager) {		
		final ItemStack is = damager.getItemInHand();
		if(damager.getItemInHand() != null) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPluginInstance(), new Runnable() {
				public void run() {
					is.setDurability((short) -1);
				}
			}, 5L);
		}
		
		super.onCac(event, damager);
	}
}
