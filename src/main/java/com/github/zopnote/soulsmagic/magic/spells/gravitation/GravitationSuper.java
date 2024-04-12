package com.github.zopnote.soulsmagic.magic.spells.gravitation;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

public class GravitationSuper {
  private static final Collection<Region> regions = new ArrayList<>();
  private static final Collection<Player> inAttack = new ArrayList<>();
  private float count;
  public void cast(Player player, boolean pvp) {
    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(18);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 0.7f, 0.1f);
    }
    Region region = new Region(player.getLocation(), 14, player, pvp);
    regions.add(region);
    region.circle(Particle.DRIPPING_OBSIDIAN_TEAR);
    count = 0;
    new BukkitRunnable() {
      @Override
      public void run() {
        if (count > 28) {
          regions.remove(region);
          cancel();
          return;
        }
        count++;
        region.circle(Particle.DRIPPING_OBSIDIAN_TEAR);
        Collection<LivingEntity> regionEntities = region.getEntities();
        for (LivingEntity regionEntity : regionEntities) {
          potionProcess(regionEntity, region, pvp);
        }
      }
    }.runTaskTimer(SoulsMagic.getInstance(), 0, 10);
  }
  private void potionProcess(LivingEntity regionEntity, Region region, boolean pvp) {
      if (!(regionEntity instanceof Player regionPlayer)) {
        regionEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72, 1, false, false));
        return;
      }
      if (pvp && regionPlayer != region.getOwner()) {
        regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 72, 1, false, false));
        regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 72, 1, false, false));
        regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 72, 1, false, false));
        return;
      }
      regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 72, 2, false, false));
      regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72, 1, false, false));
      regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 72, 6, false, false));
      regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 72, 2, false, false));
      regionPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 72, 1, false, false));
  }
}
