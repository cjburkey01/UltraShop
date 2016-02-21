package com.cjburkey.plugin.ushop.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.ushop.PlayerInter;
import com.cjburkey.plugin.ushop.UltraShop;
import com.cjburkey.plugin.ushop.Util;
import com.cjburkey.plugin.ushop.shop.ShopItem;
import com.cjburkey.plugin.ushop.shop.ShopUtil;

public class ClickEvent implements Listener {
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		e.setCancelled(true);
		
		if(!e.getInventory().getTitle().contains(Util.color("&2Shop - "))) return;
		Player player = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		String shop = e.getInventory().getTitle().replaceAll(Util.color("&2Shop - "), "");
		String i = item.getType().name() + ":" + item.getDurability();
		if(item != null) {
			for(ShopItem it : ShopUtil.getItems(shop)) {
				if(it.item.equals(item.getType().name())) {
					double buy = ShopUtil.getItemFromShop(shop, i);
					double sell = Util.buyToSell(buy);
					int amount = (e.isShiftClick()) ? 16 : 1;
					buy *= amount;
					sell *= amount;
					ItemStack stack = Util.copy(item);
					stack.setAmount(amount);
					if(e.isLeftClick()) {
						PlayerInter.buy(player, buy, stack, shop);
					} else if(e.isRightClick()) {
						PlayerInter.sell(player, sell, stack, shop, buy);
					}
					ShopGUI.show(player, UltraShop.getEcon(), shop, 1);
					return;
				}
			}
			if(item.getType().equals(Material.BARRIER)) {
				player.closeInventory();
			}
		}
	}
	
}