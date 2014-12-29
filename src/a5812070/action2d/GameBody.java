package a5812070.action2d;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;


public abstract class GameBody {
	Body body;
	String name;
	float w, h;										//	as half aabb
	private int _onGround;
	private boolean hidden;

	GameBody prev, next;

	static final int ON_GROUND_ACCURACY = 5;	//	if it becomes larger, it's bad accuracy

	protected Set<GameBody> contactTop;
	protected Set<GameBody> contactBottom;
	protected Set<GameBody> contactLeft;
	protected Set<GameBody> contactRight;

	GameBody() {
		contactTop = new HashSet<GameBody>();
		contactBottom = new HashSet<GameBody>();
		contactLeft = new HashSet<GameBody>();
		contactRight = new HashSet<GameBody>();
		name = "";
	}

	protected void create() {
		BodyDef bodyDef = new BodyDef();
		if (GameBodyDef.movable) {
			bodyDef.type = BodyType.DYNAMIC;
			GameBodyDef.w -= 0.5;
			GameBodyDef.h -= 0.5;
		}

		this.w = GameBodyDef.w / 2 / GameWorld.SCALE;
		this.h = GameBodyDef.h / 2 / GameWorld.SCALE;
		float x = GameBodyDef.x / GameWorld.SCALE + w;
		float y = GameBodyDef.y	/ GameWorld.SCALE + h;
		PolygonShape box = new PolygonShape();
		if (GameBodyDef.vertices == null) {
			float cx = GameBodyDef.centerX / GameWorld.SCALE;
			float cy = GameBodyDef.centerY / GameWorld.SCALE;
			box.setAsBox(w, h, new Vec2(cx, cy), 0);
		} else {
			for (int i = 0; i < GameBodyDef.vertices.length; ++i) {
				GameBodyDef.vertices[i].mulLocal(1.f / GameWorld.SCALE);
				GameBodyDef.vertices[i].x -= w;
				GameBodyDef.vertices[i].y -= h;
			}
			box.set(GameBodyDef.vertices, GameBodyDef.vertices.length);
		}

		bodyDef.position.set(x, y);


		hidden = GameBodyDef.hidden;

		bodyDef.fixedRotation = GameBodyDef.fixedRotation;
		body = GameWorld.inst().createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = GameBodyDef.density;
		fixtureDef.friction = GameBodyDef.friction;
		fixtureDef.restitution = GameBodyDef.restitution;
		fixtureDef.filter.categoryBits = GameBodyDef.category;
		fixtureDef.filter.maskBits = GameBodyDef.collisionInfo;
		body.createFixture(fixtureDef);
		body.setUserData(this);

		this._onGround = 0;
	}

	protected void createRevoluteJoint(GameBody gBody, float x, float y) {
		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(body, gBody.body, new Vec2(x / GameWorld.SCALE, y / GameWorld.SCALE));
		// つないだもの同士は衝突しない
	    revoluteJointDef.collideConnected = false;
	    // ジョイントの作成
	    GameWorld.inst().createJoint(revoluteJointDef);
	}


	protected void update() {}

	void updateCommon() {
		if (hidden) {
			cancelGravity();
			return;
		}
		update();
		if (--_onGround < 0) _onGround = 0;
	}

	public void draw() {	}

	/**
	 * このオブジェクトを削除します。（ワールドからは消えません）
	 */
	public void remove() {
		prev.next = next;
		if (next != null)
			next.prev = prev;
	}

	/**
	 * 物体を平行移動します。
	 * @param x	現在地からの移動量
	 * @param y 現在地からの移動量
	 */
	void move(float x, float y) {
		x *= GameWorld.FPS / GameWorld.SCALE;
		y *= GameWorld.FPS / GameWorld.SCALE;
		body.setLinearVelocity(new Vec2(x + body.getLinearVelocity().x, y + body.getLinearVelocity().y));
		body.setAwake(true);
	}

	/**
	 * 物体の中心座標を動かします。
	 * @param dx
	 * @param dy
	 */
	public void moveCenterPosition(float dx, float dy) {
		body.getLocalCenter().addLocal(dx / GameWorld.SCALE, dy / GameWorld.SCALE);
	}

	/**
	 * 物体に力を加えます。
	 * @param vecX	X軸方向の力の強さ
	 * @param vecY	Y軸方向の力の強さ
	 */
	public void addForce(float vecX, float vecY) {
		body.applyForceToCenter(new Vec2(vecX, vecY));
	}

