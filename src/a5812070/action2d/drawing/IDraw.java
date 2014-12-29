package a5812070.action2d.drawing;

public interface IDraw {

	/**
	 * バックバッファのイメージをディスプレイに表示します。
	 * @param g
	 */
	public void show();

	/**
	 * 画像を表示します。
	 * @param img
	 * @param x
	 * @param y
	 */
	public void image(BImage img, float x, float y);

	/**
	 * 画像を左右反転して表示します。
	 * @param img
	 * @param x
	 * @param y
	 */
	public void imageInverseH(BImage img, float x, float y);

	/**
	 * 塗りつぶされた矩形を表示します。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param c
	 */
	public void fillRect(float x, float y, float width, float height, Color c);

}
