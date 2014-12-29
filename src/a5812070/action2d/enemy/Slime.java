package a5812070.action2d.enemy;

import a5812070.action2d.ObjectData;

/**
 * 敵モンスター『スライム』
 *
 * ＜説明＞
 * 左右に歩きます。
 *
 * ＜パラメータ＞
 * HP：		20
 * 攻撃力：	10
 *
 * ＜パターン画像＞
 * 0番目：歩行１（デフォルト）
 * 1番目：死亡
 * 2番目：ダメージ
 * 3番目：ぺっちゃんこ
 * 4番目：歩行２
 *
 * @author a5812070
 *
 */
public class Slime  extends Enemy
{
	private static final int IMG_WALK1 = 0;
	private static final int IMG_DEAD = 1;
	private static final int IMG_HIT = 2;
	private static final int IMG_SQUASHED = 3;
	private static final int IMG_WALK2 = 4;

	private static final int HP_MAX = 20;

	private static final int POWER = 700;	//	プレイヤーを吹き飛ばす力
	private static final int DAMAGE = 10;	//	プレイヤーに与えるダメージ

	public Slime(ObjectData objdata) {
		super(objdata, HP_MAX, new int[]{0, 1, 2, 3, 4}, IMG_DEAD);

		create();
	}

	/**
	 * 移動ベクトル
	 */
	float _dx = -1.0f;

	public void update() {
		super.update();

		//	プレイヤーとの衝突判定など
		playerProcess();

		//	物と衝突したら反転して移動
		if (getVx() <= 0 && !contactLeft.isEmpty() ||getVx() >= 0 && !contactRight.isEmpty()) {
			_dx = -_dx;
			_img.invertH();
		}
		setVx(_dx);

		if (invincibleTimer == 0) {
			if (_img.getPatternNo() != IMG_SQUASHED) {
				//	歩行グラフィックの更新
				if (_img.update())
					_img.setPatternNo(_img.getPatternNo() == IMG_WALK1 ? IMG_WALK2 : IMG_WALK1);
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
			//	踏んだ時の処理
			if (_img.getPatternNo() == IMG_SQUASHED) {
				//	元の形に戻り、プレイヤーを上に吹き飛ばす
				player.addForce(0, -POWER);
				_img.setPatternNo(IMG_WALK1);
				player.setY(player.getY() - _img.get(IMG_WALK1).getHeight() + _img.get(IMG_SQUASHED).getHeight());
			} else {
				//	ぺっちゃんこ画像に切り替える
				_img.setPatternNo(IMG_SQUASHED);
			}
			//	画像に合わせて、サイズを変更する
			setSize(_img.getWidth(), _img.getHeight());
		}
	}
}

