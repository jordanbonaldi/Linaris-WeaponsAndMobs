package net.theuniverscraft.WeaponsAndMobs.Weapons;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;
import net.theuniverscraft.WeaponsAndMobs.Utils.Utils;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GunWeaponTmpl;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class GunObj {
	private LivingEntity m_entity;
	private GunWeaponTmpl m_gun;
	
	private boolean m_zoom;
	private long m_lastShot;
	
	private boolean m_recharge;
	
	private ItemStack m_is;
	
	public GunObj(LivingEntity entity, GunWeaponTmpl gun) {
		m_entity = entity;
		m_gun = gun;
		
		m_zoom = false;
		m_lastShot = 0L;
		
		m_recharge = false;
		
		m_is = new ItemStack(m_gun.getMaterial());
		ItemMeta meta = m_is.getItemMeta();
		meta.setDisplayName(m_gun.getName());
		m_is.setItemMeta(meta);
	}
	
	public void onShot(ItemStack itemStack, final int slot) {
		if(m_lastShot+m_gun.getShotInterval() > System.currentTimeMillis()) return;
		
		Long timeToRecharge = Utils.getPlayerTimeToRecharge();
		
		short add = (short) Math.ceil(itemStack.getType().getMaxDurability()*1.0 / m_gun.getChargerMaxBullet());
		
		if(itemStack.getDurability() > itemStack.getType().getMaxDurability()) {
			// Plus de munitions ni de chargeur
			updateRechargement(slot, Utils.msToTick(timeToRecharge));
			return;
		}
		
		if(m_recharge) return;
		
		m_gun.shot(m_entity, m_zoom);
		
		m_lastShot = System.currentTimeMillis();
		
		itemStack.setDurability((short) (itemStack.getDurability() + add));
		
		if(itemStack.getDurability() >= itemStack.getType().getMaxDurability()) {
			if(m_entity instanceof Player) {
				Player player = (Player) m_entity;
				PlayerInventory inv = player.getInventory();
				
				if(inv.contains(m_gun.getItemCharger())) {
					ItemMeta im = itemStack.getItemMeta();
					im.setDisplayName(m_gun.getName() + ChatColor.DARK_RED + "     Rechargement ...");
					itemStack.setItemMeta(im);
					
					updateRechargement(slot, Utils.msToTick(timeToRecharge));
					
					if(m_zoom) { onZoom(); }
				}
				else {
					ItemMeta im = itemStack.getItemMeta();
					im.setDisplayName(m_gun.getName() + ChatColor.DARK_RED + "     Vide ...");
					itemStack.setItemMeta(im);
					
					updateRechargement(slot, Utils.msToTick(timeToRecharge));
					
					if(m_zoom) { onZoom(); }
				}
			}
		}
	}
	
	private void updateRechargement(final int slot, long tick) {
		if(m_entity instanceof Player) {
			final Player player = (Player) m_entity;
			if(!player.getInventory().contains(m_gun.getItemCharger()) || m_recharge) {
				return;
			}
			
			m_recharge = true;
			Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPluginInstance(), new Runnable() {
				@Override
				public void run() {
					PlayerInventory inv = player.getInventory();
					
					ItemStack is = inv.getItem(slot);
					if(is != null) {
						if(is.getType() == m_gun.getMaterial()) {
							if(inv.contains(m_gun.getItemCharger())) {
								int slotCharger = inv.first(m_gun.getItemCharger());
								ItemStack isCharger = inv.getItem(slotCharger);
								
								if(isCharger.getAmount() > 1) {
									isCharger.setAmount(isCharger.getAmount() - 1);
								}
								else {
									inv.setItem(slotCharger, null);
								}
								
								// On enleve le mot "rechargement"
								ItemMeta im = is.getItemMeta();
								im.setDisplayName(m_gun.getName());
								is.setItemMeta(im);
								is.setDurability((short) 0);
							}
						}
					}
					m_recharge = false;
				}
			}, tick);
		}
		else {
			m_recharge = true;
			Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMain.getPluginInstance(), new Runnable() {
				@Override
				public void run() {
					m_recharge = false;
				}
			}, tick);
		}
	}
	
	public void onZoom() {
		if(m_entity instanceof Player) {
			final Player player = (Player) m_entity;
			m_zoom = !m_zoom;
			m_gun.zoom(player, m_zoom);
		}
	}
	public Boolean getIsZoom() { return m_zoom; }
	public GunWeaponTmpl getGun() { return m_gun; }
	
	public LivingEntity getPlayer() { return m_entity; }
	public ItemStack getItem() { return m_is; }
	
	public void resetTime() {
		m_lastShot = 0L;
	}
}
