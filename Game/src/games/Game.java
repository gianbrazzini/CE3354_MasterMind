package games;
public class Game {
	
	Board board;
	Difficulty difficulty;
	int Score;
	int NumPlayer;
	int Points2Win;
	Pegs CorrectPattern;
	int state;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// Generates a random pattern and sets 'CorrectPatter' equal to it's value
	private void genPattern(){
	}

	// Adds a turn to our board object.
	// Calls 'checkTurn' if the turn entered is equal to 'CorrectPattern'
	private void addTurn(){
		checkTurn();
	}
	
	//Checks if the turn entered is equal to 'CorrectPattern'
	private void checkTurn(){}
	
	// Display function
	private void display(){}
	
	// ??
	private void getModes(){}
}
