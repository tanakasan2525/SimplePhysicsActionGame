package a5812070.action2d.drawing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 画像をカプセル化したクラスです。
 * @author a5812070
 *
 */
public class BImage {

	private BufferedImage img;

	private BImage() {}

	/**
	 * ファイルから画像を読み取ります。
	 * @param filepath	画像ファイルへのパス
	 */
	public BImage(String filepath) {
		try {
			img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 画像を描画します。
	 * @param x
	 * @param y
	 */
	public void draw(float x, float y) {
		Draw.image(this, x, y);
	}

	public BImage getSubimage(int x, int y, int w, int h) {
		BImage ret = new BImage();
		ret.img = img.getSubimage(x, y, w, h);
		return ret;
	}

	/**
	 * 画像の横幅を取得します。
	 * @return
	 */
	public int getWidth() { return img.getWidth(); }

	/**
	 * 画像の縦幅を取得します。
	 * @return
	 */
	public int getHeight() { return img.getHeight(); }

	BufferedImage getImage() { return img; }	//	no public
}
