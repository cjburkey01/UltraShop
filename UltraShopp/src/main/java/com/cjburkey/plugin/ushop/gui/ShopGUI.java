package com.cjburkey.plugin.ushop.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.plugin.ushop.Util;
import com.cjburkey.plugin.ushop.shop.ShopItem;
import com.cjburkey.plugin.ushop.shop.ShopUtil;
import net.milkbowl.vault.economy.Economy;

public class ShopGUI {
	
	public static final void show(Player p, Economy econ, String shop, int page) {
		Inventory inv = Bukkit.createInventory(p, 54, Util.color("&2Shop - ") + shop);
		
		List<ShopItem> list = ShopUtil.getItems(shop);
		
		int start = 45 * (page - 1);
		double pages = Math.ceil((double) list.size() / 45d);
		
		Collections.sort(list, new Comparator<ShopItem>() {
			public int compare(ShopItem one, ShopItem two) {
				return one.item.compareTo(two.item);
			}
		});
		
		for(int j = start; j < (45 * (page - 1)) + 45; j ++) {
			if(j < list.size()) {
				ShopItem i = list.get(j);
				ItemStack item = Util.stringToItem(i.item, i.data);
				if(i != null && item != null && item.getType() != Material.AIR) {
					ItemMeta meta = item.getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add(Util.color("&2Buy: " + econ.format(i.buy)));
					lore.add(Util.color("&4Sell: " + econ.format(Util.buyToSell(i.buy))));
					meta.setLore(lore);
					item.setItemMeta(meta);
					inv.addItem(item);
				}
			}
		}
		
		ItemStack back = Util.nameStack(Util.stringToItem("ARROW", 0), "&2Back");
		ItemStack forw = Util.nameStack(Util.stringToItem("ARROW", 0), "&2Next");
		ItemStack exit = Util.nameStack(Util.stringToItem("BARRIER", 0), "&4Exit");
		
		forw = Util.loreStack(forw, "" + (page + 1));
		back = Util.loreStack(back, "" + (page - 1));
		
		if(page > 1) inv.setItem(inv.getSize() - 2, back);
		if(page < pages) inv.setItem(inv.getSize() - 1, forw);
		inv.setItem(inv.getSize() - 9, exit);
		
		p.openInventory(inv);
	}
	
}