package a5812070.action2d;

import java.util.HashMap;

import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.tmxmap.MapObject;

public class ObjectData {
	public MapObject mobject;
	int type;

	int enemyEndPos;

	int blockEndPos;

	HashMap<String, Integer> typeMap = new HashMap<String, Integer>();

	public ObjectData() {
		//XML objecttypes = new XML(baseDir + "../objecttypes.xml");
		XML objecttypes = new XML(GameEngine.getInputStream("stage/objecttypes.xml"));
		for (XML objecttype : objecttypes.getChildren("objecttype")) {
			String name = objecttype.getStr("name");
			if      (name.equals("上下に移動する床")) enemyEndPos = typeMap.size() - 1;
			else if (name.equals("ソード")) 		  blockEndPos = typeMap.size() - 1;
			typeMap.put(name, typeMap.size());
		}
	}

	public final void load(MapObject mobject) {
		this.mobject = mobject;
		this.type = -1;
		if (mobject.getType().equals("")) {
			String typeStr = mobject.getProperties().getStr("type", null);
			if (typeStr != null) {
				type = typeMap.get(typeStr);
				mobject.getProperties().remove("type");
			}
		} else {
			type = typeMap.get(mobject.getType());
		}
	}

	final boolean isPlayer() {
		return type == 0;
	}

	final boolean isEnemy() {
		return 1 <= type && type <= enemyEndPos;
	}

	final boolean isBlock() {
		return enemyEndPos < type && type <= blockEndPos;
	}

	final boolean isItem() {
		return blockEndPos < type;
	}

	final int getEnemyType() {
		return type - 1;
	}

	public final int getBlockType() {
		return type == -1 ? 0 : type - enemyEndPos;
	}

	public final int getItemType() {
		return type - blockEndPos - 1;
	}

	static class BaseProperty {
		String name, value;

		BaseProperty(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}

}
