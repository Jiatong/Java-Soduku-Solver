/**
 * SudokuApplet
 * Online demo:
 * http://www.andrew.cmu.edu/~jiatongw/sudokuApplet/project-sudoku.html
 * 
 * Copyright 2013, Jiatong Wang
 */
import java.io.*;
class FileReader 
{
  public void ReadFiletoMatrix(String fileName,int[][] matrix ){
		  try{
		  InputStream is = getClass().getResourceAsStream(fileName+".txt");
		  DataInputStream in = new DataInputStream(is);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  //Read File Line By Line
		  int row = 0;
		  while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
			  String[] tokens = strLine.split("[ ]+");
			  for(int c=0; c<SudokuApplet.NUM; c++){
				  try{
					  matrix[row][c] = Integer.parseInt(tokens[c]);
				  }
				  catch(Exception e){
					  System.err.println("Error: " + e.getMessage());
				  }
			  }
			  row++;
		  }
		  in.close();
		    }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
	}
}
