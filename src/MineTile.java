public class MineTile extends Tile {

	public void checkNeighbors() {
		// TODO - implement MineTile.checkNeighbors
		throw new UnsupportedOperationException();
	}

	public void mineNeighbors() {
		neighbors = -1;
	}

	/**
	 * does the same as the tile only sets mine to true
	 * @param row
	 * @param column
	 */
	public MineTile(int row, char column) {
		super(row, column);
		mine = true;
	}

}