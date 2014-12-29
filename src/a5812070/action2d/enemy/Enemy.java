package a5812070.action2d.enemy;

import a5812070.action2d.GameBodyDef;
import a5812070.action2d.GameObject;
import a5812070.action2d.GameWorld;
import a5812070.action2d.ObjectData;
import a5812070.action2d.Player;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.drawing.TileImage;
import a5812070.action2d.effect.Effect;


public abstract class Enemy extends GameObject
{
	/**
	 *	ダメージを受けた後の無敵時間
	 */
	protected int invincibleTime = 20;

	PatternImage _img = new PatternImage(20);

	/**
	 * 無敵時間のタイマー(0になると無敵が解除されます)
	 */
	protected int invincibleTimer;

	private int deadImgNo;

	protected static Player player;

	Enemy(ObjectData objdata, int hp, int[] pattern, int deadImgNo) {
		this.deadImgNo = deadImgNo;
		TileImage img = new TileImage();
		for (int p : pattern) {
			img = new TileImage();
			img.add(objdata.mobject.getImage(p), 0, 0);
			_img.add(img);
		}
		GameBodyDef.set(objdata.mobject.getX(), objdata.mobject.getY(), _img.getWidth(), _img.getHeight(), true);
		GameBodyDef.density = 1;
		GameBodyDef.fixedRotation = true;

		setHPMax(hp);
	}

	public void update() {
		if (invincibleTimer != 0) --invincibleTimer;
	}

	public void draw() {
		_img.draw(getDrawLeft(), getDrawTop());
		drawHP();
	}

	@Override
	public int damage(int power) {
		boolean flag = false;
		if (invincibleTimer == 0) {
			super.damage(power);
			flag = true;
		}
		invincibleTimer = invincibleTime;

		//	HPが0なら死亡エフェクトの表示＆敵の削除
		if (getHP() <= 0) {
			deadEvent();
		}

		return flag ? power : 0;
	}

	/**
	 * HPが０になった時に呼ばれます。
	 */
	protected void deadEvent() {
		//	死亡エフェクトの表示＆敵の削除
		Effect.addEnemyDead(_img.get(deadImgNo), getX(), getY());
		GameWorld.inst().remove(this);
	}

	public static void setPlayer(Player p) { player = p; }

	/**
	 * ダメージを受けた時の無敵時間を設定します。
	 * @param time	無敵時間のフレーム数
	 */
	protected void setInvincibleTime(int time) {
		invincibleTime = time;
	}
}

/*class Goomba extends Enemy
{
	float _dx = -1.5f;
	int count;

	Goomba(ObjectData objdata) {
		super(objdata, 100, new int[]{0, 1});
	}

	public void update() {
		if (getVx() == 0 && !contactLeft.isEmpty() ||getVx() == 0 && !contactRight.isEmpty()) {
			_dx = -_dx;
			_img.invertH();
		}
		setVx(_dx);

		_img.next();
	}

	public void deadEvent() { }
}*/
