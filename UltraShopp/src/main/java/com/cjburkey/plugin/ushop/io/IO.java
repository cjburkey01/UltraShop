package com.cjburkey.plugin.ushop.io;

import java.io.File;
import com.cjburkey.plugin.ushop.UltraShop;

public class IO {
	
	public static final File getDataDir() {
		File dir = new File(UltraShop.getPlugin().getDataFolder(), "/data/");
		if(!dir.exists()) dir.mkdirs();
		return dir;
	}
	
}