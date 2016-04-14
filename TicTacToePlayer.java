import java.util.*;

class TicTacToe {

    private char [][] board;

    // Default contructor, creates board
    public TicTacToe()
    {
        board = new char[3][3];
        initializeBoard();

    }

    public char[][] getBoard()
    {
        return board;
    }

    // Creates a 3x3 array for TicTacToe
    public void initializeBoard()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                board[i][j] = '-';
            }
        }
    }

    public void printBoard()
    {
        int x = 0;

        for(int i = 0; i < 3; i++)
        {
            System.out.print("| ");

            for(int j = 0; j < 3; j++)
            {
                System.out.print(board[i][j] + " | ");
                //x++;
            }

            System.out.println();

        }
    }

    // Checks if the entire board is full
    public boolean isBoardFull()
    {
        boolean isFull = true;

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(board[i][j] == '-')
                {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

    public boolean checkBox(int x, int y)
    {
        return board[x][y] == '-';
    }

    // Checks row and column of the board
    private boolean checkRowCol(char x, char y, char z)
    {
        return((x != '-' && (x == y) && (y == z)));
    }

    public boolean checkForWin()
    {
        return (checkRow() ||checkColumn() || checkDiagonal());
    }

    // Checks for 3 in a row for a winner
    private boolean checkRow()
    {
        for(int i = 0; i < 3; i++)
        {
            if(checkRowCol(board[i][0], board[i][1], board[i][2]) == true)
            {
                return true;
            }
        }
        return false;
    }

    // Checks for 3 in a colunmn for a winner
    private boolean checkColumn()
    {
        for(int i = 0 ; i < 3; i++)
        {
            if(checkRowCol(board[0][i], board[1][i], board[2][i]) == true)
            {
                return true;
            }
        }
        return false;
    }

    // Checks for a match in the diagonals
    private boolean checkDiagonal()
    {
        return((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board [1][1], board[2][0]) == true));
    }

    // Place mark on board
    public boolean placeMark(int row, int col, char currentPlayerMark)
    {
        if((row >= 0) && (row < 3))
        {
            if((col >= 0) && (col < 3))
            {
                if(board[row][col] == '-')
                {
                    board[row][col] = currentPlayerMark;
                    return true;
                }
            }
        }
        return false;
    }

    public TicTacToe cloneGame()
    {
        TicTacToe ttt = new TicTacToe();
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                ttt.board[i][j] = this.board[i][j];
            }
        }
        return ttt;
    }

}

public class TicTacToePlayer implements BaseGame
{
    public static final int AI_OFF = 0,
                            AI_SMART = 1,
                            AI_DUMB = 2;

    // 0 = off
    // 1 = on (perfect)
    // 2 = on (dumb)
    int aiStatus;

    public TicTacToePlayer()
    {
        aiStatus = 1;
    }

    public TicTacToePlayer(int aiStatus)
    {
        this.aiStatus = aiStatus;
    }

    public void startGame()
    {
        TicTacToe game = new TicTacToe();
        Scanner pInput = new Scanner(System.in);

        // Used to store players' input
        int pRow, pCol;
        char currentPlayerMark = 'X';
        do
        {
            System.out.println("Player " + currentPlayerMark + ", Enter row number(1, 2, or 3) and column number (1, 2, or 3): ");
            pRow = pInput.nextInt() -  1;
            pCol = pInput.nextInt() - 1;

            // Input validation
            while((pRow < 0 || pRow >= 3) || (pCol < 0 || pCol >= 3) || game.checkBox(pRow, pCol) == false)
            {
                System.out.println("This is invalid. Please enter a row number from 1 to 3 and a column number from 1 to 3: ");
                pRow = pInput.nextInt() - 1;
                pCol = pInput.nextInt() - 1;
            }


            game.placeMark(pRow, pCol, currentPlayerMark);   // Places the players mark down on the board
            game.printBoard();            // Prints the board

            // If a player wins end the game, else change players
            if((game.checkForWin() == true) && (currentPlayerMark == 'X'))
            {
                System.out.println("Player X wins!");
                break;
            }
            else if((game.checkForWin() == true) && (currentPlayerMark == 'O'))
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

            if(currentPlayerMark == 'X')
            {
                int[] nextMove = makeAIMovePerfect(game, 'X', 0);
                System.out.println("Next move could be [" + (nextMove[1]+1) + "," + (nextMove[2]+1) + "] with score of s=" + nextMove[0]);
            }

        }while(game.isBoardFull() == false);

        if(game.isBoardFull() == true && game.checkForWin() == false)
        {
            System.out.println("Tie Game!");
        }
    }

    public void printInstructions()
    {
        System.err.println("Please implement 'printInstructions' in the TicTacToePlayer.java file!");
    }

    public void makeAIMove(TicTacToe board, char aiMarker, boolean perfectAI)
    {
        if(perfectAI)
        {
            int[] move = makeAIMovePerfect(board, aiMarker, 0);
            board.placeMark(move[1], move[2], aiMarker);
        }
        else
        {
            //Need to immplement some dumber AI...
        }
    }

    /**
     *
     */
    private int[] makeAIMovePerfect(TicTacToe board, char aiMarker, int depth)
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

                currScore = makeAIMovePerfect(child, aiMarker, depth-1)[0];
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

                currScore = makeAIMovePerfect(child, aiMarker, depth-1)[0];
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

    private int calcScore(TicTacToe board, char positiveMarker)
    {
        int total = 0;

        for(int i = 0; i < 3; i++)
        {
            total += calcRowScore(board, i, positiveMarker);
            // System.out.println("Score @ row = " + i + " : " + total);
            total += calcColScore(board, i, positiveMarker);
            // System.out.println("Score @ col = " + i + " : " + total);
        }

        total += calcDiagonalScore1(board, positiveMarker);
        total += calcDiagonalScore2(board, positiveMarker);
        // System.out.println("Score after diagonals = " + total);


        return total;
    }

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
