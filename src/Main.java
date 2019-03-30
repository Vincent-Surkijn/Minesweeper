import java.util.Scanner;

public class Main {

	private static Grid MineField;
	private static char[] alphabet;
	private static Scanner scan;
	private static int amountMines;
	private static boolean firstclick;
	private static boolean debugging;

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
		//indicator to check if the game is in it's first round.
        firstclick = true;
        //full program loop
        while(running){
            //grid creation depending on difficulty
			switch(DifficultyInput()){
				case EASY:
					amountMines =10;
					MineField = new Grid(8, amountMines);
					break;
				case MEDIUM:
					amountMines = 40;
					MineField = new Grid(16, amountMines);
					break;
				case HARD:
					amountMines = 99;
					MineField = new Grid(30, amountMines);
					break;
				case END:
					running = false;
					break;
				case DEBUG:
					debugging = true;
					break;
			}
			//the iteration of one full game
			while(!(MineField.triggeredMine() || MineField.checkIfWon()) && running){
				showGameScreen(debugging);
				while(inputUser()){
                    System.out.println("\nError\n");
                }
			}
			if(MineField.triggeredMine()) {
				System.out.println("Game over");
			}
			else {
				System.out.println("You won!");
			}
			MineField.makeAllVisual();
			showGameScreen(debugging);
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
		if(input.equals("debug")){
			return Level.DEBUG;
		}
		else{
			System.out.println("\n\nSorry invalid input please try again\n\n");
			return DifficultyInput();
		}
	}

	/**
	 * shows the grid and all the other features like how many bombs left and difficulty and so on...
	 */
	private static void showGameScreen(boolean debug) {
		System.out.println("flags left to place: "+ (amountMines - MineField.getAmountFlags()) +"\n\n");
		System.out.println(MineField.mineFieldToString(debug));
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
        if(!validInput(inputstring)){
            System.out.println("\n\nInvalid input");
            return true;
        }
        else{
            //will only return false if everything went good
            return GameAlgorithm(inputstring);
        }
	}

	private static boolean validInput(String input){
	    boolean isvalid = false;
		//checks right length
		isvalid = input.length() == 4 || input.length() == 5;
		if(!isvalid){
			return false;
		}
		//checks flag or click and if there is a dubble point
		isvalid = isvalid && input.charAt(0) == 'f' || input.charAt(0) == 'c'
				&& input.charAt(1) == ':';
	    if(!isvalid){
	    	return false;
		}
        //checks the row
	    if(Character.isLetter(input.charAt(2))) {
            isvalid = isvalid && (0 <= CharToInt(input.charAt(2)) && CharToInt(input.charAt(2)) < MineField.getHight());
        } else
            {return false;}
	    //checks the column
        if(input.length() == 4) {
            if (Character.isDigit(input.charAt(3))) {
                isvalid = isvalid && Integer.parseInt(input.substring(3)) < MineField.getLenght();
            } else{
                return false;
            }
        }
        if(input.length() == 5){
            if (Character.isDigit(input.charAt(3)) && Character.isDigit(input.charAt(4))) {
                isvalid = isvalid && Integer.parseInt(input.substring(3)) < MineField.getLenght();
            }
            else{
                return false;
            }
        }
	    return isvalid;
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
	    	if(MineField.getAmountFlags() < amountMines) {
				ChosenTile.toggleFlag();
			}
	    	else {
				System.out.println("You can only flag as much tiles as there are mines!");
			}
	    	return false;
        }
	    if(input.split(":")[0].equals("c") && !ChosenTile.isFlagged()){
            while(firstclick && MineField.getTile(CharToInt(input.charAt(2)), Integer.parseInt(input.substring(3))).isMine()){
                MineField = new Grid(MineField.getLenght(), amountMines);
            }
            firstclick = false;
	        MineField.makeTileVisual(CharToInt(input.charAt(2)), Integer.parseInt(input.substring(3)));
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

	public static int getAmountMines(){
		return amountMines;
	}

}