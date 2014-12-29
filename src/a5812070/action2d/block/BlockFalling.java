package a5812070.action2d.block;

import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockFalling extends Block
{
	int MAX_COUNT = 10;
	int count;
	BlockFalling(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		setDefaultProperties(properties);
		create();
		count = 0;

		MAX_COUNT = properties.getInt("Timer", MAX_COUNT);
	}

	public void update() {
		cancelGravity();
		if (count == 0) {
			if (!contactTop.isEmpty()) {
				count = 1;
			}
		} else if (++count > MAX_COUNT) {
			setVy(4);
		}
	}

	@Override
	public int damage(int power) { return 0; }
}

