package a5812070.action2d.drawing;

import java.util.ArrayList;

public class PatternImage {

	private ArrayList<TileImage> img = new ArrayList<TileImage>();

	private int patternNo, patternTimer;

	private int patternWait;

	private boolean isInverseH;

	public PatternImage() {
		this(1);
	}

	/**
	 * 画像の切り替えの間隔を指定します。
	 * @param wait	画像の切り替え間隔
	 */
	public PatternImage(int wait) {
		setWait(wait);
	}

	/**
	 * 画像を表示します。
	 * @param x
	 * @param y
	 */
	public final void draw(float x, float y) {
		if (isInverseH)
			img.get(patternNo).drawInverseH(x, y);
		else
			img.get(patternNo).draw(x, y);
	}

	/**
	 * パターン画像を追加します。
	 * @param img	追加する画像
	 */
	public final void add(TileImage img) {
		this.img.add(img);
	}

	/**
	 * パターン画像を追加します。
	 * @param img	追加する画像
	 */
	public final void add(BImage img) {
		TileImage timg = new TileImage();
		timg.add(img, 0, 0);
		this.img.add(timg);
	}

	/**
	 * パターン画像を取得します。
	 * @param no	取得したいパターン番号
	 * @return	存在しない番号の場合、例外"ArrayIndexOutOfBounds"が発生します。
	 */
	public final TileImage get(int no) { return img.get(no); }

	/**
	 * 設定中のパターン番号を取得します。
	 * @return
	 */
	public final int getPatternNo() { return patternNo; }

	/**
	 * 画像の切り替え間隔を取得します。
	 * @return
	 */
	public final int getWaitTime() { return patternWait; }

	/**
	 * 現在のパターンの横幅を取得します。
	 * @return
	 */
	public final int getWidth() { return img.get(patternNo).getWidth(); }

	/**
	 * 現在のパターンの縦幅を取得します。
	 * @return
	 */
	public final int getHeight() { return img.get(patternNo).getHeight(); }

	/**
	 * draw()で表示するパターンを設定します。
	 * @param no	パターン番号
	 */
	public final void setPatternNo(int no) { patternNo = no; }

	/**
	 * 画像の切り替え間隔を設定します。
	 * @param wait	画像の切り替え間隔
	 */
	public final void setWait(int wait) { patternWait = wait; }

	/**
	 * パターンの時間を進めます。
	 * 切り替え間隔に達していた場合、次のパターンに切り替えます。
	 * パターンが終了した場合、先頭に戻ります。
	 * @return	次のパターンに切り替わった時はtrue、それ以外はfalse
	 */
	public final boolean next() {
		if (patternTimer++ % patternWait == 0) {
			patternNo = (patternNo + 1) % img.size();
			return true;
		}
		return false;
	}

	/**
	 * パターンの時間を進めます。
	 * @return	切り替え間隔に達していた時はtrue、それ以外はfalse
	 */
	public final boolean update() {
		return (patternTimer++ % patternWait == 0);
	}

	/**
	 * パターンの数を取得します。
	 * @return	パターン数
	 */
	public final int size() { return img.size(); }

	/**
	 * 画像を左右反転します。
	 */
	public final void invertH() { isInverseH = !isInverseH; }

	/**
	 * 画像の左右反転を行うかどうかを設定します。
	 * @param flag	反転したい場合はtrue
	 */
	public final void setInverseH(boolean flag) { isInverseH = flag; }

	/**
	 * 画像を左右反転しているかどうかを取得します。
	 * @return	反転している場合はtrue
	 */
	public final boolean isInverseH() { return isInverseH; }
}
