package a5812070.action2d.gameengine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFrame;

import a5812070.action2d.drawing.Draw;
import a5812070.action2d.drawing.DrawApplication;
import a5812070.action2d.scene.Scene;
import a5812070.action2d.scene.SceneTitle;

public abstract class GamePanel extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private Input input;

	private Graphics panelGraphics;

	private Thread mainThread;

	private final static Object lock = new Object();	//	入力の割り込みを防ぐセマフォ
	private static boolean isUpdate = false;		//	更新を行ったかどうかを示すフラグ

	@Override
	public void init() { }

	public void startApplication() {
		JFrame frame = new JFrame();
		frame.setTitle(GameEngine.getTitle());
		frame.getContentPane().add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

	public void initialize(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		input = new Input();
		setBackground(Color.white);
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setIgnoreRepaint(true);

		mainThread = new Thread(this);
	}

	@Override
	public void start() {
		try {
			Draw.setDraw(new DrawApplication(this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Scene.clear();
		Scene.push(new SceneTitle());
		mainThread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				long oldTime = System.currentTimeMillis();

				synchronized (lock) {
					Scene.execute();
					input.update();
					isUpdate = true;
				}

				repaint();

				long newTime = System.currentTimeMillis();
				long sleepTime = 16 - (newTime - oldTime);
				if (sleepTime < 2) sleepTime = 2;
				Thread.sleep(sleepTime);
			} catch(Exception e){e.printStackTrace();}
	    }
	}

	@Override
	public void paint(Graphics g) {
		if (isUpdate) {
			panelGraphics = g;
			Draw.show();
			isUpdate = false;
		}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public final Graphics getPanelGraphics() { return panelGraphics; }

	public final Input getInput() { return input; }

	/**
	 * 環境に依存しないInputStreamを取得します。
	 * @param path
	 * @return
	 */
	public final InputStream getInputStream(String path) {
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 画面の拡大率を取得します。
	 */
	public static final float getScale() {
		return 1.0f;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		synchronized (lock) {
			input.updateKeyPressed(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		synchronized (lock) {
			input.updateKeyReleased(e.getKeyCode());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		synchronized (lock) {
			input.updatePointPressed(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		synchronized (lock) {
			input.updatePointReleased(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		input.updatePointMoved(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		input.updatePointMoved(e.getX(), e.getY());
	}

}

