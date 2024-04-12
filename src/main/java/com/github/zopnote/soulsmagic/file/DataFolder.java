package com.github.zopnote.soulsmagic.file;

import com.github.zopnote.soulsmagic.SoulsMagic;

import java.io.File;

public interface DataFolder {
  static void create() {
    if (!exists()) {
      new File(SoulsMagic.getInstance().getDataFolder(), "/data/").mkdirs();
    }
  }
  static boolean exists() {
    return new File(SoulsMagic.getInstance().getDataFolder(), "/data/").exists();
  }
  static File get() {
    return new File(SoulsMagic.getInstance().getDataFolder(), "/data/");
  }

}
