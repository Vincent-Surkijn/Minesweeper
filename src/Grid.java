import java.util.Random;

public class Grid extends Main{

	private Tile[][] grid;
	//private int minesleft;

	/**
	 * First makes random couples for the Mine coordinates.
	 * Then makes a array of Tiles specific to the given amount. When a coordinate is edited with a coordinate form the mines then a MineTile is created otherwise a EmptyTile is created.
	 * then every Tile will check his neighbors with the checkNeigbors function.
	 * also if the value of the size is bigger as 16 then only the with would grow wider
	 *
	 * The coordinates are like this 'grid[x][y]' with the 0 point at the top left corner of the grid
	 * @param size
	 * @param amountMines
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

		//makes acual tiles in the grid mines and empties
		for(int x = 0; x < grid.length; x++){
			for(int y= 0; y < grid[0].length; y++){
				boolean wasMine = false;
				for(int[] coor : minecoordinates){
					int[] place = {x, y};
					if(coor[0] == place[0] && coor[1] == place[1]){
						grid[x][y] = new MineTile(x, Main.IntToChar(y));
						wasMine = true;
						}
					}
				if(!wasMine){
					grid[x][y] = new EmptyTiles(x, Main.IntToChar(y));
				}
			}
		}
	}

	public int getLenght(){
		return grid.length;
	}
	/**
	 * Makes a ordered string of the minefield (looking if the tiles are hidden or not)
	 */
	public Tile getTile(int row, int column){
		return grid[row][column];
	}

	public String mineFieldToString() {
		String outputstring = "|";
		for(int i = 0; i < grid.length; i++){
			if(i < 9){
				outputstring += 0;
			}
			outputstring += (i+1)+"|";
		}
		outputstring += "\n";

		for(int y = 0; y < grid[0].length; y++){
			outputstring += Main.IntToChar(y);
			for(int x = 0; x < grid.length; x++){
				outputstring += grid[x][y].toString();
			}
			outputstring += "\n";
		}
		return outputstring;
	}

	/**
	 * it let's all tiles reset their neighbors value and recount them.
	 */
	public void recountNeighbors() {
		// TODO - implement Grid.recountNeighbors
		throw new UnsupportedOperationException();
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
	 * this enables a Tile at (row, column) and will decide which actions the tile must make by determining if it has been flagged or clicked
	 * it returns a string that can be printed
	 * Like (Tile is out of bounds) or (Tile is already clicked) or (you can't click flagged tiles)
	 * @param column
	 * @param row
	 * @param flag
	 */
	public String activateTile(char column, int row, boolean flag) {
		// TODO - implement Grid.activateTile
		throw new UnsupportedOperationException();
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
	 * 
	 * @param row
	 * @param column
	 */
	public int CheckMine(int row,int column) {
		if (row < 0 || row > size|| column < 0 || column > columnSize) {
			return 0;
		}
		else {
			Tile tileToCheck = grid[row][column];
			if (tileToCheck.mine) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	public int checkNeighbors(int row, int column){
		int neighbors = 0;
		neighbors += CheckMine(row + 1, column + 1);
		neighbors += CheckMine(row, column + 1);
		neighbors += CheckMine(row - 1, column + 1);
		neighbors += CheckMine(row + 1,column);
		neighbors += CheckMine(row - 1, column - 1);
		neighbors += CheckMine(row, column - 1);
		neighbors += CheckMine(row + 1, column - 1);
		grid[row][column].neighbors = neighbors;
		grid[row][column].isvisual = true;
		return neighbors;
	}


}