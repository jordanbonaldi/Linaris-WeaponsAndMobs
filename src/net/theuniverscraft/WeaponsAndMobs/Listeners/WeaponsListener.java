package net.theuniverscraft.WeaponsAndMobs.Listeners;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;
import net.theuniverscraft.WeaponsAndMobs.Managers.PlayersManager;
import net.theuniverscraft.WeaponsAndMobs.Managers.WeaponManager;
import net.theuniverscraft.WeaponsAndMobs.Utils.Utils;
import net.theuniverscraft.WeaponsAndMobs.Weapons.GunObj;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.CacWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.GunWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.LaunchWeaponTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.MenotteTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.UnMenotteTmpl;
import net.theuniverscraft.WeaponsAndMobs.Weapons.Templates.WeaponTmpl;

import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLeaveEntityVehicleEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponsListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getPlayer().getItemInHand() == null) {	return;	}
		
		WeaponTmpl weapon = WeaponManager.getInstance().getWeapon(event.getPlayer().getItemInHand().getType());
		try {
			if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(weapon instanceof GunWeaponTmpl) { // zoom
					GunObj playerGun = PlayersManager.getInstance().getPlayerGun(event.getPlayer(), (GunWeaponTmpl) weapon);
					playerGun.onZoom();
				}
			}
			else {
				if(weapon instanceof GunWeaponTmpl) { // shot
					GunObj playerGun = PlayersManager.getInstance().getPlayerGun(event.getPlayer(), (GunWeaponTmpl) weapon);
					playerGun.onShot(event.getPlayer().getItemInHand(), event.getPlayer().getInventory().getHeldItemSlot());
				}
				else if(weapon instanceof LaunchWeaponTmpl) {
					LaunchWeaponTmpl launchWeapon = (LaunchWeaponTmpl) weapon;
					launchWeapon.onRightClick(event);
				}
				else if(weapon instanceof UnMenotteTmpl) {
					if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
						if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().split(" : ").length == 2)
							((UnMenotteTmpl) weapon).onRightClick(event.getPlayer());
					}
				}
			}
		} catch(NullPointerException e) {}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getPlayer().getItemInHand() == null) {	return;	}
		
		WeaponTmpl weapon = WeaponManager.getInstance().getWeapon(event.getPlayer().getItemInHand().getType());
		try {
			if(weapon instanceof MenotteTmpl) {
				if(event.getRightClicked() instanceof Player) {
					((MenotteTmpl) weapon).onRightClickPlayer(event.getPlayer(), (Player) event.getRightClicked());
				}
			}
		} catch(NullPointerException e) {}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player || event.getDamager() instanceof Projectile ||
				event.getDamager() instanceof Creature)) {
			return;
		}
		
		try {
			ItemStack inHand = null;
			if(event.getDamager() instanceof Creature) {
				inHand = ((Creature) event.getDamager()).getEquipment().getItemInHand();
			}
			else if(event.getDamager() instanceof Player) {
				inHand = ((Player) event.getDamager()).getItemInHand();
			}
			
			if(event.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) event.getDamager();
				
				GunWeaponTmpl weapon = (GunWeaponTmpl) Utils.getMetadata(projectile, "weapon");
				if(weapon == null) return;
				
				if(weapon instanceof GunWeaponTmpl) {
					GunWeaponTmpl gunWeapon = (GunWeaponTmpl) weapon;
					gunWeapon.onProjectilHit(event);
				}
				return;
			}
			
			WeaponTmpl weapon = WeaponManager.getInstance().getWeapon(inHand.getType());
			
			if(weapon instanceof CacWeaponTmpl && event.getDamager() instanceof Player) {
				CacWeaponTmpl cacWeapon = (CacWeaponTmpl) weapon;
				cacWeapon.onCac(event, (Player) event.getDamager());
			}
		} catch(NullPointerException e) {}
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		try {
			Player player = event.getPlayer();
			PlayersManager.getInstance().resetTime(player);
			
			WeaponTmpl weapon = WeaponManager.getInstance().getWeapon(player.getInventory().getItem(event.getPreviousSlot()).getType());
			if(weapon instanceof GunWeaponTmpl) {
				GunObj playerGun = PlayersManager.getInstance().getPlayerGun(player, (GunWeaponTmpl) weapon);
				if(playerGun.getIsZoom()) playerGun.onZoom();
			}
		}
		catch(NullPointerException e) {}
		catch(Exception e) { e.printStackTrace(); }
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if(event.getEntityType() == EntityType.PRIMED_TNT) {
			event.blockList().clear();
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayersManager.getInstance().resetPlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeaveEntityVehicle(PlayerLeaveEntityVehicleEvent event) {
		if(Utils.getMetadata(event.getPlayer(), "liberate") == null) {
			event.getPlayer().removeMetadata("liberate", PluginMain.getPluginInstance());
			event.setCancelled(true);
		}
	}
}
