package games;
public class Difficulty {

	// Sets max and min parameters for the class
	final private int  	MIN_ROWS=3, 	MAX_ROWS=7,	MIN_GUESSES=3, 	MAX_GUESSES=7;
	int easyRows, easyGuesses, mediumrows, mediumguesses;


	public void setEsayRows(int erows){

		if(erows == 0){easyRows = MIN_ROWS;} // get MIN_ROWS if they insert 0
		else{easyRows = erows;}
	}

	public int getEasyRows(){
		return 	easyRows;
	}

	public void setEasyGuesses(int eguesses){

		if(eguesses == 0){easyGuesses = MIN_GUESSES;} // get MIN_GUESSES if they insert 0
		else{easyGuesses = eguesses;}
	}

	public int getEasyGuesses(){
		return easyGuesses;
	}

	public void setMediumRows(int mrows){

		if(mrows == 0){mediumrows = MAX_ROWS;} // get MAX_ROWS if they insert 0
		else{mediumrows = mrows;}
	}

	public int getMediumRows(){
		return 	mediumrows;
	}

	public void setMediumGuesses(int mguesses){

		if(mguesses == 0){mediumguesses = MAX_GUESSES;} // get MAX_GUESSES if they insert 0
		else{mediumguesses = mguesses;}
	}

	public int getMediumuesses(){
		return mediumguesses;
	}
}
