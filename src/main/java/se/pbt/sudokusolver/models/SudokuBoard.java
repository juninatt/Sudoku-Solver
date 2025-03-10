package se.pbt.sudokusolver.models;

import se.pbt.sudokusolver.utils.Constants;

import java.util.Arrays;

/**
 * Represents the internal structure of a Sudoku board, storing and managing cell values.
 * Tracks progress by counting filled cells and provides access to board dimensions and subgrid sizes.
 * This class does not enforce game rules, which are handled externally by {@link SudokuValidator}.
 */
public class SudokuBoard {
    private final int boardSize;
    private final int[] subgridDimensions;
    private int filledCells;
    private final int[][] board;

    /**
     * Initializes an empty Sudoku board of the specified size.
     * The board starts with all cells set to zero (empty).
     * Determines the subgrid size upon creation to avoid redundant calculations.
     *
     * @param boardSize The total size of the board (e.g., 9 for a 9x9 Sudoku).
     */
    public SudokuBoard(int boardSize) {
        this.boardSize = boardSize;
        this.subgridDimensions = calculateSubgridSize();
        this.board = new int[boardSize][boardSize];
    }

    /**
     * Determines the size of subgrids based on the board's total dimensions.
     * Uses predefined configurations from {@link Constants.SudokuBoard} to support various board sizes.
     * Ensures that only valid Sudoku board sizes are used.
     */
    private int[] calculateSubgridSize() {
        if (!Constants.SudokuBoard.SUPPORTED_SIZES.contains(boardSize)) {
            throw new IllegalArgumentException(
                    String.format(Constants.ErrorMessages.INVALID_BOARD_SIZE, boardSize)
            );
        }
        return Constants.SudokuBoard.getBlockLayout(boardSize);
    }

    /**
     * Places a value in the specified cell and tracks the number of filled cells.
     * This method does not enforce Sudoku rules; validation is handled separately.
     */
    public void setValue(int row, int col, int value) {
        board[row][col] = value;
        filledCells++;
    }

    /**
     * Checks whether all cells in the board are filled.
     * This is used to determine if the game should trigger final validation.
     *
     * @return {@code true} if all cells are filled, otherwise {@code false}.
     */
    public boolean isBoardFull() {
        return filledCells == boardSize * boardSize;
    }

    /**
     * Retrieves the value stored in a specific cell on the board.
     */
    public int getValueAt(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns the total size of the board (e.g., 9 for a 9x9 Sudoku).
     */
    public int getBoardSize() {
        return boardSize;
    }


    public int[] getSubgridDimensions() {
        return subgridDimensions;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }
}
