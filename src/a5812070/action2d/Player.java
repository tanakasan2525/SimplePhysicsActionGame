package a5812070.action2d;

import a5812070.action2d.drawing.PatternImage;
import a5812070.action2d.drawing.TileImage;
import a5812070.action2d.gameengine.GameEngine;
import a5812070.action2d.tmxmap.MapObject;

public class Player extends GameObject
{
	final float SPEED = 0.3f;			 // moving speed
	final float DASH_SPEED = 0.5f;	// moving speed when he is dash
	final float AIR_SPEED = 0.16f;	// moving speed in air
	final float JUMP = 5.1f;	 // acceleration of jump

	final float A_FRICTION = 0.96f;	//	air friction
	final float G_FRICTION = 0.9f;	//	ground friction

	PatternImage _img = new PatternImage(6);

	Sword sword;

	Player(MapObject mobject) {
		for (int i = 0; i < 7; ++i) {
			TileImage img = new TileImage();
			img.add(mobject.getImage(i), 0, 0);
			img.move(-15, -3);
			_img.add(img);
		}

		GameBodyDef.set(mobject.getX(), mobject.getY(), mobject.getWidth() - 30, mobject.getHeight() - 8, true);

		GameBodyDef.category = GameBodyDef.CATEGORY_PLAYER;
		GameBodyDef.fixedRotation = true;
		GameBodyDef.friction = 0;
		create();
	}

	public void update() {
		float vx = getVx();
		float vy = getVy();
		float moveX = 0;

		if (GameEngine.getInput().left()) {
			moveX = -1;
			_img.setInverseH(true);
			_img.next();
		} else if (GameEngine.getInput().right()) {
			moveX = 1;
			_img.setInverseH(false);
			_img.next();
		} else {
			_img.setPatternNo(1);	//	(0番目から数えて)1番目が立ち絵のため
		}

		if (onGround()) {
			moveX *= GameEngine.getInput().dash() ? DASH_SPEED : SPEED;
		} else {
			moveX *= AIR_SPEED;
		}

		vx += moveX;

		if (onGround()) {
			vx *= G_FRICTION;
		} else {
			vx *= A_FRICTION;
		}

		//if (input.z[input.PRESSED]) {		// debug mode: infinity jump
		if (GameEngine.getInput().jump() && onGround()) {
			vy = -JUMP;
		}

		if (vy > JUMP) vy = JUMP;

		if (!contactBottom.isEmpty()) {
			GameBody bottom = contactBottom.iterator().next();
			setX(getX()+bottom.getVx());
			setY(getY()+bottom.getVy() + 1);
		}

		setVelocity(vx, vy);
		fixPos();

		if (sword != null) {
			if (GameEngine.getInput().release())
				throwSword();
			else if (GameEngine.getInput().attack())	//	release()の後にチェックする
				sword.attack();
		}

	}

	public void draw() {
		//Draw.setRotation(getAngle());
		_img.draw(getDrawLeft(), getDrawTop());	//	中心座標から左上座標に変換
		drawHPAlways();
		//Draw.reset();
	}

	/**
	 * ソードを持たせます。
	 */
	public void setSword() {
		sword = new Sword(this);

	}

	/**
	 * ソードを投げます。
	 */
	public void throwSword() {
		sword.release();
		sword.addForce(sword.getMass() * 500 * (isLeft() ? -1 : 1), -sword.getMass() * 20);
		sword = null;
	}

	/**
	 * 左を向いていたらtrueを返します。
	 * @return
	 */
	public boolean isLeft() {
		return _img.isInverseH();
	}

}
