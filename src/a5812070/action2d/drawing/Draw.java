package a5812070.action2d.drawing;


public class Draw {

	static int DEFAULT_ALPHA = -1;
	static int DEFAULT_ROTATION = 0;

	private static IDraw draw;

	static int alpha = DEFAULT_ALPHA;

	static float rotation = DEFAULT_ROTATION;
	static float centerDx = 0;
	static float centerDy = 0;


	/**
	 * バックバッファのイメージをディスプレイに表示します。
	 */
	public static final void show() {
		draw.show();
	}

	/**
	 * 画像を表示します。
	 * @param img
	 * @param x
	 * @param y
	 */
	public static final void image(BImage img, float x, float y) {
		draw.image(img, x, y);
	}

	/**
	 * 画像を左右反転して表示します。
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void imageInverseH(BImage img, float x, float y) {
		draw.imageInverseH(img, x, y);
	}

	/**
	 * 塗りつぶされた矩形を表示します。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param c
	 */
	public static void fillRect(float x, float y, float width, float height, Color c) {
		draw.fillRect(x, y, width, height, c);
	}

	/**
	 * 塗りつぶされた矩形を表示します。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param c
	 */
	public static void fillRect(int x, int y, int width, int height, Color c) {
		fillRect((float)x, (float)y, (float)width, (float)height, c);
	}


	/**
	 * 描画の設定を初期値に戻します。設定を変更した場合は、描画後すぐにこのメソッドを呼ぶことを推奨します。
	 */
	public static final void reset() {
		alpha = DEFAULT_ALPHA;
		rotation = DEFAULT_ROTATION;
		centerDx = 0;
		centerDy = 0;
	}


	/**
	 * 使用する描画クラスを設定します。
	 * @param draw
	 */
	public static final void setDraw(IDraw draw) {
		Draw.draw = draw;
	}

	/**
	 * アルファ値を設定します。
	 * @param alpha	0～255までの値。（数値が小さいほど透明になります）
	 */
	public static final void setAlpha(int alpha) {
		Draw.alpha = alpha;
	}

	/**
	 * 回転量を設定します。
	 * @param value	回転量(ラジアン)
	 */
	public static final void setRotation(float value) {
		Draw.rotation = value;
	}

	/**
	 * 回転量を設定します。
	 * @param value	回転量(ラジアン)
	 * @param cdx	回転の中心位置のX成分をどれだけずらすか
	 * @param cdy	回転の中心位置のY成分をどれだけずらすか
	 */
	public static final void setRotation(float value, float cdx, float cdy) {
		Draw.rotation = value;
		Draw.centerDx = cdx;
		Draw.centerDy = cdy;
	}

}
