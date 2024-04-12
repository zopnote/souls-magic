package com.github.zopnote.soulsmagic.message;

import com.github.zopnote.soulsmagic.SoulsMagic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;


public class Notification {
  String message;
  public Notification(String message) {
    this.message = message;
  }
  public Component getComponent() {
    return SoulsMagic.getPrefix().append(Component.text(message).color(TextColor.color(0xA6A6A6)));
  }
}
