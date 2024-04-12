package com.github.zopnote.soulsmagic.api.item.complex.weapons;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import com.github.zopnote.soulsmagic.api.item.complex.ingredients.SoulsBlood;
import com.github.zopnote.soulsmagic.api.region.Region;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SoulsSword implements Listener {

  private String actionbar = "none";

  private static ComplexItem SS;
  private final static List<Player> SneakEventCooldown = new ArrayList<>();
  private final static List<Player> InteractEventCooldown = new ArrayList<>();
  private final static List<Player> InteractEventCooldownDamage = new ArrayList<>();
  private final static List<Region> Regions = new ArrayList<>();
  private int countSneak;
  private int countClick;


  public SoulsSword() {
    List<Component> list = new ArrayList<>();
SS =
        new ComplexItem("SOUL_SWORD", Material.STONE_SWORD,

          Component
            .text("Souls Sword")
            .color(TextColor.color(0xFFFFFF))
            .decoration(TextDecoration.ITALIC, false)
          , list)

          .setCustomModelData(1)
          .addAttribute(Attribute.GENERIC_MOVEMENT_SPEED, 0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)
          .addAttribute(Attribute.GENERIC_ATTACK_DAMAGE, 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)
          .addFlag(ItemFlag.HIDE_ATTRIBUTES);
SS.save();
  }
  public static ComplexItem get() {
    return SS;
  }
  @EventHandler
  private void onEvent(PlayerToggleSneakEvent event) {
    ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
    Player player = event.getPlayer();
    boolean isSoulSword = false;
    if (item.getType() == Material.STONE_SWORD) {
      if (item.hasItemMeta()) {
        if (item.getItemMeta().hasCustomModelData()) {
          if (item.getItemMeta().getCustomModelData() == 1) {
            isSoulSword = true;
          }
        }
      }
    }
    if (!isSoulSword) {
      return;
    }
      if (SneakEventCooldown.contains(player)) {
        return;
      }
    Vector direction = player.getLocation().getDirection();
    Vector velocity = direction.multiply(2);
    player.setVelocity(velocity);

      SneakEventCooldown.add(player);
      new BukkitRunnable() {
        @Override
        public void run() {
          SneakEventCooldown.remove(player);
        }
      }.runTaskLater(SoulsMagic.getInstance(), 20);

  }
  @EventHandler
  public void onDamage(EntityDamageEvent event) {
    if (event.getEntityType() == EntityType.PLAYER
      && (event.getCause() == EntityDamageEvent.DamageCause.FALL
      || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL)
      && InteractEventCooldownDamage.contains(event.getEntity())) event.setCancelled(true); InteractEventCooldownDamage.remove(event.getEntity());
  }
  @EventHandler
  private void onEvent(PlayerInteractEvent event) {
    if (event.getAction().isRightClick()) {
      ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
      Player player = event.getPlayer();
      boolean isSoulSword = false;
      if (item.getType() == Material.STONE_SWORD) {
        if (item.hasItemMeta()) {
          if (item.getItemMeta().hasCustomModelData()) {
            if (item.getItemMeta().getCustomModelData() == 1) {
              isSoulSword = true;
            }
          }
        }
      }
      if (!isSoulSword) {
        return;
      }
      if (InteractEventCooldown.contains(player)) {
        return;
      }

      Collection<Entity> entities = player.getLocation().getNearbyEntities(40, 40, 40);
      player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.5f, 0.7f);
      for (Entity entity : entities) {
        if (entity instanceof LivingEntity) {
          LivingEntity livingEntity = (LivingEntity) entity;
          livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));
        }
      }

      InteractEventCooldown.add(player);
      new BukkitRunnable() {
        @Override
        public void run() {
          InteractEventCooldown.remove(player);
        }
      }.runTaskLater(SoulsMagic.getInstance(), 260);

    }
  }
  @EventHandler
  private void onEvent(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player player) {
      if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
        if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
          if (player.getInventory().getItemInMainHand().getType() == Material.STONE_SWORD && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1) {
            player.spawnParticle(Particle.SWEEP_ATTACK, event.getEntity().getLocation(), 2);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.7f, 1.3f);
          }
        }
      }
    }
  }
  @EventHandler
  private void onEvent(EntityDeathEvent event) {
    if (event.getEntity() instanceof Warden) {
      Random rand = new Random();
      int randomNumber = rand.nextInt(100);
      if (randomNumber < 50) {
        new SoulsBlood().get().setAmount(1).drop(event.getEntity().getLocation());
      }
    }
  }
  public void constructRecipe() {
      ItemStack itemStack = get().getItem();
    NamespacedKey key = new NamespacedKey(SoulsMagic.getInstance(), "1");
    ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
    RecipeChoice recipeChoice = new RecipeChoice.ExactChoice(new SoulsBlood().get().getItem());
    recipe.shape(" B ", "BIB", " B ");
    recipe.setIngredient('I', Material.NETHERITE_SWORD);
    recipe.setIngredient('B', recipeChoice);
    Bukkit.addRecipe(recipe);
  }
}

