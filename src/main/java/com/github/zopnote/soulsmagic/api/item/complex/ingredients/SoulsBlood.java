package com.github.zopnote.soulsmagic.api.item.complex.ingredients;

import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import eu.smpmc.soul.zopnote.api.item.complex.Complex;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class SoulsBlood {
    private static ComplexItem SB;
    public SoulsBlood() {
        if (SB == null) {
          List<Component> list = new ArrayList<>();
            SB =
                    new ComplexItem(Complex.SOUL_BLOOD.name(), Material.GUNPOWDER,
                      Component
                        .text("Souls blood")
                        .color(TextColor.color(0xFFFFFF))
                      .decoration(TextDecoration.ITALIC, false),
                      list)

                            .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .addFlag(ItemFlag.HIDE_ENCHANTS)
                            .addFlag(ItemFlag.HIDE_UNBREAKABLE)
                            .setCustomModelData(1)
                            .setMaterialData(1);
            SB.save();
        }
    }
  public ComplexItem get() {
    return SB;
  }
}
