package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import net.theuniverscraft.WeaponsAndMobs.Utils.Utils;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class UnMenotteTmpl extends WeaponTmpl {
	public UnMenotteTmpl(String name, Material material) {
		super(name, material, 0.0D);
	}
	
	public void onRightClick(Player clicker) {
		String liberate_name = clicker.getItemInHand().getItemMeta().getDisplayName().split(" : ")[1];
		
		for(Entity passenger = clicker.getPassenger(); passenger != null; passenger = passenger.getPassenger()) {
			if(passenger instanceof Player) {
				final Player player = (Player) passenger;
				if(player.getName().equalsIgnoreCase(liberate_name)) {
					Utils.setMetadata(player, "liberate", true);
					player.leaveVehicle();
					clicker.setPassenger(player.getPassenger());
					break;
				}
			}
		}
		
		clicker.setItemInHand(null);
	}
}
