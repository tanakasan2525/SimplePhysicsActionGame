package a5812070.action2d.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import a5812070.action2d.Camera;
import a5812070.action2d.GameWorld;
import a5812070.action2d.Stage;


public class EventMoveCamera extends Event {

	private float defX, defY;

	private ArrayList<CameraAction> act;

	public EventMoveCamera() {
		defX = Camera.getX();
		defY = Camera.getY();

		act = new ArrayList<CameraAction>();
	}

	@Override
	protected void init() {
		for (CameraAction ca : act)
			ca.init();
	}

	@Override
	protected void update() {

		Stage.inst().setPaused(true);

		CameraAction ca = act.get(0);

		if (ca.countdown == 0) {
			if (!ca.action.run()) return;
			act.remove(ca);
			if (act.isEmpty()) {
				Stage.inst().setPaused(false);
				remove();
			} else {
				act.get(0).init();
			}
		} else {
			ca.countdown--;
		}

		Camera.moveDirectly(ca.vx, ca.vy);
	}

	/**
	 * カメラの行動を追加します。
	 * @param x	どこに向かうか
	 * @param y	どこに向かうか
	 * @param time	何秒で到着するか
	 * @param action	着いたら何をするか(nullの時は、元の場所にカメラを移動するモードになります)
	 */
	public void add(float x, float y, int time, IEventAction action) {
		if (action == null)
			act.add(new CameraAction(defX, defY, time, null));
		else
			act.add(new CameraAction(x, y, time, action));
	}

	/**
	 * 現在の位置から近い順番にカメラの行動を並べ替えます。
	 */
	public void sort() {
		Collections.sort(act, new ActionComparator());
	}

	private class CameraAction {

		public float x, y;				//	どこに向かうか
		public int time;
		public IEventAction action;

		public float vx, vy;
		public int countdown;

		public CameraAction(float x, float y, int time, IEventAction action) {
			this.x = x;
			this.y = y;
			this.time = time;
			this.action = action;
		}

		public void init() {
			if (action == null) {
				vx = (float) Math.sqrt(Float.MAX_VALUE) / 2;
				vy = vx;
			} else {
				vx = x - Camera.getX() / time;
				vy = y - Camera.getY() / time;
			}

			countdown = time * GameWorld.FPS;

			vx /= countdown;
			vy /= countdown;
		}

	}

	private class ActionComparator implements Comparator<CameraAction> {

		@Override
		public int compare(CameraAction o1, CameraAction o2) {
			float a = o1.vx*o1.vx+o1.vy*o1.vy;
			float b = o2.vx*o2.vx+o2.vy*o2.vy;
			return (int) ((a - b) * 100000);
		}

	}

}
