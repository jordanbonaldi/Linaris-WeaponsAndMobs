package net.theuniverscraft.WeaponsAndMobs;

import net.theuniverscraft.WeaponsAndMobs.Managers.EntitiesManager;
import net.theuniverscraft.WeaponsAndMobs.Managers.WeaponManager;
import net.theuniverscraft.WeaponsAndMobs.Weapons.GunObj;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GunWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.WeaponTmpl;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class MobWeaponTimer {
	public MobWeaponTimer() {}
	
	public void start() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getPluginInstance(), new Runnable() {
			@Override
			public void run() {
				for(World world : Bukkit.getWorlds()) {
					for(Entity entity : world.getEntities()) {
						if(entity instanceof Creature) {
							Creature creature = (Creature) entity;
							if(creature.getTarget() != null) {
								if(creature.getEquipment().getItemInHand() != null) {
									WeaponTmpl weapon = WeaponManager.getInstance().getWeapon(creature.getEquipment().getItemInHand().getType());
									
									if(weapon != null && weapon instanceof GunWeaponTmpl) {
										LivingEntity target = creature.getTarget();
										if(target.getLocation().distance(creature.getLocation()) > 60 || target.isDead() || 
												!creature.isValid() || creature.isDead()) {
											creature.setTarget(null);
										}
										else {
											GunObj gun = EntitiesManager.getInstance().getEntityGuns(creature).getGun((GunWeaponTmpl) weapon);
											gun.onShot(creature.getEquipment().getItemInHand(), 0);
										}
									}
								}
							}
							
							if(creature.getTarget() == null && EntitiesManager.getInstance().getEntityGuns(creature).isTmp()) {
								EntitiesManager.getInstance().removeEntity(creature);
							}
						}
					}
				}
			}
		}, 20L, 20L);
	}
}
