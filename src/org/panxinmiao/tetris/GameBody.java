package org.panxinmiao.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameBody extends JPanel implements Runnable {
	private static final long serialVersionUID = -8060311775767427199L;
	public static final int GAME_NOT_BEGIN=0;
	public static final int GAME_RUNNING=1;
	public static final int GAME_PAUSE=2;
	public static final int GAME_WAIT_ANIMATION=3;
	public static final int GAME_OVER=4;
	
	private Random rand=new Random();
	
	private Brick brick;
	private boolean[][] gameBodyFlag =new boolean[10][19];
	private List<Integer>fullRow=new ArrayList<Integer>();
	
	private static int speed=1;
	
	private int gameStatus;
	private String message;
	
	private JButton[] but=new JButton[6];
	
	private GamePanel brickPanel;
	private GameFrame brickFrame;
	
	public int getGameStatus() {
		return gameStatus;
	}

	public GameBody(GamePanel brickPanel) {
		this.brickPanel=brickPanel;
		init();
	}

	public void init(){
		brickFrame=brickPanel.getBrickFrame();
		setLayout(null);
		String[] butStr={"开始游戏","重新开始","提高速度","降低速度","退出游戏"};
		for(int i=0;i<5;i++){
			but[i]=new JButton(butStr[i]);
			but[i].addActionListener(brickFrame);
			but[i].setFocusable(false);
		}
		but[0].setBounds(90, 250, 120, 25);
		but[1].setBounds(90, 250, 120, 25);
		but[2].setBounds(90, 280, 120, 25);
		but[4].setBounds(90, 280, 120, 25);
		but[3].setBounds(90, 310, 120, 25);
		but[3].setEnabled(false);
		add(but[0]);
		add(but[2]);
		add(but[3]);
	}
	
	private Color getRandColor(){
		while(true){
			int r=rand.nextInt(256);
			int g=rand.nextInt(256);
			int b=rand.nextInt(256);
			if(r+g+b>165){
				return new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("微软雅黑",Font.BOLD,30));
		for(int row=0;row<19;row++){
			for(int col=0;col<10;col++){
				if(gameStatus==GAME_OVER){
					g.setColor(Color.darkGray);
				}else{
					g.setColor(Color.lightGray);
				}
				if(fullRow.contains(row)){
					g.setColor(getRandColor());
					g.fill3DRect(30*col+1, 30*(row)+1,28, 28, true);
				}else{
					if(gameBodyFlag[col][row]){
						g.fill3DRect(30*col+1, 30*(row)+1,28, 28, true);
					}
				}
			}
		}
		if(brick!=null){
			int[] pos_X=brick.getBrickPos_X();
			int[] pos_Y=brick.getBrickPos_Y();
			for (int i = 0; i < 4; i++) {
				g.setColor(getRandColor());
				g.fill3DRect(30*(brick.getPosition().x+pos_X[i]) + 1, 30 * (brick.getPosition().y+pos_Y[i])+1, 28, 28, true);
			}
		}
		
		if(gameStatus==GAME_NOT_BEGIN){
			g.setColor(new Color(192,192,192,100));
			g.fillRect(0, 0, 300, 570);
			g.setColor(new Color(255,0,0,100));
			g.drawString("准备开始", 90, 200);
		}
		if(gameStatus==GAME_PAUSE){
			g.setColor(new Color(192,192,192,100));
			g.fillRect(0, 0, 300, 570);
			g.setColor(new Color(255,0,0,100));
			g.drawString("游戏暂停", 90, 200);
			g.drawString("按回车继续", 80, 250);
		}
		if(gameStatus==GAME_OVER){
			g.setColor(new Color(192,192,192,100));
			g.fillRect(0, 0, 300, 570);
			g.setColor(new Color(255,0,0,100));
			g.drawString("游戏结束", 90, 200);
		}
		if(gameStatus==GAME_WAIT_ANIMATION){
			if(message!=null){
				g.setColor(new Color(0,255,0,100));
				g.drawString(message, 100, 200);
			}
		}
	}

	
	public synchronized void run() {
		while(true){
			while(gameStatus!=GAME_RUNNING){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			brick=brickPanel.nextBrick();
			repaint();
			try {
				Thread.sleep(1000/speed);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while(!isDrop()){
				try {
					while(gameStatus!=GAME_RUNNING){
						this.wait();
						Thread.sleep(1000/speed);
					}
					moveDown();
					Thread.sleep(1000/speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			updateGameBodyFlag();
		}
	}

	private void updateGameBodyFlag() {
		if(brick==null){
			return;
		}
		for(int i=0;i<4;i++){
			gameBodyFlag[brick.getPosition().x+brick.getBrickPos_X()[i]][brick.getPosition().y+brick.getBrickPos_Y()[i]]=true;
		}
		brick=null;
		repaint();
		checkFullRow();
		checkGameOver();
	}

	private void checkGameOver() {
		for(int row=0;row<1;row++){
			for(int col=0;col<10;col++){
				if(gameBodyFlag[col][row]){
					gameOver();
					return;
				}
			}
		}
		
	}

	

	private void checkFullRow() {
		int score=0;
		int n=0;
		row:for(int row=4;row<19;row++){
				for(int col=0;col<10;col++){
					if(!gameBodyFlag[col][row]){
						continue row;
					}
				}
				fullRow.add(row);
				n++;
				score+=n;
			}
		if(fullRow.size()>0){
			gameStatus=GAME_WAIT_ANIMATION;
			message="+"+score+"分";
			try {
				repaint();
				Thread.sleep(300);
				repaint();
				Thread.sleep(300);
				repaint();
				Thread.sleep(300);
				repaint();
				Thread.sleep(300);
				delFullRow();
				brickPanel.updateScore(score);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			message=null;
			gameResume();
		}
	}

	private void delRow(int row) {
		for(int i=row;i>=4;i--){
			for(int j=0;j<10;j++){
				gameBodyFlag[j][i]=gameBodyFlag[j][i-1];
			}
		}
	}

	private void delFullRow(){
		for(int i:fullRow){
			delRow(i);
		}
		fullRow.clear();
	}
	
	private boolean isDrop() {
		if(brick==null){
			return true;
		}
		for(int i=0;i<4;i++){
			if(brick.getPosition().y+ brick.getBrickPos_Y()[i]==18){
				return true;
			}
			if(gameBodyFlag[brick.getPosition().x+brick.getBrickPos_X()[i]][brick.getPosition().y+brick.getBrickPos_Y()[i]+1]){
				return true;
			}
		}
		return false;
	}

	public void moveDown() {
		if(gameStatus!=GAME_RUNNING||brick==null){
			return;
		}
		synchronized(brick){
			if(!isDrop()){
				brick.moveDown();
			}
		}
		repaint();
	}
	
	public void moveLeft() {
		if(gameStatus!=GAME_RUNNING||brick==null){
			return;
		}
		for(int i=0;i<4;i++){
			if(brick.getPosition().x+ brick.getBrickPos_X()[i]==0){
				return;
			}
			if(gameBodyFlag[brick.getPosition().x+brick.getBrickPos_X()[i]-1][brick.getPosition().y+brick.getBrickPos_Y()[i]]){
				return;
			}
		}
		brick.moveLeft();
		repaint();
	}

	public void moveRight() {
		if(gameStatus!=GAME_RUNNING||brick==null){
			return;
		}
		for(int i=0;i<4;i++){
			if(brick.getPosition().x+ brick.getBrickPos_X()[i]==9){
				return;
			}
			if(gameBodyFlag[brick.getPosition().x+brick.getBrickPos_X()[i]+1][brick.getPosition().y+brick.getBrickPos_Y()[i]]){
				return;
			}
		}
		brick.moveRight();
		repaint();
	}

	public void moveDrop() {
		if(gameStatus!=GAME_RUNNING||brick==null){
			return;
		}
		new Thread(){
			public void run() {
				while(!isDrop()){
					moveDown();
				}
				updateGameBodyFlag();
			};
		}.start();
	}
	public void changeDirection() {
		if(gameStatus!=GAME_RUNNING||brick==null){
			return;
		}
		Brick tempBrick;
		switch(brick.getType()){
		case Brick.SQUARE_TYPE:
			return;
		case Brick.STICK_VERT_TYPE:
			tempBrick=new Brick(Brick.STICK_HOR_TYPE);
			break;
		case Brick.L_LEFT_TYPE:
			tempBrick=new Brick(Brick.L_UP_TYPE);
			break;
		case Brick.OPL_LEFT_TYPE:
			tempBrick=new Brick(Brick.OPL_UP_TYPE);
			break;
		case Brick.Z_VERT_TYPE:
			tempBrick=new Brick(Brick.Z_HOR_TYPE);
			break;
		case Brick.OPZ_VERT_TYPE:
			tempBrick=new Brick(Brick.OPZ_HOR_TYPE);
			break;
		case Brick.T_LEFT_TYPE:
			tempBrick=new Brick(Brick.T_UP_TYPE);
			break;
		default:
			tempBrick=new Brick(brick.getType()+1);
		}
		tempBrick.setPosition(brick.getPosition());
		if(canDoChange(tempBrick)){
			brick=tempBrick;
			repaint();
		}
	}

	private boolean canDoChange(Brick changeBrick) {
		for(int i=0;i<4;i++){
			if(changeBrick.getPosition().x+ changeBrick.getBrickPos_X()[i]>9){
				return false;
			}
			if(gameBodyFlag[changeBrick.getPosition().x+changeBrick.getBrickPos_X()[i]][changeBrick.getPosition().y+changeBrick.getBrickPos_Y()[i]]){
				return false;
			}
		}
		return true;
	}

	public void gameResume() {
		synchronized(this){
			gameStatus=GAME_RUNNING;
			this.notify();
		}
		repaint();
	}

	public boolean gamePause() {
		if(gameStatus==GAME_RUNNING){
			gameStatus=GAME_PAUSE;
			repaint();
			return true;
		}else{
			return false;
		}
	}

	public void gameRestart() {
		gameResume();
		gameBodyFlag=new boolean[10][19];
		brick=null;
		gameStatus=GAME_NOT_BEGIN;
		remove(but[1]);
		remove(but[4]);
		add(but[0]);
		add(but[2]);
		add(but[3]);
	}

	public void gameStart() {
		gameResume();
		remove(but[0]);
		remove(but[2]);
		remove(but[3]);
		gameStatus=GAME_RUNNING;
		repaint();
	}
	
	private void gameOver() {
		gameStatus=GAME_OVER;
		add(but[1]);
		add(but[4]);
		repaint();
	}
	
	public int speedUp(){
		if(speed==1){
			but[3].setEnabled(true);
		}
		if(speed<10){
			speed++;
		}
		if(speed==10){
			but[2].setEnabled(false);
		}
		return speed;
	}
	public int speedDown(){
		if(speed==10){
			but[2].setEnabled(true);
		}
		if(speed>1){
			speed--;
		}
		if(speed==1){
			but[3].setEnabled(false);
		}
		return speed;
	}

	public int levelUp() {
		if(speed<10){
			speed++;
			gameStatus=GAME_WAIT_ANIMATION;
			try {
				message="第"+speed+"级";
				repaint();
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			message=null;
			gameResume();
		}
		return speed;
	}
}
