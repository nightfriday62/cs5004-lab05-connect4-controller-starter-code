package connect;

/**
 * The model component in the MVC architecture of a Connect Four game.
 */
public class ConnectFourModelImpl implements ConnectFourModel {
  private Player[][] board;
  private int moveCounter;
  private int rows;
  private int columns;
  private final int four = 4;

  /**
   * Constructs a new ConnectFourModelImpl object with the default number of rows and columns.
   */
  public ConnectFourModelImpl() {
    this.rows = 6;
    this.columns = 7;
    this.board = new Player[rows][columns];
    this.moveCounter = 0;

  }

  /**
   * Constructs a new ConnectFourModelImpl object with the specified number of rows and columns.
   *
   * @param rows    the number of rows in the game board
   * @param columns the number of columns in the game board
   */
  public ConnectFourModelImpl(int rows, int columns) throws IllegalArgumentException {
    if (rows < 4 || columns < 4) {
      throw new IllegalArgumentException("Board size must be at least 4x4");
    }
    this.rows = rows;
    this.columns = columns;
    this.board = new Player[rows][columns];
    this.moveCounter = 0;
  }

  @Override
  public void initializeBoard() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        board[i][j] = null;
      }
    }
  }

  @Override
  public void makeMove(int column) throws IllegalArgumentException {
    if (column < 1 || column > columns) {
      throw new IllegalArgumentException("Column out of bounds");
    }
    if (board[rows - 1][column - 1] != null) {
      throw new IllegalArgumentException("Column is full");
    }
    int row = 0;
    while (row < rows - 1 && board[row][column - 1] != null) {
      row++;
    }
    board[row][column - 1] = getTurn();
    moveCounter++;
  }

  @Override
  public Player getTurn() {
    if (isGameOver()) {
      return null;
    }
    return moveCounter % 2 == 0 ? Player.RED : Player.YELLOW;
  }

  @Override
  public boolean isGameOver() {
    if (moveCounter >= rows * columns) {
      return true;
    }
    if (getWinner() != null) {
      return true;
    }
    return false;
  }

  @Override
  public Player getWinner() {
    Player winner = checkForFourHorizontal();
    if (winner != null) {
      return winner;
    }
    winner = checkForFourVertical();
    if (winner != null) {
      return winner;
    }
    winner = checkForFourDiagonalDown();
    if (winner != null) {
      return winner;
    }
    winner = checkForFourDiagonalUp();
    return winner;
  }

  @Override
  public void resetBoard() {
    initializeBoard();
    moveCounter = 0;
  }

  @Override
  public Player[][] getBoardState() {
    Player[][] deepCopy = new Player[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        deepCopy[i][j] = board[i][j];
      }
    }
    return deepCopy;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = rows - 1; i >= 0; i--) {
      sb.append(i);
      sb.append(": ");
      for (int j = 0; j < columns; j++) {
        if (board[i][j] == null) {
          sb.append("n ");
        } else {
          sb.append(board[i][j].getDisplayName());
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Checks for four consecutive discs of the same player in a row. From left to right.
   *
   * @return the player who has four consecutive discs in a row, or null if no player has four.
   */
  private Player checkForFourHorizontal() {
    Player playerToReturn = null;
    //outLoop:
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns - (four - 1); col++) {
        for (int i = col; i < col + four; i++) {
          if (board[row][col] != board[row][i] || board[row][col] == null) {
            break;
          } else if (i == col + four - 1) {
            playerToReturn = board[row][col];
            //break outLoop;
          }
        }
      }
    }
    return playerToReturn;
  }

  /**
   * Checks for four consecutive discs of the same player in a column. From bottom to top.
   *
   * @return the player who has four consecutive discs in a column, or null if no player has four.
   */
  private Player checkForFourVertical() {
    Player playerToReturn = null;
    for (int row = 0; row < rows - (four - 1); row++) {
      for (int col = 0; col < columns; col++) {
        for (int i = row; i < row + four; i++) {
          if (board[row][col] != board[i][col] || board[row][col] == null) {
            break;
          } else if (i == row + four - 1) {
            playerToReturn = board[row][col];
            //break outLoop;
          }
        }
      }
    }
    return playerToReturn;
  }

  /**
   * Checks for four consecutive discs of the same player in a
   * diagonal from top left to bottom right.
   *
   * @return the player who has four consecutive discs in a
   *         diagonal, or null if no player has four.
   */
  private Player checkForFourDiagonalDown() {
    Player playerToReturn = null;
    for (int row = four - 1; row < rows; row++) {
      for (int col = 0; col < columns - (four - 1); col++) {
        for (int i = 0; i < four; i++) {
          if (board[row][col] != board[row - i][col + i] || board[row][col] == null) {
            break;
          } else if (i == four - 1) {
            playerToReturn = board[row][col];
          }
        }
      }
    }
    return playerToReturn;
  }

  /**
   * Checks for four consecutive discs of the same player in a diagonal
   * from bottom left to top right.
   *
   * @return the player who has four consecutive discs in a diagonal, or null if no player has four.
   */
  private Player checkForFourDiagonalUp() {
    Player playerToReturn = null;
    for (int row = 0; row < rows - (four - 1); row++) {
      for (int col = 0; col < columns - (four - 1); col++) {
        for (int i = 0; i < four; i++) {
          if (board[row][col] != board[row + i][col + i] || board[row][col] == null) {
            break;
          } else if (i == four - 1) {
            playerToReturn = board[row][col];
          }
        }
      }
    }
    return playerToReturn;
  }

}
