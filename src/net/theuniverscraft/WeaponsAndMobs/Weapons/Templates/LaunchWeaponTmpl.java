package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LaunchWeaponTmpl extends WeaponTmpl {
	protected Double m_distanceMultiplier;
	public LaunchWeaponTmpl(String name, Material material, Double distanceMultiplier) {
		super(name, material, 0D);
		m_distanceMultiplier = distanceMultiplier;
	}
	
	public Item onRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		final Item dropped = player.getWorld().dropItem(player.getEyeLocation().subtract(0, 0.1, 0), new ItemStack(m_material));
		dropped.setPickupDelay(Integer.MAX_VALUE);
		dropped.setVelocity(player.getLocation().getDirection().multiply(m_distanceMultiplier));
		
		ItemStack inHand = player.getItemInHand();
		if(inHand.getAmount() <= 1) {
			player.setItemInHand(null);
		}
		else {
			inHand.setAmount(inHand.getAmount()-1);
		}
		return dropped;
	}
}
