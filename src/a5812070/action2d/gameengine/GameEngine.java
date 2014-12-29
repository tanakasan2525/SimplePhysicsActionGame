package a5812070.action2d.gameengine;

import java.io.InputStream;


public class GameEngine {


	private static GamePanel gamePanel;
	private static String title;
	private static int width, height;

	public void doMain() { }

	public static final void init(GamePanel panel, int width, int height) {
		GameEngine.gamePanel = panel;
		GameEngine.width = width;
		GameEngine.height = height;
		gamePanel.initialize(width, height);
	}

	/**
	 * アプリケーションとして開始します。(モバイル端末は不可)
	 * @param panel
	 */
	public static final void startApplication(GamePanel panel) {
		panel.init();
		panel.startApplication();
		panel.start();
	}

	/**
	 * タイトルを設定します。
	 * @param name
	 */
	public static final void setTitle(String name) { GameEngine.title = name; }

	/**
	 * タイトルを取得します。
	 * @return
	 */
	public static final String getTitle() { return GameEngine.title; }

	/**
	 * ウィンドウの横幅を取得します。
	 * @return
	 */
	public static final int getWidth() { return width; }

	/**
	 * ウィンドウの縦幅を取得します。
	 * @return
	 */
	public static final int getHeight() { return height; }

	/**
	 * 環境に依存しないファイル用InputStreamを取得します。
	 * @param path
	 * @return
	 */
	public static final InputStream getInputStream(String path) {
		return gamePanel.getInputStream(path);
	}

	/**
	 * 入力管理クラスを取得します。
	 * @return
	 */
	public static final Input getInput() {
		return gamePanel.getInput();
	}

	/**
	 * 画面の拡大率を取得します。
	 */
	public static final float getScale() {
		return gamePanel.getScale();
	}
}
