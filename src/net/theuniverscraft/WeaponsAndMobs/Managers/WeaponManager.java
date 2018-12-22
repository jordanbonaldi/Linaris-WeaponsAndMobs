package net.theuniverscraft.WeaponsAndMobs.Managers;

import java.util.LinkedList;
import java.util.List;

import net.theuniverscraft.WeaponsAndMobs.Utils.WeaponSound;
import net.theuniverscraft.WeaponsAndMobs.Utils.WeaponSound.FinalSound;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.BazookaTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GrenadeTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GunWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.KnifeTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.MenotteTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.SniperTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.UnMenotteTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.WeaponTmpl;

import org.bukkit.Material;
import org.bukkit.Sound;

public class WeaponManager {
	private List<WeaponTmpl> weapons = new LinkedList<WeaponTmpl>();
	
	private static WeaponManager instance = null;
	public static WeaponManager getInstance() {
		if(instance == null) instance = new WeaponManager();
		return instance;
	}
	
	private WeaponManager() {
		weapons.add(new KnifeTmpl("&b&lCouteau", Material.GOLD_SWORD));
		
		weapons.add(new GunWeaponTmpl("&b&l9mm", Material.WOOD_SPADE, 5D).setVelocityMultiplier(5D).setMaxDecalage(0.25)
				.setShotInterval(800L).setWeaponSound(new WeaponSound(new FinalSound(Sound.GHAST_FIREBALL, 1L, 20L), 
						new FinalSound(Sound.ZOMBIE_METAL, 1L, 20L)))
				.setItemCharger(Material.GHAST_TEAR)
				.setChargerMaxBullet(12));
		
		weapons.add(new GunWeaponTmpl("&b&lFusil de Chasse", Material.IRON_PICKAXE, 14D).setShotInterval(1200L).setVelocityMultiplier(4D).setMaxDecalage(0.1)
				.setBulletPerShot(2).setWeaponSound(new WeaponSound(new FinalSound(Sound.GHAST_FIREBALL, 1L, 20L)))
				.setItemCharger(Material.SPIDER_EYE)
				.setChargerMaxBullet(7));
		
		weapons.add(new GunWeaponTmpl("&b&lAK47", Material.DIAMOND_SPADE, 6D).setVelocityMultiplier(5D).setMaxDecalage(0.25)
				.setShotInterval(100L).setChargerMaxBullet(30)
				.setWeaponSound(new WeaponSound(new FinalSound(Sound.SKELETON_HURT, 1L, 20L), 
						new FinalSound(Sound.ZOMBIE_WOOD, 1L, 20L), new FinalSound(Sound.GHAST_FIREBALL, 1L, 20L)))
				.setItemCharger(Material.GLOWSTONE_DUST));
		
		weapons.add(new GunWeaponTmpl("&b&lMSG", Material.DIAMOND_PICKAXE, 9D).setVelocityMultiplier(25D).setMaxDecalage(0.1)
				.setShotInterval(25L).setWeaponSound(new WeaponSound(new FinalSound(Sound.GHAST_FIREBALL, 1L, 20L), 
						new FinalSound(Sound.ZOMBIE_METAL, 1L, 20L))).setChargerMaxBullet(40)
				.setItemCharger(Material.EGG));
		
		weapons.add(new GunWeaponTmpl("&b&lM4", Material.GOLD_SPADE, 4D).setVelocityMultiplier(6D).setMaxDecalage(0.12)
				.setBulletPerShot(1).setShotInterval(50L)
				.setWeaponSound(new WeaponSound(new FinalSound(Sound.SKELETON_HURT, 1L, 20L), 
						new FinalSound(Sound.ZOMBIE_WOOD, 1L, 20L), new FinalSound(Sound.GHAST_FIREBALL, 1L, 20L)))
				.setItemCharger(Material.SLIME_BALL).setChargerMaxBullet(38));
		
		weapons.add(new SniperTmpl("&b&lSniper", Material.IRON_SPADE, 18D).setVelocityMultiplier(60D).setMaxDecalage(0.1)
				.setShotInterval(2000L).setWeaponSound(new WeaponSound(new FinalSound(Sound.GHAST_FIREBALL, 1L, 20L), 
						new FinalSound(Sound.ZOMBIE_METAL, 1L, 20L))).setZoomIsPrecise(true)
				.setItemCharger(Material.MELON_SEEDS)
				.setChargerMaxBullet(4));
		
		weapons.add(new GrenadeTmpl("&b&lGrenade", Material.FIREWORK_CHARGE));
		
		weapons.add(new MenotteTmpl("&b&lMenotte", Material.WOOD_HOE));
		weapons.add(new UnMenotteTmpl("&b&lPrisonnier", Material.STONE_HOE));
		
		weapons.add(new BazookaTmpl("&b&lBazooka", Material.GOLD_PICKAXE).setVelocityMultiplier(2D)
				.setItemCharger(Material.FERMENTED_SPIDER_EYE)
				.setChargerMaxBullet(3));
	}
	
	public WeaponTmpl getWeapon(Material material) {
		for(WeaponTmpl weapon : weapons) {
			if(weapon.getMaterial() == material) return weapon;
		}
		return null;
	}
	public GunWeaponTmpl getGunWeaponByItemCharger(Material mat) {
		for(WeaponTmpl weapon : weapons) {
			if(weapon instanceof GunWeaponTmpl) {
				GunWeaponTmpl gun = (GunWeaponTmpl) weapon;
				if(gun.getItemCharger() == mat) return gun;
			}
		}
		return null;
	}
	
	public List<WeaponTmpl> getListWeapon() {
		return weapons;
	}
}
