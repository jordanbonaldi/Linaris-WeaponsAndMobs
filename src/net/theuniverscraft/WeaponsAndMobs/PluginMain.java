package net.theuniverscraft.WeaponsAndMobs;

import net.milkbowl.vault.permission.Permission;
import net.theuniverscraft.WeaponsAndMobs.Listeners.PlayersListener;
import net.theuniverscraft.WeaponsAndMobs.Listeners.WeaponsListener;
import net.theuniverscraft.WeaponsAndMobs.Managers.WeaponManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {
	private Permission m_permission = null;

	private static PluginMain pluginInstance = null;

	public static PluginMain getPluginInstance() {
		return pluginInstance;
	}

	public void onEnable() {
		PluginMain.pluginInstance = this;

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			m_permission = permissionProvider.getProvider();
		}

		WeaponManager.getInstance();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new WeaponsListener(), this);
		pm.registerEvents(new PlayersListener(), this);

		(new MobWeaponTimer()).start();

		Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + this.getName()
						+ ChatColor.WHITE + "] Enabled !");
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + this.getName()
						+ ChatColor.WHITE + "] Disabled !");
	}

	public boolean hasPerm(Player player, String node) {
		return m_permission.has(player, node);
	}
}
