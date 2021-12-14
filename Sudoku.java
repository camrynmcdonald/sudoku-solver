/*
File name: Sudoku.java
Author: Cami McDonald
Date: 9/29/2021
Class: 231 A
Project 3
*/

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sudoku{

	private Board board;
	private LandscapeDisplay landscape;

	public Sudoku(){

		this.board = new Board();
		this.landscape = new LandscapeDisplay(this.board, 30);

	}

	public Sudoku(int startingNum){

		Random rand = new Random();

		this.board = new Board();

		this.landscape = new LandscapeDisplay(this.board, 30);

		// counter for number of values put in grid
		int counter = 0;

		// loop for number of starting values
		while(counter < startingNum){

			// generate random row, col, and value
			int randRow = rand.nextInt(9);
			int randCol = rand.nextInt(9);
			int randValue = rand.nextInt(9) + 1;

			// check to make sure value is 0
			if (this.board.get(randRow, randCol).getValue() == 0){

				// check to make sure value is valid
				if (this.board.validValue(randRow, randCol, randValue)){

					this.board.get(randRow, randCol).setValue(randValue);
					this.board.get(randRow, randCol).setLocked(true);

					// increase counter
					counter ++;

				}
			}
		}
	}

	public boolean solve(int delay){

		//Given: a stack, and the number of locked cells
		CellStack stack = new CellStack();

		int numLocked = this.board.numLocked();

		// while the stack size is less than the number of unspecified cells
		while (stack.size() < (81 - numLocked)){

			if( delay > 0 ){

		        try {
		            Thread.sleep(delay);
		        }
		        catch(InterruptedException ex) {
		            System.out.println("Interrupted");
		        }
		        landscape.repaint();
		        
	    	}

			//select the next cell to check 
			Cell nextCell = this.board.findBestCell();

			//if there is a valid next cell to try
			if (nextCell != null){
			
				//push the cell onto the stack
				stack.push(nextCell);

				// update the board
				this.board.set(nextCell.getRow(), nextCell.getCol(), nextCell.getValue());

			}

			else{

				// create variable for being stuck
				boolean stuck = true;

				//while it is necessary and possible to backtrack
				while (stack.size() > 0 && stuck == true){

					//pop a cell off the stack
					Cell popped = stack.pop();

					//check if there are other untested values this cell could try
					for (int i = popped.getValue() + 1; i < this.board.size + 1; i++){
						if (this.board.validValue(popped.getRow(), popped.getCol(), i)){

							//push the cell with its new value onto the stack
							popped.setValue(i);

							stack.push(popped);

							//update the board
							this.board.set(popped.getRow(), popped.getCol(), popped.getValue());

							stuck = false;

							//break
							break;
						}
					}

					if (stuck && popped != null){

						//set this cell's value to 0 on the board
						popped.setValue(0);

						this.board.set(popped.getRow(), popped.getCol(), popped.getValue(), false);

					}
				}
			}

			//if the stack size is 0 (no more backtracking possible)
			if (stack.empty()){

				//return false: there is no solution
				System.out.println("stack is empty");
				return false;
			}
		}
		System.out.println(this.board.validSolution());
		// return true: the board contains the solution
		return true;
	}

	public String toString(){

		return this.board.toString();

	}

	public static void main(String [] args){

		int startingNum = Integer.parseInt(args[0]);

		// String file = args[0];

		Sudoku test = new Sudoku(startingNum);

		// test.board.read(file);

		System.out.println("Original board:" + test.board.toString());

		long startTime = System.nanoTime();

		System.out.println(test.solve(10));

		long endTime = System.nanoTime();

		// get difference of two nanoTime values
		long timeElapsed = endTime - startTime;

		System.out.println("Time in milliseconds:" + timeElapsed/100000);

		System.out.println(test);
	}
}
