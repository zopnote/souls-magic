package com.github.zopnote.soulsmagic.api.item.complex.magic;

import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import eu.smpmc.soul.zopnote.api.item.complex.Complex;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class StaffGravitation extends Staff {
  public StaffGravitation() {
      List<Component> list = new ArrayList<>();
      list.add(
        Component.text("Ein Stab um die Energie")
          .color(TextColor.color(0xB8B8B8))
          .decoration(TextDecoration.ITALIC, false));
      list.add(
        Component.text("eines Elements zu fesseln.")
          .color(TextColor.color(0xB8B8B8))
          .decoration(TextDecoration.ITALIC, false));

        new ComplexItem(Complex.MAGIC_STAFF_GRAVITATION.name(), Material.GOLDEN_SHOVEL,


          Component
          .text("Staff of")
            .color(TextColor.color(0xB8B8B8))
            .decoration(TextDecoration.ITALIC, false)
            .append(Component.text(" Gravitation")
              .color(TextColor.color(0xB06CCB))
              .decoration(TextDecoration.ITALIC, false)),
          list)


          .setCustomModelData(1)
          .generateKey()
          .addFlag(ItemFlag.HIDE_ATTRIBUTES)
          .setAmount(1)
          .addFlag(ItemFlag.HIDE_UNBREAKABLE)
          .addFlag(ItemFlag.HIDE_ENCHANTS)
          .setUnbreakable(true)
          .save();

  }

}
