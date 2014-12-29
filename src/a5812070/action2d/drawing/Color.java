package a5812070.action2d.drawing;

/**
 * 色情報をカプセル化したクラスです。
 * @author a5812070
 *
 */
public class Color {

	java.awt.Color c;

	public static Color white = new Color(255, 255, 255);

	public Color(int r, int g, int b) {
		c = new java.awt.Color(r, g, b);
	}

	public Color(int r, int g, int b, int a) {
		c = new java.awt.Color(r, g, b, a);
	}

}
