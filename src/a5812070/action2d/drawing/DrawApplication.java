package a5812070.action2d.drawing;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import a5812070.action2d.gameengine.GamePanel;

public class DrawApplication implements IDraw {

	private Graphics2D backBuffer;
	private Image backBufferImage;
	private GamePanel panel;

	private int width, height;

	private Composite defaultComposite;

	public DrawApplication(GamePanel panel) throws Exception {
		this.panel = panel;
		this.backBufferImage = panel.createImage(panel.getWidth(), panel.getHeight());
		if (backBufferImage == null) throw new Exception("Back Buffer Image is null.");
		this.backBuffer = (Graphics2D) backBufferImage.getGraphics();
		width = panel.getWidth();
		height = panel.getHeight();
		defaultComposite = backBuffer.getComposite();
	}

	/**
	 * バックバッファのイメージをディスプレイに表示します。
	 */
	public void show() {
		panel.getPanelGraphics().drawImage(backBufferImage, 0, 0, null);

		backBuffer.setColor(Color.white.c);
        backBuffer.fillRect(0, 0, width, height);
	}

	/**
	 * 画像を表示します。
	 */
	public void image(BImage img, float x, float y) {
		BufferedImage img_ = img.getImage();
		float cx = x + img_.getWidth() / 2f + Draw.centerDx;
		float cy = y + img_.getHeight() / 2f + Draw.centerDy;
		backBuffer.rotate(Draw.rotation, cx, cy);
		if (Draw.alpha == Draw.DEFAULT_ALPHA)
			backBuffer.drawImage(img_, (int)x, (int)y, null);
		else {
			backBuffer.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Draw.alpha / 255f));
			backBuffer.drawImage(img_, (int)x, (int)y, null);
			backBuffer.setComposite(defaultComposite);
		}
		backBuffer.rotate(-Draw.rotation, cx, cy);
	}

	/**
	 * 画像を左右反転して表示します。
	 */
	public void imageInverseH(BImage img, float x, float y) {
		BufferedImage img_ = img.getImage();
		float cx = x + img_.getWidth() / 2f + Draw.centerDx;
		float cy = y + img_.getHeight() / 2f + Draw.centerDy;
		backBuffer.rotate(Draw.rotation, cx, cy);
		if (Draw.alpha == Draw.DEFAULT_ALPHA)
			backBuffer.drawImage(img_, (int)x + img_.getWidth(), (int)y, -img_.getWidth(), img_.getHeight(), null);
		else {
			backBuffer.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Draw.alpha / 255f));
			backBuffer.drawImage(img_, (int)x + img_.getWidth(), (int)y, -img_.getWidth(), img_.getHeight(), null);
			backBuffer.setComposite(defaultComposite);
		}
		backBuffer.rotate(-Draw.rotation, cx, cy);
	}

	/**
	 * 塗りつぶされた矩形を表示します。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param c
	 */
	public void fillRect(float x, float y, float width, float height, Color c) {
		backBuffer.setColor(c.c);
		backBuffer.fillRect((int)x, (int)y, (int)width, (int)height);
	}

}
