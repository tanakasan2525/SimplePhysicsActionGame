package a5812070.action2d.tmxmap;

import java.io.File;
import java.util.ArrayList;

import a5812070.action2d.XML;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.gameengine.GameEngine;

public class TileSet {

	private int firstgid, lastgid;
	private String name;
	private int tilewidth, tileheight;
	private String tileSource;
	private String imageSource;
	private int w, h;
	private ArrayList<Tile> tiles;

	TileSet(XML xml, String imageDir) {
		load(xml, imageDir);
	}

	public final void load(XML xml, String imageDir) {
		tiles = new ArrayList<Tile>();
		firstgid = xml.getInt("firstgid");
		//	外部タイルセットの読み込み
		tileSource = xml.getStr("source", null);
		if (tileSource != null) {
			xml = new XML(GameEngine.getInputStream(new File(imageDir + tileSource).getPath()));
			imageDir = new File(imageDir + tileSource).getParent() + "/";
		}
		name = xml.getStr("name");
		tilewidth = xml.getInt("tilewidth");
		tileheight = xml.getInt("tileheight");
		int count = 0;
		XML child = xml.getChild("image");
		if (child == null) {
			for (XML tile : xml.getChildren()) {
				tiles.add(new Tile(tile, imageDir));
				++count;
			}
		} else {
			imageSource = new File(imageDir + child.getStr("source")).getPath();
			w = child.getInt("width");
			h = child.getInt("height");
			count = (int) (Math.ceil(w / tilewidth) * Math.ceil(h / tileheight));
			BImage bimg = new BImage(imageSource);
			int x = 0, y = 0;
			for (int i = 0; i < count; ++i) {
				tiles.add(new Tile(xml.getChild("tile"), bimg, x, y, tilewidth, tileheight));
				if ((x += tilewidth) >= w) {
					x = 0;
					y += tileheight;
				}
			}
		}
		lastgid = firstgid + count - 1;
	}

	public final int getFirstGID() { return this.firstgid; }

	public final int getLastGID() { return this.lastgid; }

	public final boolean containGID(int gid) { return firstgid <= gid && gid <= lastgid; }

	public final String getName() { return this.name; }

	public final int getTileWidth() { return tilewidth; }

	public final int getTileHeight() { return tileheight; }

	public final BImage getImage(int gid) { return getTile(gid).getImage(); }

	public final Tile getTile(int gid) { return tiles.get(gid - firstgid); }

}
