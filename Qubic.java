import java.util.Random;
import java.util.Scanner;
/**
 * 3D tic-tac-toe game
 * @author Tony Gonzalez
 * @version 8/19/22
 */
public class Qubic 
{
    public static Random generator = new Random();
    public static Scanner input = new Scanner(System.in);

    // Contant values for each player 
    public static final int CROSS   = 0;
    public static final int ZERO  = 1;
    public static final int EMPTY = 2;

    // The game board
    public static final int ROWS = 4, COLS = 4, WID = 4; // number of rows/columns/width
    public static int[][][] board = new int[ROWS][COLS][WID]; // EMPTY, CROSS, ZERO

    // The current player
    public static int currentPlayer;  // CROSS, ZERO

    // The current state of the game
    public static boolean winner;

    /**
     * Runs until a winner is found or game ends in a tie. 
     * @param args
     */
    public static void main(String[] args) 
    {
        // Start new game with a blank board
        newGame();
        displayWholeBoard();

        // Gameloop
        Gameloop:
        do
        {
            boolean validInput = false;  // for input validation
            // Prompt user input and input validation loop
            do 
            {        
                int wid;
                if (currentPlayer == CROSS) // X's turn
                {
                    System.out.print("Player 'X', enter which board you would like to play (1-4): ");
                    wid = input.nextInt() - 1;
    
                    displaySingleBoard(wid); 
    
                    System.out.print("Enter the row and column you would like to play (1-4): ");
                } 
                else // O's turn
                {
                    System.out.print("Player 'O', enter which board you would like to play (1-4): ");
                    wid = input.nextInt() - 1;
    
                    displaySingleBoard(wid); 
    
                    System.out.print("Enter the row and column you would like to play (1-4): ");
                }
                int row = input.nextInt() - 1;
                int col = input.nextInt() - 1;
    
                // Input validation
                if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col][wid] == EMPTY) 
                {
                    // Update board and return the new game state after the move
                    updateBoard(currentPlayer, row, col, wid);
    
                    validInput = true;  // input okay, exit loop
                } 
                else // Invaid input 
                {
                    System.out.println("Invalid input. Please try again");
                } 
    
            }while(!validInput);

            // Check for wining move
            winner = checkWinner(board, currentPlayer);

            displayWholeBoard();

            // End game if winning move is found & print winner
            if(winner == true && currentPlayer == 0)
            {
                System.out.println("Player 'X' has won!");
                break Gameloop;
            }
            else if(winner == true && currentPlayer == 1)
            {
                System.out.println("Player 'O' has won!");
                break Gameloop;
            }
    
