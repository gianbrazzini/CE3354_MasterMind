
public class Difficulty {

	// Sets max and min parameters for the class
	final private int  	MIN_ROWS=3, 	MAX_ROWS=7,	MIN_GUESSES=3, 	MAX_GUESSES=7;
	int easyRows, easyGuesses, mediumrows, mediumguesses;


	public void setEsayRows(int erows){

		if(erows == 0){easyRows = MIN_ROWS;}
		else{easyRows = erows;}
	}

	public int getEasyRows(){
		return 	easyRows;
	}

	public void setEasyGuesses(int eguesses){

		if(eguesses == 0){easyGuesses = MIN_GUESSES;}
		else{easyGuesses = eguesses;}
	}

	public int getEasyGuesses(){
		return easyGuesses;
	}

	public void setMediumRows(int mrows){

		if(mrows == 0){mediumrows = MAX_ROWS;}
		else{mediumrows = mrows;}
	}

	public int getMediumRows(){
		return 	mediumrows;
	}

	public void setMediumGuesses(int mguesses){

		if(mguesses == 0){mediumguesses = MIN_GUESSES;}
		else{mediumguesses = mguesses;}
	}

	public int getMediumuesses(){
		return mediumguesses;
	}
}
