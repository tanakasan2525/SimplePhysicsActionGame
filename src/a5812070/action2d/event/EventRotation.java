package a5812070.action2d.event;

import a5812070.action2d.GameBody;

public class EventRotation extends Event {

	GameBody gb;

	float rotation;

	float rotationMax;

	float x, y;

	float speed, speedRad;

	public EventRotation(GameBody gb, float rotation, float x, float y, float speed) {
		this.gb = gb;
		this.rotationMax = rotation;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.speedRad = (float) Math.toRadians(speed);
	}

	@Override
	protected void update() {

		if ((rotation += speed) >= rotationMax) {
			rotation = rotationMax;
			remove();
		}

		gb.rotate(speed, x, y);

	}

}