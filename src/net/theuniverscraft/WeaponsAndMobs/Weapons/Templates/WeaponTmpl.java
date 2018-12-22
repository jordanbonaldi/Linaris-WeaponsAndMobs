package net.theuniverscraft.WeaponsAndMobs.Weapons.Templates;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class WeaponTmpl {
	protected String m_name;
	protected Material m_material;
	protected Double m_damage;
	
	public WeaponTmpl(String name, Material material, Double damage) {
		m_name = name;
		m_material = material;
		m_damage = damage;
	}
		
	public String getName() { return ChatColor.translateAlternateColorCodes('&', m_name); }
	public Material getMaterial() { return m_material; }
	public Double getDamage() { return m_damage; }
	
	public boolean equals(Object other) {
		if(other == null) return false;
		if(!(other instanceof WeaponTmpl)) return false;
		if(this == other) return true;
		
		WeaponTmpl weapon = (WeaponTmpl) other;
		if(weapon.getMaterial() == m_material) return true;
		
		return false;
	}
	
	public int hashCode() {
		int result = 7;
		final int multiplier = 17;
		
		result = multiplier*result + m_material.hashCode();
		
		return result;
	}
}
