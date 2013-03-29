/**
 * SudokuApplet Class
 * Online demo:
 * http://www.andrew.cmu.edu/~jiatongw/sudokuApplet/project-sudoku.html
 * 
 * Copyright 2013, Jiatong Wang
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class SudokuApplet extends Applet implements MouseListener, KeyListener {
  /**
	 * SudokuApplet Class is controller of the application, includes class
	 * object of AppletView, Sudoku and FileReader
	 */

	private static final long serialVersionUID = 1L;
	private int[][] coreMatrix; //coreMatrix, hold the initial values from data file
	private int[][] displayMatrix;//displayMatrix, hold the updated values for display.

	private int[] selectGrid = { 9, 9 };// The index of selected grid.
	private boolean mouseInBoard = false; // The state if mouse is clicked in the board.
	private Button solveButton;
	private Button resetButton;
	private Choice puzzles;
	private String instruction;
	private String output;
	private String elapsedTime;
	private String puzzleName = "easy_puzzle_01";

	private AppletView apv;    //View part, hold the format information of this application.
	private Sudoku sdk;		   //Model part, used for solving the sudoku puzzles.
	private FileReader fdr;	   //read the puzzle data from external txt file.
	public static final int NUM = 9; //9-9 matrix.

	// Deep Copy 2d array
	private void deepCopy(int[][] to, int [][] from) {
		try {
			for (int i = 0; i < from.length; i++) {
				to[i] = Arrays.copyOf(coreMatrix[i],coreMatrix[i].length);
			}
		} catch (Exception e) {
			System.err.println("Error in deepCopy.");
		}
	}

	/**
	 * initialize two matrix, AppletView and FileReader objects, two buttons and
	 * other variables.
	 */
	public void init() {
		instruction = "You can select a grid and enter a number between 0-9, then solve the puzzle.";
		this.coreMatrix = new int[NUM][NUM];
		displayMatrix = new int[NUM][NUM];
		apv = new AppletView();
		fdr = new FileReader();

		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
		Reset();
		solveButton = new Button("Solve");
		this.add(solveButton);
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sdk = new Sudoku(displayMatrix);
				if (sdk.Solve()) { // if the puzzle is solved successfully.
					output = "Puzzle sovled successfully :)";
					elapsedTime = "Elapsed Time: "
							+ Long.toString(sdk.elapsedTime) + " ms.";
					
				} else {//if fail.
					output = "Fail to sovle it :(";
					elapsedTime = "Elapsed Time: "
							+ Long.toString(sdk.elapsedTime) + " ms.";
				}
				repaint();
				requestFocus();
			}
		});

		resetButton = new Button("Reset");
		this.add(resetButton);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reset();
			}
		});

		puzzles = new Choice();
		puzzles.add("easy_puzzle_01");
		puzzles.add("easy_puzzle_02");
		puzzles.add("medium_puzzle_01");
		puzzles.add("medium_puzzle_02");
		puzzles.add("hard_puzzle_01");
		puzzles.add("hard_puzzle_02");
		add(puzzles);

		puzzles.setLocation(400, 200);
		puzzles.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				puzzleName = e.getItem().toString();
				Reset();
			}
		});
	}

	public void Reset() {
		fdr.ReadFiletoMatrix(puzzleName, coreMatrix);
		deepCopy(displayMatrix, coreMatrix);
		output = "Ready to solve.";
		elapsedTime = "Elapsed Time:";
		repaint();
		this.requestFocus();
	}

	/**
	 * Draw the Sudoku board with gray and white squares and fill the grids with
	 * numbers. If certain grid is clicked, color it with blue.
	 */
	public void update(Graphics g) {
		Dimension d = getSize();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, d.width, d.height);
		puzzles.setLocation(400, 200);
		for (int i = 0; i < NUM; i++) {
			for (int j = 0; j < NUM; j++) {
				// odds white, even gray.
				if ((i + j) % 2 == 1)
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.lightGray);
				int x = apv.gridPos[0] + apv.gridSize * j;
				int y = apv.gridPos[1] + apv.gridSize * i;
				g.fillRect(x, y, apv.gridSize, apv.gridSize);
				if (displayMatrix[i][j] != 0) {
					if (this.coreMatrix[i][j] == 0) {
						g.setColor(Color.BLACK);
						g.setFont(apv.font2);
					} else {
						g.setColor(apv.darkRed);
						g.setFont(apv.font1);
					}
					g.drawString(Integer.toString(this.displayMatrix[i][j]),
							apv.relativePos[0] + x, apv.relativePos[1] + y);
				}
			}
		}
		// if mouse is clicked in the board.
		if (mouseInBoard == true) {
			g.setColor(apv.semiBlue);
			g.fillRect(apv.gridPos[0] + apv.gridSize * selectGrid[0],
					apv.gridPos[1] + apv.gridSize * selectGrid[1],
					apv.gridSize, apv.gridSize);
		}
		// Draw the frame.
		g.setColor(Color.BLACK);
		g.drawRect(apv.gridPos[0], apv.gridPos[1], apv.frameSize, apv.frameSize);
		// Draw instruction text.
		g.setColor(Color.BLACK);
		g.setFont(apv.font3);
		g.drawString(instruction, 20, 20);
		g.drawString(output, 20, 440);
		g.drawString(elapsedTime, 20, 460);
	}

	public void paint(Graphics g) {
		this.solveButton.setLocation(160, 400);
		this.resetButton.setLocation(220, 400);
		update(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if (x >= apv.gridPos[0] && x <= apv.gridPos[0] + apv.frameSize
				&& y >= apv.gridPos[1] && y <= apv.gridPos[1] + apv.frameSize) {
			selectGrid[0] = (x - apv.gridPos[0]) / apv.gridSize;
			selectGrid[1] = (y - apv.gridPos[1]) / apv.gridSize;
			mouseInBoard = true;
		} else {
			mouseInBoard = false;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (mouseInBoard) {
			if (c != KeyEvent.CHAR_UNDEFINED && Character.isDigit(c)) {
				displayMatrix[selectGrid[1]][selectGrid[0]] = Character
						.getNumericValue(c);
				coreMatrix[selectGrid[1]][selectGrid[0]] = Character
						.getNumericValue(c);
				e.consume();
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

}


