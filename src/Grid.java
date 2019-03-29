import java.util.Random;

public class Grid extends Main{

	private Tile[][] grid;

	/**
	 * First makes random couples for the Mine coordinates.
	 * Then makes a array of Tiles specific to the given amount. When a coordinate is edited with a coordinate form the mines then a MineTile is created otherwise a EmptyTile is created.
	 * then every Tile will check his neighbors with the checkNeigbors function.
	 * also if the value of the size is bigger as 16 then only the with would grow wider
	 *
	 * The coordinates are like this 'grid[x][y]' with the 0 point at the top left corner of the grid
	 */
	public Grid(int size, int amountMines) {
		//creating empty grid
		if(size > 16){
			grid = new Tile[size][16];
		}
		else{
			grid = new Tile[size][size];
		}
		//randomly generating minecoordinates
		int[][] minecoordinates = new int[amountMines][2];
		Random rand = new Random();

		for(int r = 0; r < amountMines; r++ ){
			//catch overlap
			int[] minecoordinate = {rand.nextInt(grid.length), rand.nextInt(grid[0].length)};
			minecoordinates[r] = minecoordinate;
			//form [[x,y]]
		}

		//makes actual tiles in the grid mines and empties
		for(int column = 0; column < grid.length; column++){
			for(int row = 0; row < grid[0].length; row++){
				boolean wasMine = false;
				for(int[] coor : minecoordinates){
					int[] place = {column, row};
					if(coor[0] == place[0] && coor[1] == place[1]){
						grid[column][row] = new MineTile();
						wasMine = true;
						}
					}
				if(!wasMine){
					grid[column][row] = new EmptyTiles();
				}
			}
		}
		//count the neighbors of all the empty tiles
		setAllNeighbors();
	}

	/**
	 * methods who return the lenght and hight of the grid
	 */
	public int getLenght(){
		return grid.length;
	}

	public int getHight(){
		return grid[0].length;
	}

	public Tile getTile(int row, int column){
		return grid[column][row];
	}

	/**
	 * Makes a ordered string of the minefield (looking if the tiles are hidden or not)
	 */
	public String mineFieldToString() {
		String outputstring = "|";
		for(int i = -1; i < grid.length; i++){
			if(i < 9){
				outputstring += 0;
			}
			outputstring += (i+1)+"|";
		}
		outputstring += "\n";

		for(int row = 0; row < grid[0].length; row++){
			outputstring += Main.IntToChar(row);
			for(int column = 0; column < grid.length; column++){
				outputstring += grid[column][row].toString();
			}
			outputstring += "\n";
		}
		return outputstring;
	}

	/**
	 * gives the amount of undiscovered mines
	 */
	public int countMinesLeft() {
		int counter = 0;
		for(Tile[] column: grid){
			for(Tile tile: column){
				if(tile.isMine()){
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * Checks if there is a mine in the grid with a visual true
	 */
	public boolean triggeredMine() {
		for(Tile[] column: grid){
			for(Tile tile: column){
				if(tile.isvisual && tile.isMine()){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks the mine
	 */
	private int CheckMine(int row, int column) {
		if (row < 0 || row >= grid[0].length || column < 0 || column >= grid.length) {
			return 0;
		}
		else {
			if (grid[column][row].isMine()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	private int checkNeighbors(int row, int column){
		int neighbors = 0;
		neighbors += CheckMine(row + 1, column + 1);
		neighbors += CheckMine(row, column + 1);
		neighbors += CheckMine(row - 1, column + 1);
		neighbors += CheckMine(row + 1,column);
		neighbors += CheckMine(row - 1, column);
		neighbors += CheckMine(row - 1, column - 1);
		neighbors += CheckMine(row, column - 1);
		neighbors += CheckMine(row + 1, column - 1);
		return neighbors;
	}

	private void setAllNeighbors(){
		for(int row = 0; row < grid[0].length; row++){
			for(int column = 0; column < grid.length; column++){
				if(!grid[column][row].isMine()){
					grid[column][row].setNeighbors(this.checkNeighbors(row,column));
				}
			}
		}
	}

	public void makeTileVisual(int row, int column){
		if(row >= 0 && row < grid[0].length && column >= 0 && column < grid.length && !grid[column][row].isvisual){
			System.out.println("row"+row+"column"+column);
			grid[column][row].toString();
			grid[column][row].makeVisual();
			if(grid[column][row].zeroNeighbors()){
				makeTileVisual(row+1,column);
				makeTileVisual(row-1,column);
				makeTileVisual(row,column+1);
				makeTileVisual(row,column-1);
			}
		}
	}

	public void makeallvisual(){
		for(Tile[] column: grid){
			for(Tile tile: column){
				tile.makeVisual();
			}
		}
	}

	public boolean checkIfWon(){
		boolean won = false;
		int notVisual = 0;
		int mineFlagged = 0;
		for (Tile[] tiles: grid) {
			for(Tile tile: tiles){
				if (!tile.mine) {
					if (!tile.isvisual) {
						notVisual++;
					}
				}
				else{
					if(tile.flagged){
						mineFlagged++;
					}
				}
			}
		}
		if (notVisual == 0){
			won = true;
			System.out.println("you have clicked all empty tiles!");
		}
		if (mineFlagged == Main.getAmountMines()){
			won = true;
			System.out.println("You have flagged all mines!");
		}
		return won;
	}

	public int getAmountFlags(){
		int tileFlagged = 0;
		for (Tile[] tiles: grid) {
			for(Tile tile: tiles){
				if(tile.flagged){
					tileFlagged++;
				}
			}
		}
		return tileFlagged;
	}
}