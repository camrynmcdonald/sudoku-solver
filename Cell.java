/*
File name: Cell.java
Author: Cami McDonald
Date: 9/27/2021
Class: 231 A
Project 3
*/

import java.awt.Graphics;

public class Cell{

	private int row;
	private int col;
	private int value;
	private boolean locked;

	public Cell(){

		this.row = 0;
		this.col = 0;
		this.value = 0;
		this.locked = false;
	}

	public Cell(int row, int col, int value){

		this.row = row;
		this.col = col;
		this.value = value;
		this.locked = false;
	}

	public Cell(int row, int col, int value, boolean locked){

		this.row = row;
		this.col = col;
		this.value = value;
		this.locked = locked;
	}

	public int getRow(){
		 return this.row;
	}

	public int getCol(){
		return this.col;
	}

	public int getValue(){
		return this.value;
	}

	public void setValue(int newval){

		this.value = newval;
	}

	public boolean isLocked(){
		return this.locked;
	}

	public void setLocked(boolean lock){
		this.locked = lock;
	}

	// creates a clone of cell
	public Cell clone(){

		Cell clone = new Cell();

		clone = this;

		return clone;
	}

	public String toString(){

		String result = "";

		result += Integer.toString(value);

		return result;
	}

	// draws cell with its corresponding value
	public void draw(Graphics g, int x0, int y0, int scale){

		int dx = x0 * scale;
		int dy = y0 * scale;

		// create charcter array
		char characters [] = new char[2];

		characters[0] = (char)('0' + this.value);
		characters[1] = 0;

		g.drawChars(characters, 0, 1, dx, dy);

	}


	public static void main(String [] args){

		Cell testCell = new Cell(1, 1, 1, true);

		System.out.println(testCell.getRow());

		testCell.getCol();

		testCell.getValue();

		testCell.setValue(2);

		testCell.isLocked();

		testCell.setLocked(false);

		Cell clone = testCell.clone();

		System.out.println(testCell);
		System.out.println(clone);
	}


}