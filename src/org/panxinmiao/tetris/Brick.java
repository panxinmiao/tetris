package org.panxinmiao.tetris;

import java.awt.Point;

public class Brick {

	public static final int SQUARE_TYPE = 0;
	public static final int STICK_HOR_TYPE = 1;
	public static final int STICK_VERT_TYPE = 2;
	public static final int L_UP_TYPE = 3;
	public static final int L_RIGHT_TYPE = 4;
	public static final int L_DOWN_TYPE = 5;
	public static final int L_LEFT_TYPE = 6;
	public static final int OPL_UP_TYPE = 7;
	public static final int OPL_RIGHT_TYPE = 8;
	public static final int OPL_DOWN_TYPE = 9;
	public static final int OPL_LEFT_TYPE = 10;
	public static final int Z_HOR_TYPE = 11;
	public static final int Z_VERT_TYPE = 12;
	public static final int OPZ_HOR_TYPE = 13;
	public static final int OPZ_VERT_TYPE = 14;
	public static final int T_UP_TYPE=15;
	public static final int T_RIGHT_TYPE=16;
	public static final int T_DOWN_TYPE=17;
	public static final int T_LEFT_TYPE=18;
	
	private int type;
	private Point position;
	private int[] BrickPos_X;
	private int[] BrickPos_Y;

	public Brick(int type) {
		this.type = type;
		this.position = new Point(4, 0);
		switch (type) {
		case SQUARE_TYPE:
			BrickPos_X = new int[] { 0, 1, 0, 1 };
			BrickPos_Y = new int[] { 0, 0, 1, 1 };
			break;
		case STICK_HOR_TYPE:
			BrickPos_X = new int[] { 0, 1, 2, 3 };
			BrickPos_Y = new int[] { 0, 0, 0, 0 };
			this.position = new Point(3, 0);
			break;
		case STICK_VERT_TYPE:
			BrickPos_X = new int[] { 0, 0, 0, 0 };
			BrickPos_Y = new int[] { 0, 1, 2, 3 };
			break;
		case L_UP_TYPE:
			BrickPos_X = new int[] { 0, 0, 0, 1 };
			BrickPos_Y = new int[] { 0, 1, 2, 2 };
			break;
		case L_RIGHT_TYPE:
			BrickPos_X = new int[] { 0, 1, 2, 0 };
			BrickPos_Y = new int[] { 0, 0, 0, 1 };
			break;
		case L_DOWN_TYPE:
			BrickPos_X = new int[] { 0, 1, 1, 1 };
			BrickPos_Y = new int[] { 0, 0, 1, 2 };
			break;
		case L_LEFT_TYPE:
			BrickPos_X = new int[] { 2, 0, 1, 2 };
			BrickPos_Y = new int[] { 0, 1, 1, 1 };
			break;
		case OPL_UP_TYPE:
			BrickPos_X = new int[] { 1, 1, 0, 1 };
			BrickPos_Y = new int[] { 0, 1, 2, 2 };
			break;
		case OPL_RIGHT_TYPE:
			BrickPos_X = new int[] { 0, 0, 1, 2 };
			BrickPos_Y = new int[] { 0, 1, 1, 1 };
			break;
		case OPL_DOWN_TYPE:
			BrickPos_X = new int[] { 0, 1, 0, 0 };
			BrickPos_Y = new int[] { 0, 0, 1, 2 };
			break;
		case OPL_LEFT_TYPE:
			BrickPos_X = new int[] { 0, 1, 2, 2 };
			BrickPos_Y = new int[] { 0, 0, 0, 1 };
			break;
		case Z_HOR_TYPE:
			BrickPos_X = new int[] { 0, 1, 1, 2 };
			BrickPos_Y = new int[] { 0, 0, 1, 1 };
			break;
		case Z_VERT_TYPE:
			BrickPos_X = new int[] { 1, 0, 1, 0 };
			BrickPos_Y = new int[] { 0, 1, 1, 2 };
			break;
		case OPZ_HOR_TYPE:
			BrickPos_X = new int[] { 1, 2, 0, 1 };
			BrickPos_Y = new int[] { 0, 0, 1, 1 };
			break;
		case OPZ_VERT_TYPE:
			BrickPos_X = new int[] { 0, 0, 1, 1 };
			BrickPos_Y = new int[] { 0, 1, 1, 2 };
			break;
		case T_UP_TYPE:
			BrickPos_X = new int[] { 1, 0, 1, 2 };
			BrickPos_Y = new int[] { 0, 1, 1, 1 };
			break;
		case T_RIGHT_TYPE:
			BrickPos_X = new int[] { 0, 0, 1, 0 };
			BrickPos_Y = new int[] { 0, 1, 1, 2 };
			break;
		case T_DOWN_TYPE:
			BrickPos_X = new int[] { 0, 1, 2, 1 };
			BrickPos_Y = new int[] { 0, 0, 0, 1 };
			break;
		case T_LEFT_TYPE:
			BrickPos_X = new int[] { 1, 0, 1, 1 };
			BrickPos_Y = new int[] { 0, 1, 1, 2 };
			break;
		}
	}
	public void moveDown() {
		position.y++;
	}

	public void moveLeft() {
		position.x--;
	}

	public void moveRight() {
		position.x++;
	}

	public void changeDirection() {

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int[] getBrickPos_X() {
		return BrickPos_X;
	}

	public void setBrickPos_X(int[] brickPosX) {
		BrickPos_X = brickPosX;
	}

	public int[] getBrickPos_Y() {
		return BrickPos_Y;
	}

	public void setBrickPos_Y(int[] brickPosY) {
		BrickPos_Y = brickPosY;
	}

}
