package games;
import java.util.*;

public class MasterMindGameAI implements BaseGame {

	public void printInstructions() {
		System.out.println("Welcome to Master Mind Game");
		System.out.println("=========================================================================================");
		System.out.println("Here are the instruction.");
		System.out.println("The rule and instruction is simiple. Try to guess what color and order the AI has chosen." +
						   "\nYou have twelve turns to figure it out!" +
						   "\n" +
						   "\nThe color option is (r) Red, (g) Green, (b) Blue, (y) Yellow. Enter those letters to" +
						   "\nguess. You have twelve turns to guess what combination the computer has chosen. GL");
		System.out.println("=========================================================================================");
	}

	public int randomColorGenerator() {
		Random randomGenerator = new Random();

		int randomNumber = randomGenerator.nextInt(4);

		return randomNumber;
	}

	public boolean mindMechanics () {
		int turncounter = 1;
		boolean toExit = false;
		ArrayList<String> input = new ArrayList<String>();
		ArrayList<String> inputcopy = new ArrayList<String>();
		ArrayList<String> ramcolor = new ArrayList<String>();

		Scanner terminalInput = new Scanner(System.in);

		for (int i = 1; i < 5; i++) {
			int forRandomColor = randomColorGenerator();

			switch(forRandomColor) {
				case 0: 
					ramcolor.add("r");
					break;
				case 1:
					ramcolor.add("g");
					break;
				case 2:
					ramcolor.add("b");
					break;
				case 3:
					ramcolor.add("y");
					break;
			}
		}

		while (turncounter < 13) {
			int numcolorcorrect = 0;
			int numcolorwrong = 0;
			int maybe = 0;

			inputcopy.clear();

			for (int i = 0; i < 4; i++) {
				System.out.print("Color " + i + ": ");
				input.add(terminalInput.nextLine());
			}

			for(int i = 0; i < ramcolor.size(); i++) {
				inputcopy.add(input.get(i));
			}
			
			for (int j = 0; j < input.size(); j++) {
				numcolorcorrect = 0;
				numcolorwrong = 0;

				System.out.println(inputcopy);

				if (inputcopy.get(j).equals(ramcolor.get(j)))
				{
					inputcopy.set(j, "-1");
					System.out.print("Correct ");
					numcolorcorrect++;
				}
				else if(inputcopy.contains(ramcolor.get(j))){
					inputcopy.set(j, "-1");
					System.out.print("Maybe ");
					maybe++;
				}
				else
				{
					System.out.print("Wrong ");
					numcolorwrong++;
				}


				if(numcolorcorrect > 3) {
					System.out.println(ramcolor);
					System.out.println("Congrats you won with: " + input);
				}
			}

			System.out.println();
			System.out.println(input);

			for (int i = input.size() - 1; i >= 0; i--) {
				input.remove(i);
			}

			System.out.println();

			turncounter ++;

			if(turncounter > 12) {
				System.out.println(ramcolor);
				System.out.println("You lost!");

				toExit = true;
				break;
			}
		}	

		return toExit;
	}

	public void startGame() {
		boolean toExit = false;
		String startexitoption;

		System.out.println("Input in 'continue' to start the game....");
		System.out.println("Else, input in 'exit' to exit the game...");

		Scanner terminalInput = new Scanner(System.in);
		startexitoption = terminalInput.nextLine();

		System.out.println();

		System.out.println(startexitoption.equals("exit"));

		if(startexitoption.equals("exit")) toExit = true;

		while (!toExit) {
			toExit = mindMechanics();
		}
	}
}
