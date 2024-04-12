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


public class StaffFire extends Staff {
  public StaffFire() {

      List<Component> list = new ArrayList<>();


      new ComplexItem(Complex.MAGIC_STAFF_FIRE.name(), Material.IRON_SHOVEL,

        Component
          .text("Staff of")
        .color(TextColor.color(0xB8B8B8))
        .decoration(TextDecoration.ITALIC, false)
        .append(
          Component.text(" Fire")
            .color(TextColor.color(0xDD5828))
            .decoration(TextDecoration.ITALIC, false)
        ), list)

        .setCustomModelData(1)
        .generateKey()
        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
        .addFlag(ItemFlag.HIDE_UNBREAKABLE)
        .addFlag(ItemFlag.HIDE_ENCHANTS)
        .setAmount(1)
        .setUnbreakable(true)
        .save();
  }

}
