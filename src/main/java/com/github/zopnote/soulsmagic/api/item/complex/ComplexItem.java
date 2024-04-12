package com.github.zopnote.soulsmagic.api.item.complex;

import com.github.zopnote.soulsmagic.SoulsMagic;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ComplexItem implements Cloneable {
  public static HashMap<String, ComplexItem> SAVED;
  private final ItemStack ITEM;
  private final String identification;
  public ComplexItem(String identification, Material material, Component DisplayName, List<Component> Lores) {
    ItemStack itemStack = new ItemStack(material);
    this.identification = identification;
    ItemMeta meta = itemStack.getItemMeta();
    meta.displayName(DisplayName);
    meta.lore(Lores);
    itemStack.setItemMeta(meta);
    this.ITEM = itemStack;
  }
  public ItemStack getItem() {
    return this.ITEM;
  }
  public ComplexItem setCustomModelData(int integer) {
    ItemMeta meta = this.ITEM.getItemMeta();
    meta.setCustomModelData(integer);
    this.ITEM.setItemMeta(meta);
    return this;
  }
  public ComplexItem setMaterialData(int integer) {
    this.ITEM.setData(new MaterialData(ITEM.getType(), (byte) 1));
    return this;
  }
  public MaterialData getMaterialData() {
    return this.ITEM.getData();
  }
  public void giveItem(Player player) {
    player.getInventory().addItem(this.ITEM);
  }
  public ComplexItem addAttribute(Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot slot) {
    ItemMeta meta = this.ITEM.getItemMeta();
    meta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.toString(), amount, operation, slot));
    this.ITEM.setItemMeta(meta);
    return this;
  }
  public ComplexItem setAmount(int amount) {
    this.ITEM.setAmount(amount);
    return this;
  }
  public ComplexItem addFlag(ItemFlag flag) {
    ItemMeta meta = this.ITEM.getItemMeta();
    meta.addItemFlags(flag);
    this.ITEM.setItemMeta(meta);
    return this;
  }
  public void drop(Location location) {
    location.getWorld().dropItem(location, this.ITEM);
  }
  public ComplexItem setUnbreakable(boolean unbreakable) {
    ItemMeta meta = this.ITEM.getItemMeta();
    meta.setUnbreakable(unbreakable);
    this.ITEM.setItemMeta(meta);
    return this;
  }
  public ComplexItem addEnchantment(Enchantment enchantment, int level) {
    this.ITEM.addUnsafeEnchantment(enchantment, level);
    return this;
  }
  public ComplexItem setKey(NamespacedKey key) {
    ItemMeta meta = this.ITEM.getItemMeta();
    meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
    this.ITEM.setItemMeta(meta);
    return this;
  }

  public ComplexItem generateKey() {
    ItemMeta meta = this.ITEM.getItemMeta();
    meta.getPersistentDataContainer().set(new NamespacedKey(SoulsMagic.getInstance(), identification), PersistentDataType.DOUBLE, Math.PI);
    this.ITEM.setItemMeta(meta);
    return this;
  }
  public static NamespacedKey createKey(String identification) {
    return new NamespacedKey(SoulsMagic.getInstance(), identification);
  }

  public static boolean hasKey(ItemStack itemStack, NamespacedKey key) {
    if (itemStack == null) {
      return false;
    }
    ItemMeta itemMeta = itemStack.getItemMeta();
    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
    return container.has(key, PersistentDataType.DOUBLE);
  }
  public void save() {
    if (SAVED == null) {
      SAVED = new HashMap<>();
    }
    SAVED.put(identification, this);
  }

    @Override
    public ComplexItem clone() {
        try {
            return (ComplexItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
