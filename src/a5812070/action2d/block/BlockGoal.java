package a5812070.action2d.block;

import a5812070.action2d.GameBodyDef;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.tmxmap.Properties;

public class BlockGoal extends Block
{

	BlockGoal(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x + (w/2-1), y, 2, h, properties);//	change collision region
		GameBodyDef.movable = false;
		setDefaultProperties(properties);

		img.get(0).move(-w/2+1, 0);		//	adjust position of image

		create();
	}

	public void update() {
	}

	@Override
	public int damage(int power) { return 0; }
}
