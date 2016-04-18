package games;
import java.util.Scanner;

class GameController
{

    public static final String CMD_EXIT = "exit";

    private BaseGame currentGame;

    private String[] gameOptions = {
                                    "1. Mastermind",
                                    "2. Hangman",
                                    "3. Tic-Tac-Toe",
                                };

    private GameController()
    {

    }

    private void gameLoop()
    {

        boolean exit = false;

        Scanner pInput = new Scanner(System.in);
        //Repeat until user inputs "exit"
        do
        {
        	//Print game options
            System.out.println("Please choose a game option:");
            for(String option : gameOptions)
            {
                System.out.println(option);
            }

            //Player chooses option
            String input = pInput.next();

            int gameNum = -1;

            for(int i = 0; i < gameOptions.length; i++)
            {
                String option = gameOptions[i];
                String[] tokens = option.split(" ");

                if(input.toLowerCase().equals(option.toLowerCase()))
                {
                    gameNum = i;
                }
                else
                    for(String token : tokens)
                    {
                        if(input.toLowerCase().equals(token.toLowerCase()) || input.equals(Integer.toString(i+1)))
                        {
                            gameNum = i;
                            break;
                        }
                    }

                if(gameNum >= 0)
                    break;
            }

            exit = input.equals(CMD_EXIT);
            currentGame = null;

            //Create and start game based on user input
            if(exit == false)
            {
                switch(gameNum)
                {
                    case 0:
                        currentGame = new MasterMindGameAI();
                        break;
                    case 1:
                        currentGame = new SEHangman();
                        break;
                    case 2:
                        currentGame = new TicTacToePlayer();
                        break;
                    default:
                        currentGame = null;
                        System.out.println("Invalid input. Please enter a game option by number, or name, or 'exit' to exit.\n");
                        break;
                }
            }

            if(currentGame != null)
            {
            	currentGame.printInstructions();
                currentGame.startGame();
            }

        } while (exit == false);
        pInput.close();
    }

    public static void main(String...args)
    {
        GameController gc = new GameController();
        gc.gameLoop();

    }

}
