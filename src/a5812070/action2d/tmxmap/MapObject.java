package a5812070.action2d.tmxmap;

import a5812070.action2d.XML;
import a5812070.action2d.drawing.BImage;

public class MapObject {

	private String name;
	private int gid;
	private int x, y, w, h;
	private float rotation;
	private String type;
	private Properties properties;
	private TMXMap tmxmap;
	private TileSet tileset;

	MapObject(TMXMap tmxmap, XML xml) {
		load(tmxmap, xml);
	}

	public final void load(TMXMap tmxmap, XML xml) {
		this.name = xml.getStr("name", "");
		this.gid = xml.getInt("gid", -1);
		this.x = xml.getInt("x");
		this.y = xml.getInt("y");
		this.rotation = xml.getFloat("rotation", 0);
		this.type = xml.getStr("type", "");
		this.tmxmap = tmxmap;
		this.properties = new Properties();
		if (gid == -1) {
			this.w = xml.getInt("width");
			this.h = xml.getInt("height");
		} else {
			this.w = getImage().getWidth();
			this.h = getImage().getHeight();
			this.y -= this.h;
			tileset = tmxmap.getTileSet(gid);
			properties.add(tileset.getTile(gid).getProperties());
		}

		XML pxml = xml.getChild("properties");
		if (pxml != null) properties.load(pxml);
	}

	public final boolean detectCollision(MapObject obj) {
		return !(x + w < obj.x || x > obj.x + obj.w || y + h < obj.y || y > obj.y + obj.h);
	}

	public final String getName() { return this.name; }

	public final int getGID() { return this.gid; }

	public final int getX() { return this.x; }

	public final int getY() { return this.y; }

	public final int getWidth() { return this.w; }

	public final int getHeight() { return this.h; }

	public final float getRotation() { return this.rotation; }

	public final String getType() { return this.type; }

	public final BImage getImage(int move) { return tmxmap.getImage(gid + move); }

	public final BImage getImage() { return getImage(0); }

	public final Properties getProperties() { return properties; }

	@Override
	public String toString() {
		return String.format("gid = %d\tx = %d\ty = %d\tw = %d\th = %d", gid, x, y, w, h);
	}

}

