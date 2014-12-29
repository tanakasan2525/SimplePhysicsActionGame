package a5812070.action2d.effect;

import a5812070.action2d.Camera;
import a5812070.action2d.drawing.Draw;
import a5812070.action2d.drawing.TileImage;


public class EffectEnemyDead extends Effect {

	TileImage img;

	float x, y;

	int count;

	public EffectEnemyDead(TileImage img, float x, float y) {
		this.img = img;
		this.x = x - img.getWidth() / 2;
		this.y = y - img.getHeight() / 2;

		this.count = 255;
	}

	@Override
	public void update() {
		count = count - 2 < 0 ? 0 : count - 2;
		Draw.setAlpha(count);
		img.draw(Camera.toDisplayX(x), Camera.toDisplayY(y));
		Draw.reset();

		if (count == 55)
			addDead(x + img.getWidth() / 2, y + img.getHeight() / 2);

		else if (count == 0)
			remove();
	}
}
