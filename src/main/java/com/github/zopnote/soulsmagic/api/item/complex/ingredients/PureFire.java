package com.github.zopnote.soulsmagic.api.item.complex.ingredients;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import com.github.zopnote.soulsmagic.file.MagicFile;
import com.github.zopnote.soulsmagic.magic.spells.MagicType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class PureFire implements Listener {
  private static ComplexItem PF;
  public PureFire() {
    if (PF == null) {
      List<Component> list = new ArrayList<>();
      PF =
        new ComplexItem(eu.smpmc.soul.zopnote.api.item.complex.Complex.PURE_FIRE.name(), Material.GUNPOWDER,
          Component
            .text("Essence of Fire")
            .color(TextColor.color(0xDD5828))
            .decoration(TextDecoration.ITALIC, false),
          list)

          .addFlag(ItemFlag.HIDE_ATTRIBUTES)
          .addFlag(ItemFlag.HIDE_ENCHANTS)
          .addFlag(ItemFlag.HIDE_UNBREAKABLE)
          .setCustomModelData(2)
          .setMaterialData(2);
      PF.save();
    }
  }
  public ComplexItem get() {
    return PF;
  }

  @EventHandler
  public void onEvent(PlayerInteractEvent event) {
    boolean isPureFire = false;
    if (event.getAction().isRightClick()) {
      if (event.hasItem()) {
        if (event.getItem().getType() == Material.GUNPOWDER) {
          if (event.getItem().hasItemMeta()) {
            if (event.getItem().getItemMeta().hasCustomModelData()) {
              if (event.getItem().getItemMeta().getCustomModelData() == 2) {
                isPureFire = true;
              }
            }
          }
        }
      }
    }
    if (!isPureFire) {
      return;
    }
    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.5f, 0.7f);
    MagicFile.set(event.getPlayer().getUniqueId(), MagicType.FIRE);
    if (event.getItem().getAmount() > 1) {
      event.getItem().setAmount(event.getItem().getAmount()-1);
    }
    else {
      event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemSlot());
    }
  }
  public void constructRecipe() {
    ItemStack itemStack = get().getItem();
    NamespacedKey key = new NamespacedKey(SoulsMagic.getInstance(), "2");
    ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
    recipe.shape("BTB", "TST", "QTQ");
    recipe.setIngredient('T', Material.GHAST_TEAR);
    recipe.setIngredient('B', Material.BLAZE_POWDER);
    recipe.setIngredient('S', Material.BOWL);
    recipe.setIngredient('Q', Material.QUARTZ);
    Bukkit.addRecipe(recipe);
  }
}
