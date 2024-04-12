package com.github.zopnote.soulsmagic.magic.spells.weather;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.region.Region;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

public class WeatherDefense implements Listener {
  private int count;
  private static final Collection<Region> regions = new ArrayList<>();
  public void cast(Player player, boolean pvp) {
    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(14);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.7f, 0.8f);
    }
    Region region = new Region(player.getLocation(), 6, player, pvp);
    region.circle(Particle.DRIP_WATER);
    regions.add(region);
    count = 0;
    new BukkitRunnable() {
      @Override
      public void run() {
        if (count > 9) {
          regions.remove(region);
          cancel();
          return;
        }
        region.circle(Particle.DRIP_WATER);
        count++;
      }
    }.runTaskTimer(SoulsMagic.getInstance(), 0, 20);
  }

  @EventHandler
  public void OnDamage(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Player player)) {
      return;
    }
    for (Region region : regions) {
      if (!region.containsEntity(player)) {
        return;
      }
      if (region.getPvP() && player != region.getOwner()) {
        return;
      }
      event.setDamage(event.getDamage()*0.3333);
      Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(14);
      for (Player nearbyPlayer : nearbyPlayers) {
        nearbyPlayer.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation(), 4);
      }
    }
  }
}
