package com.github.zopnote.soulsmagic.api.item.complex.ingredients;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import com.github.zopnote.soulsmagic.file.MagicFile;
import com.github.zopnote.soulsmagic.magic.spells.MagicType;
import eu.smpmc.soul.zopnote.api.item.complex.Complex;
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

public class PureWeather implements Listener {
  private static ComplexItem PW;
  public PureWeather() {
    if (PW == null) {
      List<Component> list = new ArrayList<>();
      PW =
        new ComplexItem(Complex.PURE_WEATHER.name(), Material.GUNPOWDER,
          Component
            .text("Essence of Weather")
            .color(TextColor.color(0x4C7AD2))
            .decoration(TextDecoration.ITALIC, false),
          list)

          .addFlag(ItemFlag.HIDE_ATTRIBUTES)
          .addFlag(ItemFlag.HIDE_ENCHANTS)
          .addFlag(ItemFlag.HIDE_UNBREAKABLE)
          .setCustomModelData(3)
          .setMaterialData(3);
      PW.save();
    }
  }
  public ComplexItem get() {
    return PW;
  }

  @EventHandler
  public void onEvent(PlayerInteractEvent event) {
    boolean isPureWeather = false;
    if (event.getAction().isRightClick()) {
      if (event.hasItem()) {
        if (event.getItem().getType() == Material.GUNPOWDER) {
          if (event.getItem().hasItemMeta()) {
            if (event.getItem().getItemMeta().hasCustomModelData()) {
              if (event.getItem().getItemMeta().getCustomModelData() == 3) {
                isPureWeather = true;
              }
            }
          }
        }
      }
    }
    if (!isPureWeather) {
      return;
    }
    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.5f, 0.7f);
   MagicFile.set(event.getPlayer().getUniqueId(), MagicType.WEATHER);
    if (event.getItem().getAmount() > 1) {
      event.getItem().setAmount(event.getItem().getAmount()-1);
    }
    else {
      event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemSlot());
    }
  }

  public void constructRecipe() {
    ItemStack itemStack = get().getItem();
    NamespacedKey key = new NamespacedKey(SoulsMagic.getInstance(), "4");
    ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
    recipe.shape("GHG", "GBG", "PPP");
    recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
    recipe.setIngredient('B', Material.BOWL);
    recipe.setIngredient('P', Material.PRISMARINE_CRYSTALS);
    recipe.setIngredient('G', Material.GLOW_INK_SAC);
    Bukkit.addRecipe(recipe);
  }
}
