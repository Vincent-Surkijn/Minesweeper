import java.util.Scanner;

public class Main {

	private static Grid MineField;
	private static char[] alphabet;
	protected static int size;
	protected static int amountMines;
	protected static int columnSize;

	/**
	 * the heard of the game. It start by asking the difficulty then it creates a minefield
	 * the game begins.
	 * the game itself is a loop of 3 parts
	 * first it shows the grid
	 * then it ask the coordinates
	 * at last it computate the coordinates to a result
	 * *loops* or game is won
	 * the difficulty is asked again and you can start again
	 */
	public static void main(String[] args) {
		alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		boolean running = true;
		size = 8;
		amountMines = 10;
		while(running){
			switch(DifficultyInput()){
				case EASY:
					MineField = new Grid(size, amountMines);
					columnSize = size;
					break;
				case MEDIUM:
					size = 16;
					amountMines = 40;
					MineField = new Grid(size, amountMines);
					columnSize = size;
					break;
				case HARD:
					size = 30;
					amountMines = 99;
					MineField = new Grid(size, amountMines);
					columnSize = 16;
					break;
				case END:
					running = false;
					break;
			}
			while(!(MineField.triggeredMine() || MineField.countMinesLeft() == 0) && running){
				showGameScreen();
			}
		}
	}

	/**
	 * gives a Level value
	 * used for chosing difficulty or ending the game.
	 * the user can input e =easy, m=medium or h=hard
	 * but also end to end the game.
	 */
	private static Level DifficultyInput() {
		System.out.println("Welkom\n" +
				"Choose your difficulty by pressing:\n" +
				"'e' for easy\n" +
				"'m' for medium\n" +
				"'h' for hard\n" +
				"\nIf you want to end the game press:\n" +
				"'end'");
		System.out.print("->");
		Scanner scan = new Scanner(System.in);
		String input = scan.next().toLowerCase().trim();
		if(input.equals("e")){
			return Level.EASY;
		}
		if(input.equals("m")){
			return Level.MEDIUM;
		}
		if(input.equals("h")){
			return Level.HARD;
		}
		if(input.equals("end")){
			return Level.END;
		}
		else{
			System.out.println("\n\nSorry invalid input please try again\n\n");
			return DifficultyInput();
		}
	}

	/**
	 * shows the grid and all the other features like how many bombs left and difficulty and so on...
	 */
	private static void showGameScreen() {
		MineField.checkIfWon();
		if (MineField.checkIfWon()){
			System.out.println("You won!");
			reset();
		}
		else {
			System.out.println("Mines left:"+ MineField.countMinesLeft()+"\n\n");
			System.out.println(MineField.mineFieldToString());
			System.out.println("Enter a number for the row: ");
			Scanner scan = new Scanner(System.in);
			String  input = scan.next();
			int row = Integer.valueOf(input);
			int x =0;
				if (row < 0 || row > size) {
					System.out.println("Invalid row");
					showGameScreen();
				}
			else {
				x = row - 1;
			}
			System.out.println("Enter a character for the column: ");
			Scanner scan2 = new Scanner(System.in);
			String input2 = scan2.next();
			char[] input2char = input2.toCharArray();
			int column = CharToInt(input2char[0]);
			int y = 0;
			if (column < 0 || column > columnSize){
				System.out.println("Invalid column");
				showGameScreen();
			}
			else {
				y = column;
			}
			InputCoordinate(x,y);
		}
	}

	/**
	 * first checks for flag or check
	 * then if check
	 * checks
	 *         if bom if true return true else false
	 *         if zero keep checking neigbours with zero and make them visual
	 * flags
	 *         always return false
	 *         doesn't matter if is marked or if it is just a tile the flag always is placed and the minesleft variable goes down one
	 *         
	 */
	private static void InputCoordinate(int row, int column) {
		if (MineField.getTile(row,column).mine){
			System.out.println("Game over");
			MineField.reset();
		}
		else {
			MineField.checkNeighbors(row, column);
			/*MineField.checkNeighbors(row + 1, column + 1);
			MineField.checkNeighbors(row, column + 1);
			MineField.checkNeighbors(row - 1, column + 1);
			MineField.checkNeighbors(row + 1,column);
			MineField.checkNeighbors(row - 1, column - 1);
			MineField.checkNeighbors(row, column - 1);
			MineField.checkNeighbors(row + 1, column - 1);*/
		}
	}

	/**
	 * used to convert the char to integers everywhere in the code
	 * @param character
	 */
	public static int CharToInt(char character) {
		int place = 0;
		for(char Char: alphabet){
			if(Char == character){
				return place;
			}
			place++;
		}
		return -1;
	}

	public static char IntToChar(int number){
		return alphabet[number];
	}

	public static void reset(){
		MineField = new Grid(size, amountMines);
	}
}