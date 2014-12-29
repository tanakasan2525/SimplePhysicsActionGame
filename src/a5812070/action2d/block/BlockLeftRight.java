package a5812070.action2d.block;

import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockLeftRight extends Block
{
	float minX, maxX, speed;
	BlockLeftRight(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		setDefaultProperties(properties);
		create();

		minX = getX() + 1;
		maxX = getX() + 100;
		speed = 1;

		float distance = properties.getFloat("Distance", Float.MAX_VALUE);
		speed = properties.getFloat("Speed", speed);

		if (distance != Float.MAX_VALUE) {
			if (distance > 0) {
				maxX = getX() + distance;
			} else {
				maxX = minX;
				minX = getX() + distance;
			}
		}
	}

	public void update() {
		if (getX() <= minX && getVx() < 0 || getX()+getVx() >= maxX && getVx() > 0) { speed = -speed; }
		cancelGravity();
		setVelocity(speed, 0);
	}

	@Override
	public int damage(int power) { return 0; }
}
