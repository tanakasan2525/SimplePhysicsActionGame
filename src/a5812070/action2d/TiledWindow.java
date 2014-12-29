package a5812070.action2d;

import static a5812070.action2d.drawing.Draw.*;

import java.util.ArrayList;

import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.tmxmap.MapObject;
import a5812070.action2d.tmxmap.ObjectGroup;
import a5812070.action2d.tmxmap.TMXMap;

/**
 * マップエディタでUIを作成できるウィンドウクラス
 *
 * レイヤー名を「component」にするとボタンが出来ます。
 * レイヤー名を「image」にすると画像の配置ができます。
 *
 * ＜ボタン＞
 * プロパティ
 * Depth：	このレイヤーよりいくつ下までをボタンとするか設定できます。
 *
 *
 * @author a5812070
 *
 */
public class TiledWindow {

	ArrayList<Component> components;

	int selectedIndex = -1;

	public TiledWindow(String path) {
		components = new ArrayList<Component>();

		load(path);
	}

	public void load(String path) {
		TMXMap data = new TMXMap(path);

		for (ObjectGroup objectgroup : data.getLayers()) {
			if (objectgroup.getName().equals("component")) {
				for (MapObject object : objectgroup.getObjects()) {

					ComponentButton c = new ComponentButton(object);
					int depth = object.getProperties().getInt("Depth");

					MapObject first = null;
					ObjectGroup og = objectgroup.getNext();
					while (og != null) {
						MapObject obj = og.detectCollision(object, first);
						if (obj != null) {
							c.addImage(obj);
							first = obj;
						} else {
							og = og.getNext();
							if (--depth <= 0) break;
						}
					}

					components.add(0, c);
				}
			} else if (objectgroup.getName().equals("image")) {
				for (MapObject object : objectgroup.getObjects())
					components.add(0, new ComponentImage(object));
			}
		}

	}

	public void update() {
		 for (int i = 0; i < components.size(); ++i) {
			if (components.get(i).update()) {
				selectedIndex = i;
			}
		}
	}

	public void draw() {
		for (Component c : components) {
			c.draw();
		}
	}

	public void draw(int no) {
		components.get(no).draw();
	}

	public void unselect() { selectedIndex = -1; }

	public boolean isSelected() { return selectedIndex != -1; }

	public String getStr(String name) {
		if (selectedIndex == -1) return null;
		return components.get(selectedIndex).getStr(name);
	}

	public int getInt(String name) {
		if (selectedIndex == -1) return -1;
		return components.get(selectedIndex).getInt(name);
	}

	public float getFloat(String name) {
		if (selectedIndex == -1) return -1;
		return components.get(selectedIndex).getFloat(name);
	}

	public Component getComponent(int no) { return components.get(no); }

	public int getComponentCount() { return components.size(); }


	abstract class Component {
		int x1, y1, x2, y2;
		boolean isPointing;

		MapObject object;

		Component(MapObject object) {
			this.x1 = object.getX();
			this.y1 = object.getY();
			this.x2 = object.getWidth() + x1;
			this.y2 = object.getHeight() + y1;
			this.isPointing = false;
			this.object = object;
		}

		public abstract boolean update();

		public abstract void draw();

		public boolean isContains(int x, int y) {
			return !(x < x1 || x > x2 || y < y1 || y > y2);
		}

		public String getStr(String name) {
			return object.getProperties().getStr(name);
		}

		public int getInt(String name) {
			return object.getProperties().getInt(name);
		}

		public float getFloat(String name) {
			return object.getProperties().getFloat(name);
		}
	}

	class ComponentButton extends Component {
		boolean isPointing;

		ArrayList<MapObject> imgList;

		ComponentButton(MapObject object) {
			super(object);
			this.isPointing = false;
			this.imgList = new ArrayList<MapObject>();
		}

		public void addImage(MapObject obj) {
			imgList.add(0, obj);
		}

		public boolean update() {
			if (GameEngine.getInput().pointPressed()) {
				if (isContains(GameEngine.getInput().pointX(), GameEngine.getInput().pointY())) return true;
			}
			if (isContains(GameEngine.getInput().pointX(), GameEngine.getInput().pointY())) {
				isPointing = true;
				if (GameEngine.getInput().pointPressed()) return true;
			} else {
				isPointing = false;
			}
			return false;
		}

		public void draw() {
			if (!isPointing) setAlpha(180);

			for (MapObject obj : imgList) {
				image(obj.getImage(), obj.getX(), obj.getY());
			}
			if (!isPointing) reset();
		}
	}

	class ComponentImage extends Component {

		ComponentImage(MapObject object) {
			super(object);
		}

		@Override
		public boolean update() {
			return false;
		}

		@Override
		public void draw() {
			image(object.getImage(), object.getX(), object.getY());
		}

	}

}
