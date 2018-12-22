package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import net.theuniverscraft.WeaponsAndMobs.Utils.Utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class BazookaTmpl extends GunWeaponTmpl {
	public BazookaTmpl(String name, Material material) {
		super(name, material, 0D);
	}
	
	public void shot(LivingEntity entity, Boolean useZoom) {
		for(int i = 0; i < m_bulletPerShot; i++) {
			Vector vec = entity.getLocation().getDirection().multiply(m_velocityMultiplier);
			TNTPrimed tnt = (TNTPrimed) entity.getWorld().spawnEntity(entity.getEyeLocation(), EntityType.PRIMED_TNT);
			
			Utils.setMetadata(tnt, "owner", entity);
			Utils.setMetadata(tnt, "weapon", this);
			tnt.setFuseTicks(30);
			tnt.setVelocity(vec);
		}
		if(m_sound != null) m_sound.playSounds(entity.getLocation());
	}
	
	public void zoom(Player player, Boolean isZoom) {} // Pas de zoom au bazooka
}
