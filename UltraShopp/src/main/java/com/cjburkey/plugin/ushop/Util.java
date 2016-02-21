package com.cjburkey.plugin.ushop;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.plugin.ushop.shop.ShopItem;

public class Util {
	
	public static final String color(String msg) {
		if(msg == null) { return "NULL"; }
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static final void log(Object msg) {
		Bukkit.getServer().getConsoleSender().sendMessage("[" + UltraShop.getPlugin().getName() + "] " + color("" + msg));
	}
	
	public static final ItemStack stringToStack(String item) {
		return new ItemStack(Material.getMaterial(item));
	}
	
	public static final ItemStack nameStack(ItemStack stack, String name) {
		ItemMeta data = stack.getItemMeta();
		data.setDisplayName(Util.color(name));
		stack.setItemMeta(data);
		return stack;
	}
	
	public static final int getInventorySize(int max) {
		if (max <= 0) return 9;
		int quotient = (int) Math.ceil(max / 9.0);
		return quotient > 5 ? 54: quotient * 9;
	}
	
	public static final Player uuidToPlayer(UUID uuid) {
		return UltraShop.getPlugin().getServer().getPlayer(uuid);
	}
	
	public static final String getCFString(String s) {
		return color(UltraShop.getPlugin().getConfig().getString(s));
	}
	
	public static final int getCFInt(String s) {
		return UltraShop.getPlugin().getConfig().getInt(s);
	}
	
	public static final ItemStack loreStack(ItemStack s, String lore) {
		ItemMeta meta = s.getItemMeta();
		List<String> loree = new ArrayList<String>();
		loree.add(lore);
		meta.setLore(loree);
		s.setItemMeta(meta);
		return s;
	}
	
	public static final String format(double i) {
		String better = UltraShop.getEcon().format(i).replaceAll("[^\\d.]", "");
		return "$" + NumberFormat.getInstance().format(Double.parseDouble(better));
	}
	
	public static final ItemStack stringToItem(String item, int data) {
		ItemStack s = new ItemStack(Material.getMaterial(item));
		s.setDurability((short) data);
		return s;
	}

	public static final double buyToSell(double buy) {
		return buy / 2.5d;
	}
	
	public static final ItemStack stackFromShop(ShopItem s) {
		ItemStack i = stringToStack(s.item);
		i.setDurability((short) s.data);
		return i.clone();
	}
	
	public static final ItemStack copy(ItemStack old) {
		return new ItemStack(old.getType(), old.getAmount(), (short) old.getDurability());
	}
	
}