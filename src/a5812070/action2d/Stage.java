package a5812070.action2d;

import static a5812070.action2d.drawing.Draw.*;

import java.io.File;
import java.util.ArrayList;

import a5812070.action2d.block.Block;
import a5812070.action2d.block.BlockFactory;
import a5812070.action2d.block.BlockGoal;
import a5812070.action2d.drawing.BImage;
import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.drawing.TileImage;
import a5812070.action2d.enemy.Enemy;
import a5812070.action2d.enemy.Frog;
import a5812070.action2d.enemy.Slime;
import a5812070.action2d.enemy.Snail;
import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.item.Item;
import a5812070.action2d.item.ItemCoin;
import a5812070.action2d.item.ItemSword;
import a5812070.action2d.tmxmap.MapObject;
import a5812070.action2d.tmxmap.ObjectGroup;
import a5812070.action2d.tmxmap.Properties;
import a5812070.action2d.tmxmap.TMXMap;

public class Stage
{
	private static int SCORE_X = 300;
	private static int SCORE_Y = 10;

	//	singleton
	private static final Stage _instance = new Stage();
	public static final Stage inst() { return _instance; }

	StageData data = new StageData();

	BImage background;

	Player player;
	BlockGoal goal;

	BImage scoreImage;
	PatternImage numberImage;

	int score;

	boolean isPaused;

	private Stage() {
		//	数字画像の読み込み
		numberImage = new PatternImage();
		/*BImage numimg = new BImage("img/number.png");
		for (int i = 0; i < 10; i++) {
			numberImage.add(numimg.getSubimage(i * 16, 0, 16, 18));
		}*/
		for (int i = 0; i < 10; i++) {
			numberImage.add(new BImage("img/" + i + ".png"));
		}

		//	スコア画像の読み込み
		scoreImage = new BImage("img/score.png");
	}

	/**
	 * ステージデータを読み込みます。
	 * @param path
	 */
	public final void load(String path) {
		GameWorld.inst().reset();
		Camera.setPosition(0, 0);
		background = null;
		score = 0;

		try {
			player =	data.load(path, this);
			Enemy.setPlayer(player);
			Item.setPlayer(player);
		} catch (Exception e) { e.printStackTrace(); }
	}

	/**
	 * 状態を更新します。
	 */
	public final void update() {
		Camera.move(player.getX(), player.getY());
	}

	/**
	 * 背景を描画します。
	 */
	public final void drawBackground() {
		if (background != null) {
			float x = -Camera.getX() / 20 % background.getWidth();
			for (; x < GameEngine.getWidth(); x += background.getWidth()) {
				image(background, (int)x, 0);
			}
		}
	}

	/**
	 * ステージに関する情報(スコアなど)を描画します。
	 */
	public final void drawInfo() {
		drawScore(SCORE_X, SCORE_Y);
	}

	//	桁数の取得のために使用します
	private final static int [] numlenTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
        99999999, 999999999, Integer.MAX_VALUE };

	/**
	 * スコアを描画します。
	 */
	public final void drawScore(int x, int y) {

		//	スコアテキストの表示
		scoreImage.draw(x, y + 3);	//	3 ≒(scoreImage.getHeight() - numberImage.getHeight()) / 2

		x += scoreImage.getWidth() + 5;

		int numLen = 0;
		//	数字の桁数を取得します
		for (int i = 0; ; i++)
			if (score <= numlenTable[i]) {
				numLen = i+1;
				break;
			}

		int d = (int)Math.pow(10, numLen - 1);
		int n = score;

		for (int i = 0; i < numLen; i++) {
			numberImage.setPatternNo(n / d);
			numberImage.draw(x + i * numberImage.getWidth(), y);
			n %= d;
			d /= 10;
		}
	}

	/**
	 * ゲームを一時停止するかどうか設定します。
	 * @param flag	trueの時、一時停止
	 */
	public final void setPaused(boolean flag) {
		isPaused = flag;
	}

	/**
	 * 背景画像を設定します。
	 * @param img
	 */
	final void setBackground(BImage img) {
		background = img;
	}

	/**
	 * ゴールを設定します。(必ず設定する必要があります)
	 * @param goal
	 */
	public final void setGoal(BlockGoal goal) {
		this.goal = goal;
	}

	/**
	 * スコアを設定します。
	 * @param score
	 */
	public final void setScore(int score) {
		this.score = score;
	}

	/**
	 * スコアを増やします。
	 * @param score	スコアの増量
	 */
	public final void addScore(int score) {
		this.score += score;
	}

	/**
	 * ゲームが一時停止しているかどうかを取得します。
	 * @return
	 */
	public final boolean isPaused() {
		return isPaused;
	}

	/**
	 * ゲームオーバーかどうか取得します。
	 * @return
	 */
	public final boolean isGameOver() {
		return player.getY() > getDeadLineY() || player.getHP() <= 0;
	}

	/**
	 * ゲームクリアしているかどうかを取得します。
	 * @return
	 */
	public final boolean isGameClear() {
		return goal.isContact(player);
	}

	/**
	 * これより下に行くと死んでしまうY座標を取得します。
	 * @return
	 */
	final float getDeadLineY() { return Camera.getMaxY() + 128; }
}


