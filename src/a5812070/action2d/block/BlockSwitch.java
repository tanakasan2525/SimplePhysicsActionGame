package a5812070.action2d.block;

import java.util.ArrayList;

import a5812070.action2d.GameBody;
import a5812070.action2d.GameBodyDef;
import a5812070.action2d.GameWorld;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.effect.Effect;
import a5812070.action2d.event.Event;
import a5812070.action2d.event.EventMoveCamera;
import a5812070.action2d.event.EventRotation;
import a5812070.action2d.event.IEventAction;
import a5812070.action2d.tmxmap.Properties;

public class BlockSwitch extends Block {

	private int count = 0;

	private ArrayList<SwitchMethod> methods = new ArrayList<SwitchMethod>();

	BlockSwitch(PatternImage img, float x, float y, float w, float h, Properties properties) {
		super(img, x, y, w, h, properties);
		GameBodyDef.movable = false;
		//setDefaultProperties(properties);	//	デフォルトプロパティを無効にします。
		for (String name : properties.getNames()) {
			SwitchMethod sm = new SwitchMethod();
			sm.opecode = name;
			sm.operand = properties.getStr(name).split(",");
			methods.add(sm);
		}
		create();
	}

	public void update() {
		if (!contactTop.isEmpty()) {
			count++;
		}
		if (count >= 1) {
			if (count == 1) {
				_img.setPatternNo(1);						//	押されている画像に切り替える
				setSize(_img.get(1).getWidth(), _img.get(1).getHeight());
				doSwitch();
			}
			if (++count > 120)
				GameWorld.inst().remove(this);		//	一定時間したら消える
		}
	}

	@Override
	public int damage(int power) { return 0; }

	/**
	 * スイッチをおした時の動作
	 */
	private void doSwitch() {
		EventMoveCamera emc = new EventMoveCamera();
		for (SwitchMethod sm : methods) {
			ArrayList<GameBody> gblist = GameWorld.inst().getAll(sm.operand[sm.operand.length - 1]);
			//	重さの変更(静的から動的へ):	Density,<float>,<target>
			if ("Density".equals(sm.opecode)) {
				for (GameBody gb : gblist) {
					gb.setDensity(Float.parseFloat(sm.operand[0]));
					gb.setFixedFlag(false);
					gb.setCollisionCategory(GameBodyDef.CATEGORY_NORMAL);
					gb.setCollisionTarget(GameBodyDef.COLLISION_ALL);
				}
			}

			if ("Hidden".equals(sm.opecode)) {
				for (GameBody gb : gblist) {
					if (gb.isHidden()) {
						HiddenAction action = new HiddenAction(gb, Integer.parseInt(sm.operand[0]) != 0);
						emc.add(gb.getX(), gb.getY(), 1, action);
					}
				}
				Event.add(emc);
			}

			if ("Rotation".equals(sm.opecode)) {
				float rotation = Float.parseFloat(sm.operand[0]);
				float x = Float.parseFloat(sm.operand[1]);
				float y = Float.parseFloat(sm.operand[2]);
				float speed = Float.parseFloat(sm.operand[3]);
				for (GameBody gb : gblist) {
					EventRotation er = new EventRotation(gb, rotation, x, y, speed);
					er.observe(emc);
					Event.add(er);
				}
			}
		}
		emc.sort();
	}

	private class SwitchMethod {
		public String opecode;
		public String[] operand;
	}

	/**
	 * Hiddenプロパティがあるスイッチのイベント
	 * @author a5812070
	 *
	 */
	class HiddenAction implements IEventAction {

		int count;

		public boolean isHidden;

		public GameBody gb;

		HiddenAction(GameBody gb, boolean isHidden) {
			this.gb = gb;
			this.isHidden = isHidden;
		}

		@Override
		public boolean run() {
			if (count == 0) {
				gb.setCollisionTarget(~GameBodyDef.CATEGORY_MAP_OBJECT);
				if (!isHidden()) {
					Event.addAppearance(gb, 120);
					Effect.addAppearance(gb.getLeft(), gb.getTop(), gb.getHalfWidth() * 2, 100);
				}
			}
			if (++count == 120) return true;
			return false;
		}

	}

}