	/**
	 * 重力を打ち消します。毎フレーム呼ぶ必要があります。
	 */
	protected void cancelGravity() {
		body.getLinearVelocity().y = 0;
		body.applyForceToCenter(new Vec2(0, -body.getMass()*GameWorld.inst().gravity.y));
	}

	/**
	 * 名前を設定します。
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 密度を設定します。
	 * @param density
	 */
	public void setDensity(float density) {
		for (Fixture fix = body.getFixtureList(); fix !=	null; fix = fix.getNext()) {
			fix.setDensity(density);
		}
		body.resetMassData();
	}

	/**
	 * 反発係数を設定します。
	 * @param friction
	 */
	public void setFriction(float friction) {
		for (Fixture fix = body.getFixtureList(); fix !=	null; fix = fix.getNext()) {
			fix.setFriction(friction);
		}
	}

	/**
	 * 物体を固定するかどうかを設定します。
	 * @param flag	固定する場合はtrue、動かせるようにする場合はfalse
	 */
	public void setFixedFlag(boolean flag) {
		body.setType(flag ? BodyType.STATIC : BodyType.DYNAMIC);
		body.setAwake(true);
	}

	/**
	 * 物理演算を行うかどうかを設定します。
	 * @param flag	行う場合はtrue
	 */
	public void setAwake(boolean flag) {
		body.setAwake(flag);
	}

	/**
	 * 衝突カテゴリーを設定します。
	 * ここで設定した値が、衝突した相手のsetCollisionTarget()で設定した値と論理積で真になった時、衝突します。
	 * @param bit
	 */
	public void setCollisionCategory(int bit) {
		for (Fixture fix = body.getFixtureList(); fix != null; fix = fix.getNext()) {
			Filter f = fix.getFilterData();
			f.categoryBits = bit;
			fix.setFilterData(f);
		}
	}

	/**
	 * どのカテゴリーのオブジェクトと衝突するかを表すビット列を設定します。
	 * @param bit
	 */
	public void setCollisionTarget(int bit) {
		for (Fixture fix = body.getFixtureList(); fix != null; fix = fix.getNext()) {
			Filter f = fix.getFilterData();
			f.maskBits = bit;
			fix.setFilterData(f);
		}
	}

	/**
	 * 物体を非表示にするかどうかを設定します。
	 * @param flag	非表示にする場合はtrue
	 */
	public void setHidden(boolean flag) {
		hidden = flag;
	}

	/**
	 * 物体のサイズを変更します。(左下を基準に変更します。)
	 * @param w
	 * @param h
	 */
	public void setSize(float w, float h) {
		w /= 2 * GameWorld.SCALE;
		h /= 2 * GameWorld.SCALE;
		if (body.m_fixtureList.getShape() instanceof PolygonShape) {
			PolygonShape ps = (PolygonShape)body.m_fixtureList.getShape();
			ps.setAsBox(w, h);
			Vec2 pos = body.getTransform().p;
			pos.x += this.w - w;
			pos.y += (this.h - h) * 2;
			this.w = w;
			this.h = h;
			body.setTransform(pos, body.getTransform().q.getAngle());
		}
		body.setAwake(true);
	}

	/**
	 * 物体のX座標成分を設定します。
	 * @param x
	 */
	public void setX(float x) {
		Vec2 pos = body.getTransform().p;
		pos.x = x / GameWorld.SCALE;
		body.setTransform(pos, body.getTransform().q.getAngle());
		body.setAwake(true);
	}

	/**
	 * 物体のY座標成分を設定します。
	 * @param y
	 */
	public void setY(float y) {
		Vec2 pos = body.getTransform().p;
		pos.y = y / GameWorld.SCALE;
		body.setTransform(pos, body.getTransform().q.getAngle());
		body.setAwake(true);
	}

	/**
	 * 物体の速度のX成分を設定します。
	 * @param vx
	 */
	public void setVx(float vx) {
		body.getLinearVelocity().x = vx * GameWorld.FPS / GameWorld.SCALE;
		body.setAwake(true);
	}

	/**
	 * 物体の速度のY成分を設定します。
	 * @param vy
	 */
	public void setVy(float vy) {
		body.getLinearVelocity().y = vy * GameWorld.FPS / GameWorld.SCALE;
		body.setAwake(true);
	}

	/**
	 * 物体の速度を設定します。
	 * @param vx
	 * @param vy
	 */
	public void setVelocity(float vx, float vy) {
		body.setLinearVelocity(new Vec2(vx * GameWorld.FPS / GameWorld.SCALE, vy * GameWorld.FPS / GameWorld.SCALE));
		body.setAwake(true);
	}

