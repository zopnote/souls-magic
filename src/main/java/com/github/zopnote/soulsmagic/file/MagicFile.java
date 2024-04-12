package com.github.zopnote.soulsmagic.file;

import com.github.zopnote.soulsmagic.SoulsMagic;
import com.github.zopnote.soulsmagic.magic.spells.MagicType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface MagicFile {

  File configFile = new File(SoulsMagic.getInstance().getDataFolder(), "/data/magic.yml");
  static void createCustomConfig() {
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    if (!configFile.exists()) {
      try {
        configFile.createNewFile();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  static void set(UUID playerUuid, MagicType magic){
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    config.set("uuid-magic." + playerUuid.toString(),String.valueOf(magic));
    try {
      config.save(configFile);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  static MagicType get(UUID playerUuid) {
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    if (config.getString("uuid-magic." + playerUuid.toString()) == null) {
      set(playerUuid, MagicType.NONE);
      return MagicType.NONE;
    }
    else {
      if (
        config.getString("uuid-magic." + playerUuid).equals("NONE") ||
          config.getString("uuid-magic." + playerUuid).equals("FIRE") ||
          config.getString("uuid-magic." + playerUuid).equals("REALITY") ||
          config.getString("uuid-magic." + playerUuid).equals("WEATHER") ||
          config.getString("uuid-magic." + playerUuid).equals("GRAVITATION")) {
        return MagicType.valueOf(config.getString("uuid-magic." + playerUuid.toString()));
      }
      else {
        set(playerUuid, MagicType.NONE);
        return MagicType.NONE;
      }
    }
  }
}
