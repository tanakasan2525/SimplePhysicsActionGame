package a5812070.action2d.enemy;

import a5812070.action2d.GameBodyDef;
import a5812070.action2d.ObjectData;

/**
 * 敵モンスター『カタツムリ』
 *
 * ＜説明＞
 * 左右に歩きます。攻撃を受けると殻に籠もります。
 *
 * ＜パラメータ＞
 * HP：		40
 * 攻撃力：	10
 *
 * ＜パターン画像＞
 * 0番目：歩行１（デフォルト）
 * 1番目：ダメージ
 * 2番目：殻にこもる
 * 3番目：歩行２
 *
 * @author a5812070
 *
 */
public class Snail extends Enemy {
	private static final int IMG_WALK1 = 0;
	private static final int IMG_HIT = 1;
	private static final int IMG_SHELL = 2;
	private static final int IMG_WALK2 = 3;

	private static final int HP_MAX = 40;

	private static final int POWER = 700;	//	プレイヤーを吹き飛ばす力
	private static final int DAMAGE = 10;	//	プレイヤーに与えるダメージ

	/**
	 * 殻にこもる長さ
	 */
	private static final int SHELL_TIME = 360;

	/**
	 * 殻にこもっている時のダメージの低減率
	 */
	private static final double DEFENCE = 0.1;

	public Snail(ObjectData objdata) {
		super(objdata, HP_MAX, new int[]{0, 1, 2, 3}, IMG_SHELL);

		GameBodyDef.density = 2;

		create();

		setInvincibleTime(5);	//	0にすると多段ヒットしてしまうので微小な値にする
	}

	/**
	 * 移動ベクトル
	 */
	private float _dx = -1.0f;

	/**
	 * 0ではない時は殻にこもっている
	 */
	private int shellCount;

	public void update() {
		super.update();

		if (shellCount == 0) {

			//	プレイヤーとの衝突判定など
			playerProcess();

			//	物と衝突したら反転して移動
			if (getVx() <= 0 && !contactLeft.isEmpty() ||getVx() >= 0 && !contactRight.isEmpty()) {
				_dx = -_dx;
				_img.invertH();
			}
			setVx(_dx);

			//	歩行グラフィックの更新
			if (_img.update())
				_img.setPatternNo(_img.getPatternNo() == IMG_WALK1 ? IMG_WALK2 : IMG_WALK1);

		} else {
			if (--shellCount == 0) {
				int h = _img.getHeight();
				_img.setPatternNo(IMG_WALK1);
				setSize(_img.getWidth(), _img.getHeight());
				setY(getY() - _img.getHeight() + h);	//	地面にめり込まないようにサイズ分持ち上げる
			}
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
		}
	}

	@Override
	public int damage(int power) {

		shellCount = SHELL_TIME;

		power *= DEFENCE;

		//	殻にこもっている時のグラフィック
		_img.setPatternNo(IMG_SHELL);
		setSize(_img.getWidth(), _img.getHeight());

		return super.damage(power);
	}
}
