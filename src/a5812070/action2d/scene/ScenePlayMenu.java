package a5812070.action2d.scene;

import a5812070.action2d.Stage;
import a5812070.action2d.TiledWindow;
import a5812070.action2d.drawing.Color;
import a5812070.action2d.drawing.Draw;
import a5812070.action2d.gameengine.GameEngine;

/**
 * ゲームプレイ中のメニュー画面
 * @author a5812070
 *
 */
public class ScenePlayMenu extends Scene {

	TiledWindow window;

	public ScenePlayMenu() {
		window = new TiledWindow("scenes/play/menu/menu.tmx");
	}

	protected void update() {
		window.update();

		Draw.fillRect(0, 0, GameEngine.getWidth(), GameEngine.getHeight(), new Color(0, 0, 0, 180));
		window.draw();

		if (window.isSelected()) {
			String ret = window.getStr("Return");
			if (ret.equals("close")) {
				Stage.inst().setPaused(false);
				removeThisLayer();
			}else if (ret.equals("goSelectStage")) {
				pop();
			} else {
				pop(2);
				top().init();
			}
			window.unselect();
		}
	}
}
