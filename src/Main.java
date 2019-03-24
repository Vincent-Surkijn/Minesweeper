import java.util.Scanner;

public class Main {

	private static Grid MineField;
	private static char[] alphabet;
	private static Scanner scan;

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
	    //for char to int conversion
		alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		//indicator the game is still in proces
		boolean running = true;
        //full program loop
        while(running){
            //grid creation depending on difficulty
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
			//the iteration of one full game
			while(!(MineField.triggeredMine() || MineField.countMinesLeft() == 0) && running){
				showGameScreen();
				while(inputUser()){
                    System.out.println("\nError\n");
                }
			}
            System.out.println("Game over");
			MineField.makeallvisual();
			showGameScreen();
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
		scan = new Scanner(System.in);
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
	private static boolean inputUser() {
	    //input
        System.out.println("\nplease insert the coordinates:\npress i if you want more info");
        System.out.print("->");
        scan = new Scanner(System.in);
        String inputstring = scan.next().toLowerCase().trim();
        if(inputstring.equals("i")){
            System.out.println("the input form is like this:\n C/F:row, column" +
                    "\n - C/F stand for click (C) or flag (F) so press one of the two" +
                    "\n - the row bust be the letter of the row between a and " + IntToChar(MineField.getHight())+
                    "\n - the column should be the number of the column between 0 and "+ MineField.getLenght()+
                    "\n Example: C:a2\nThis means a click on the first row and second column\n\n");
            System.out.print("->");
            scan = new Scanner(System.in);
            inputstring = scan.next().toLowerCase().trim();
        }
        //boolean calculation if input in valid
        boolean validinput =
                inputstring.length() == 4 || inputstring.length() == 5
                && inputstring.charAt(0) == 'f' || inputstring.charAt(0) == 'c'
                && inputstring.charAt(1) == ':'
                && 0 < CharToInt(inputstring.charAt(2)) && CharToInt(inputstring.charAt(2)) < MineField.getHight()
                && Integer.parseInt(inputstring.substring(3)) < MineField.getLenght();
        if(!validinput){
            System.out.println("\n\nInvalid input");
            return true;
        }
        else{
            //will only return false if everything went good
            return GameAlgorithm(inputstring);
        }
	}

	/**
     * This class contains the actual algorithm for the game itself
	 */
	private static boolean GameAlgorithm(String input){
	    Tile ChosenTile = MineField.getTile(CharToInt(input.charAt(2)), Integer.parseInt(input.substring(3)));
	    if(ChosenTile.isvisual){
            System.out.println("\n\nIs already unlocked");
	        return true;
        }
	    if(input.split(":")[0].equals("f")){
	        ChosenTile.toggleFlag();
	        return false;
        }
	    if(input.split(":")[0].equals("c") && !ChosenTile.isFlagged()){
            MineField.makeTileVisual(CharToInt(input.charAt(2)), Integer.parseInt(input.substring(3)));
            System.out.println("komt hier");
            return false;
        }
	    else{
            System.out.println("\n\nThis tile is flagged and can't be clicked");
            return true;
        }
    }

	/**
	 * used to convert the char to integers everywhere in the code
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

    /**
     * converts a integer to a character of the alphabet
     */
	public static char IntToChar(int number){
		return alphabet[number];
	}

}