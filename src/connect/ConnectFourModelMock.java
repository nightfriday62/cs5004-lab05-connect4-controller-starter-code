package connect;

/**
 * A mock model for testing the ConnectFourController class.
 */
public class ConnectFourModelMock implements ConnectFourModel {

  private final boolean[] gameOverResponses;
  private final String toStringResponse;
  private int responseIndex = 0;

  /**
   * Constructor for the MockConnectFourModel class.
   *
   * @param gameOverResponses the responses to the isGameOver method
   * @param toStringResponse  the response to the toString method
   */
  public ConnectFourModelMock(boolean[] gameOverResponses, String toStringResponse) {
    this.gameOverResponses = gameOverResponses;
    this.toStringResponse = toStringResponse;
  }

  @Override
  public void initializeBoard() {

  }


  @Override
  public void makeMove(int column) throws IllegalArgumentException {
    if (column < 0 || column >= 7) {
      throw new IllegalArgumentException("The column is out of bounds");
    }
  }

  @Override
  public Player getTurn() {
    return Player.RED;
  }

  @Override
  public boolean isGameOver() {
    return gameOverResponses[responseIndex++];
  }

  @Override
  public Player getWinner() {
    if (toStringResponse.contains("winner")) {
      return Player.RED;
    } else {
      return null;
    }
  }


  @Override
  public void resetBoard() {

  }

  @Override
  public Player[][] getBoardState() {
    return new Player[0][];
  }

  @Override
  public String toString() {
    return toStringResponse;
  }
}
