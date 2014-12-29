package a5812070.action2d.item;

import a5812070.action2d.GameWorld;
import a5812070.action2d.ObjectData;
import a5812070.action2d.Sword;

public class ItemSword extends Item{

	public ItemSword(ObjectData data) {
		super(data);
	}

	public ItemSword(Sword sword) {
		super(sword.getImage(), sword.getX(), sword.getY());
	}

	/**
	 * プレイヤーがこのアイテムと接触した時に呼ばれるイベント
	 */
	protected void hitPlayer() {
		player.setSword();
		GameWorld.inst().remove(this);
	}
}
