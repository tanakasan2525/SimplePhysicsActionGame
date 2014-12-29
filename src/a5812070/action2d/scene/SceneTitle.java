package a5812070.action2d.scene;

import a5812070.action2d.Camera;
import a5812070.action2d.GameBody;
import a5812070.action2d.GameWorld;
import a5812070.action2d.TiledWindow;
import a5812070.action2d.block.BlockNormal;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.drawing.TileImage;
import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.gameengine.Input;

public class SceneTitle extends Scene {

	TiledWindow window;

	PatternImage boxImg;
	PatternImage groundImg;

	Input input;

	int w, h;

	GameBody body;

	public SceneTitle() {
		window = new TiledWindow("scenes/title/title.tmx");
		boxImg = new PatternImage();
		boxImg.add(new BImage("stage/img/tiles_spritesheet/box.png"));

		w = boxImg.getWidth() / 2;
		h = boxImg.getHeight() / 2;
		input = GameEngine.getInput();


		groundImg = new PatternImage();
		TileImage gtile = new TileImage();
		BImage ground = new BImage("stage/img/tiles_spritesheet/grassHalfMid.png");
		for (int x = 0; x < GameEngine.getWidth(); x += ground.getWidth()) {
			gtile.add(ground, x, 0);
		}
		groundImg.add(gtile);
	}

	@Override
	protected void init() {
		Camera.reset();
		GameWorld.inst().reset();
		GameBody obj = new BlockNormal(groundImg, 0, GameEngine.getHeight() - groundImg.getHeight(), 0);
		GameWorld.inst().add(obj);
	}

	protected void update() {
		window.update();
		window.draw(0);

		GameWorld.inst().update();
		GameWorld.inst().draw();

		for (int i = 1; i < window.getComponentCount(); ++i)
			window.draw(i);

		if (input.pointPressed()) {
			body = GameWorld.inst().get(input.pointX(), input.pointY());
			if (body != null) body = body.isStatic() ? null : body;	//	地面は除く
			if (body == null)
				GameWorld.inst().add(new BlockNormal(boxImg, input.pointX() - w, input.pointY() - h, .1f));

		} else if (input.pointReleased()) {
			body = null;
		} else {
			if (body != null) {
				body.setX(input.pointX());
				body.setY(input.pointY());

			}
		}

		if (window.isSelected()) {
			push(new SceneStageSelect());
			window.unselect();
		}
	}

}
