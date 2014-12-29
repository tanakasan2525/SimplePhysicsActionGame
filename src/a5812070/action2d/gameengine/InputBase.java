package a5812070.action2d.gameengine;

import java.awt.event.KeyEvent;

class InputBase
{
	public GameKey left = new GameKey();
	public GameKey right = new GameKey();
	public GameKey up = new GameKey();
	public GameKey down = new GameKey();
	public GameKey c = new GameKey();
	public GameKey x = new GameKey();
	public GameKey z = new GameKey();
	public GameKey shift = new GameKey();

	public int pointX, pointY;

	public boolean pointDowned;
	public boolean pointPressed;
	public boolean pointReleased;

	public static int threadLock = 0;

	InputBase() {
	}

	/**
	*	call this method in last of main-loop.
	*/
	void update() {
		left.update();
		right.update();
		up.update();
		down.update();
		c.update();
		x.update();
		z.update();
		shift.update();
		pointPressed = false;
		pointReleased = false;
	}

	/**
	*	call this method in "keyPressed()".
	*/
	void updateKeyPressed(int keycode) {
		switch(keycode) {
			case KeyEvent.VK_LEFT:	left.press(); return;
			case KeyEvent.VK_RIGHT:	right.press(); return;
			case KeyEvent.VK_UP:	up.press(); return;
			case KeyEvent.VK_DOWN:	down.press(); return;
			case KeyEvent.VK_SHIFT:	shift.press(); return;
			case KeyEvent.VK_C:		c.press(); return;
			case KeyEvent.VK_X:		x.press(); return;
			case KeyEvent.VK_Z:		z.press(); return;
		}
	}

	/**
	*	call this method in "keyReleased()".
	*/
	void updateKeyReleased(int keycode) {
		switch(keycode) {
			case KeyEvent.VK_LEFT:	left.release(); return;
			case KeyEvent.VK_RIGHT:	right.release(); return;
			case KeyEvent.VK_UP:	up.release(); return;
			case KeyEvent.VK_DOWN:	down.release(); return;
			case KeyEvent.VK_SHIFT:	shift.release(); return;
			case KeyEvent.VK_C:		c.release(); return;
			case KeyEvent.VK_X:		x.release(); return;
			case KeyEvent.VK_Z:		z.release(); return;
		}
	}

	void updatePointMoved(int x, int y) {
		pointX = x;
		pointY = y;
	}

	void updatePointPressed(int x, int y) {
		pointPressed = pointDowned = true;
	}

	void updatePointReleased(int x, int y) {
		pointDowned = false;
		pointReleased = true;
	}
}
