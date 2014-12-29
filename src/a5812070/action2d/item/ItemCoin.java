package a5812070.action2d.item;

import a5812070.action2d.GameWorld;
import a5812070.action2d.ObjectData;
import a5812070.action2d.Stage;

/**
 * アイテム「コイン」
 *
 * ＜説明＞
 * 入手するとスコアが増えます。
 *
 * @author a5812070
 *
 */
public class ItemCoin extends Item{

	private static int SCORE = 100;

	public ItemCoin(ObjectData data) {
		super(data);
	}

	@Override
	public void update() {
		super.update();

		cancelGravity();

	}

	/**
	 * プレイヤーがこのアイテムと接触した時に呼ばれるイベント
	 */
	protected void hitPlayer() {
		Stage.inst().addScore(SCORE);
		GameWorld.inst().remove(this);
	}
}

