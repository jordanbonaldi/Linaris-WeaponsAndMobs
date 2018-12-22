package net.theuniverscraft.WeaponsAndMobs.Managers;

import java.util.HashMap;
import java.util.LinkedList;

import net.theuniverscraft.WeaponsAndMobs.Weapons.GunObj;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GunWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.WeaponTmpl;

import org.bukkit.entity.Player;

public class PlayersManager {
	private HashMap<String, LinkedList<GunObj>> m_playersGuns = new HashMap<String, LinkedList<GunObj>>();
	
	private static PlayersManager instance = null;
	public static PlayersManager getInstance() {
		if(instance == null) instance = new PlayersManager();
		return instance;
	}
	
	private PlayersManager() {}
	
	public GunObj getPlayerGun(Player player, GunWeaponTmpl gun) {
		if(!m_playersGuns.containsKey(player.getName())) {
			LinkedList<GunObj> playerGuns = new LinkedList<GunObj>();
			
			for(WeaponTmpl weapon : WeaponManager.getInstance().getListWeapon()) {
				if(weapon instanceof GunWeaponTmpl) {
					playerGuns.add(new GunObj(player, (GunWeaponTmpl) weapon));
				}
			}
			
			m_playersGuns.put(player.getName(), playerGuns);
		}
		
		if(m_playersGuns.containsKey(player.getName())) {
			for(GunObj pg : m_playersGuns.get(player.getName())) {
				if(pg.getGun().equals(gun)) {
					return pg;
				}
			}
		}
		return null;
	}
	
	public void resetPlayer(Player player) {
		if(m_playersGuns.containsKey(player.getName())) {
			m_playersGuns.remove(player.getName());
		}
	}
	
	public LinkedList<GunObj> getPlayerGuns(Player player) {
		if(!m_playersGuns.containsKey(player.getName())) {
			LinkedList<GunObj> playerGuns = new LinkedList<GunObj>();
			
			for(WeaponTmpl weapon : WeaponManager.getInstance().getListWeapon()) {
				if(weapon instanceof GunWeaponTmpl) {
					playerGuns.add(new GunObj(player, (GunWeaponTmpl) weapon));
				}
			}
			
			m_playersGuns.put(player.getName(), playerGuns);
		}
		
		if(m_playersGuns.containsKey(player.getName())) {
			return m_playersGuns.get(player.getName());
		}
		return null;
	}
	
	public void resetTime(Player player) {
		if(m_playersGuns.containsKey(player.getName())) {
			for(GunObj pg : m_playersGuns.get(player.getName())) {
				pg.resetTime();
			}
		}
	}
}
