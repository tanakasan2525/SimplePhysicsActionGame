package a5812070.action2d.scene;

import a5812070.action2d.TiledWindow;

public class SceneStageSelect extends Scene {

	TiledWindow window;

	public SceneStageSelect() {
		window = new TiledWindow("scenes/stageselect/stageselect.tmx");
	}

	protected void update() {
		window.update();
		window.draw();

		if (window.isSelected()) {
			push(new SceneLoading(new ScenePlay(window.getStr("mapPath"))));
			window.unselect();
		}
	}

}