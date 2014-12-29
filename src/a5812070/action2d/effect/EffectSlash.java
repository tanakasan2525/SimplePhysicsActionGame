package a5812070.action2d.effect;

import a5812070.action2d.Camera;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.PatternImage;


public class EffectSlash extends Effect {

	private static PatternImage img;

	protected float x, y;

	private int no;

	private int wait;

	static {
		BImage baseImg = new BImage("stage/img/effect/slash.png");
		img = new PatternImage(1);

		for (int i = 0; i < 9; ++i) {
			img.add(baseImg.getSubimage(i * 120, 0, 120, 120));
		}
	}

	protected EffectSlash(float x, float y) {
		this.x = x - 60;
		this.y = y - 60;
	}

	protected void update() {
		img.setPatternNo(no);
		img.draw(Camera.toDisplayX(x), Camera.toDisplayY(y));

		if (++wait % img.getWaitTime() == 0) {
			if (++no % img.size() == 0) {
				remove();
			}
		}
	}
}

