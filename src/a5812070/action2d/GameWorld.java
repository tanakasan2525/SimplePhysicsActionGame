package a5812070.action2d;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;


public class GameWorld {
	public final static int FPS = 60;

	public final static float SCALE = 50;		//	size range: 0.1~10 m

	private World world;

	public Vec2 gravity = new Vec2(0.0f, 10f);

	private GameBody objList;

	//	singleton
	private static final GameWorld _instance = new GameWorld();
	public static final GameWorld inst() { return _instance; }

	GameWorld() {
		reset();
	}


	public void add(GameBody obj) {
		obj.prev = objList;
		obj.next = objList.next;
		if (obj.next != null)
			obj.next.prev = obj;
		objList.next = obj;
	}


	public final void update() {

		for (GameBody next = objList.next; next != null; next = next.next) {
			next.updateCommon();
		}

		for (GameBody next = objList.next; next != null; next = next.next) {
			next.clearContactInfo();
		}

		world.step(1.0f / FPS, 10, 10);	//6	velocityiterations,	2:positionIterations

		for (Contact it = world.getContactList(); it != null; it = it.getNext()) {
			if (!it.isTouching()) continue;

			GameBody body1 = (GameBody)it.getFixtureA().getBody().getUserData();
			GameBody body2 = (GameBody)it.getFixtureB().getBody().getUserData();

			Vec2 norm = it.getManifold().localNormal;
			body1.onContact(body2, new Vector2D(norm.x, norm.y));
			body2.onContact(body1, new Vector2D(-norm.x, -norm.y));
		}
	}

	public final void draw() {
		for (GameBody next = objList.next; next != null; next = next.next) {
			if (!next.isHidden())
				next.draw();
		}
	}

	Body createBody(BodyDef bodyDef) {
		return world.createBody(bodyDef);
	}

	public Joint createJoint(RevoluteJointDef def) {
		return world.createJoint(def);
	}

	public final void remove(GameBody body) {
		body.remove();
		world.destroyBody(body.body);
	}

	public void reset() {
		world = new World(gravity);
		objList = new GameBodyDummy();
	}

	public final GameBody get(String name) {
		for (GameBody next = objList.next; next != null; next = next.next) {
			if (next.getName().equals(name)) return next;
		}
		return null;
	}

	public final ArrayList<GameBody> getAll(String name) {
		ArrayList<GameBody> ret = new ArrayList<GameBody>();
		for (GameBody next = objList.next; next != null; next = next.next) {
			if (next.getName().equals(name)) ret.add(next);
		}
		return ret;
	}

	public final GameBody get(float x, float y) {
		for (GameBody next = objList.next; next != null; next = next.next) {
			if (next.isContains(x, y))
				return next;
		}
		return null;
	}
}

class GameBodyDummy extends GameBody {}

