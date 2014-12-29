package a5812070.action2d.gameengine;



public class Input
{
	InputBase base = new InputBase();

	public boolean jump() {
		return base.z.isPressed();
	}

	public boolean left() {
		return base.left.isDowned();
	}

	public boolean right() {
		return base.right.isDowned();
	}

	public boolean dash() {
		return base.shift.isDowned();
	}

	public boolean attack() {
		return base.x.isDowned();
	}

	public boolean menu() {
		return base.c.isDowned();
	}

	public boolean release() {
		return base.x.getPressTime() > 40;
	}

	public boolean pointDowned() {
		return base.pointDowned;
	}

	public boolean pointPressed() {
		return base.pointPressed;
	}

	public boolean pointReleased() {
		return base.pointReleased;
	}

	public int pointX() {
		return base.pointX;
	}

	public int pointY() {
		return base.pointY;
	}

	/**
	*	call this method in last of main-loop.
	*/
	public void update() {
		base.update();
	}

	/**
	*	call this method in "keyPressed()".
	*/
	public void updateKeyPressed(int keycode) {
		base.updateKeyPressed(keycode);
	}

	/**
	*	call this method in "keyReleased()".
	*/
	public void updateKeyReleased(int keycode) {
		base.updateKeyReleased(keycode);
	}

	/**
	*	call this method in "mouseMoved()".
	*/
	public void updatePointMoved(int x, int y) {
		base.updatePointMoved(x, y);
	}

	/**
	*	call this method in "mousePressed()".
	*/
	public void updatePointPressed(int x, int y) {
		base.updatePointPressed(x, y);
	}

	/**
	*	call this method in "mouseReleased()".
	*/
	public void updatePointReleased(int x, int y) {
		base.updatePointReleased(x, y);
	}
}


