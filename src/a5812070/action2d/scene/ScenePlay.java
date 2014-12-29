package a5812070.action2d.scene;

import a5812070.action2d.GameWorld;
import a5812070.action2d.Stage;
import a5812070.action2d.gameengine.GameEngine;

class ScenePlay extends Scene implements ILoading {

	String mapPath;

	ScenePlay(String path) {
		mapPath = path;
		Stage.inst().setPaused(false);
	}

	public void run() {
		Stage.inst().load(mapPath);
	}

	public void finish() {
		push(this);
	}

	protected void update() {
		Stage.inst().drawBackground();

		if (!Stage.inst().isPaused()) {
			GameWorld.inst().update();

			Stage.inst().update();
		}

		GameWorld.inst().draw();

		Stage.inst().drawInfo();

		if (GameEngine.getInput().menu())
			menu();

		//	judge whether the state is gameover
		if (Stage.inst().isGameOver()) {
			gameover(); return;
		} else if (Stage.inst().isGameClear()) {
			gameclear(); return;
		}
	}

	void menu() {
		Stage.inst().setPaused(true);
		addOverLayer(new ScenePlayMenu());
	}

	void gameover() {
		Stage.inst().setPaused(true);
		addOverLayer(new SceneGameOver());
	}

	void gameclear() {
		Stage.inst().setPaused(true);
		addOverLayer(new SceneGameClear());
	}

}
