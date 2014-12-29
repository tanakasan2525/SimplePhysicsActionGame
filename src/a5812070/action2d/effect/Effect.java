package a5812070.action2d.effect;

import a5812070.action2d.Camera;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.drawing.TileImage;

/**
 * ゲームエフェクト管理クラス
 * @author a5812070
 *
 */
public abstract class Effect {

	private static Effect effects = new EffectDummy();

	private Effect prev, next;

	protected abstract void update();

	protected void remove() {
		prev.next = next;
		if (next != null)
			next.prev = prev;
	}

	public static void execute() {
		for (Effect e = effects.next; e != null; e = e.next) {
			e.update();
		}
	}

	public static void add(Effect effect) {
		effect.prev = effects;
		effect.next = effects.next;
		if (effect.next != null)
			effect.next.prev = effect;
		effects.next = effect;
	}

	/**
	 * ゲームのオブジェクトが出現するときのエフェクト
	 * @param x
	 * @param y
	 * @param width
	 * @param time	何フレームでエフェクトを行うか
	 */
	public static void addAppearance(float x, float y, float width, int time) {
		add(new EffectAppearance(x, y, x + width, time));
	}

	/**
	 * 敵がやられた時のエフェクト
	 * @param img	敵の画像
	 * @param x
	 * @param y
	 */
	public static void addEnemyDead(TileImage img, float x, float y) {
		add(new EffectEnemyDead(img, x, y));
	}

	/**
	 * やられた時のエフェクト
	 * @param x
	 * @param y
	 */
	public static void addDead(float x, float y) {
		add(new EffectDead(x, y));
	}

	/**
	 * 剣で切りつけた時のエフェクト
	 * @param x
	 * @param y
	 */
	public static void addSlash(float x, float y) {
		add(new EffectSlash(x, y));
	}
}

class EffectDummy extends Effect { protected void update() {} }

class EffectDead extends Effect {

	protected PatternImage img;

	protected float x, y;

	public EffectDead(float x, float y) {
		BImage baseImg = new BImage("stage/img/effect/dead.png");
		img = new PatternImage(16);

		for (int i = 0; i < 8; ++i) {
			img.add(baseImg.getSubimage(120 * i, 0, 120, 120));
		}

		this.x = x - 60;
		this.y = y - 80;
	}

	protected void update() {
		img.draw(Camera.toDisplayX(x), Camera.toDisplayY(y));
		if (img.next() && img.getPatternNo() == 0)
			remove();
	}
}
