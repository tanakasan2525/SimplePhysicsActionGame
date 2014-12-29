package a5812070.action2d.scene;

import static a5812070.action2d.drawing.Draw.*;

import java.util.Stack;

import a5812070.action2d.Stage;
import a5812070.action2d.TiledWindow;
import a5812070.action2d.drawing.Color;
import a5812070.action2d.effect.Effect;
import a5812070.action2d.event.Event;
import a5812070.action2d.gameengine.GameEngine;

/**
 * シーンのベースクラス
 * @author 15812070
 *
 */
public class Scene {

	public Scene prev;
	public Scene next;

	/**
	 * シーンの初期化(画像など一度だけ読みこめばいいものは、ここではなく、コンストラクタに記述して下さい。)
	 */
	protected void init() { }

	/**
	 * シーンの更新
	 */
	protected void update() { }

	/**
	 * シーンの上にシーンを重ねます。
	 * @param s	上に重ねるシーン
	 */
	protected void addOverLayer(Scene s) {
		next = s;
		s.prev = this;
	}

	/**
	 * このシーンより上に重ねられているシーンを削除します。
	 */
	protected void removeOverLayer() {
		next = null;
	}

	/**
	 * このシーンが重ねられたシーンならこのシーンを含む上のシーンをすべて削除します。
	 */
	protected void removeThisLayer() {
		if (prev != null)
			prev.next = null;
	}

	private static Stack<Scene> _scenes = new Stack<Scene>();

	/**
	 * シーンを追加します。次からは追加されたシーンが実行されます。
	 * @param scene	追加するシーン
	 */
	public final static void push(Scene scene) {
		_scenes.push(scene);
		scene.init();
	}

	/**
	 * 実行中のシーンを削除します。
	 * @return	削除したシーン
	 */
	public final static Scene pop() {
		return _scenes.pop();
	}

	/**
	 * シーンスタックから複数のシーンを削除します。
	 * @param n	削除するシーンの数(例えば、２の時は実行中のシーンとその一つ前のシーンが削除されます)
	 */
	public final static void pop(int n) {
		for (int i = 0; i < n; ++i)
			_scenes.pop();
	}

	/**
	 * シーンを全て削除します。
	 */
	public final static void clear() {
		_scenes.clear();
	}

	/**
	 * 現在のシーンを取得します。
	 * @return
	 */
	public final static Scene top() {
		return _scenes.peek();
	}

	/**
	 * シーンを実行します。このメソッドは１フレームに１度しか呼ぶことはできません。
	 */
	public final static void execute() {
		for (Scene next = _scenes.peek(); next != null; next = next.next)
			next.update();
		Event.execute();
		Effect.execute();
	}
}


/**
 * ゲームクリア時に実行するシーン
 * @author a5812070
 *
 */
class SceneGameClear extends Scene {

	TiledWindow window;

	SceneGameClear() {
		window = new TiledWindow("scenes/gameclear/gameclear.tmx");
	}

	protected void update() {

		fillRect(0, 0, GameEngine.getWidth(), GameEngine.getHeight(), new Color(0, 0, 0, 128));

		window.update();
		window.draw();
		Stage.inst().drawScore(300, 250);

		if (window.isSelected()) {
			pop();
		}
	}
}

/**
 * ゲームオーバー時に実行するシーン
 * @author a5812070
 *
 */
class SceneGameOver extends Scene {

	TiledWindow window;

	SceneGameOver() {
		window = new TiledWindow("scenes/gameover/gameover.tmx");
	}

	protected void update() {
		fillRect(0, 0, GameEngine.getWidth(), GameEngine.getHeight(), new Color(0, 0, 0, 128));

		window.update();
		window.draw();

		if (window.isSelected()) {
			pop();
		}
	}

}

