import java.util.Scanner;

public class Main {

	private static Grid MineField;
	private static char[] alphabet;

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
		while(running){
			switch(DifficultyInput()){
				case EASY:
					MineField = new Grid(8, 10);
					break;
				case MEDIUM:
					MineField = new Grid(16, 40);
					break;
				case HARD:
					MineField = new Grid(30, 99);
					break;
				case END:
					running = false;
					break;
			}
			while(!(MineField.triggeredMine() || MineField.countMinesLeft() == 0) && running){
				showGameScreen();
				//InputCoordinate();
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
		System.out.println("Mines left:"+ MineField.countMinesLeft()+"\n\n");
		System.out.println(MineField.mineFieldToString());
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
	private static void InputCoordinate() {
		// TODO - implement Main.InputCoordinate
		throw new UnsupportedOperationException();
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
}