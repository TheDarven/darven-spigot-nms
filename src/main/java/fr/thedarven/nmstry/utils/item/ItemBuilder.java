package fr.thedarven.nmstry.utils.item;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.itemMeta = this.item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, byte durability) {
        this.item = new ItemStack(material, amount, durability);
        this.itemMeta = this.item.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack;
        this.itemMeta = this.item.getItemMeta();
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public ItemBuilder setMaterial(Material material) {
        this.item.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        this.item.setDurability(durability);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        return setDurability(Short.MAX_VALUE);
    }

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.itemMeta.setLore(lore);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(new ArrayList<>(Arrays.asList(lore)));
    }

    public ItemBuilder addLoreLines(int index, List<String> lines) {
        List<String> lore = this.itemMeta.hasLore() ? this.itemMeta.getLore() : new ArrayList<>();
        while (lore.size() < index) {
            lore.add("");
        }
        lore.addAll(index, lines);
        setLore(lore);
        return this;
    }

    public ItemBuilder addLoreLines(List<String> lines) {
        return addLoreLines(0, lines);
    }

    public ItemBuilder addLoreLines(int index, String... lines) {
        return addLoreLines(index, new ArrayList<>(Arrays.asList(lines)));
    }

    public ItemBuilder addLoreLines(String... lines) {
        return addLoreLines(0, lines);
    }

    public ItemBuilder removeLoreLines(List<String> lines) {
        if (this.itemMeta.hasLore()) {
            List<String> lore = this.itemMeta.getLore();
            for (String line: lines) {
                lore.remove(line);
            }
            setLore(lore);
        }
        return this;
    }

    public ItemBuilder removeLoreLines(String... lines) {
        return removeLoreLines(new ArrayList<>(Arrays.asList(lines)));
    }

    public ItemBuilder removeLoreLines(int index) {
        if (this.itemMeta.hasLore() && this.itemMeta.getLore().size() <= index) {
            List<String> lore = this.itemMeta.getLore();
            lore.remove(index);
            setLore(lore);
        }
        return this;
    }

    public ItemBuilder resetLore() {
        return setLore(new ArrayList<>());
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
        this.item.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        this.item.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder removeEnchantments(List<Enchantment> enchantments) {
        enchantments.forEach(this.item::removeEnchantment);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment... enchantments) {
        return removeEnchantments(new ArrayList<>(Arrays.asList(enchantments)));
    }

    public ItemBuilder setSkullOwner(String owner) {
        if (this.itemMeta instanceof SkullMeta) {
            ((SkullMeta) this.itemMeta).setOwner(owner);
            this.item.setItemMeta(this.itemMeta);
        }
        return this;
    }

    public ItemBuilder addItemFlags(List<ItemFlag> itemFlags) {
        itemFlags.forEach(this.itemMeta::addItemFlags);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        this.item.setItemMeta(itemMeta);
        return this;
    }


    public ItemBuilder setUnbreakable() {
        this.itemMeta.spigot().setUnbreakable(true);
        this.item.setItemMeta(this.itemMeta);
        return this;
    }

    public ItemBuilder setDyeColor(DyeColor dyeColor) {
        return setDurability(dyeColor.getData());
    }

    public ItemBuilder setWoolColor(DyeColor dyeColor) {
        if (this.item.getType() != Material.WOOL) {
            return this;
        }
        return setDurability(dyeColor.getData());
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        if (this.itemMeta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemMeta;
            leatherArmorMeta.setColor(color);
            this.item.setItemMeta(leatherArmorMeta);
        }
        return this;
    }

    public ItemBuilder clone() {
        return new ItemBuilder(this.item);
    }

}
