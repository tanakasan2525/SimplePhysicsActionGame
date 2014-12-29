package a5812070.action2d.block;

import a5812070.action2d.GameBody;
import a5812070.action2d.GameBodyDef;
import a5812070.action2d.GameObject;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockDamage extends Block
{
	BlockDamage(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		GameBodyDef.movable = false;
		setDefaultProperties(properties);
		create();
	}

	public void update() {
		if (!contactTop.isEmpty()) {
			for (GameBody b : contactTop)
				((GameObject)b).damage(((GameObject)b).getHP());
		}
	}

	@Override
	public int damage(int power) { return 0; }
}
