package a5812070.action2d.tmxmap;

import java.util.ArrayList;

import a5812070.action2d.XML;

public class ObjectGroup {

	private String name;
	private int width, height;
	private ArrayList<MapObject> objects;

	ObjectGroup prev, next;

	ObjectGroup(TMXMap tmxmap, XML xml) {
		load(tmxmap, xml);
		prev = next = null;
	}

	public final void load(TMXMap tmxmap, XML xml) {
		this.name = xml.getStr("name");
		this.width = xml.getInt("width");
		this.height = xml.getInt("height");
		this.objects = new ArrayList<MapObject>();
		for (XML object : xml.getChildren("object")) {
			objects.add(new MapObject(tmxmap, object));
		}
	}

	public final MapObject detectCollision(MapObject obj) {
		return detectCollision(obj, null);
	}

	public final MapObject detectCollision(MapObject obj, MapObject first) {
		for (int i = objects.indexOf(first) + 1; i < objects.size(); ++i) {
			MapObject obj2 = objects.get(i);
			if (obj2.detectCollision(obj)) return obj2;
		}
		return null;
	}

	public final String getName() { return this.name; }

	public final int getWidth() { return this.width; }

	public final int getHeight() { return this.height; }

	public final ArrayList<MapObject> getObjects() { return objects; }

	public final MapObject getObject(String name) {
		for (int i = 0; i < objects.size(); ++i)
			if (objects.get(i).getName().equals(name)) return objects.get(i);
		return null;
	}

	public final ObjectGroup getNext() { return this.next; }

	public final ObjectGroup getPrev() { return this.prev; }

}