            // Switche turns
            if(currentPlayer == CROSS)
            {
                currentPlayer = ZERO;
            }
            else
            {
                currentPlayer = CROSS;
            }

        }while(!winner);

        // End game
        System.out.println("Thank you for playing!");
    }

    /**
     * Creates a new game board with all empty elements
     */
    public static void newGame() 
    {
        for (int row = 0; row < ROWS; ++row) 
        {
           for (int col = 0; col < COLS; ++col) 
           {
                for(int wid = 0; wid < WID; ++wid)
                {
                    board[row][col][wid] = EMPTY;  // All cells empty
                }
           }
        }
        winner = false; 
        currentPlayer = generator.nextInt(2); // Randomly pick who plays first
    }

    /**
     * Displays all four playing boards 
     */
    public static void displayWholeBoard() 
    {
        for(int wid = 0; wid < WID; ++wid)
        {
            System.out.println("Board " + (wid + 1));
            for (int row = 0; row < ROWS; ++row) 
            {
                for (int col = 0; col < COLS; ++col) 
                {
                    populateArray(board[row][col][wid]); // Print each of the cells
                    if (col != COLS - 1) 
                    {
                        System.out.print("|");   // Print vertical partition
                    }
                }
                System.out.println();
                if (row != ROWS - 1) {
                    System.out.println("---------------"); // Print horizontal partition
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Displays only one of the four playing boards 
     * @param wid The input for which board to display
     */
    public static void displaySingleBoard(int wid)
    {
        System.out.println("Board " + (wid + 1));
            for (int row = 0; row < ROWS; ++row) 
            {
                for (int col = 0; col < COLS; ++col) 
                {
                    populateArray(board[row][col][wid]); // Print each of the cells
                    if (col != COLS - 1) 
                    {
                        System.out.print("|");   // Print vertical partition
                    }
                }
                System.out.println();
                if (row != ROWS - 1) {
                    System.out.println("---------------"); // Print horizontal partition
                }
                System.out.println();
            }
            System.out.println();
    }

    /**
     * Fills a given element with the current player's token
     * @param currentPlayer Indication to who played 
     */
    public static void populateArray(int currentPlayer) 
    {
        switch(currentPlayer) 
        {
           case CROSS: System.out.print(" X "); break;
           case ZERO:  System.out.print(" O "); break;
           case EMPTY: System.out.print("   "); break;
        }
    }

    /**
     * Updates board based on the last played move
     * @param currentPlayer The token of the current player
     * @param selectedRow Their selected row
     * @param selectedCol Their selected column
     * @param selectedWid Their selected board
     */
    public static void updateBoard(int currentPlayer, int selectedRow, int selectedCol, int selectedWid)
    {
        board[selectedRow][selectedCol][selectedWid] = currentPlayer;
    }

    /**
     * Checks to see if there are any winning combinations on the playing board
     * @param board Current playing board
     * @param currentPlayer Current player 
     * @return Returns true if winning combo is found, false if no winning combos are found 
     */
    public static boolean checkWinner(int[][][] board, int currentPlayer)
    {
        // Check for 4 across on 1 board 
        for(int wid = 0; wid < WID; wid++)
        {
            for(int row = 0; row < board.length; row++)
            {
                for (int col = 0; col < board[0].length - 3; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row][col+1][wid] == currentPlayer &&
                        board[row][col+2][wid] == currentPlayer &&
                        board[row][col+3][wid] == currentPlayer)
                        {
                            return true;
                        }
                }			
		    }
        }

        // Check for 4 vertically on 1 board
        for(int wid = 0; wid < WID; wid++)
        {
            for(int row = 0; row < board.length - 3; row++)
            {
                for(int col = 0; col < board[0].length; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row+1][col][wid] == currentPlayer &&
                        board[row+2][col][wid] == currentPlayer &&
                        board[row+3][col][wid] == currentPlayer)
                        {
                        return true;
                    }
                }
            }
        }

        // Check upward diagonal on 1 board 
        for(int wid = 0; wid < WID; wid++)
        {
            for(int row = 3; row < board.length; row++)
            {
                for(int col = 0; col < board[0].length - 3; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row-1][col+1][wid] == currentPlayer &&
                        board[row-2][col+2][wid] == currentPlayer &&
                        board[row-3][col+3][wid] == currentPlayer)
                        {
                        return true;
                    }
                }
            }
        }

        // Check downward diagonal on 1 board
        for(int wid = 0; wid < WID; wid++)
        {
            for(int row = 0; row < board.length - 3; row++)
            {
                for(int col = 0; col < board[0].length - 3; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row+1][col+1][wid] == currentPlayer &&
                        board[row+2][col+2][wid] == currentPlayer &&
                        board[row+3][col+3][wid] == currentPlayer)
                        {
                        return true;
                    }
                }
            }
        }

        // Check for 4 in line across 4 boards 
        for(int wid = 0; wid < WID - 3; wid++)
        {
            for(int row = 0; row < board.length; row++)
            {
                for (int col = 0; col < board[0].length; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row][col][wid+1] == currentPlayer &&
                        board[row][col][wid+2] == currentPlayer &&
                        board[row][col][wid+3] == currentPlayer)
                        {
                            return true;
                        }
                }			
		    }
        }

        // Check for 4 in a row across 4 boards
        for(int wid = 0; wid < WID - 3; wid++)
        {
            for(int row = 0; row < board.length; row++)
            {
                for (int col = 0; col < board[0].length - 3; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row][col+1][wid+1] == currentPlayer &&
                        board[row][col+2][wid+2] == currentPlayer &&
                        board[row][col+3][wid+3] == currentPlayer)
                        {
                            return true;
                        }
                }			
		    }
        }

        // Check for 4 vertically across 4 boards
        for(int wid = 0; wid < WID - 3; wid++)
        {
            for(int row = 0; row < board.length - 3; row++)
            {
                for(int col = 0; col < board[0].length; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row+1][col][wid+1] == currentPlayer &&
                        board[row+2][col][wid+2] == currentPlayer &&
                        board[row+3][col][wid+3] == currentPlayer)
                        {
                        return true;
                    }
                }
            }
        }

        // Check upward diagonal across 4 boards 
        for(int wid = 0; wid < WID - 3; wid++)
        {
            for(int row = 3; row < board.length; row++)
            {
                for(int col = 0; col < board[0].length - 3; col++)
                {
                    if (board[row][col][wid] == currentPlayer   && 
                        board[row-1][col+1][wid+1] == currentPlayer &&
                        board[row-2][col+2][wid+2] == currentPlayer &&
                        board[row-3][col+3][wid+3] == currentPlayer)
                        {
                        return true;
                    }
                }
            }
        }

        // Found no winning combo 
        return false;
    }
}