package games;

public class TicTacToe {

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

    /**
     * Deep copy of the board array
     */
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