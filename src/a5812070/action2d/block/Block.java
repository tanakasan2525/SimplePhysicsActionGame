package a5812070.action2d.block;

import java.util.ArrayList;

import a5812070.action2d.GameBodyDef;
import a5812070.action2d.GameObject;
import a5812070.action2d.Vector2D;
import a5812070.action2d.drawing.Draw;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;


public class Block extends GameObject
{
	protected PatternImage _img;
	Block(PatternImage img, float x, float y, float w, float h, Properties properties) {
		_img = img;
		ArrayList<Vector2D> points = new ArrayList<Vector2D>();
		while(true) {
			String p = properties.getStr("p"+points.size());
			if (p != null) {
				String[] pointStr = p.split(",");
				points.add(new Vector2D(Integer.parseInt(pointStr[0]), Integer.parseInt(pointStr[1])));
			} else {
				break;
			}
		}

		if (points.isEmpty()) {
			GameBodyDef.set(x, y, w, h, true);
		} else {
			GameBodyDef.set(points, x, y, w, h, true);
		}
		GameBodyDef.density = 10000000000000000.0f;
		GameBodyDef.category = GameBodyDef.CATEGORY_MAP_OBJECT;
		GameBodyDef.collisionInfo = ~GameBodyDef.CATEGORY_MAP_OBJECT;
	}

	public void update() {}

	public void draw() {
		Draw.setRotation(getAngle());
		_img.draw(getDrawLeft(), getDrawTop());
		Draw.reset();
		drawHP();
	}

	void setDefaultProperties(Properties properties) {
		float density = properties.getFloat("Density", -1);
		if(density != -1) {
			GameBodyDef.movable = true;
			GameBodyDef.density = density;
			GameBodyDef.category |= GameBodyDef.CATEGORY_NORMAL;
			GameBodyDef.collisionInfo = GameBodyDef.COLLISION_ALL;
			GameBodyDef.x -= 1;
			GameBodyDef.y -= 1;
			GameBodyDef.w -= 2;
			GameBodyDef.h -= 2;
			for (int i = 0; i < _img.size(); ++i)
				_img.get(i).move(1, 1);
		}

		boolean hidden = properties.getInt("Hidden", 0) != 0;
		if (hidden) {
			GameBodyDef.hidden = true;
			//GameBodyDef.category = GameBodyDef.CATEGORY_NO_COLLISION;
			GameBodyDef.collisionInfo = GameBodyDef.CATEGORY_NO_COLLISION;
		}
	}
}

