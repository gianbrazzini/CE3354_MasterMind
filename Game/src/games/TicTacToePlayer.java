package games;
import java.util.*;

public class TicTacToePlayer implements BaseGame
{

    public TicTacToePlayer()
    {
    	//Empty Constructor
    }

    /**
     * Implement from BasicGame.
     * Starts and controls the tic-tac-toe game.
     */
    public void startGame()
    {
    	//Tell user to choose game mode.
    	System.out.println("Please type 'H' to play Human vs Human, or 'C' to play Human vs Computer.\n");
    	
    	//The board
        TicTacToe tttBoard = new TicTacToe();
        // Scanner to get user input
        Scanner pInput = new Scanner(System.in);
        
        //Fields to track player AI choice
        boolean vsAI = true;
        String aiChoice = null;
        
        //Get player choice
        do
        {
        	aiChoice = pInput.nextLine();
        	if(aiChoice.startsWith("H"))
        	{
        		vsAI = false;
        	}
        	else if(aiChoice.startsWith("C"))
        	{
        		vsAI = true;
        	}
        }while(aiChoice.startsWith("H") == false && aiChoice.startsWith("C") == false);

        // Used to store player's input
        int pRow, pCol;
        char currentPlayerMark = 'X';
        do
        {
        	//If the player is against an AI, and it's the AI's turn:
        	if(currentPlayerMark == 'O' && vsAI == true)
        	{
        		System.out.println("AI moving...");
        		int[] move = makeAIMove(tttBoard, 'O', 0);
        		pRow = move[1];
        		pCol = move[2];
        	}
        	else	//Else, it must be a human's turn, so ask for their input
        	{
        		System.out.println("Player " + currentPlayerMark + ", Enter row number(1, 2, or 3) and column number (1, 2, or 3): ");
        		pRow = pInput.nextInt() -  1;
        		pCol = pInput.nextInt() - 1;
        		
        		// Input validation
        		while((pRow < 0 || pRow >= 3) || (pCol < 0 || pCol >= 3) || tttBoard.checkBox(pRow, pCol) == false)
        		{
        			System.out.println("This is invalid. Please enter a row number from 1 to 3 and a column number from 1 to 3: ");
        			pRow = pInput.nextInt() - 1;
        			pCol = pInput.nextInt() - 1;
        		}        		
        	}

        	//Update the board with the marker
            tttBoard.placeMark(pRow, pCol, currentPlayerMark);   // Places the players mark down on the board
            //Print the board state
            tttBoard.printBoard();            // Prints the board

            System.out.println("\n");		//Buffer space
            
            // If a player wins end the game, else change players
            if((tttBoard.checkForWin() == true) && (currentPlayerMark == 'X'))
            {
                System.out.println("Player X wins!");
                break;
            }
            else if((tttBoard.checkForWin() == true) && (currentPlayerMark == 'O'))
            {
                System.out.println("Player O wins!");
                break;
            }
            else
            {
                if(currentPlayerMark == 'X')
                {
                    currentPlayerMark = 'O';
                }
                else
                    currentPlayerMark = 'X';
            }

        }while(tttBoard.isBoardFull() == false);

        if(tttBoard.isBoardFull() == true && tttBoard.checkForWin() == false)
        {
            System.out.println("Tie Game!");
        }
    }

    /**
     * Implemented from BasicGame.
     * Prints basic instructions.
     */
    public void printInstructions()
    {
        String instructions =
        		"*************************     Welcome to Tic-Tac-Toe!     *************************\n"
        	  + "Rules:\n"
        	  + "\t1. Get 3 in a row.\n"
        	  + "\t2. Don't let your opponent get 3 in a row.\n\n"
        	  + "There are two modes:\n"
        	  + "\tHuman vs Human\n"
        	  + "\tHuman vs Computer\n";
        System.out.println(instructions);
    }

    /**
     * Minimax-esque algorithm to find the AI's  next move.
     */
    private int[] makeAIMove(TicTacToe board, char aiMarker, int depth)
    {
        if(board.checkForWin() || board.isBoardFull())
            return new int[]{calcScore(board, aiMarker), -1, -1};

        ArrayList<int[]> children = new ArrayList<int[]>();

        int[] bestScore = new int[3];
        int currScore;
        if(depth % 2 == 0)      //if (player is computer, i.e., maximizing player)
        {
            //fill children with all legal moves for this player.
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(board.checkBox(i, j) == true)
                    {
                        children.add(new int[]{i, j});
                    }
                }
            }

