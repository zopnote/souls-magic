package com.github.zopnote.soulsmagic.magic;
import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.item.Item;
import com.github.zopnote.soulsmagic.file.MagicFile;
import com.github.zopnote.soulsmagic.magic.spells.MagicType;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import eu.smpmc.soul.zopnote.api.item.complex.Complex;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
enum CastAction {
  Attack,
  Defense,
  Super
}
public class CastSpell implements Listener {
  private static final Collection<UUID> attackCooldown = new ArrayList<>();
  private static final Collection<UUID> defenseCooldown = new ArrayList<>();
  private static final Collection<UUID> superCooldown = new ArrayList<>();
  @EventHandler
  public static void onDrop(PlayerDropItemEvent event) {
    if (isStaff(event.getItemDrop().getItemStack())) {
      event.setCancelled(true);
    }
  }
  @EventHandler
  public static void onHover(PlayerItemHeldEvent event) {
    Player player = event.getPlayer();
    if (player.getInventory().getItem(event.getNewSlot()) != null) {
      if (isStaff(player.getInventory().getItem(event.getNewSlot()))) {
        setActive(event.getNewSlot(), player);
      }
    }

    if (player.getInventory().getItem(event.getPreviousSlot()) != null) {
      if (isStaff(player.getInventory().getItem(event.getPreviousSlot()))) {
        player.getInventory().setItem(event.getPreviousSlot(), Item.get(eu.smpmc.soul.zopnote.api.item.complex.Complex.MAGIC_STAFF_NONE.name()).getItem());
      }
    }
  }
  @EventHandler
  public static void onInteract(PlayerInteractEvent event) {
    if (!event.hasItem()) {
      return;
    }
    if (!isStaff(event.getItem())) {
      return;
    }
    if (event.getAction().isRightClick()) {
      event.setCancelled(true);
    }
    boolean pvp = false;
    Player player = event.getPlayer();
    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    RegionQuery query = container.createQuery();
    ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(event.getPlayer().getLocation()));
    if (!set.testState(null, (StateFlag) WorldGuard.getInstance().getFlagRegistry().get("magic"))) {
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
      return;
    }
    if (!set.testState(null, (StateFlag) WorldGuard.getInstance().getFlagRegistry().get("magic-pvp"))) {
      pvp = true;
    }
    CastAction castAction = null;
    if (event.getAction().isLeftClick()) {
      castAction = CastAction.Attack;
    }
    if (event.getAction().isRightClick()) {
      castAction = CastAction.Defense;
    }
    if (player.isSneaking() && event.getAction().isRightClick()) {
      castAction = CastAction.Super;
    }
    if (castAction == null) {
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
      return;
    }
    if (hasCooldown(player, castAction)) {
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
      return;
    }
    MagicType magicType = MagicFile.get(player.getUniqueId());
    if (magicType == MagicType.NONE) {
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
      return;
    }
    if (castAction == CastAction.Attack) {
      cooldown(player, 2, castAction);
      if (magicType == MagicType.FIRE) {
        Spells.fireAttack(player, pvp);
        return;
      }
      else if (magicType == MagicType.GRAVITATION) {
        Spells.gravitationAttack(player, pvp);
        return;
      }
      else if (magicType == MagicType.WEATHER) {
        Spells.weatherAttack(player, pvp);
        return;
      }
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
    }
    else if (castAction == CastAction.Defense) {
      cooldown(player, 10, castAction);
      if (magicType == MagicType.FIRE) {
        Spells.fireDefense(player, pvp);
        return;
      }
      else if (magicType == MagicType.GRAVITATION) {
        Spells.gravitationDefense(player, pvp);
        return;
      }
      else if (magicType == MagicType.WEATHER) {
        Spells.weatherDefense(player, pvp);
        return;
      }
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
    }
    else if (castAction == CastAction.Super) {
      cooldown(player, 20, castAction);
      if (magicType == MagicType.FIRE) {
        Spells.fireSuper(player, pvp);
        return;
      }
      else if (magicType == MagicType.GRAVITATION) {
        Spells.gravitationSuper(player, pvp);
        return;
      }
      else if (magicType == MagicType.WEATHER) {
        Spells.weatherSuper(player, pvp);
        return;
      }
      player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 0.7f, 0.5f);
    }
  }
  private static boolean hasCooldown(Player player, CastAction cast) {
    HashMap<CastAction, Collection<UUID>> cooldowns = new HashMap<>();
    cooldowns.put(CastAction.Attack, attackCooldown);
    cooldowns.put(CastAction.Defense, defenseCooldown);
    cooldowns.put(CastAction.Super, superCooldown);
    return cooldowns.get(cast).contains(player.getUniqueId());
  }
  private static void cooldown(Player player, long seconds, CastAction cast) {
    HashMap<CastAction, Collection<UUID>> cooldowns = new HashMap<>();
    cooldowns.put(CastAction.Attack, attackCooldown);
    cooldowns.put(CastAction.Defense, defenseCooldown);
    cooldowns.put(CastAction.Super, superCooldown);
    cooldowns.get(cast).add(player.getUniqueId());
    new BukkitRunnable() {
      @Override
      public void run() {
        cooldowns.get(cast).remove(player.getUniqueId());
      }
    }.runTaskLater(SoulsMagic.getInstance(), 20 * seconds);
  }
  private static boolean isStaff(ItemStack itemStack) {
    Collection<Material> material = new ArrayList<>();
    material.add(Material.STONE_SHOVEL);
    material.add(Material.IRON_SHOVEL);
    material.add(Material.GOLDEN_SHOVEL);
    material.add(Material.WOODEN_HOE);
    if (material.contains(itemStack.getType())) {
      if (itemStack.hasItemMeta()) {
        if (itemStack.getItemMeta().hasCustomModelData()) {
          return itemStack.getItemMeta().getCustomModelData() == 1;
        }
      }
    }
    return false;
  }
  private static void setActive(int slot, Player player) {
    MagicType magicType = MagicFile.get(player.getUniqueId());
    HashMap<MagicType, Complex> magicRef = new HashMap<>();
    magicRef.put(MagicType.FIRE, Complex.MAGIC_STAFF_FIRE);
    magicRef.put(MagicType.GRAVITATION, Complex.MAGIC_STAFF_GRAVITATION);
    magicRef.put(MagicType.WEATHER, Complex.MAGIC_STAFF_WEATHER);
    magicRef.put(MagicType.NONE, Complex.MAGIC_STAFF_WEATHER);
    if (!magicRef.containsKey(magicType)) {
      return;
    }
    player.getInventory().setItem(slot, Item.get(magicRef.get(magicType).name()).getItem());
  }
}
