package com.github.zopnote.soulsmagic.magic.spells.fire;

import com.github.zopnote.soulsmagic.SoulsMagic;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class FireSuper {
  private float count;
  public FireSuper(Player player, boolean pvp) {
    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(20);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1.0f, 0.1f);
      nearbyPlayer.spawnParticle(Particle.LAVA, player.getLocation(), 20);
    }
    Location areaLocation = player.getLocation();
    double radius = 1.5;
    for (int degree = 0; degree < 360; degree+=4) {
      double radians = Math.toRadians(degree);
      double x = radius * Math.cos(radians);
      double z = radius * Math.sin(radians);
      {
        Location particleLocation = areaLocation.toLocation(areaLocation.getWorld());
        particleLocation.add(x, 0, z);
        areaLocation.getWorld().spawnParticle(Particle.DRIP_LAVA, particleLocation, 2);
      }
    }
    player.addPotionEffect(
      new PotionEffect(
        PotionEffectType.LEVITATION,
        20 * 4,
        2,
        false,
        false,
        false)
    );
    count = 0;
    new BukkitRunnable() {
      @Override
      public void run() {
        count++;
        for (Player nearbyPlayer : nearbyPlayers) {
          nearbyPlayer.spawnParticle(Particle.DRIP_LAVA, player.getLocation(), 1);
          nearbyPlayer.playSound(player.getLocation(), Sound.BLOCK_STONE_STEP, (count/16), 0.5f);
        }
        if (count > 15) {
          player.removePotionEffect(PotionEffectType.LEVITATION);
          player.addPotionEffect(
            new PotionEffect(
              PotionEffectType.SLOW_FALLING,
              20 * 3,
              1,
              false,
              false,
              false)
          );
          for (Player nearbyPlayer : nearbyPlayers) {
            nearbyPlayer.spawnParticle(Particle.LAVA, player.getLocation(), 5);
            nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1.0f, 0.4f);
            nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7f, 0.5f);
          }
          for (LivingEntity targetEntity : areaLocation.getNearbyLivingEntities(15,15,15)) {
            if (targetEntity instanceof Player targetPlayer && targetPlayer != player && pvp) {
              cancel();
              return;
            }
            targetEntity.damage(11);
            targetEntity.setFireTicks(20 * 3);
          }
          cancel();
        }
      }
    }.runTaskTimer(SoulsMagic.getInstance(), 0, 5);
  }
}
