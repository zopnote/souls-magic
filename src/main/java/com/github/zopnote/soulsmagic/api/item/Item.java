package com.github.zopnote.soulsmagic.api.item;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.api.item.complex.ComplexItem;
import eu.smpmc.soul.zopnote.api.item.complex.Complex;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Item {
  public static ComplexItem get(String identification) {
    if (ComplexItem.SAVED.containsKey(identification)) {
      try {
        return (ComplexItem) ComplexItem.SAVED.get(identification).clone();
      }
      catch (Exception e) {
        Bukkit.getServer().getLogger().info(SoulsMagic.getPrefix()+"ERROR: "+e);
        return null;
      }
    }
    else {
      return null;
    }
  }

  public static boolean give(Player player, String identification, int amount) {
    if (ComplexItem.SAVED.containsKey(identification)) {
      ComplexItem.SAVED.get(identification).setAmount(amount).giveItem(player);
      return true;
    }
    return false;
  }

  public static void drop(Location location, Complex item, int amount) {
    if (ComplexItem.SAVED.containsKey(item)) {
      ComplexItem.SAVED.get(item).setAmount(amount).drop(location);
    }
  }
}
