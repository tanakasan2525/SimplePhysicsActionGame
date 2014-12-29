package a5812070.action2d.block;

import a5812070.action2d.ObjectData;
import a5812070.action2d.Stage;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.drawing.TileImage;
import a5812070.action2d.tmxmap.MapObject;

public class BlockFactory {
	Stage stage;

	public BlockFactory(Stage stage) {
		this.stage = stage;
	}

	public Block create(ObjectData data) {
		PatternImage bimg = new PatternImage(1);
		TileImage img = new TileImage();
		img.add(data.mobject.getImage(), 0, 0);
		bimg.add(img);
		return create(data.getBlockType(), data.mobject.getX(), data.mobject.getY(), data.mobject.getWidth(),
									data.mobject.getHeight(), data.mobject, bimg);
	}

	public Block create(int objId, float x1, float y1, float w, float h, MapObject mobject, PatternImage img) {
		Block blk = createImpl(objId, x1, y1, w, h, mobject, img);
		blk.setName(mobject.getName());
		float angle = mobject.getRotation();
		//	エディタでは回転の中心は左上なので、平行移動をして回転の中心をBox2dに合わせる
		//	ここの処理は正しくありません。回転した時に予想とは異なる位置に設置された場合は、ここが原因です。
		if (angle != 0) {
			angle = (float) Math.toRadians(angle);
			blk.setAngle(angle);
			w /= 2;
			h /= 2;
			float x = (float) (Math.cos(angle) * w - Math.sin(angle) * h);
			float y = (float) (Math.sin(angle) * w + Math.cos(angle) * h);
			blk.setX(x1 - x + w * 1.5f);
			blk.setY(y1 + y);
		}

		return blk;
	}

	private Block createImpl(int objId, float x1, float y1, float w, float h, MapObject mobject, PatternImage img) {
		switch (objId) {
			case 0: return new BlockNormal(img, x1, y1, w, h, mobject.getProperties());
			case 1: return new BlockUpDown(img, x1, y1, w, h, mobject.getProperties());
			case 2: return new BlockLeftRight(img, x1, y1, w, h, mobject.getProperties());
			case 3: return new BlockFalling(img, x1, y1, w, h, mobject.getProperties());
			case 4: return createGoal(img, x1, y1, w, h, mobject);
			case 5: return new BlockDamage(img, x1, y1, w, h, mobject.getProperties());
			case 6: return createSwitch(img, x1, y1, w, h, mobject);
		}
		System.out.printf("BlockFactory: there is no registered type in objecttypes.xml (objID = %d)\n", objId);
		return new BlockNormal(img, x1, y1, w, h, mobject.getProperties());
	}

	Block createGoal(PatternImage img, float x1, float y1, float w, float h, MapObject mobject) {
		BlockGoal goal = new BlockGoal(img, x1, y1, w, h, mobject.getProperties());
		stage.setGoal(goal);
		return goal;
	}

	Block createSwitch(PatternImage img, float x1, float y1, float w, float h, MapObject mobject) {
		TileImage timg = new TileImage();
		timg.add(mobject.getImage(1), 0, 0);
		img.add(timg);
		return new BlockSwitch(img, x1, y1, w, h, mobject.getProperties());
	}
}
