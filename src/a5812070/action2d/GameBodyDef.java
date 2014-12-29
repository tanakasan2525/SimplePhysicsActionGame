package a5812070.action2d;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class GameBodyDef {
	public static float x, y, w, h;
	public static boolean movable;
	public static boolean hidden;
	public static float density = 1.0f;
	public static float friction = 0.5f;
	public static float restitution = 0.f;
	public static Vec2[] vertices;
	public static boolean fixedRotation;
	public static float centerX, centerY;

	public static final int CATEGORY_NO_COLLISION = 0x000;
	public static final int CATEGORY_NORMAL = 0x0001;
	public static final int CATEGORY_PLAYER = 0x0002;
	public static final int CATEGORY_MAP_OBJECT = 0x0004;
	public static final int CATEGORY_ITEM = 0x0008;
	//final int TYPE_OTHER = 0x008;

	public static int category = CATEGORY_NORMAL;

	public static final int COLLISION_ALL = 0xFFFF;

	public static int collisionInfo = COLLISION_ALL;

	public static void set(float x_, float y_, float w_, float h_, boolean movable_) {
		x = x_;
		y = y_;
		w = w_;
		h = h_;
		movable = movable_;
		hidden = false;
		density = 1.0f;
		friction = 0.5f;
		restitution = 0;
		category = CATEGORY_NORMAL;
		collisionInfo = COLLISION_ALL;
		vertices = null;
		fixedRotation = false;
		centerX = 0;
		centerY = 0;
	}

	public static void set(ArrayList<Vector2D> points, float x_, float y_, float w_, float h_, boolean movable_) {
		set(x_, y_, w_, h_, movable_);

		vertices = new Vec2[points.size()];
		for (int i = 0; i < points.size(); ++i) {
			vertices[i] = new Vec2(points.get(i).x, points.get(i).y);
		}
	}
}