            bestScore[0] = Integer.MIN_VALUE;
            for(int[] move : children)
            {
                TicTacToe child = board.cloneGame();
                child.placeMark(move[0], move[1], aiMarker);

                currScore = makeAIMove(child, aiMarker, depth-1)[0];
                if(currScore > bestScore[0])
                {
                    bestScore[0] = currScore;
                    bestScore[1] = move[0];
                    bestScore[2] = move[1];
                }
            }
            return bestScore;

        }
        else        //(player is opponent, i.e., minimizing player)
        {
            char otherPlayerMark = (aiMarker == 'X') ? 'O' : 'X';
            //fill children with all legal moves for this player.
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(board.checkBox(i, j) == true)
                    {
                        children.add(new int[]{i, j});
                    }
                }
            }

            bestScore[0] = Integer.MAX_VALUE;
            for(int[] move : children)
            {
                TicTacToe child = board.cloneGame();
                child.placeMark(move[0], move[1], otherPlayerMark);

                currScore = makeAIMove(child, aiMarker, depth-1)[0];
                if(currScore < bestScore[0])
                {
                    bestScore[0] = currScore;
                    bestScore[1] = move[0];
                    bestScore[2] = move[1];
                }
            }
            return bestScore;

        }

    }

    /**
     * Calculates a heuristic for the given board
     * @param board - The board to evaluate
     * @param positiveMarker - The marker that counts positively toward the score
     * @return
     */
    private int calcScore(TicTacToe board, char positiveMarker)
    {
        int total = 0;

        for(int i = 0; i < 3; i++)
        {
            total += calcRowScore(board, i, positiveMarker);
            total += calcColScore(board, i, positiveMarker);
        }

        total += calcDiagonalScore1(board, positiveMarker);
        total += calcDiagonalScore2(board, positiveMarker);

        return total;
    }

    /**
     * 
     * @param ttt
     * @param rowNum
     * @param positiveMarker
     * @return
     */
    private int calcRowScore(TicTacToe ttt, int rowNum, char positiveMarker)
    {
        char[][] board = ttt.getBoard();
        boolean goodPlayer0 = board[rowNum][0] == positiveMarker,       //if positive player has marked the 0 col.
                goodPlayer1 = board[rowNum][1] == positiveMarker,       //if pos. player has marked the 1 col.
                goodPlayer2 = board[rowNum][2] == positiveMarker;       //if pos. player has marked the 2 col.
        boolean badPlayer0 = !goodPlayer0 && board[rowNum][0] != '-',   //if pos. player has NOT marked the 0 col, and the col is NOT unmarked, assume negative player has it.
                badPlayer1 = !goodPlayer1 && board[rowNum][1] != '-',   //if pos. player has NOT marked the 1 col, and the col is NOT unmarked, assume negative player has it.
                badPlayer2 = !goodPlayer2 && board[rowNum][2] != '-';   //if pos. player has NOT marked the 2 col, and the col is NOT unmarked, assume negative player has it.

        int numGood = (goodPlayer0 ? 1 : 0) + (goodPlayer1 ? 1 : 0) + (goodPlayer2 ? 1 : 0);    //Sum of number of positive markers
        int numBad = (badPlayer0 ? 1 : 0) + (badPlayer1 ? 1 : 0) + (badPlayer2 ? 1 : 0);    //Sum of number of negative markers

        switch(numGood)
        {
            case 3:
                return 100;
            case 2:
                if((goodPlayer0 && goodPlayer1) || (goodPlayer1 && goodPlayer2))
                {
                    return 10 - numBad;
                }
                else
                {
                    return 2 - numBad;
                }
            case 1:     //Enemy has 2/3
                if((badPlayer0 && badPlayer1) || (badPlayer1 && badPlayer2))    //Enemy has 2 in a row
                {
                    return -9;
                }
                else    //Enemy has 2, not in a row
                {
                    return 1 - numBad;
                }
            case 0:     //Enemy had 3 in a row
                if(badPlayer0 && badPlayer1 && badPlayer2)
                    return -100;
                else
                    return -1*numBad;
            default:
                return 0;
        }
    }

    private int calcColScore(TicTacToe ttt, int colNum, char positiveMarker)
    {
        char[][] board = ttt.getBoard();
        boolean goodPlayer0 = board[0][colNum] == positiveMarker,       //if positive player has marked the 0 row.
                goodPlayer1 = board[1][colNum] == positiveMarker,       //if pos. player has marked the 1 row.
                goodPlayer2 = board[2][colNum] == positiveMarker;       //if pos. player has marked the 2 row.
        boolean badPlayer0 = !goodPlayer0 && board[0][colNum] != '-',   //if pos. player has NOT marked the 0 row, and the row is NOT unmarked, assume negative player has it.
                badPlayer1 = !goodPlayer1 && board[1][colNum] != '-',   //if pos. player has NOT marked the 1 row, and the row is NOT unmarked, assume negative player has it.
                badPlayer2 = !goodPlayer2 && board[2][colNum] != '-';   //if pos. player has NOT marked the 2 row, and the row is NOT unmarked, assume negative player has it.

        int numGood = (goodPlayer0 ? 1 : 0) + (goodPlayer1 ? 1 : 0) + (goodPlayer2 ? 1 : 0);    //Sum of number of positive markers
        int numBad = (badPlayer0 ? 1 : 0) + (badPlayer1 ? 1 : 0) + (badPlayer2 ? 1 : 0);    //Sum of number of negative markers

        switch(numGood)
        {
            case 3:
                return 100;
            case 2:
                if((goodPlayer0 && goodPlayer1) || (goodPlayer1 && goodPlayer2))
                {
                    return 10 - numBad;
                }
                else
                {
                    return 2 - numBad;
                }
            case 1:     //Enemy has 2/3
                if((badPlayer0 && badPlayer1) || (badPlayer1 && badPlayer2))    //Enemy has 2 in a row
                {
                    return -9;
                }
                else    //Enemy has 2, not in a row
                {
                    return 1 - numBad;
                }
            case 0:     //Enemy had 3 in a row
                if(badPlayer0 && badPlayer1 && badPlayer2)
                    return -100;
                else
                    return -1*numBad;
            default:
                return 0;
        }
    }

    private int calcDiagonalScore1(TicTacToe ttt, char positiveMarker)
    {
        char[][] board = ttt.getBoard();
        boolean goodPlayer0 = board[0][0] == positiveMarker,       //if positive player has marked the 0 row.
                goodPlayer1 = board[1][1] == positiveMarker,       //if pos. player has marked the 1 row.
                goodPlayer2 = board[2][2] == positiveMarker;       //if pos. player has marked the 2 row.
        boolean badPlayer0 = !goodPlayer0 && board[0][0] != '-',   //if pos. player has NOT marked the 0 row, and the row is NOT unmarked, assume negative player has it.
                badPlayer1 = !goodPlayer1 && board[1][1] != '-',   //if pos. player has NOT marked the 1 row, and the row is NOT unmarked, assume negative player has it.
                badPlayer2 = !goodPlayer2 && board[2][2] != '-';   //if pos. player has NOT marked the 2 row, and the row is NOT unmarked, assume negative player has it.

        int numGood = (goodPlayer0 ? 1 : 0) + (goodPlayer1 ? 1 : 0) + (goodPlayer2 ? 1 : 0);    //Sum of number of positive markers
        int numBad = (badPlayer0 ? 1 : 0) + (badPlayer1 ? 1 : 0) + (badPlayer2 ? 1 : 0);    //Sum of number of negative markers

        switch(numGood)
        {
            case 3:
                return 100;
            case 2:
                if((goodPlayer0 && goodPlayer1) || (goodPlayer1 && goodPlayer2))
                {
                    return 10 - numBad;
                }
                else
                {
                    return 2 - numBad;
                }
            case 1:     //Enemy has 2/3
                if((badPlayer0 && badPlayer1) || (badPlayer1 && badPlayer2))    //Enemy has 2 in a row
                {
                    return -9;
                }
                else    //Enemy has 2, not in a row
                {
                    return 1 - numBad;
                }
            case 0:     //Enemy had 3 in a row
                if(badPlayer0 && badPlayer1 && badPlayer2)
                    return -100;
                else
                    return -1*numBad;
            default:
                return 0;
        }
    }

    private int calcDiagonalScore2(TicTacToe ttt, char positiveMarker)
    {
        char[][] board = ttt.getBoard();
        boolean goodPlayer0 = board[0][2] == positiveMarker,       //if positive player has marked the 0 row.
                goodPlayer1 = board[1][1] == positiveMarker,       //if pos. player has marked the 1 row.
                goodPlayer2 = board[2][0] == positiveMarker;       //if pos. player has marked the 2 row.
        boolean badPlayer0 = !goodPlayer0 && board[0][2] != '-',   //if pos. player has NOT marked the 0 row, and the row is NOT unmarked, assume negative player has it.
                badPlayer1 = !goodPlayer1 && board[1][1] != '-',   //if pos. player has NOT marked the 1 row, and the row is NOT unmarked, assume negative player has it.
                badPlayer2 = !goodPlayer2 && board[0][2] != '-';   //if pos. player has NOT marked the 2 row, and the row is NOT unmarked, assume negative player has it.

        int numGood = (goodPlayer0 ? 1 : 0) + (goodPlayer1 ? 1 : 0) + (goodPlayer2 ? 1 : 0);    //Sum of number of positive markers
        int numBad = (badPlayer0 ? 1 : 0) + (badPlayer1 ? 1 : 0) + (badPlayer2 ? 1 : 0);    //Sum of number of negative markers

        switch(numGood)
        {
            case 3:
                return 100;
            case 2:
                if((goodPlayer0 && goodPlayer1) || (goodPlayer1 && goodPlayer2))
                {
                    return 10 - numBad;
                }
                else
                {
                    return 2 - numBad;
                }
            case 1:     //Enemy has 2/3
                if((badPlayer0 && badPlayer1) || (badPlayer1 && badPlayer2))    //Enemy has 2 in a row
                {
                    return -9;
                }
                else    //Enemy has 2, not in a row
                {
                    return 1 - numBad;
                }
            case 0:     //Enemy had 3 in a row
                if(badPlayer0 && badPlayer1 && badPlayer2)
                    return -100;
                else
                    return -1*numBad;
            default:
                return 0;
        }
    }
}
