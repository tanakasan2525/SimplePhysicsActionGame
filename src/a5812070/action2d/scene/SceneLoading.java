package a5812070.action2d.scene;

import static a5812070.action2d.drawing.Draw.*;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.Color;
import a5812070.action2d.gameengine.GameEngine;

interface ILoading extends Runnable {
	public void finish();
}

class SceneLoading extends Scene {

	static BImage text = new BImage("scenes/loading/loading.png");
	static BImage icon = new BImage("scenes/loading/icon.gif");

	ILoading loading;
	Thread loadThread;

	int count;

	SceneLoading(ILoading loading) {
		this.loadThread = new Thread(loading);
		this.loading = loading;
		this.loadThread.start();
	}

	protected void update() {
		fillRect(0, 0, GameEngine.getWidth(), GameEngine.getHeight(), new Color(0, 0, 0));
		setRotation(count++ / 10 % 360);
		image(icon, 200, 200);
		reset();
		image(text, 250, 205);
		checkLoading();
	}

	protected void checkLoading() {
		if (!loadThread.isAlive()) {
			pop();
			loading.finish();
		}
	}
}

