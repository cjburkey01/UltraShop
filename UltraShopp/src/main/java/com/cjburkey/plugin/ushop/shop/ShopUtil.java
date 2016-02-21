package com.cjburkey.plugin.ushop.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import com.cjburkey.plugin.ushop.io.IO;
import com.cjburkey.plugin.ushop.prop.Prop;

public class ShopUtil {
	
	public static final boolean createShop(String name) {
		Prop.writeMap(fromName(name), new HashMap<Object, Object>());
		File f = fromName(name);
		if(f.exists()) return false;
		try {
			return f.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static final boolean deleteShop(String name) {
		File f = fromName(name);
		if(f.exists())
			return f.delete();
		else
			return false;
	}
	
	public static final boolean existsShop(String name) {
		File f = fromName(name);
		return f.exists();
	}
	
	public static final ArrayList<String> shopList() {
		ArrayList<String> shops = new ArrayList<String>();
		for(File f : IO.getDataDir().listFiles()) {
			if(f.getName().endsWith(".shop")) {
				shops.add(fromFile(f));
			}
		}
		return shops;
	}
	
	public static final void addItemToShop(String shop, String item, double price) {
		Prop.set(fromName(shop), item, price);
	}
	
	public static final void removeItemFromShop(String shop, String item) {
		Prop.remove(fromName(shop), item);
	}
	
	public static final double getItemFromShop(String shop, String item) {
		if(Prop.contains(fromName(shop), item)) {
			return Double.parseDouble("" + Prop.get(fromName(shop), item));
		}
		return -1d;
	}
	
	public static final ArrayList<ShopItem> getItems(String shop) {
		File f = fromName(shop);
		ArrayList<ShopItem> item = new ArrayList<ShopItem>();
		for(Object k : Prop.getKeys(f)) {
			String key = k + "";
			item.add(new ShopItem(key.split(":")[0], Double.parseDouble("" + Prop.get(f, key)), Integer.parseInt(key.split(":")[1])));
		}
		return item;
	}
	
	private static final File fromName(String n) {
		return new File(IO.getDataDir(), "/" + n + ".shop");
	}
	
	private static final String fromFile(File f) {
		String n = f.getName();
		return n.substring(0, n.length() - 5);
	}
	
}