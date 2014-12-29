package a5812070.action2d.block;

import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockUpDown extends Block
{
	float speed, minY, maxY, defaultY;
	int distance;
	int type;

	BlockUpDown(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		setDefaultProperties(properties);
		create();

		distance = -100;
		speed = -1;
		defaultY = getY() - 1;
		type = 0;

		distance = properties.getInt("Distance", distance);
		speed = -properties.getFloat("Speed", -speed);
		type = properties.getInt("Type", type);

		if (distance < 0) {
			maxY = defaultY;
			minY = maxY + distance;
			speed = -Math.abs(speed);
		} else {
			minY = defaultY;
			maxY = minY + distance;
			speed = Math.abs(speed);
		}
	}

	public void update() {
		cancelGravity();
		setVelocity(0, speed);
		if (type == 0) {
			if (getY() < minY && getVy() < 0 || getY() > maxY && getVy() > 0) {
				if (type == 0) {
					speed = -speed;
				}
			}
		} else {	// type == 1
			if (Math.abs(defaultY - getY()) > Math.abs(distance)) {
				setY(defaultY);
			}
		}
	}

	@Override
	public int damage(int power) { return 0; }

}
