package org.panxinmiao.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 780882237622866467L;
	private int score=0;
	private int speed=1;
	private int levelUpScore=speed*20;
	private int tempLevelScore=0;
	
	private Random rand=new Random();
	
	private GameBody gameBody;
	private GameFrame brickFrame;
	
	
	private Brick nextBrick;
	private Brick currentBrick;
	
	public GamePanel(GameFrame brickFrame){
		this.brickFrame=brickFrame;
		init();
	}
	
	
	public GameFrame getBrickFrame() {
		return brickFrame;
	}


	public void setBrickFrame(GameFrame brickFrame) {
		this.brickFrame = brickFrame;
	}


	public void init(){
		setLayout(null);
		gameBody=new GameBody(this);
		add(gameBody);
		gameBody.setBounds(10, 10, 300, 570);
		gameBody.setBackground(Color.black);
		Thread t1=new Thread(gameBody);
		t1.setDaemon(true);
		t1.start();
	}

	public Brick nextBrick() {
		currentBrick=nextBrick;
		nextBrick=new Brick(rand.nextInt(19));
		repaint();
		return currentBrick;
	}
	
	public void moveLeft() {
		gameBody.moveLeft();
		
	}

	public void moveRight() {
		gameBody.moveRight();
		
	}
	
	public void moveDown() {
		gameBody.moveDown();
		
	}

	public void moveDrop() {
		gameBody.moveDrop();
		
	}
	public void changeDirection() {
		gameBody.changeDirection();
		
	}

	public void gameStart() {
		nextBrick=new Brick(new Random().nextInt(19));
		gameBody.gameStart();
	}

	public void gamePause() {
		gameBody.gamePause();
	}
	
	public void gameResume(){
		gameBody.gameResume();
	}

	public void gameRestart() {
		nextBrick=null;
		score=0;
		tempLevelScore=0;
		gameBody.gameRestart();
		repaint();
	}

	public void updateScore(int s) {
		score+=s;
		tempLevelScore+=s;
		if(tempLevelScore>=levelUpScore){
			tempLevelScore=tempLevelScore-levelUpScore;
			speed=gameBody.levelUp();
			levelUpScore=speed*20;
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("微软雅黑",Font.BOLD|Font.ITALIC,20));
		g.setColor(new Color(0,0,255,160));
		g.drawString("下一个方块", 320, 35);
		g.drawString("分数    "+score, 320, 200);
		g.drawString("速度    "+speed, 320, 250);
		if(nextBrick!=null){
			int[] pos_X=nextBrick.getBrickPos_X();
			int[] pos_Y=nextBrick.getBrickPos_Y();
			for (int i = 0; i < 4; i++) {
				g.setColor(new Color(rand.nextInt(200),rand.nextInt(200),rand.nextInt(200)));
				g.fill3DRect(20*(pos_X[i]) + 345, 20 * pos_Y[i]+65, 18, 18, true);
			}
		}
	}

	public void speedUp() {
		speed=gameBody.speedUp();
		levelUpScore=speed*20;
		repaint();
	}

	public void speedDown() {

		speed=gameBody.speedDown();
		levelUpScore=speed*20;
		repaint();
	}

	public void gameRunOrPause() {
		if(gameBody.getGameStatus()==GameBody.GAME_RUNNING){
			gamePause();
			return;
		}
		if(gameBody.getGameStatus()==GameBody.GAME_PAUSE){
			gameResume();
			return;
		}
		
	}


}
