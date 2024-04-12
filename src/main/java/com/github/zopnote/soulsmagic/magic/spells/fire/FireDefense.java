package com.github.zopnote.soulsmagic.magic.spells.fire;

import com.github.zopnote.soulsmagic.SoulsMagic;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class FireDefense {
  private int count;
  public FireDefense(Player player, boolean pvp) {
    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(14);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.7f, 0.8f);
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.2f, 0.3f);
    }
    Location areaLocation = player.getLocation().toCenterLocation();
    float radius = 4;
    count = 0;
    new BukkitRunnable() {
      @Override
      public void run() {
        if (count > 17) {
          cancel();
          return;
        }
        count++;
        for (int degree = 0; degree < 360; degree++) {
          double radians = Math.toRadians(degree);
          double x = radius * Math.cos(radians);
          double z = radius * Math.sin(radians);
          {
            Location particleLocation = areaLocation.toLocation(areaLocation.getWorld());
            particleLocation.add(x, 0, z);
            areaLocation.getWorld().spawnParticle(Particle.DRIP_LAVA, particleLocation, 1);
          }
        }
        for (Player areaPlayer : areaLocation.getNearbyPlayers(radius)) {
          if (pvp && (areaPlayer != player)) {
            return;
          }
          areaPlayer.spawnParticle(Particle.LAVA, areaPlayer.getLocation(), 4);
          areaPlayer.setHealth(Math.min(areaPlayer.getHealth() + 6, areaPlayer.getHealthScale()));
        }
      }
    }.runTaskTimer(SoulsMagic.getInstance(), 5, 30);
  }
}
