package com.cjburkey.plugin.ushop;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.ushop.shop.ShopUtil;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerInter {
	
	public static final boolean take(UUID player, double amount) {
		Player p = UltraShop.getPlugin().getServer().getPlayer(player);
		EconomyResponse e = UltraShop.getEcon().withdrawPlayer(p, amount);
		return e.transactionSuccess();
	}
	
	public static final boolean give(UUID player, double amount) {
		Player p = UltraShop.getPlugin().getServer().getPlayer(player);
		EconomyResponse e = UltraShop.getEcon().depositPlayer(p, amount);
		return e.transactionSuccess();
	}
	
	public static final void giveItem(Player player, ItemStack i) {
		player.getInventory().addItem(i);
	}
	
	public static final boolean takeItem(Player p, ItemStack it) {
		if(p.getInventory().containsAtLeast(it, it.getAmount())) {
			p.getInventory().removeItem(it);
			return true;
		}
		return false;
	}
	
	public static final double getPercent() {
		return UltraShop.getPlugin().getConfig().getDouble("PercentPrice");
	}
	
	public static final void buy(Player player, double buymoney, ItemStack stack, String shop, int amount) {
		double buy = ShopUtil.getItemFromShop(shop, stack.getType().name() + ":" + stack.getDurability());
		if(PlayerInter.take(player.getUniqueId(), buymoney)) {
			stack.setAmount(stack.getAmount());
			PlayerInter.giveItem(player, Util.copy(stack));
			double percent = 1 + (amount * getPercent());
			buy *= percent;
			ShopUtil.addItemToShop(shop, stack.getType().name() + ":" + stack.getDurability(), buy);
			return;
		}
	}
	
	public static final void sell(Player player, double sellmoney, ItemStack stack, String shop, int amount) {
		double buy = ShopUtil.getItemFromShop(shop, stack.getType().name() + ":" + stack.getDurability());
		if(PlayerInter.takeItem(player, Util.copy(stack))) {
			stack.setAmount(stack.getAmount() - 1);
			PlayerInter.give(player.getUniqueId(), sellmoney);
			double percent = (1 - (amount * getPercent()));
			Util.log(percent + " = 1 - (" + amount + " * " + getPercent() + "));");
			buy *= percent;
			ShopUtil.addItemToShop(shop, stack.getType().name() + ":" + stack.getDurability(), buy);
			return;
		}
	}
	
}