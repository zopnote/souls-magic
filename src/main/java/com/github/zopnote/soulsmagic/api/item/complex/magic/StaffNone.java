package com.github.zopnote.soulsmagic.api.item.complex.magic;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import eu.smpmc.soul.zopnote.api.item.complex.Complex;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;


public class StaffNone extends Staff {
  private static ComplexItem SN;
  public StaffNone() {
    if (SN == null) {
      List<Component> list = new ArrayList<>();
      SN =
        new ComplexItem(Complex.MAGIC_STAFF_NONE.name(), Material.STONE_SHOVEL,

          Component
            .text("Staff")
            .color(TextColor.color(0xFFFFFF))
            .decoration(TextDecoration.ITALIC, false),
          list)

          .setCustomModelData(1)
          .generateKey()
          .addFlag(ItemFlag.HIDE_ATTRIBUTES)
          .setAmount(1)
          .addFlag(ItemFlag.HIDE_UNBREAKABLE)
          .addFlag(ItemFlag.HIDE_ENCHANTS)
          .setUnbreakable(true);
      SN.save();
    }
  }

  public static ComplexItem get() {
    return SN;
  }

  public void constructRecipe() {
    ItemStack itemStack = get().getItem();
    NamespacedKey key = new NamespacedKey(SoulsMagic.getInstance(), "6");
    ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
    recipe.shape(" LA", " SL", "S  ");
    recipe.setIngredient('S', Material.STICK);
    recipe.setIngredient('A', Material.AMETHYST_SHARD);
    recipe.setIngredient('L', Material.LAPIS_LAZULI);
    Bukkit.addRecipe(recipe);
  }
}
