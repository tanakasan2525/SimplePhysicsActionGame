package a5812070.action2d.tmxmap;

import java.io.File;
import java.util.ArrayList;

import a5812070.action2d.XML;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.gameengine.GameEngine;

public class TMXMap {

	private int width, height;
	private int tilewidth, tileheight;
	private ArrayList<TileSet> tilesets;
	private ArrayList<ObjectGroup> objectgroups;
	private Properties properties;


	public TMXMap(String path) {
		load(path);
	}

	public final void load(String path) {

		XML xml = new XML(GameEngine.getInputStream(path));

		width = xml.getInt("width");
		height = xml.getInt("height");
		tilewidth = xml.getInt("tilewidth");
		tileheight = xml.getInt("tileheight");
		tilesets = new ArrayList<TileSet>();
		String imageDir = null;
		try {
			imageDir = new File(new File(path).getAbsolutePath()).getParent() + "/";
		} catch (Exception e) {}
		for (XML tileset : xml.getChildren("tileset")) {
			tilesets.add(new TileSet(tileset, imageDir));
		}
		objectgroups = new ArrayList<ObjectGroup>();
		for (XML objectgroup : xml.getChildren("objectgroup")) {
			ObjectGroup og = new ObjectGroup(this, objectgroup);
			if (!objectgroups.isEmpty()) {
				objectgroups.get(0).prev = og;
				og.next = objectgroups.get(0);
			}
			objectgroups.add(0, og);
		}
		properties = new Properties();
		XML property = xml.getChild("properties");
		if (property != null) properties.load(property);
	}

	public final int getWidth() { return this.width; }

	public final int getHeight() { return this.height; }

	public final int getTileWidth() { return this.tilewidth; }

	public final int getTileHeight() { return this.tileheight; }

	public final ArrayList<TileSet> getTileSets() { return tilesets; }

	public final int getLayerCount() { return objectgroups.size(); }

	public final ObjectGroup getLayer(int n) { return objectgroups.get(n); }

	public final ArrayList<ObjectGroup> getLayers() { return objectgroups; }

	public final TileSet getTileSet(int gid) {
		for (TileSet tileset : tilesets) {
			if (tileset.containGID(gid)) {
				return tileset;
			}
		}
		return null;
	}

	public final BImage getImage(int gid) {
		return getTileSet(gid).getImage(gid);
	}

	public final Properties getProperties() { return properties; }

}
