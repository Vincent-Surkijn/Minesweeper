public class EmptyTiles extends Tile {

	public void checkNeigbours() {
		// TODO - implement EmptyTilesd.checkNeigbours
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if Tile is a zero Tile and if true the Tile wil activate the neighboring TIles
	 * as wel to do the same. if the Tile is Zero it sets the visual parameter to true.
	 */
	public void mineNeighbors() {
		// TODO - implement EmptyTilesd.emptyNeighbors
		neighbors = 0;
	}

	/**
	 * does the same as tile
	 * @param row
	 * @param column
	 */
	public EmptyTiles(int row, char column) {
		super(row, column);
		mine = false;
	}

}