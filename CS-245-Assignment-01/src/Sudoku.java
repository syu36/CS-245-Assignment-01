import java.util.Random;

/**
 *
 *	Provides methods to generate and print a valid and completed sudoku board.
 *
 */
public class Sudoku {
	private int[][] board;
	private final int SIZE = 9;
	private Random random;

	/**
	 * Initializes the sudoku board and a Random object to use for creating candidates.
	 */
	public Sudoku() {
		board = new int[SIZE][SIZE];
		random = new Random();
	}

	/**
	 * Fills the board with valid sudoku numbers.
	 */
	public void fillBoard() {
		fillBoard(board, random.nextInt(SIZE) + 1, 0, 0);
	}

	/**
	 * Fills the board with valid sudoku numbers.
	 * @param board the array that represents the sudoku board
	 * @param num the number to place
	 * @param row the row to place in
	 * @param col the column to place in
	 * @return true if the board has been filled. Should not ever return false outside of the recursion
	 */
	private boolean fillBoard(int[][] board, int num, int row, int col) {
		// Base case, if the row becomes larger than or equal to the size, we've reached the end.
		if (row >= SIZE) {
			return true;
		}

		// This for loop iterates the number of iterations needed to check all of the numbers (1-9) if one doesn't work out
		for (int i = 0; i < SIZE; i++) {
			if (valid(board, num, row, col)) {
				board[row][col] = num;

				// Properly move to the next cell. If we reach the end of the row, move down a row and start from the beginning. Otherwise, go to the next spot in the row.
				if (col >= SIZE - 1) {
					if (fillBoard(board, random.nextInt(SIZE) + 1, row + 1, 0)) {
						return true;
					}
				} else {
					if (fillBoard(board, random.nextInt(SIZE) + 1, row, col + 1)) {
						return true;
					}
				}

				// Set the cell back to 0 if future board cell placements don't work. This is important or else our checking won't work properly
				board[row][col] = 0;
			}
			// Tertiary: Sets the number to the next sequential number. If number is 9, set to 1.
			num = num < 9 ? num + 1 : 1;
		}
		return false;
	}

	/**
	 * Checks if the number is valid in a row and column.
	 * @param board the array that represents the sudoku board
	 * @param num the number to place
	 * @param row the row to place in
	 * @param col the column to place in
	 * @return true if the cell is valid, false otherwise
	 */
	private boolean valid(int[][] board, int num, int row, int col) {
		return checkRow(board, num, row, col) && checkCol(board, num, row, col) && checkSquare(board, num, row, col);
	}

	/**
	 * Checks if the number is valid in a row.
	 * @param board the array that represents the sudoku board
	 * @param num the number to place
	 * @param row the row to place in
	 * @param col the column to place in
	 * @return true if placing the number in the row is valid, false otherwise
	 */
	private boolean checkRow(int [][] board, int num, int row, int col) {
		for (int j = 1; j < SIZE; j++) {
			// Tertiary: if the current index of column is less than SIZE (max index of array is SIZE - 1), then use that index; else, start over from 0
			if (board[row][col + j < SIZE ? col + j : col + j - SIZE] == num) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the number is valid in a column.
	 * @param board the array that represents the sudoku board
	 * @param num the number to place
	 * @param row the row to place in
	 * @param col the column to place in
	 * @return true if placing the number in the column is valid, false otherwise
	 */
	private boolean checkCol(int [][] board, int num, int row, int col) {
		for (int i = 1; i < SIZE; i++) {
			// Tertiary: if the current index of row is less than SIZE (max index of array is SIZE - 1), then use that index; else, start over from 0
			if (board[row + i < SIZE ? row + i : row + i - SIZE][col] == num) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the number is valid in a square (3x3)
	 * @param board the array that represents the sudoku board
	 * @param num the number to place
	 * @param row the row to place in
	 * @param col the column to place in
	 * @return true if placing the number in the square is valid, false otherwise
	 */
	private boolean checkSquare(int [][] board, int num, int row, int col) {
		// SIZE / 3 means that we only check the 3 spaces
		for (int i = 0; i < SIZE / 3; i++) {
			for (int j = 0; j < SIZE / 3; j++) {
				// using row / 3 and col / 3 will give us 0, 3, or 6 as the starting row or column of the square
				if (board[(row / 3) * 3 + i][(col / 3) * 3 + j] == num) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Prints the sudoku board to the console as shown in figure 1 of the assignment.
	 */
	public void printBoard() {
		for (int row = 0; row < SIZE; row++) {
			if (row % 3 == 0) {
				System.out.println(" -------------------------------------");
			}
			for (int col = 0; col < SIZE; col++) {
				if (col % 3 == 0) {
					System.out.print(" | ");
				}
				System.out.print("[" + board[row][col] + "]");
			}
			System.out.print(" |");
			System.out.println();
		}
	}

	public static void main(String args[]) {
		Sudoku s = new Sudoku();
		s.fillBoard();
		s.printBoard();
	}
}
