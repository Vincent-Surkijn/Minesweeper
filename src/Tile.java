public abstract class Tile {

	protected int row;
	protected char column;
	protected int neighbors;
	protected boolean isvisual;
	protected boolean mine;

	/**
	 * makes tile, sets row, column to input
	 * sets neighbors to zero
	 * isvisual and mine to false
	 * @param in_row
	 * @param in_column
	 */
	public Tile(int in_row, char in_column) {
		row = in_row;
		column = in_column;
		isvisual = false;
		this.mineNeighbors();
	}

	//public abstract void checkNeighbors();

	public String toString() {
		if(isvisual){
			if(mine){
				return "O~*";
			}
			else{
				if (neighbors == 0){
					return " _ ";
				}
				else {
					return " " + neighbors + " ";
				}
			}
		}
		else{
			return "[ ]";
		}
	}

	public abstract void mineNeighbors();

	/**
	 * this resets the neighbor value to zero
	 */
	public void resetNeighbors() {
		// TODO - implement Tile.resetNeighbors
		throw new UnsupportedOperationException();
	}

	public boolean isMine() {
		return mine;
	}



}