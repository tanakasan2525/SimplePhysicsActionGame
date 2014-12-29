package a5812070.action2d.tmxmap;

import java.io.File;

import a5812070.action2d.XML;
import a5812070.action2d.drawing.BImage;

public class Tile {

	private int width, height;
	private String source;
	private BImage img;
	private Properties properties;

	public Tile(XML xml, String imageDir) {
		XML imgxml = xml.getChild("image");
		source = new File(imageDir + imgxml.getStr("source")).getPath();
		load(xml, new BImage(source), imgxml.getInt("width"), imgxml.getInt("height"));
	}

	public Tile(XML xml, BImage sourceImg, int x, int y, int tilewidth, int tileheight) {
		load(xml, sourceImg.getSubimage(x, y, tilewidth, tileheight), tilewidth, tileheight);
	}

	private void load(XML xml, BImage img, int width, int height) {
		this.width = width;
		this.height = height;
		this.img = img;
		properties = new Properties();
		if (xml == null) return;
		xml = xml.getChild("properties");
		if (xml != null)
			properties.load(xml);
	}

	public final int getWidth() { return width; }

	public final int getHeight() { return height; }

	public final BImage getImage() { return img; }

	public final Properties getProperties() { return properties; }
}
