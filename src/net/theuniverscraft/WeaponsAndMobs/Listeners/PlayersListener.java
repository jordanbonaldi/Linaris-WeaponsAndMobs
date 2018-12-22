package net.theuniverscraft.WeaponsAndMobs.Listeners;

import net.theuniverscraft.WeaponsAndMobs.Managers.EntitiesManager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class PlayersListener implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player dead = event.getEntity();
		Player killer = dead.getKiller();
		
		if(killer != null) {
			// On spawn 2 flics temporaires
			World world = killer.getWorld();
			
			double deltaX = 1;
			double deltaZ = 1;
			
			Location loc = killer.getLocation().add(killer.getLocation().getDirection().multiply(5));
			while(loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.LONG_GRASS)
				loc.add(0, 1, 0);
			
			
			Location loc1 = loc.add(new Vector(deltaX, 0, deltaZ));
			Location loc2 = loc.subtract(new Vector(deltaX, 0, deltaZ));
			
			PigZombie pigZombie_1 = (PigZombie) world.spawnEntity(loc1, EntityType.PIG_ZOMBIE);
			PigZombie pigZombie_2 = (PigZombie) world.spawnEntity(loc2, EntityType.PIG_ZOMBIE);
			
			pigZombie_1.setBaby(false);
			pigZombie_2.setBaby(false);
			
			pigZombie_1.setCustomName(ChatColor.AQUA + "Flic");
			pigZombie_2.setCustomName(ChatColor.AQUA + "Flic");
			
			pigZombie_1.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SPADE));
			pigZombie_2.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SPADE));
			
			pigZombie_1.setTarget(killer);
			pigZombie_2.setTarget(killer);
			
			EntitiesManager.getInstance().getEntityGuns(pigZombie_1).setTmp(true);
			EntitiesManager.getInstance().getEntityGuns(pigZombie_2).setTmp(true);
		}
	}
	
	@EventHandler
	public void onPlayerToggleSneak(VehicleExitEvent event) {
		if(event.getExited().getVehicle() != null && event.getVehicle() instanceof Player) {
			event.setCancelled(true);
		}
	}
}
