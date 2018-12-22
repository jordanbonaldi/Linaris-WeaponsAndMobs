package net.theuniverscraft.WeaponsAndMobs.Utils;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;

public class WeaponSound {
	List<FinalSound> m_sounds = new LinkedList<FinalSound>();
	
	public WeaponSound(FinalSound... sounds) {
		for(FinalSound sound : sounds) {
			m_sounds.add(sound);
		}
	}
	
	public void playSounds(Location loc) {
		for(FinalSound sound : m_sounds) {
			sound.playSound(loc);
		}
	}
	
	public static class FinalSound {
		Sound m_sound;
		Long m_volume;
		Long m_pitch;
		
		public FinalSound(Sound sound, Long volume, Long pitch) {
			m_sound = sound;
			m_volume = volume;
			m_pitch = pitch;
		}
		
		public void playSound(Location loc) {
			loc.getWorld().playSound(loc, m_sound, m_volume, m_pitch);
		}
	}
}
