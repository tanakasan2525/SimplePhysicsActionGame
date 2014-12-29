package a5812070.action2d;

import a5812070.action2d.gameengine.GameEngine;

public class Camera {
	private static float x, y;
	private static float minPosX, maxPosX, centerPosX;
	private static float minPosY, maxPosY, centerPosY;

	public static final void move(float playerPosX, float playerPosY) {
		x = playerPosX - centerPosX;
		if (x < minPosX)  x = minPosX;
		else if (x >= maxPosX) x = maxPosX;

		y = playerPosY - centerPosY;
		if (y < minPosY)  y = minPosY;
		else if (y >= maxPosY) y = maxPosY;
	}

	public static final void moveDirectly(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public static final void reset() {
		x = y = minPosX = minPosY = 0.001f;
	}

	public static final void setPosition(float x_, float y_) { x = x_; y = y_; }

	//public static final void setMinX(float pos) { minPosX = pos; }
	public static final void setMaxX(float pos) { maxPosX = pos - GameEngine.getWidth(); }
	public static final void setCenterX(float pos) { centerPosX = pos; }  //  call before other methods
	public static final void setMaxY(float pos) { maxPosY = pos - GameEngine.getHeight(); }
	public static final void setCenterY(float pos) { centerPosY = pos; }  //  call before other methods

	public static final void setX(float x) { Camera.x = x - centerPosX; }
	public static final void setY(float y) { Camera.y = y - centerPosY; }

	public static final float getX() { return x + centerPosX; }
	public static final float getY() { return y + centerPosY; }

	public static final float toDisplayX(float x) { return x - Camera.x; }
	public static final float toDisplayY(float y) { return y - Camera.y; }

	public static final float getMaxY() { return maxPosY + GameEngine.getHeight(); }
}
