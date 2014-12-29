package a5812070.action2d.event;

import a5812070.action2d.GameBody;

/**
 * イベント管理クラス
 * @author a5812070
 *
 */
public abstract class Event {

	private static Event events = new EventDummy();

	private Event prev, next;

	private Event observer;

	private boolean removedFlag = true;

	protected void init() { }

	protected abstract void update();

	protected void remove() {
		prev.next = next;
		if (next != null)
			next.prev = prev;
		removedFlag = true;
	}

	/**
	 * 指定のイベントが終わるまで、このイベントを実行しないようにします。
	 * @param event
	 */
	public void observe(Event event) {
		observer = event;
	}

	public static void execute() {
		for (Event e = events.next; e != null; e = e.next) {
			if (e.observer == null || e.observer.removedFlag)
				e.update();
		}
	}

	public static void add(Event effect) {
		effect.removedFlag = false;
		effect.prev = events;
		effect.next = events.next;
		if (effect.next != null)
			effect.next.prev = effect;
		events.next = effect;
		effect.init();
	}

	/**
	 * 一定速度で回転するイベントを追加します。
	 * @param gb	回転するオブジェクト
	 * @param rotation	現在の角度からどのくらい回転させるか(度数法)
	 * @param speed	回転の速さ
	 */
	public static final void addRotation(GameBody gb, float rotation, float speed) {
		add(new EventRotation(gb, rotation, gb.getX() + gb.getHalfWidth(), gb.getX() + gb.getHalfHeight(), speed));
	}

	/**
	 * 一定速度で回転するイベントを追加します。
	 * @param gb	回転するオブジェクト
	 * @param rotation	現在の角度からどのくらい回転させるか(度数法)
	 * @param speed	回転の速さ
	 */
	public static final void addRotation(GameBody gb, float rotation, float x, float y, float speed) {
		add(new EventRotation(gb, rotation, x, y, speed));
	}

	/**
	 * ゲームのオブジェクトが出現するときのエフェクト
	 * ※予め、GameWorldに登録しておく必要があります。
	 * @param gb	対象のオブジェクト
	 * @param time	何プレームで表示されるか
	 */
	public static void addAppearance(GameBody gb, int time) {
		add(new EventAppearance(gb, time));
	}

}

class EventDummy extends Event { protected void update() {} }