class StageData {

	StageData() {
	}

	Player load(String path, Stage stage) {
		TMXMap map = new TMXMap(path);
		Player player = null;

		String baseDir = new File(path).getParent() + "/";

		ObjectData objdata = new ObjectData();

		//	カメラの設定
		Camera.reset();
		Camera.setCenterX(GameEngine.getWidth() / 2.f);
		Camera.setCenterY(GameEngine.getHeight() / 2.f);
		Camera.setMaxX(map.getWidth() * map.getTileWidth());
		Camera.setMaxY(map.getHeight() * map.getTileHeight());

		BlockFactory blockFactory = new BlockFactory(stage);

		ArrayList<ShapeItem> shpList = new ArrayList<ShapeItem>();

		//	マップのプロパティの読み込み
		Properties properties = map.getProperties();
		String backgroundPath = properties.getStr("BackgroundImage");
		if (backgroundPath != null)
			stage.setBackground(new BImage(baseDir + backgroundPath));


		//	オブジェクトの配置の読み込み
		for (ObjectGroup objectgroup : map.getLayers()) {

			//	グルーピングレイヤーの読み込み
			if ("Group".equals(objectgroup.getName())) {
				for (MapObject mobject : objectgroup.getObjects()) {
					objdata.load(mobject);
					//objdata.type = objdata.getBlockType();
					shpList.add(new ShapeItem(objdata));
				}
				continue;
			}


			OBJECT_LOOP:
			for (MapObject mobject : objectgroup.getObjects()) {
				objdata.load(mobject);
				if (objdata.isPlayer()) {
					//	プレイヤーの読み込み
					player = new Player(objdata.mobject);
					GameWorld.inst().add(player);
				} else if (objdata.isEnemy()) {
					//	敵の読み込み
					switch (objdata.getEnemyType()) {
						case 0: GameWorld.inst().add(new Slime(objdata)); break;
						case 1: GameWorld.inst().add(new Frog(objdata)); break;
						case 2: GameWorld.inst().add(new Snail(objdata)); break;
					}
				} else if (objdata.isItem()) {
					//	アイテムの読み込み
					switch (objdata.getItemType()) {
					case 0: GameWorld.inst().add(new ItemSword(objdata)); break;
					case 1: GameWorld.inst().add(new ItemCoin(objdata)); break;
					}
				} else {
					//	マップのオブジェクトの読み込み
					Point p = new Point(objdata.mobject.getX(), objdata.mobject.getY());
					for (ShapeItem s : shpList) {
						if (s.shape.collisionDetection(p)) {
							s.add(objdata.mobject.getX(), objdata.mobject.getY(), objdata.mobject.getImage(), objdata.mobject);
							continue OBJECT_LOOP;
						}
					}

					GameWorld.inst().add(blockFactory.create(objdata));
				}
			}

			//	for grouping layer
			if (!shpList.isEmpty()) {
				for (ShapeItem s : shpList) {
					Block blk = blockFactory.create(s.type, s.x1, s.y1, s.x2 - s.x1, s.y2 - s.y1,
																					s.mobject, s.getImage());
					GameWorld.inst().add(blk);
				}
				shpList.clear();
			}
		}

		return player;
	}

	class ShapeItem {
		public Shape shape;
		public int type;
		public MapObject mobject;
		public float x1 = Float.MAX_VALUE, y1 = x1, x2 = Float.MIN_VALUE, y2 = x2;
		public TileImage tileImg = new TileImage();
		ShapeItem(ObjectData objdata) {
			shape = new Rectangle(objdata.mobject.getX(), objdata.mobject.getY(), objdata.mobject.getWidth() - 1, objdata.mobject.getHeight() - 1);
			this.type = objdata.getBlockType();
			this.mobject = objdata.mobject;
		}
		void add(float x, float y, BImage img, MapObject object) {
			float x2 = x + img.getWidth();
			float y2 = y + img.getHeight();
			if (x < x1) x1 = x;
			if (x2 > this.x2) this.x2 = x2;
			if (y < y1) y1 = y;
			if (y2 > this.y2) this.y2 = y2;
			tileImg.add(img, x, y);
		}
		//	calling once only
		PatternImage getImage() {
			tileImg.move(-x1, -y1);		//	convert to relative position
			PatternImage img = new PatternImage(1);
			img.add(tileImg);
			return img;
		}
	}
}

