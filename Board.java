/*
File name: Board.java
Author: Cami McDonald
Date: 9/27/2021
Class: 231 A
Project 3
*/

import java.io.*;
import java.util.Arrays;
import java.awt.Graphics;

public class Board{

	private Cell[][] grid;
	public static final int size = 9;

	public Board(){

		// initialize grid
		grid = new Cell[this.size][this.size];

		// loop over grid and create a cell with value 0
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){

				this.grid[i][j] = new Cell(i, j, 0);

			}
		}
	}

	public int getRows(){

		return this.size;
	}

	public int getCols(){

		return this.size;
	}

	// returns cell at location r,c
	public Cell get(int r, int c){

		return this.grid[r][c];

	}

	// returns whether a cell is locked or not
	public boolean isLocked(int r, int c){

		return this.grid[r][c].isLocked();
	}

	public int numLocked(){

		int numberLocked = 0;

		// loop over entire grid
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){

				// check if cell at grid location is locked
				if (this.grid[i][j].isLocked() == true){

					// increase locked count
					numberLocked += 1;
				}
			}
		}

		return numberLocked;

	}

	public int value(int r, int c){

		return this.grid[r][c].getValue();
	}

	public void set(int r, int c, int value){

		this.grid[r][c].setValue(value);

	}

	public void set(int r, int c, int value, boolean locked){

		this.grid[r][c].setValue(value);
		this.grid[r][c].setLocked(locked);
	}

	public String toString(){

		String result = "";

		// looop over gird
		for (int i = 0; i < this.size; i++){

			result += "\n";

			// add another line break after every 3 rows
			if (i == 3 || i == 6){

				result += "\n";
			}

			for (int j = 0; j < this.size; j++){

				// add spaces every 3 cells
				if (j == 3 || j == 6){

					result += "   ";
				}

				result += this.grid[i][j] + " ";

			}
		}

		return result;
	}

	public void draw(Graphics g, int scale){

		// loop over grid
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){

				// call draw method from cell class
				this.grid[i][j].draw(g, j + 1, i + 1, scale);

			}
		}
	}

	// checks is a given value is valid at a given row and col
	public boolean validValue(int row, int col, int value){

		// get which 3x3 grid it is in
		int subsectionRowStart = (row / 3) * 3;
		int subsectionRowEnd = subsectionRowStart + 3;

		int subsectionColStart = (col / 3) * 3;
		int subsectionColEnd = subsectionColStart + 3;

		// check if value is between 1 and 9
		if (value > 9 && value < 1){

			return false;
		}

		// check if value is already in row
		for (int i = 0; i < this.size; i++){

			if (this.grid[row][i].getValue() == value){

				if (! (i == col)){

					return false;

				}
			}
		}

		// check if value is already in col
		for (int j = 0; j < this.size; j++){

			if (this.grid[j][col].getValue() == value){

				if (! (j == row)){

					return false;
				}
			}
		}

		// check if value is already in 3x3 grid
		for (int i = subsectionRowStart; i < subsectionRowEnd; i++){
			for( int j = subsectionColStart; j < subsectionColEnd; j++){

				if (this.grid[i][j].getValue() == value){

					if (! (i == row && j == col)){

						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean validSolution(){

		// loop over grid
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){

				// get value at specified position in grid
				int value = this.grid[i][j].getValue();

				// if a cell has a value of 0 then it is not a valid solution
				if (value == 0){

					return false;
				}

				// if cell has an invalid value then it is not a valid solution
				if (this.validValue(i, j, value) == false){

					return false;
				}
			}
		}

		return true;
	}

	public Cell findBestCell(){
	// finds the next available cell to test for solutions

		// loop over grid
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){

				// check if cell is a zero
				if (this.grid[i][j].getValue() == 0){

					// check values 1-9 for a valid value
					for (int k = 1; k < 10; k++){

						// check if value is valid at location in grid
						if (this.validValue(i, j, k)){

							// create new cell
							Cell newCell = new Cell(i, j, k);

							// assign grid at location i, j to newCell
							this.grid[i][j] = newCell;

							// return the cell
							return this.grid[i][j];
						}
					}
					return null;
				}
			}
		}

		return null;
	}

	public Cell findBestCell2(){
	// finds the next best cell to test by finding the cell with the least amount of valid values

		// counter for number of valid values
		int counter = 0;

		// variable for row with samllest number of valid values
		int smallestRow = 0;

		// variable for col with smallest number of valid values
		int smallestCol = 0;

		// initilaize max number of valid values possible
		int smallest = 9;

		// initialize variable for the cell with smallest # of valid values
		Cell smallestCell;

		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){

				// find a zero on the board
				if (this.grid[i][j].getValue() == 0){

					// loop through values 1-9
					for (int k = 1; k < 10; k++){

						// check if value is valid
						if (this.validValue(i, j, k)){

							// increase counter when a valid value is found
							counter ++;
						}
					}

					// return null if no valid cells
					if (counter == 0){

						return null;
					}
				}

				// check if count is smaller than current smallest count
				if (smallest > counter && counter != 0){

					// reassign smallest variable
					smallest = counter;

					// keep track of the row and column of cell with smallest count
					smallestRow = i;
					smallestCol = j;

				}
				// reset counter to zero
				counter = 0;
			}
		}

		// get first valid value for the smallest cell
		for (int k = 1; k < 10; k++){

			if (this.validValue(smallestRow, smallestCol, k)){

				// create a new cell for one with smallest count of valid values
				smallestCell = new Cell(smallestRow, smallestCol, k);

				this.grid[smallestRow][smallestCol] = smallestCell;

				return this.grid[smallestRow][smallestCol];
			}
		}
		return null;
	}

	public boolean read(String filename) {
	    try {
			// assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
	    	FileReader fileReader = new FileReader(filename);

			// assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
	    	BufferedReader bufferedReader = new BufferedReader(fileReader);

			// assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
	    	String line = bufferedReader.readLine();

	    	// grid to hold text input
	    	String[][] tempGrid = new String[this.size][this.size];

	    	int row = 0;

			// start a while loop that loops while line isn't null
	    	while (line != null){

	          // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
	    		String [] result = line.split("[ ]+");

	          // print the String (line)
	    		System.out.println(line);

	          // print the size of the String array (you can use .length)
	    		System.out.println(result.length);

	    		System.out.println(Arrays.toString(result));

				// loop over each number in result array
				for (int k = 0; k < result.length; k++){

					// convert String value to integer
					int num = Integer.parseInt(result[k]);

					// assign integer variable to location i,j in grid
					this.grid[row][k].setValue(num);

					// lock cell
					if (num != 0){

						this.grid[row][k].setLocked(true);
					}
				}

	          // assign to line the result of calling the readLine method of your BufferedReader object.
	    		line = bufferedReader.readLine();
	    		row ++;

	    	}

	      // call the close method of the BufferedReader
	    	bufferedReader.close();

	      // return true
	    	return true;
	    }
	    catch(FileNotFoundException ex) {
	      System.out.println("Board.read():: unable to open file " + filename );
	    }
	    catch(IOException ex) {
	      System.out.println("Board.read():: error reading file " + filename);
	    }

	    return false;
	 }


	 public static void main(String [] args){

	 	Board testBoard = new Board();

	 	testBoard.read(args[0]);

	 	System.out.println(testBoard);

	 	// System.out.println("Should be true:" + testBoard.validValue(1, 1, 4));
	 	// System.out.println("Should be true:" + testBoard.validValue(1, 8, 2));
	 	// System.out.println("Should be true:" + testBoard.validValue(8, 5, 4));

	 	// System.out.println("Should be false:" + testBoard.validValue(1, 1, 3));
	 	// System.out.println("Should be false:" + testBoard.validValue(1, 8, 7));
	 	// System.out.println("Should be false:" + testBoard.validValue(8, 5, 6));

	 	// System.out.println("Should be true:" + testBoard.validSolution());

	 	// Cell cell = testBoard.findBestCell();

	 	// System.out.println(cell.getCol());

	 	// System.out.println(cell.getRow());

	 	// System.out.println(cell.getValue());

	 	// System.out.println(testBoard.validValue(cell.getRow(), cell.getCol(), cell.getValue()));


	 	// Cell cell1 = testBoard.findBestCell();

	 	// System.out.println(cell1.getCol());

	 	// System.out.println(cell1.getRow());

	 	// System.out.println(cell1.getValue());

	 	// System.out.println(testBoard.validValue(cell1.getRow(), cell1.getCol(), cell1.getValue()));


	 	// Cell cell2 = testBoard.findBestCell();

	 	// System.out.println(cell2.getCol());

	 	// System.out.println(cell2.getRow());

	 	// System.out.println(cell2.getValue());

	 	// System.out.println(testBoard.validValue(cell2.getRow(), cell2.getCol(), cell2.getValue()));


	 	// Cell cell3 = testBoard.findBestCell();

	 	// System.out.println(cell3.getCol());

	 	// System.out.println(cell3.getRow());

	 	// System.out.println(cell3.getValue());

	 	// System.out.println(testBoard.validValue(cell3.getRow(), cell3.getCol(), cell3.getValue()));


	 	Cell cell4 = testBoard.findBestCell2();

	 	System.out.println(cell4.getRow());

	 	System.out.println(cell4.getCol());

	 	System.out.println(cell4.getValue());

	 	System.out.println(testBoard.validValue(cell4.getRow(), cell4.getCol(), cell4.getValue()));


	 	// System.out.println(testBoard.getCols());
	 	// System.out.println(testBoard.getRows());

	 	// System.out.println(testBoard.get(1,1));

	 	// System.out.println(testBoard.isLocked(1,1));

	 	// testBoard.set(1, 1, 1, true);

	 	// System.out.println(testBoard.numLocked());

	 	// System.out.println(testBoard);


	}
}