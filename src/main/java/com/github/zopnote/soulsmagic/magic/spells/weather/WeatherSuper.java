package com.github.zopnote.soulsmagic.magic.spells.weather;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.region.Region;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class WeatherSuper {
  private int degree;
  public WeatherSuper(Player player, boolean pvp) {
    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(20);
    Region region = new Region(player.getLocation(), 16, player, pvp);
    degree = 0;
    new BukkitRunnable() {
      @Override
      public void run() {
        if (degree < 361) {
          degree++;
          double radians = Math.toRadians(degree);
          double x = region.getRadius() * Math.cos(radians);
          double z = region.getRadius() * Math.sin(radians);
          Location particleLocation = region.getCenter().toLocation(region.getCenter().getWorld());
          particleLocation.add(x, 0, z);
          region.getCenter().getWorld().spawnParticle(Particle.DRIP_WATER, particleLocation, 1);
          region.circle(Particle.WATER_SPLASH);
          return;
        }
        Collection<LivingEntity> targetEntities = region.getEntities();
        for (LivingEntity targetEntity : targetEntities) {
          lightningProcess(targetEntity, pvp, player, nearbyPlayers);
        }
        cancel();
      }
    }.runTaskTimer(SoulsMagic.getInstance(), 0, 1);
  }
  private void lightningProcess(LivingEntity targetEntity, boolean pvp, Player player, Collection<Player> nearbyPlayers) {
    if (!pvp && (targetEntity instanceof Player)) {
      return;
    }
    if (targetEntity == player) {
      return;
    }
    for (int x = -4; x < 4; x=5) {
      for (int y = -4; y < 4; y=5) {
        for (int z = -4; z < 4; z+=5) {
          for (Player nearbyPlayer : nearbyPlayers) {
            nearbyPlayer.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, targetEntity.getLocation(), 1, 0, 0, 0);
          }
        }
      }
    }
    targetEntity.getWorld().strikeLightningEffect(targetEntity.getLocation());
    targetEntity.damage(14);
  }
}
