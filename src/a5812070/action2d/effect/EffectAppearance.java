package a5812070.action2d.effect;

import a5812070.action2d.Camera;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.PatternImage;


public class EffectAppearance extends Effect {

	/**
	 * エフェクトのX軸方向の間隔
	 */
	private static int INTERVAL = 60;

	private static PatternImage img;

	protected float x, y;

	private float maxX;

	private int no;

	private int wait;

	private int time;

	static {
		BImage baseImg = new BImage("stage/img/effect/appear.png");
		img = new PatternImage(5);

		for (int i = 0; i < 14; ++i) {
			img.add(baseImg.getSubimage(i * 120, 0, 120, 120));
		}
	}

	protected EffectAppearance(float x, float y, float maxX, int time) {
		this.x = x - 60;
		this.y = y - 60;

		this.maxX = maxX;

		int count = (int) ((maxX - x) / INTERVAL) + 1;

		int framePerEffect = time / count;

		this.time = framePerEffect / img.getWaitTime();
	}

	protected void update() {
		img.setPatternNo(no);
		img.draw(Camera.toDisplayX(x), Camera.toDisplayY(y));

		if (wait == time) {
			if (maxX - x + 60 > 120) {
				EffectAppearance ea = new EffectAppearance(x + 60 + INTERVAL, y + 60, maxX, 0);
				ea.time = time;
				add(ea);
			}
		}

		if (++wait % img.getWaitTime() == 0) {
			switch (++no % img.size()) {
			case 0:
				remove();
				break;
			}
		}
	}
}
