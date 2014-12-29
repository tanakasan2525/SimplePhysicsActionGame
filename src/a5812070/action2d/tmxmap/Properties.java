package a5812070.action2d.tmxmap;

import java.util.HashMap;
import java.util.Set;

import a5812070.action2d.XML;

public class Properties {

	HashMap<String, String> pMap;

	public Properties() {
		pMap = new HashMap<String, String>();
	}

	public final void load(XML xml) {
		for (XML p : xml.getChildren("property")) {
			pMap.put(p.getStr("name"), p.getStr("value"));
		}
	}

	public final boolean isEmpty() { return pMap.isEmpty(); }

	public final String getStr(String name) { return pMap.get(name); }

	public final String getStr(String name, String def) { return pMap.get(name) == null ? def : pMap.get(name); }

	public final int getInt(String name) { return getInt(name, 0); }

	public final int getInt(String name, int def) {
		String ret = pMap.get(name);
		return ret == null ? def : Integer.parseInt(ret);
	}

	public final float getFloat(String name) { return getFloat(name, 0); }

	public final float getFloat(String name, float def) {
		String ret = pMap.get(name);
		return ret == null ? def : Float.parseFloat(pMap.get(name));
	}

	public final Set<String> getNames() {
		return pMap.keySet();
	}

	public final void add(String name, String value) {
		pMap.put(name, value);
	}

	public final void add(Properties properties) {
		for (String name : properties.pMap.keySet())
			pMap.put(name, properties.pMap.get(name));
	}

	public final void remove(String name) {
		pMap.remove(name);
	}

}
