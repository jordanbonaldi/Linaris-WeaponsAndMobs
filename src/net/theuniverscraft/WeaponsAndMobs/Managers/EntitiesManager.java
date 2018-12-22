package net.theuniverscraft.WeaponsAndMobs.Managers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.theuniverscraft.WeaponsAndMobs.Weapons.GunObj;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GunWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.WeaponTmpl;

import org.bukkit.entity.LivingEntity;

public class EntitiesManager {
	private HashMap<UUID, EntityGuns> m_entitiesGuns = new HashMap<UUID, EntityGuns>();
	
	private static EntitiesManager instance = null;
	public static EntitiesManager getInstance() {
		if(instance == null) instance = new EntitiesManager();
		return instance;
	}
	
	private EntitiesManager() {}
	
	
	public EntityGuns getEntityGuns(LivingEntity entity) {
		if(!m_entitiesGuns.containsKey(entity.getUniqueId())) {
			m_entitiesGuns.put(entity.getUniqueId(), new EntityGuns(entity));
		}
		
		return m_entitiesGuns.get(entity.getUniqueId());
	}
	
	public void removeEntity(LivingEntity entity) {
		if(m_entitiesGuns.containsKey(entity.getUniqueId())) {
			m_entitiesGuns.remove(entity.getUniqueId());
		}
		entity.remove();
	}
	
	public class EntityGuns {
		private LivingEntity m_entity;
		private List<GunObj> m_guns = new LinkedList<GunObj>();
		private boolean m_isTmp;
		
		public EntityGuns(LivingEntity entity) {
			m_entity = entity;
			m_isTmp = false;
			
			for(WeaponTmpl weapon : WeaponManager.getInstance().getListWeapon()) {
				if(weapon instanceof GunWeaponTmpl) {
					m_guns.add(new GunObj(m_entity, (GunWeaponTmpl) weapon));
				}
			}
		}
		
		public void setTmp(boolean isTmp) { m_isTmp = isTmp; }
		public boolean isTmp() { return m_isTmp; }
		
		public GunObj getGun(GunWeaponTmpl gun) {
			for(GunObj pg : m_guns) {
				if(pg.getGun().equals(gun)) {
					return pg;
				}
			}
			return null;
		}
		
		public List<GunObj> getGuns() { return m_guns; }
	}
}
