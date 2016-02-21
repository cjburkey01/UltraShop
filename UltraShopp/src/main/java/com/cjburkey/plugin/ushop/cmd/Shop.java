package com.cjburkey.plugin.ushop.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.plugin.ushop.UltraShop;
import com.cjburkey.plugin.ushop.Util;
import com.cjburkey.plugin.ushop.gui.ShopGUI;
import com.cjburkey.plugin.ushop.shop.ShopUtil;

public class Shop implements CommandExecutor {
	
	public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
		go(s, args);
		return true;
	}
	
	private static final void go(CommandSender s, String[] args) {
		if(args.length < 2) {
			if(args.length >= 1) {
				Util.log("1");
				Util.log(args[0]);
				if(args[0].equals("list")) {
					Util.log("2");
					if(s.hasPermission("ushop.user")) {
						Util.log("3");
						s.sendMessage(Util.color("&2Shops:"));
						for(String shop : ShopUtil.shopList()) {
							s.sendMessage(Util.color("&2 - " + shop));
						}
						return;
					}
					perm(s);
					return;
				}
				help(s);
				return;
			}
			help(s);
			return;
		}
		
		if(!(s instanceof Player)) return;
		
		Player p = (Player) s;
		
		if(args[0].equals("open")) {
			if(s.hasPermission("ushop.user")) {
				if(ShopUtil.existsShop(args[1])) { ShopGUI.show(p, UltraShop.getEcon(), args[1], 1); } else { s.sendMessage(Util.color("&4Shop not found.")); return; }
				return;
			}
			perm(s);
			return;
		} else if(args[0].equals("create")) {
			if(s.hasPermission("ushop.admin")) {
				ShopUtil.createShop(args[1]);
				p.sendMessage(Util.color("&2Shop created!"));
				return;
			}
			perm(s);
			return;
		} else if(args[0].equals("delete")) {
			if(s.hasPermission("ushop.admin")) {
				ShopUtil.deleteShop(args[1]);
				p.sendMessage(Util.color("&4Shop deleted!"));
				return;
			}
			perm(s);
			return;
		} else if(args[0].equals("addto")) {
			if(args.length >= 3) {
				if(s.hasPermission("ushop.admin")) {
					try {
						if(ShopUtil.existsShop(args[1])) {
							ItemStack i = p.getItemInHand();
							if(i == null) {
								help(s);
								return;
							}
							ShopUtil.addItemToShop(args[1], i.getType().name() + ":" + i.getDurability(), Double.parseDouble(args[2]));
							p.sendMessage(Util.color("&2Item added!"));
							return;
						} else {
							s.sendMessage(Util.color("&4Shop doesn't exist!"));
							return;
						}
					} catch(Exception e) {  }
					help(s);
					return;
				}
				perm(s);
				return;
			}
			help(s);
			return;
		} else if(args[0].equals("removefrom")) {
			if(args.length >= 2) {
				if(s.hasPermission("ushop.admin")) {
					if(ShopUtil.existsShop(args[1])) {
						ItemStack i = p.getItemInHand();
						if(i == null) {
							help(s);
							return;
						}
						ShopUtil.removeItemFromShop(args[1], i.getType().name() + ":" + i.getDurability());
						p.sendMessage(Util.color("&4Item removed!"));
						return;
					} else {
						s.sendMessage(Util.color("&4Shop doesn't exist!"));
						return;
					}
				}
				perm(s);
				return;
			}
			help(s);
			return;
		}
	}
	
	private static final void help(CommandSender s) {
		s.sendMessage(Util.color("&2UltraShop:"));
		s.sendMessage(Util.color("&2&l/shop list &r- &2List all shops."));
		s.sendMessage(Util.color("&2&l/shop open <shop> &r- &2Open <shop>."));
		s.sendMessage(Util.color("&2&l/shop create <shop> &r- &2Create <shop>."));
		s.sendMessage(Util.color("&2&l/shop delete <shop> &r- &2Delete <shop>."));
		s.sendMessage(Util.color("&2&l/shop addto <shop> <price> &r- &2Add held item to <shop> to buy for <price>."));
		s.sendMessage(Util.color("&2&l/shop removefrom <shop> &r- &2Remove held item from <shop>."));
	}
	
	private static final void perm(CommandSender s) {
		s.sendMessage(Util.color("&4You don't have permission to do that."));
	}
	
}