package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;
import net.theuniverscraft.WeaponsAndMobs.Utils.WeaponSound;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class GunWeaponTmpl extends WeaponTmpl {
	protected Double m_maxDecalage;
	protected Double m_velocityMultiplier;
	
	protected Integer m_bulletPerShot;
	protected WeaponSound m_sound;
	
	protected Integer m_chargerMaxBullet;
	
	protected Long m_interval;
	protected Boolean m_zoomIsPrecise;
	
	protected Material m_matCharger;
	
	public GunWeaponTmpl(String name, Material material, Double damage) {
		super(name, material, damage);
		m_maxDecalage = 0D;
		m_velocityMultiplier = 1D;
		m_bulletPerShot = 1;
		m_interval = 0L;
		m_sound = null;
		m_chargerMaxBullet = 1;
		m_zoomIsPrecise = false;
		
		m_matCharger = m_material;
	}
	
	public GunWeaponTmpl setItemCharger(Material mat) {
		m_matCharger = mat;
		return this;
	}
	public Material getItemCharger() { return m_matCharger; }
	
	
	public GunWeaponTmpl setVelocityMultiplier(Double velocityMultiplier) {
		m_velocityMultiplier = velocityMultiplier;
		return this;
	}
	public GunWeaponTmpl setShotInterval(Long interval) {
		m_interval = interval;
		return this;
	}
	public Long getShotInterval() { return m_interval; }
	
	public GunWeaponTmpl setMaxDecalage(Double maxDecalage) {
		m_maxDecalage = maxDecalage;
		return this;
	}
	
	public GunWeaponTmpl setBulletPerShot(Integer bulletPerShot) {
		m_bulletPerShot = bulletPerShot;
		return this;
	}
	
	public GunWeaponTmpl setWeaponSound(WeaponSound sound) {
		m_sound = sound;
		return this;
	}
	
	public GunWeaponTmpl setZoomIsPrecise(Boolean zoomIsPrecise) {
		m_zoomIsPrecise = zoomIsPrecise;
		return this;
	}
	
	public GunWeaponTmpl setChargerMaxBullet(Integer chargerMaxBullet) {
		m_chargerMaxBullet = chargerMaxBullet;
		return this;
	}
	public Integer getChargerMaxBullet() { return m_chargerMaxBullet; }
	
	protected Vector setLocationDecalage(Vector vector, Boolean useZoom) { // pour le sniper
		if(useZoom && m_zoomIsPrecise) return vector;
		
		Double decalageX = Math.random() * m_maxDecalage;
		Double decalageY = Math.random() * m_maxDecalage;
		Double decalageZ = Math.random() * m_maxDecalage;
		
		Double add = 1 - m_maxDecalage;
		
		Vector narmol = new Vector(decalageX + add, decalageY + add, decalageZ + add);
		vector.multiply(narmol);
		return vector;
	}
	
	public void onProjectilHit(EntityDamageByEntityEvent event) {
		event.setDamage(m_damage);
	}
	
	public void shot(LivingEntity entity, Boolean useZoom) {
		for(int i = 0; i < m_bulletPerShot; i++) {
			Vector vec = setLocationDecalage(entity.getLocation().getDirection().multiply(m_velocityMultiplier), useZoom);
			Snowball snowball = entity.launchProjectile(Snowball.class);
			snowball.setMetadata("weapon", new FixedMetadataValue(PluginMain.getPluginInstance(), this));
			snowball.setVelocity(vec);
		}
		
		if(m_sound != null) m_sound.playSounds(entity.getLocation());
	}
	
	public void zoom(Player player, Boolean isZoom) {		
		if(isZoom) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 24000, 4));
		}
		else {
			player.removePotionEffect(PotionEffectType.SLOW);
		}
	}
}
