package a5812070.action2d;

import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.gameengine.GamePanel;

public class Action2DApplet extends GamePanel {

	@Override
	public void init() {
		GameEngine.init(this, 640, 480);
		GameEngine.setTitle("Physical Jumper");
	}
}
