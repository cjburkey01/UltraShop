package com.cjburkey.plugin.ushop.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Prop {
	
	public static final void set(File f, Object key, Object value) {
		HashMap<Object, Object> neww = new HashMap<Object, Object>();
		HashMap<Object, Object> oldd = getMap(f);
		neww.putAll(oldd);
		neww.put(key, value);
		writeMap(f, neww);
	}
	
	public static final ArrayList<Object> getKeys(File f) {
		ArrayList<Object> key = new ArrayList<Object>();
		HashMap<Object, Object> map = getMap(f);
		for(Object k : map.keySet()) {
			key.add(k);
		}
		return key;
	}
	
	public static final void remove(File f, String key) {
		HashMap<Object, Object> map = getMap(f);
		map.remove(key);
		writeMap(f, map);
	}
	
	public static final boolean contains(File f, String key) {
		return (get(f, key) != null);
	}
	
	public static final Object get(File f, String key) {
		HashMap<Object, Object> map = getMap(f);
		return map.get(key);
	}
	
	@SuppressWarnings("unchecked")
	private static final HashMap<Object, Object> getMap(File f) {
		if(f.exists()) {
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				HashMap<Object, Object> old = null;
				old = (HashMap<Object, Object>) ois.readObject();
				ois.close();
				return old;
			} catch(Exception e) { e.printStackTrace(); }
		}
		return null;
	}
	
	public static final void writeMap(File f, HashMap<Object, Object> map) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
			oos.writeObject(map);
			oos.close();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
}