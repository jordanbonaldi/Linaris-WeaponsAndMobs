package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CacWeaponTmpl extends WeaponTmpl {
	public CacWeaponTmpl(String name, Material material, Double damage) {
		super(name, material, damage);
	}

	public void onCac(EntityDamageByEntityEvent event, Player damager) {
		event.setDamage(m_damage);
	}
}
