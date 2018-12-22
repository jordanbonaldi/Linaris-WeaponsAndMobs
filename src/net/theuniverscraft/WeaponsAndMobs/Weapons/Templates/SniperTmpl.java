package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SniperTmpl extends GunWeaponTmpl {
	public SniperTmpl(String name, Material material, Double damage) {
		super(name, material, damage);
	}
	
	public void zoom(Player player, Boolean isZoom) {		
		if(isZoom) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 24000, 8));
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 24000, 1));
		}
		else {
			player.removePotionEffect(PotionEffectType.SLOW);
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
	}
}
