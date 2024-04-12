package com.github.zopnote.soulsmagic;

import com.github.zopnote.soulsmagic.api.item.complex.ingredients.PureFire;
import com.github.zopnote.soulsmagic.api.item.complex.ingredients.PureGravitation;
import com.github.zopnote.soulsmagic.api.item.complex.ingredients.PureWeather;
import com.github.zopnote.soulsmagic.api.item.complex.ingredients.SoulsBlood;
import com.github.zopnote.soulsmagic.api.item.complex.magic.StaffFire;
import com.github.zopnote.soulsmagic.api.item.complex.magic.StaffGravitation;
import com.github.zopnote.soulsmagic.api.item.complex.magic.StaffNone;
import com.github.zopnote.soulsmagic.api.item.complex.magic.StaffWeather;
import com.github.zopnote.soulsmagic.api.item.complex.weapons.SoulsSword;
import com.github.zopnote.soulsmagic.magic.CastSpell;
import com.github.zopnote.soulsmagic.magic.spells.weather.WeatherDefense;
import com.github.zopnote.soulsmagic.message.Notification;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoulsMagic extends JavaPlugin {
  public static StateFlag magic;
  public static StateFlag magicPvp;
  //•
  static TextComponent prefix = Component.text("Souls").color(TextColor.color(176, 129, 225))
    .append(Component.text("Magic").color(TextColor.color(TextColor.color(160, 129, 225)))
      .append(Component.text(" --> ").color(TextColor.color(TextColor.color(123, 123, 123))))
    );
  private static SoulsMagic instance;

  @Override
  public void onEnable() {
    getServer().sendMessage(new Notification("Register events, flags and items.").getComponent());
    instance = this;
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new CastSpell(), this);
    pluginManager.registerEvents(new WeatherDefense(), this);
    pluginManager.registerEvents(new SoulsSword(), this);
    registerWG();
    registerItems();
    getServer().sendMessage(new Notification("Done, plugin started successfully.").getComponent());
    super.onEnable();
  }

  public static SoulsMagic getInstance() {
    return instance;
  }

  public static TextComponent getPrefix() {
    return prefix;
  }
  private static void registerItems() {
    new SoulsSword().constructRecipe();
    new PureGravitation().constructRecipe();
    new PureFire().constructRecipe();
    new PureWeather().constructRecipe();
    new SoulsBlood();
    new StaffNone().constructRecipe();
    new StaffFire();
    new StaffGravitation();
    new StaffWeather();
  }
  private static void registerWG() {
    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
    try {
      StateFlag flag = new StateFlag("magic", false);
      registry.register(flag);
      magic = flag;
    } catch (FlagConflictException e) {
      Flag<?> existing = registry.get("magic");
      if (existing instanceof StateFlag) {
        magic = (StateFlag) existing;
      }
    }
    try {
      StateFlag flag = new StateFlag("magic-pvp", false);
      registry.register(flag);
      magicPvp = flag;
    } catch (FlagConflictException e) {
      Flag<?> existing = registry.get("magic-pvp");
      if (existing instanceof StateFlag) {
        magicPvp = (StateFlag) existing;
      }
    }
  }
}
