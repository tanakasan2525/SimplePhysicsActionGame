package a5812070.action2d.enemy;

import a5812070.action2d.ObjectData;

/**
 * 敵モンスター『カエル』
 *
 * ＜説明＞
 * 飛び跳ねてプレイヤーを追いかけます。
 *
 * ＜パラメータ＞
 * HP：		30
 * 攻撃力：	10
 *
 * ＜パターン画像＞
 * 0番目：歩行１（デフォルト）
 * 1番目：死亡
 * 2番目：ダメージ
 * 3番目：歩行２
 *
 * @author a5812070
 *
 */
public class Frog extends Enemy {
	private static final int IMG_WALK1 = 0;
	private static final int IMG_DEAD = 1;
	private static final int IMG_HIT = 2;
	private static final int IMG_WALK2 = 3;

	private static final int HP_MAX = 30;

	private static final int POWER = 700;	//	プレイヤーを吹き飛ばす力
	private static final int DAMAGE = 10;	//	プレイヤーに与えるダメージ

	/**
	 * 着地してから次のジャンプまでの待機時間
	 */
	private static final int WAIT_TIME = 80;


	int timer;

	public Frog(ObjectData objdata) {
		super(objdata, HP_MAX, new int[]{0, 1, 2, 3}, IMG_DEAD);

		create();
	}

	public void update() {
		super.update();

		//	プレイヤーとの衝突判定など
		playerProcess();

		if (invincibleTimer == 0) {
			if (timer != 0 || onGround() && Math.abs(getVy()) < 1E-3) {
				_img.setPatternNo(IMG_WALK1);

				if (++timer > WAIT_TIME) {
					timer = 0;
					int direction = getX() < player.getX() ? 1 : -1;

					//	方向転換
					if (direction == 1)
						_img.setInverseH(true);
					else
						_img.setInverseH(false);

					//	ジャンプ＆ジャンプ中画像へ切り替え
					setVelocity(direction * 2, -5);
					_img.setPatternNo(IMG_WALK2);
				}
			}
		} else {
			//	無敵時のグラフィック
			_img.setPatternNo(IMG_HIT);
		}
	}

	/**
	 * プレイヤーとの衝突判定などを行います。
	 */
	private void playerProcess() {
		if (contactLeft.contains(player)) {
			player.damage(DAMAGE);
			player.addForce(-POWER, 0);
		} else if (contactRight.contains(player)) {
			player.damage(DAMAGE);
			player.addForce(POWER, 0);
		}else if (contactBottom.contains(player)) {
			player.damage(DAMAGE);
			int direction = getX() < player.getX() ? 1 : -1;
			player.addForce(direction * POWER, 0);	//	押しつぶされた多段ヒットするのを防ぐ
		}else if (contactTop.contains(player)) {
			player.damage(DAMAGE);
			player.addForce(0, -POWER);
		}
	}
}
