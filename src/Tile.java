public abstract class Tile {

	protected int neighbors;
	protected boolean isvisual;
	protected boolean mine;
	protected boolean flagged;

	/**
	 * makes tile
	 * sets neighbors to zero
	 * isvisualto false
	 */
	public Tile() {
	    neighbors = 0;
		flagged = false;
		isvisual = false;
	}

    /**
     * returns a string combination of 3 charaters spesific to this tile
     */
	public String toString() {
		if(!flagged){
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
		else{
		    return "[F]";
        }
	}

    /**
     * setters and getters
     */

	public boolean isMine() {
		return mine;
	}

	public boolean isFlagged(){
	    return flagged;
    }

    public void toggleFlag(){
	    flagged = !flagged;
    }

    public void makeVisual(){
	    isvisual = true;
    }

	public void setNeighbors(int countedneighbors){
		neighbors = countedneighbors;
	}

	public boolean zeroNeighbors(){
		return (neighbors == 0);
	}

}