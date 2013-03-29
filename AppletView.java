/**
 * SudokuApplet
 * Online demo:
 * http://www.andrew.cmu.edu/~jiatongw/sudokuApplet/project-sudoku.html
 * 
 * Copyright 2013, Jiatong Wang
 */
import java.awt.Color;
import java.awt.Font;

public class AppletView {
  public Font font1;
	public Font font2;
	public Font font3;
	public Color darkRed;
	public Color semiBlue;
	public int gridSize;
	public int frameSize;
	public int [] gridPos;
	public int [] relativePos;
	
	public AppletView(int gSize, int[] gPos, int[] relPos, Font f1, Font f2, Font f3){
		gridSize = gSize;
		gridPos = gPos;
		relativePos = relPos;
		font1 = f1;
		font2 = f2;
		font3 = f3;
		frameSize = SudokuApplet.NUM*gridSize;
		darkRed = new Color(204, 0 ,0);
		semiBlue = new Color(0,60,255,128);
	}
	
	public AppletView(){
		gridSize = 40;
		gridPos = new int[2];
		gridPos[0] = 20;
		gridPos[1] = 40;
		relativePos = new int[2];
		relativePos[0] = 14;
		relativePos[1] = 30;
		font1 = new Font("Arial", Font.BOLD, 24);
		font2 = new Font("Kristen ITC", Font.ITALIC, 24);
		font3 = new Font("Kristen ITC", Font.PLAIN, 16);
		frameSize = SudokuApplet.NUM*gridSize;
		darkRed = new Color(204, 0 ,0);
		semiBlue = new Color(0,60,255,128);
	}
}
