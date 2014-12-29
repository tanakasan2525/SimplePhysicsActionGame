package a5812070.action2d.gameengine;

class GameKey {
	private int pressCount;

	/**
	 * 毎フレーム呼んで下さい。
	 */
	public final void update() {
		if (pressCount > 0)
			++pressCount;
		else
			pressCount = 0;
	}

	/**
	 * キーが押された時に呼んで下さい。
	 */
	public final void press() {
		if (pressCount == 0) pressCount = 1;
	}

	/**
	 * キーが離された時に呼んで下さい。
	 */
	public final void release() {
		pressCount = -1;
	}

	/**
	 * キーが押された瞬間かどうかを取得します。
	 * @return
	 */
	public final boolean isPressed() {
		return pressCount == 1;
	}

	/**
	 * キーが押されたままになっているかを取得します。
	 * @return
	 */
	public final boolean isDowned() {
		return pressCount > 0;
	}

	/**
	 * キーが離された瞬間かどうかを取得します。
	 * @return
	 */
	public final boolean isReleased() {
		return pressCount == -1;
	}

	/**
	 * キーが押されている時間(フレーム数)を取得します。
	 * @return
	 */
	public final int getPressTime() {
		return pressCount;
	}
}
