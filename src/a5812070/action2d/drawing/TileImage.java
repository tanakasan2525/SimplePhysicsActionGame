package a5812070.action2d.drawing;

import static a5812070.action2d.drawing.Draw.*;

import java.util.ArrayList;

public class TileImage {
	ArrayList<TileImageItem> imgs = new ArrayList<TileImageItem>();

	private float minX, maxX;			//	for calculation
	private float minY, maxY;			//	for calculation
	private int w, h;

	public TileImage() {
		minX = Float.MAX_VALUE;
		minY = Float.MAX_VALUE;
		maxX = -Float.MAX_VALUE;
		maxY = -Float.MAX_VALUE;
	}

	/**
	 * タイルイメージを追加します。
	 * @param img	追加する画像
	 * @param x		表示する相対X座標
	 * @param y		表示する相対Y座標
	 */
	public final void add(BImage img, float x, float y) {
		BImage img_ = img;
		if (minX > x) minX = x;
		if (maxX < x + img_.getWidth()) maxX = x + img_.getWidth();
		if (minY > y) minY = y;
		if (maxY < y + img_.getHeight()) maxY = y + img_.getHeight();

		w = (int)(maxX - minX);
		h = (int)(maxY - minY);

		imgs.add(new TileImageItem(img, x, y));
	}

	/**
	 * 画像を表示します。
	 * @param x
	 * @param y
	 */
	public final void draw(float x, float y) {
		float cx = w / 2.f;
		float cy = h / 2.f;
		float angle = Draw.rotation;

		for (TileImageItem img : imgs) {
			float cdx = cx - (img.x + img.img.getWidth() / 2.f);
			float cdy = cy - (img.y + img.img.getHeight() / 2.f);
			Draw.centerDx += cdx;
			Draw.centerDy += cdy;
			image(img.img, x + img.x, y + img.y);
			Draw.centerDx -= cdx;
			Draw.centerDy -= cdy;
		}
	}

	/**
	 * 画像を左右反転して表示します。
	 * @param x
	 * @param y
	 */
	public final void drawInverseH(float x, float y) {
		for (TileImageItem img : imgs) {
			imageInverseH(img.img, x + img.x, y + img.y);
		}
	}

	/**
	 * 追加したすべての画像の位置を移動します。
	 * @param dx
	 * @param dy
	 */
	public final void move(float dx, float dy) {
		for (TileImageItem img : imgs) {
			img.x += dx;
			img.y += dy;
		}
	}

	/**
	 * no番目に追加したタイルを取得します。
	 * @param no
	 * @return
	 */
	public final BImage get(int no) { return imgs.get(no).img; }

	/**
	 * タイルの数を取得します。
	 * @return
	 */
	public final int count() { return imgs.size(); }

	/**
	 * タイルを並べた時の横幅を取得します。
	 * @return
	 */
	public final int getWidth() { return this.w; }

	/**
	 * タイルを並べた時の縦幅を取得します。
	 * @return
	 */
	public final int getHeight() { return this.h; }

	class TileImageItem {
		BImage img;
		float x, y;
		TileImageItem(BImage img, float x, float y) {
			this.img = img;
			this.x = x;
			this.y = y;
		}
	}
}