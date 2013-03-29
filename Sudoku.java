/**
 * Sudoku Class is used for solving the sudoku puzzle.
 * Copyright 2013, Jiatong Wang
 */
import java.util.*;

public class Sudoku {
  public long elapsedTime;
	public int[][] matrix;

	public Sudoku(int[][] M) {
		matrix = M;
	}
	public boolean Solve() {
		long startTime = System.currentTimeMillis();
		boolean result = BackTrack();
		elapsedTime = System.currentTimeMillis() - startTime;
		return result;
	}
/**
 * BackTrack is a recursive function used to find the solution
 */
	public boolean BackTrack() {
		int[] index = { -1, -1 };
		// Try to find a zero element in the matrix
		for (int i = 0; i < SudokuApplet.NUM; i++) {
			for (int j = 0; j < SudokuApplet.NUM; j++) {
				if (matrix[i][j] == 0) {
					index[0] = i;
					index[1] = j;
					break;
				}
			}
		}
		// No more zero element, means the matrix if fullfilled, return true. 
		if (index[0] == -1) {
			return true;
		}
		// Find the constraint value set.
		Set<Integer> cSet = getConstraint(index[0], index[1]);
		// Try to assign an available value, then call BackTrack.
		// If BackTrack return false, try next available value.
		for (int v = 1; v < 10; v++) {
			if (!cSet.contains(v)) {
				matrix[index[0]][index[1]] = v;
				boolean result = BackTrack();
				if (result)
					return true;
			}
		}
		// No available value found, clear the number in this element, return false.
		matrix[index[0]][index[1]] = 0;
		return false;
	}

	public Set<Integer> getConstraint(int r, int c) {
		Set<Integer> constraintSet = new HashSet<Integer>();
		int baseRow = (int) Math.floor(r / 3) * 3;
		int baseCol = (int) Math.floor(c / 3) * 3;
		for (int i = 0; i < SudokuApplet.NUM; i++) {
			constraintSet.add(matrix[r][i]);
			constraintSet.add(matrix[i][c]);
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				constraintSet.add(matrix[baseRow + i][baseCol + j]);
			}
		}
		constraintSet.remove(0);
		return constraintSet;
	}
}
