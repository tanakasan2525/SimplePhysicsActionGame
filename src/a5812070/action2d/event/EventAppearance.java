package a5812070.action2d.event;

import a5812070.action2d.GameBody;
import a5812070.action2d.drawing.Draw;

/**
 * ゲームのオブジェクトが出現するときのエフェクト
 * ※予め、GameWorldに登録しておく必要があります。
 * @author a5812070
 *
 */
public class EventAppearance extends Event {

	private GameBody gBody;

	private int count;

	private int speed;

	public EventAppearance(GameBody gBody, int time) {
		this.gBody = gBody;
		this.speed = 255 / time;
		gBody.setHidden(true);
	}

	@Override
	protected void update() {

		Draw.setAlpha(count);
		gBody.draw();
		Draw.reset();

		if ((count += speed) > 255) {
			gBody.setHidden(false);
			remove();
		}
	}

}