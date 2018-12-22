package net.theuniverscraft.WeaponsAndMobs.Utils;

import net.theuniverscraft.WeaponsAndMobs.PluginMain;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

public class Utils {
	public static long getPlayerTimeToRecharge() {
		return 2500L;
	}
	
	public static long msToTick(Long ms) {
		return ms*20L / 1000L;
	}
	
	public static Object getMetadata(Metadatable meta, String key) {
		if(!meta.hasMetadata(key)) return null;
		
		for(MetadataValue value : meta.getMetadata(key)) {
			if(value.getOwningPlugin().getName().equalsIgnoreCase(PluginMain.getPluginInstance().getName())) {
				return value.value();
			}
		}
		return null;
	}
	
	public static void setMetadata(Metadatable meta, String key, Object obj) {
		meta.setMetadata(key, new FixedMetadataValue(PluginMain.getPluginInstance(), obj));
	}
	
	private Utils() {}
}
