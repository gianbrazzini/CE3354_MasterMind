package games;

import java.util.*;
import java.io.*;

public class SEHangman implements BaseGame
{

	private Scanner scanner = new Scanner( System.in );
	private Random random = new Random();
	private GetWordList gw = new GetWordList();
	private Validate check = new Validate();
	private String wordtoguess;
	private LinkedList<String> guesses = gw.WordList(); // guesses = {"google",
														// "java", "pizza",
														// "software"};

	public SEHangman()
	{
		// empty constructor
	}
	
	public void printInstructions()
	{
		System.out.println("*******************************************************************\n" +
						   "*****************     Welcome to HANGMAN!!!!     ******************\n" + 
						   "** Rules:                                                        **\n" + 
						   "**        1) To win the game you need to enter all of the        **\n" + 
						   "**           characters or guess the word on the final guess!    **\n" +
						   "**        2) Please only enter one Character.                    **\n" +
						   "**        3) To exit the game enter the word \"-\".                **\n" +
						   "**        4) And have fun!!!!                                    **\n" +
						   "*******************************************************************");
	}

	public void startGame()
	{
		boolean startGame = true;
		while (startGame)
		{
			//System.out.println( "------------------- HANGMAN GAME -------------------" );
			char[] randomWordToGuess = guesses.get( random.nextInt( guesses.size() ) ).toCharArray(); // google
																										// ->
																										// g,o,o,g,l,e
			wordtoguess = String.valueOf( randomWordToGuess ); // converts
																// randomWordToGuess
																// to a complete
																// string;
			int guessAmount = randomWordToGuess.length; // gets the length of
														// randomWordToGuess
			char[] guessFromPlayer = new char[guessAmount]; // _ _ _ _
			LinkedList<String> wordsUsed = new LinkedList<>();// going to store
																// in all the
																// inputs from
																// user

			for (int i = 0; i < guessFromPlayer.length; i++)
			{
				guessFromPlayer[i] = '_';
			}

			boolean wordIsGuessed = false;
			int tries = 0;

			while (!wordIsGuessed && tries != guessAmount)
			{
				System.out.print( "\nCurrent guesses: " );
				printArray( guessFromPlayer );
				System.out.println( "Words used: " + wordsUsed );// prints all
																	// of the
																	// user
																	// inputs
				System.out.printf( "You have %d tries left.\n", guessAmount - tries ); // print
																						// out
																						// tries
																						// left
				System.out.println( "Enter a single character" );
				char input = scanner.nextLine().charAt( 0 );
				tries++;

				if ( input == '-' )
				{
					startGame = false;
					wordIsGuessed = true;
				}
				else
				{
					for (int i = 0; i < randomWordToGuess.length; i++)
					{ // checking if the input is a letter from the wordtoguess
						if ( randomWordToGuess[i] == input )
						{
							guessFromPlayer[i] = input;
						}
					}
					wordsUsed.add( String.valueOf( input ) );// adds input from
																// user to
																// wordsUsed

					if ( check.isTheWordGuessed( guessFromPlayer ) )
					{// checks to see if the complete wird is Correct
						wordIsGuessed = true;
						System.out.println( "Correct Word: " + wordtoguess );
						System.out.println( "Congratulations you won!" );
					}
				}

			}

			if ( !wordIsGuessed )// if the word is not guessed then player has
									// chance to guess final word if they want
									// to.
			{
				System.out.print( "\nYou ran out of guesses, Would you like to guess the word? " );
				String wouldLikeToGuess = scanner.nextLine();
				if ( wouldLikeToGuess.equals( "yes" ) || wouldLikeToGuess.equals( "y" ) )
				{
					System.out.print( "\nPlease Enter The Word: " );
					String userWord = scanner.nextLine();
					check.validateWord( userWord, wordtoguess );
				}
				else
				{
					System.out.print( "The Correct Word Was: " + wordtoguess );
				}
				System.out.print( "\nDo you want to play another game? " );
				String newGame = scanner.nextLine();
				if ( newGame.equals( "no" ) || newGame.equals( "n" ) ) startGame = false;

			}
			System.out.println( "\nGame over." );

		}
	}

	public void printArray(char[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print( array[i] + " " );
		}
		System.out.println();
	}

}
