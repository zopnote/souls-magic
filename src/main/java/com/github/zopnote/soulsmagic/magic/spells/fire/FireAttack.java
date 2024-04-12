package com.github.zopnote.soulsmagic.magic.spells.fire;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;

public class FireAttack {

    public FireAttack(Player player, boolean pvp) {

    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(14);
    for (Player nearbyPlayer : nearbyPlayers) {
      nearbyPlayer.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 0.7f, 0.8f);
    }

    World world = player.getWorld();
    Location position = new Location(world, player.getX(), player.getY() + 1, player.getZ(), player.getYaw(), player.getPitch());
    Vector direction = position.getDirection();

    for (int count = 0; count < 17; count++) {
      position.add(direction.multiply(1.0));
      world.spawnParticle(Particle.DRIP_LAVA, position, 1);
      Collection<LivingEntity> targetEntities = position.getNearbyLivingEntities(1);
      damageProcess(targetEntities, player, pvp, nearbyPlayers);
    }
  }
  private void damageProcess(Collection<LivingEntity> targetEntities, Player player, boolean pvp, Collection<Player> nearbyPlayers) {
    for (LivingEntity targetEntity : targetEntities) {
      if (targetEntity instanceof Player && !pvp) {
        return;
      }
      if (targetEntity == player) {
        return;
      }
      targetEntity.damage(6);
      targetEntity.setFireTicks(6*20);
      for (Player nearbyPlayer : nearbyPlayers) {
        nearbyPlayer.spawnParticle(Particle.LAVA, targetEntity.getLocation(), 5);
      }
    }
  }
}
