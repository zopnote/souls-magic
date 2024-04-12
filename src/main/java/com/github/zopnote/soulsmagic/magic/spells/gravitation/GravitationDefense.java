package com.github.zopnote.soulsmagic.magic.spells.gravitation;

import com.github.zopnote.soulsmagic.api.region.Region;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class GravitationDefense {
  public GravitationDefense(Player player, boolean pvp) {
    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(14);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.7f, 0.8f);
      nearbyPlayer.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 0.6f, 0.1f);
    }
    Region region = new Region(player.getLocation(), 5, player, pvp);
    region.circle(Particle.DRIPPING_OBSIDIAN_TEAR);
    for (LivingEntity regionEntity : region.getEntities()) {
      damageProcess(regionEntity, player, pvp, nearbyPlayers);
    }
  }
  private void damageProcess(LivingEntity regionEntity, Player player, boolean pvp, Collection<Player> nearbyPlayers) {
    if ((!pvp && regionEntity instanceof Player) || regionEntity == player) {
      return;
    }
    regionEntity.addPotionEffect(
      new PotionEffect(
        PotionEffectType.LEVITATION,
        20 * 4,
        2,
        false,
        false,
        false)
    );
    regionEntity.damage(4);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(regionEntity.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 0.7f, 0.2f);
    }
  }
}
