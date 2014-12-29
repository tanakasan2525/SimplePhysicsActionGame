package a5812070.action2d.block;

import a5812070.action2d.GameBodyDef;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockNormal extends Block
{
	BlockNormal(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		GameBodyDef.movable = false;
		setDefaultProperties(properties);
		create();
	}

	public BlockNormal(PatternImage img, float x, float y, float density) {
		super(img, x, y, img.getWidth(), img.getHeight(), new Properties());

		if (density != 0.0) {
			GameBodyDef.density = density;
			GameBodyDef.category |= GameBodyDef.CATEGORY_NORMAL;
			GameBodyDef.collisionInfo = GameBodyDef.COLLISION_ALL;
		} else {
			GameBodyDef.movable = false;
		}

		create();
	}

	@Override
	public int damage(int power) { return 0; }
}
