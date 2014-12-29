package a5812070.action2d;

import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.Draw;
import a5812070.action2d.effect.Effect;
import a5812070.action2d.item.ItemSword;

public class Sword extends GameObject {

	private final static int STATE_NORMAL = 0;
	private final static int STATE_READY = 1;
	private final static int STATE_ATTACK = 2;
	private final static int STATE_READY2 = 3;

	BImage _img;

	Player player;

	/**
	 * 現在の状態を表す変数(STATE_から始まる定数を値とします)
	 */
	int state;

	/**
	 * 刀の向き
	 */
	int angle;

	/**
	 * 攻撃力
	 */
	int power;

	public Sword(Player player) {
		this._img = new BImage("stage/img/other/swordBronze.png");
		this.player = player;

		float w = _img.getWidth();
		float h = _img.getHeight();

		GameBodyDef.set(player.getX(), player.getY(), w, h, true);
		GameBodyDef.collisionInfo = GameBodyDef.CATEGORY_NO_COLLISION;
		GameBodyDef.density = 1000;
		GameBodyDef.friction = 1.0f;
		GameBodyDef.centerY = -26;
		create();
		GameWorld.inst().add(this);

		state = STATE_NORMAL;
		power = 10;
	}

	@Override
	public void update() {
		if (player != null) {

			switch (state) {
			case STATE_NORMAL: angle = 40; break;
			case STATE_READY:
				if ((angle-=4) < 10) {
					state = STATE_ATTACK;
					setCollisionCategory(~GameBodyDef.CATEGORY_PLAYER);
					setCollisionTarget(~GameBodyDef.CATEGORY_PLAYER);//	プレイヤー以外と衝突判定
				}
				break;
			case STATE_ATTACK:
				checkHit();
				if ((angle+=10) > 130) {
					state = STATE_READY2;
					setCollisionTarget(GameBodyDef.CATEGORY_NO_COLLISION);
				}
				break;
			case STATE_READY2:
				if ((angle-=6) < 40) state = STATE_NORMAL;
				break;
			}

			if (player.isLeft()) {
				setX(player.getX() - 32);
				setAngle(-(float)Math.toRadians(angle));
			} else {
				setX(player.getX() + 30);
				setAngle((float)Math.toRadians(angle));
			}
			setY(player.getY() + 20);


			cancelGravity();
		} else {
			//	投げられているときは、常に攻撃判定
			checkHit();
			float speed = Math.abs(getVx()) + Math.abs(getVy());
			if (speed != 0.0 && speed < 1E-8) {	//	力を加えても最初のフレームでは速度は０になるので、それを回避する
				//	静止したら、アイテム化する
				GameWorld.inst().add(new ItemSword(this));
				GameWorld.inst().remove(this);
			}
		}
	}

	@Override
	public void draw() {
		Draw.setRotation(getAngle(), 0, 26);	//	中心位置をずらす
		Draw.image(_img, getDrawLeft(), getDrawTop() - 26);
		Draw.reset();
	}

	/**
	 * 攻撃します。
	 */
	public final void attack() {
		if (state == STATE_NORMAL) {
			state = STATE_READY;
		}
	}

	/**
	 * 装備を解除します。
	 */
	public final void release() {
		player = null;
		setCollisionTarget(~GameBodyDef.CATEGORY_PLAYER);
	}

	/**
	 * 攻撃があたっているか確認し、当たっていたら対応する処理を行います。
	 */
	public final void checkHit() {
		for (GameBody gb : contactTop) {
			if (((GameObject)gb).damage(power) > 0)
				Effect.addSlash(gb.getX(), gb.getY());
		}
		for (GameBody gb : contactBottom) {
			if (((GameObject)gb).damage(power) > 0)
				Effect.addSlash(gb.getX(), gb.getY());
		}
		for (GameBody gb : contactLeft) {
			if (((GameObject)gb).damage(power) > 0)
				Effect.addSlash(gb.getX(), gb.getY());
		}
		for (GameBody gb : contactRight) {
			if (((GameObject)gb).damage(power) > 0)
				Effect.addSlash(gb.getX(), gb.getY());
		}
	}

	/**
	 * ソードの画像を取得します。
	 * @return
	 */
	public BImage getImage() {
		return _img;
	}
}
