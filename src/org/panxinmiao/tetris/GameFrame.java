package org.panxinmiao.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame implements ActionListener,KeyListener,FocusListener{
	private static final long serialVersionUID = 8489797623956453243L;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu helpMenu;
	private JMenuItem newGame;
	private JMenuItem exitGame;
	private JMenuItem about;
	private JMenuItem operation;
	
	private GamePanel brickPanel;
	
	public GameFrame(){
		init();
	}
	
	public void init(){
		menuBar=new JMenuBar();
		gameMenu = new JMenu("游戏");
		helpMenu = new JMenu("帮助");
		newGame=new JMenuItem("新游戏");
		exitGame = new JMenuItem("退出");
		about = new JMenuItem("作者");
		operation=new JMenuItem("操作说明");
		newGame.setEnabled(false);
		//gameMenu.addActionListener(this);
		//helpMenu.addActionListener(this);
		newGame.addActionListener(this);
		exitGame.addActionListener(this);
		about.addActionListener(this);
		operation.addActionListener(this);
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		gameMenu.add(newGame);
		gameMenu.add(exitGame);
		helpMenu.add(about);
		helpMenu.add(operation);
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
				
		brickPanel=new GamePanel(this);
		setContentPane(brickPanel);
		
		setSize(450,640);
		setResizable(false);
		setTitle("俄罗斯方块");
		setLocation(400, 50);
		setVisible(true);
		addKeyListener(this);
		setFocusable(true);
		addFocusListener(this);
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("开始游戏")){
			newGame.setEnabled(true);
			brickPanel.gameStart();
			return;
		}
		if(e.getActionCommand().equals("游戏暂停")){
			brickPanel.gamePause();
			return;
		}
		if(e.getActionCommand().equals("取消暂停")){
			brickPanel.gameResume();
			return;
		}
		if(e.getActionCommand().equals("重新开始")||e.getActionCommand().equals("新游戏")){
			newGame.setEnabled(false);
			brickPanel.gameRestart();
			return;
		}
		if(e.getActionCommand().equals("提高速度")){
			brickPanel.speedUp();
			return;
		}
		if(e.getActionCommand().equals("降低速度")){
			brickPanel.speedDown();
			return;
		}
		if(e.getActionCommand().equals("作者")){
			JOptionPane.showMessageDialog(this, " 作者:  PanXinmiao\n 邮箱:  1092djhfg@163.com");
			return;
		}
		if(e.getActionCommand().equals("操作说明")){
			JOptionPane.showMessageDialog(this, "左         移：←键\n右         移：→键\n变         形：↑键\n加速落下：↓键\n直接落下：空格键\n暂         停：回车键");
			return;
		}
		if(e.getActionCommand().equals("退出")||e.getActionCommand().equals("退出游戏")){
			System.exit(getDefaultCloseOperation());
		}
	}

	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			brickPanel.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			brickPanel.moveRight();
			break;
		case KeyEvent.VK_DOWN:
			brickPanel.moveDown();
			break;
		case KeyEvent.VK_UP:
			brickPanel.changeDirection();
			break;
		case KeyEvent.VK_SPACE:
			brickPanel.moveDrop();
			break;
		}
	}
		
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER:
			brickPanel.gameRunOrPause();
			break;
		}
	}

	
	public void keyTyped(KeyEvent e) {
		
	}

	
	public void focusGained(FocusEvent e) {
		
	}


	public void focusLost(FocusEvent e) {
		brickPanel.gamePause();
	}
}
