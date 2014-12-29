package a5812070.action2d.item;

import a5812070.action2d.GameBodyDef;
import a5812070.action2d.GameObject;
import a5812070.action2d.ObjectData;
import a5812070.action2d.Player;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.Draw;

public abstract class Item extends GameObject {

	protected BImage _img;

	protected static Player player;

	public Item(ObjectData data) {
		this(data.mobject.getImage(), data.mobject.getX(), data.mobject.getY());
	}

	public Item(BImage img, float x, float y) {
		_img = img;
		GameBodyDef.set(x, y, img.getWidth(), img.getHeight(), true);
		GameBodyDef.density = 1;
		GameBodyDef.category = GameBodyDef.CATEGORY_ITEM;
		GameBodyDef.collisionInfo = GameBodyDef.CATEGORY_MAP_OBJECT | GameBodyDef.CATEGORY_PLAYER;
		create();
	}

	@Override
	public void update() {
		if (isContact(player)) {
			hitPlayer();
		}
	}

	@Override
	public void draw() {
		Draw.image(_img, getDrawLeft(), getDrawTop());
	}

	/**
	 * プレイヤーがこのアイテムと接触した時に呼ばれるイベント
	 */
	protected abstract void hitPlayer();

	public static void setPlayer(Player player) {
		Item.player = player;
	}
}
