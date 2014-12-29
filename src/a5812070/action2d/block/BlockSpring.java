package a5812070.action2d.block;

import a5812070.action2d.GameBody;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockSpring extends Block {

	/**
	 * 跳ね返るまでの時間
	 */
	private final static int SPRING_TIME = 60;

	/**
	 * 跳ね返すときの力
	 */
	private final static int POWER = 400;

	int count;

	BlockSpring(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		setDefaultProperties(properties);
		create();
	}

	public void update() {
		if (!contactTop.isEmpty()) {
			if (++count == 1) {

			} else if (count > SPRING_TIME) {
				count = 0;
				_img.setPatternNo(count < SPRING_TIME ? 1 : 0);
				for (GameBody b : contactTop) {
					b.setY(getY() - b.getHalfHeight() * 2);
					b.addForce(0, -POWER);
				}
			}

			for (GameBody b : contactTop) {
				b.setY(getY() - b.getHalfHeight() * 2);
			}
		}
	}

	@Override
	public int damage(int power) { return 0; }
}
