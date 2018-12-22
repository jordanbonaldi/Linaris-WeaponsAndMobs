package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class GrenadeTmpl extends LaunchWeaponTmpl {
	public GrenadeTmpl(String name, Material material) {
		super(name, material, 1D);
	}
	
	public Item onRightClick(PlayerInteractEvent event) {
		final Item dropped = super.onRightClick(event);
		final Player player = event.getPlayer();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPluginInstance(), new Runnable() {
			@Override
			public void run() {
				Location loc = dropped.getLocation();
				TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
				tnt.setMetadata("owner", new FixedMetadataValue(PluginMain.getPluginInstance(), player));
				tnt.setMetadata("weapon", new FixedMetadataValue(PluginMain.getPluginInstance(), this));
				tnt.setFuseTicks(0);
				dropped.remove();
			}
		}, 50L);
		return dropped;
	}
}
