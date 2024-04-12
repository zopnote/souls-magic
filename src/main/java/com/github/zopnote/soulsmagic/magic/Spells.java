package com.github.zopnote.soulsmagic.magic;

import com.github.zopnote.soulsmagic.magic.spells.fire.FireAttack;
import com.github.zopnote.soulsmagic.magic.spells.fire.FireDefense;
import com.github.zopnote.soulsmagic.magic.spells.fire.FireSuper;
import com.github.zopnote.soulsmagic.magic.spells.gravitation.GravitationAttack;
import com.github.zopnote.soulsmagic.magic.spells.gravitation.GravitationDefense;
import com.github.zopnote.soulsmagic.magic.spells.gravitation.GravitationSuper;
import com.github.zopnote.soulsmagic.magic.spells.weather.WeatherAttack;
import com.github.zopnote.soulsmagic.magic.spells.weather.WeatherDefense;
import com.github.zopnote.soulsmagic.magic.spells.weather.WeatherSuper;
import org.bukkit.entity.Player;

public interface Spells {
  static void fireAttack(Player player, boolean pvp) {
    new FireAttack(player, pvp);
  }
  static void fireDefense(Player player, boolean pvp) {
    new FireDefense(player, pvp);
  }
  static void fireSuper(Player player, boolean pvp) {
    new FireSuper(player, pvp);
  }
  static void gravitationAttack(Player player, boolean pvp) {
    new GravitationAttack(player, pvp);
  }
  static void gravitationDefense(Player player, boolean pvp) {
    new GravitationDefense(player, pvp);
  }
  static void gravitationSuper(Player player, boolean pvp) {
    new GravitationSuper().cast(player, pvp);
  }
  static void weatherAttack(Player player, boolean pvp) {
    new WeatherAttack(player, pvp);
  }
  static void weatherDefense(Player player, boolean pvp) {
    new WeatherDefense().cast(player, pvp);
  }
  static void weatherSuper(Player player, boolean pvp) {
    new WeatherSuper(player, pvp);
  }
}