	/**
	 * 物体の回転値を設定します。
	 * @param angle	角度（ラジアン）
	 */
	public void setAngle(float angle) {
		Vec2 pos = body.getTransform().p;
		body.setTransform(pos, angle);
	}

	public void rotate(float degree, float x, float y) {
		double angle = Math.toRadians(degree);
		Vec2 pos = body.getTransform().p;
		body.setTransform(pos, (float) (getAngle() + angle));

		x /= GameWorld.SCALE;
		y /= GameWorld.SCALE;

		float dX = pos.x - x;
		float dY = pos.y - y;

		pos.x = (float) (Math.cos(angle) * dX - Math.sin(angle) * dY);
		pos.y = (float) (Math.sin(angle) * dX + Math.cos(angle) * dY);

		pos.x += x;
		pos.y += y;

		body.setTransform(pos, body.getTransform().q.getAngle());
		body.setAwake(true);
	}

	/**
	 *
	 * @return
	 */
	public final boolean isAwake() { return body.isAwake(); }

	/**
	 * 静的なオブジェクトかどうかを取得します。
	 * @return	静的なオブジェクトならtrue
	 */
	public final boolean isStatic() { return body.getType() == BodyType.STATIC; }

	/**
	 * 物体が非表示かどうかを取得します。
	 * @return	非表示の場合true
	 */
	public final boolean isHidden() { return hidden; }

	/**
	 * 引数の座標にこのオブジェクトが含まれているかを取得します。
	 * @param x
	 * @param y
	 * @return	含まれている場合はtrue
	 */
	public final boolean isContains(float x, float y) {
		x /= GameWorld.SCALE;
		y /= GameWorld.SCALE;
		Vec2 pos = body.getTransform().p;
		return !(pos.x - w > x || pos.x + w < x || pos.y - y > y || pos.y + h < y);
	}

	/**
	 * 物体の名前を取得します。
	 * @return
	 */
	public final String getName() { return name; }

	/**
	 * 物体の中心座標のX成分を取得します。
	 * @return
	 */
	public final float getX() { return body.getPosition().x * GameWorld.SCALE; }

	/**
	 * 物体の中心座標のY成分を取得します。
	 * @return
	 */
	public final float getY() { return body.getPosition().y * GameWorld.SCALE; }
	public final float getVx() { return body.getLinearVelocity().x * GameWorld.SCALE / GameWorld.FPS; }
	public final float getVy() { return body.getLinearVelocity().y * GameWorld.SCALE / GameWorld.FPS; }

	public final float getAngle() { return body.getAngle(); }

	public final float getMass() { return body.getMass(); }

	public float getHalfWidth() { return this.w * GameWorld.SCALE; }
	public float getHalfHeight() { return this.h * GameWorld.SCALE; }

	public final float getLeft() { return (body.getPosition().x - w) * GameWorld.SCALE; }
	public final float getRight() { return (body.getPosition().x + w) * GameWorld.SCALE; }
	public final float getTop() { return (body.getPosition().y - h) * GameWorld.SCALE; }
	public final float getBottom() { return (body.getPosition().y - h) * GameWorld.SCALE; }

	public final boolean onGround() { return this._onGround != 0; }

	protected void onContact(GameBody gBody, Vector2D norm) {
		if (norm.x < -0.7f){// && body.getLinearVelocity().x <= 0.f) {
			contactLeft.add(gBody);
		} else
		if (norm.x > 0.7f){// && body.getLinearVelocity().x >= 0.f) {
			contactRight.add(gBody);
		}
		if (norm.y > 0.0) {	//	should change: falling bodies, rising bodies, etc.
			this._onGround = ON_GROUND_ACCURACY;
			contactBottom.add(gBody);
		} else
		if (norm.y < -0.7f && gBody.body.getLinearVelocity().y >= 0.0f) {
			contactTop.add(gBody);
		}
	}
	void clearContactInfo() {
		contactTop.clear();
		contactBottom.clear();
		contactLeft.clear();
		contactRight.clear();
	}

	public boolean isContact(GameBody gBody) {
		for (GameBody gb : contactTop)
			if (gb == gBody) return true;
		for (GameBody gb : contactBottom)
			if (gb == gBody) return true;
		for (GameBody gb : contactLeft)
			if (gb == gBody) return true;
		for (GameBody gb : contactRight)
			if (gb == gBody) return true;
		return false;
	}

	void fixPos() {
		if (!contactBottom.isEmpty())
				setY(getY() - 0.15f);			//	hover on ground
	}
}

