package a5812070.action2d;

import a5812070.action2d.drawing.Color;
import a5812070.action2d.drawing.Draw;

public abstract class GameObject extends GameBody
{
	static final int DEFAULT_MAX_HP = 100;

	private final int HP_BAR_WIDTH = 50;
	private final int HP_BAR_HEIGHT = 5;

	/**
	 * HPを表示する時間
	 */
	private final int HP_SHOW_TIME = 120;

	/**
	 * 現在のHP
	 */
	private int _hp;

	/**
	 * HPの最大値
	 */
	private int _hpMax;

	/**
	 * HPを表示する残り時間
	 */
	private int _hpShowTimer;

	public GameObject() {
		_hpMax = DEFAULT_MAX_HP;
		_hp = _hpMax;
	}

	/**
	 * ダメージを与えます
	 * @param power　強さ
	 * @return	与えたダメージ量
	 */
	public int damage(int power) {
		_hp = _hp - power < 0 ? 0 : _hp - power;
		_hpShowTimer = HP_SHOW_TIME;
		return power;
	}

	/**
	 * HPを描画します。一定時間すると消えます。
	 */
	public final void drawHP() {
		if (_hpShowTimer-- <= 0) return;
		drawHPAlways();
	}

	/**
	 * HPを描画します。
	 */
	public void drawHPAlways() {
		float hpWidth = (float)HP_BAR_WIDTH * _hp / _hpMax;
		Draw.fillRect(getDrawX() - HP_BAR_WIDTH/2, getDrawTop() - 20, HP_BAR_WIDTH, HP_BAR_HEIGHT, new Color(0, 0, 0, 128));
		Draw.fillRect(getDrawX() - HP_BAR_WIDTH/2, getDrawTop() - 20, hpWidth, HP_BAR_HEIGHT, new Color(30, 255, 30, 128));
	}

	/**
	 * HPを設定します。
	 * @param hp
	 */
	public final void setHP(int hp) { _hp = hp; }

	/**
	 * 最大HPを設定します。
	 * @param hpMax
	 */
	public final void setHPMax(int hpMax) { _hpMax = _hp = hpMax; }

	/**
	 * カメラを考慮した描画時の使用する中心のX座標を取得します。
	 * @return
	 */
	public final float getDrawX() { return Camera.toDisplayX(getX()); }

	/**
	 * カメラを考慮した描画時の使用する中心のY座標を取得します。
	 * @return
	 */
	public final float getDrawY() { return Camera.toDisplayY(getY()); }

	/**
	 * カメラを考慮した描画時の使用する左側のX座標を取得します。
	 * @return
	 */
	public final float getDrawLeft() { return Camera.toDisplayX(getLeft()); }

	/**
	 * カメラを考慮した描画時に使用する上側のY成分を取得します。
	 * @return
	 */
	public final float getDrawTop() { return Camera.toDisplayY(getTop()); }

	/**
	 * HPを取得します。
	 * @return
	 */
	public final int getHP() { return _hp; }
}
