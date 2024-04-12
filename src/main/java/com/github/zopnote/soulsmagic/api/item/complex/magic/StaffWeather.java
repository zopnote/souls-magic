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


public class StaffWeather extends Staff {
  public StaffWeather() {
      List<Component> list = new ArrayList<>();

        new ComplexItem(Complex.MAGIC_STAFF_WEATHER.name(), Material.WOODEN_HOE,

          Component
          .text("Staff of")
            .color(TextColor.color(0xB8B8B8))
            .decoration(TextDecoration.ITALIC, false)
            .append(Component.text(" Weather")
              .color(TextColor.color(0x4C7AD2))
              .decoration(TextDecoration.ITALIC, false)),
          list)

          .setCustomModelData(1)
          .generateKey()
          .addFlag(ItemFlag.HIDE_ATTRIBUTES)
          .setAmount(1)
          .addFlag(ItemFlag.HIDE_UNBREAKABLE)
          .addFlag(ItemFlag.HIDE_ENCHANTS)
          .setUnbreakable(true).save();


  }

}
