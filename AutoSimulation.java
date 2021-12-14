/*
File name: AutoSimulation.java
Author: Cami McDonald
Date: 10/5/2021
Class: 231 A
Project 3
*/

public class AutoSimulation{

	public static void main(String [] args){

		if (args.length < 2){

			System.out.println("Two command line arguemnts needed: Put desired number of simulations first and then the desried number of starting intitial values in the board");
			return;
		}

		int simulations = Integer.parseInt(args[0]);
		int startingNum = Integer.parseInt(args[1]);

		// initialize counter for number of boards solved
		int solveCounter = 0;

		// run sudoku with desired # of simulations
		for (int i = 0; i < simulations; i ++){

			// create new sudoku game
			Sudoku mySudoku = new Sudoku(startingNum);

			// check if the board was solved
			if (mySudoku.solve(0)){

				// increase counter by 1
				solveCounter ++;
			}else{
			

				continue;
			}
		}

		System.out.println(solveCounter);
		System.out.println(simulations);

		// calculate percetange of boards solved
		double percentSolved = ((float)(solveCounter))/((float)(simulations)) * 100;

		System.out.println("Percent of boards solved:" + " " + percentSolved);

	}
}