/*
File name: CellStack.java
Author: Cami McDonald
Date: 9/27/2021
Class: 231 A
Project 3
*/

public class CellStack{

	private Cell [] stack;
	private int top;
	private int max;

	public CellStack(){

		this.max = 10;
		this.stack = new Cell[this.max];
		this.top = -1;

	}

	public CellStack(int max){

		this.max = max;
		this.stack = new Cell[this.max];
		this.top = -1;
	}

	// checks if stack is full
	public boolean isFull(){

		return (this.top + 1 == this.max);
	}

	public void push(Cell c){

		// check if stack is full and needs to be expanded
		if (this.isFull()){

			// create new stack with double the size
			Cell [] newStack = new Cell[this.max * 2];
			this.max *= 2;

			// copy over all elements from old stack to new larger stack
			for (int i = 0; i < this.top + 1; i++){
				
				newStack[i] = this.stack[i]; 
			}

			this.stack = newStack;

		}

		// increase size of stack
		this.top++;

		// put the given cell at the top of the stack
		this.stack[top] = c;
	}

	public Cell pop(){

		// check to make sure stack is not empty
		if (this.empty() == false){

			// retrieve item on top of stack
			Cell item = this.stack[this.top];

			// reduce size of stack
			this.top--;

			return item;
		}

		return null;
	}

	public int size(){

		return this.top +1;
	}

	public boolean empty(){

		// if stack size is -1 then stack is empty
		if (this.top == -1){

			return true;
		}

		return false;
		
	}


	public static void main(String [] args){

		CellStack testStack = new CellStack();

	}

}