import static org.junit.Assert.assertEquals;

import connect.ConnectFourConsoleController;
import connect.ConnectFourModel;
import connect.ConnectFourModelMock;
import connect.ConnectFourView;
import java.io.StringReader;
import org.junit.Test;

/**
 * This is the test for the ConnectFourController class.
 */
public class ConnectFourControllerTest {
  private ConnectFourConsoleController controller;


  /**
   * Test the controller sends the correct information to the view.
   * When the game ends in a tie. 3.1
   */
  @Test
  public void testPlayGameTie() {
    boolean[] gameOverResponses = {false, true, true};
    String toStringResponse = "tie";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("5 n\n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("tie\n"
        + "Player RED, make your move: \n"
        + "tie\n" + "Game over! It's a tie!\n"
        + "Do you want to play again? (yes/no)\n", out.toString());
  }

  /**
   * Test the controller sends the correct information to the view.
   * When the game ends with a winner. 3.2
   */
  @Test
  public void testPlayGameWinner() {
    boolean[] gameOverResponses = {false, true, true};
    String toStringResponse = "winner";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("5 n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("winner\n" + "Player RED, make your move: \n"
        + "winner\n" + "Game over! RED is the winner!\n"
        + "Do you want to play again? (yes/no)\n", out.toString());
  }

  /**
   * Test the controller rejects a non-integer value and throws
   * a InputMismatchException when the
   * input is not a number. 3.3
   */
  @Test
  public void testPlayGameNotNumber() {
    boolean[] gameOverResponses = {false, true, true};
    String toStringResponse = "notNumber";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("k 0");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("notNumber\n"
        + "Player RED, make your move: \n"
        + "Is not a number!\n", out.toString());
  }

  /**
   * Test the controller allows player to play again. 3.4
   */
  @Test
  public void testPlayGamePlayAgain() {
    boolean[] gameOverResponses = {false, true, false};
    String toStringResponse = "playAgain";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("5 y 0");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("playAgain\n" + "Player RED, make your move: \n"
        + "playAgain\n" + "Game over! It's a tie!\n"
        + "Do you want to play again? (yes/no)\n" + "playAgain\n"
        + "Player RED, make your move: \n" + "Game quit! Ending game state:\n"
        + "playAgain\n", out.toString());
  }

  /**
   * Test the player can quit the game by entering 0. 3.5
   */
  @Test
  public void testPlayGameQuit() {
    boolean[] gameOverResponses = {false};
    String toStringResponse = "quit";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("0");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("quit\n" + "Player RED, make your move: \n"
        + "Game quit! Ending game state:\nquit\n", out.toString());
  }

  /**
   * Test the controller passes the IllegalArgumentException when the
   * column is out of bounds. 3.6
   */
  @Test
  public void testPlayGameColumnOutOfBounds() {
    boolean[] gameOverResponses = {false, true, true};
    String toStringResponse = "outOFBounds";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("8 n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("outOFBounds\n"
        + "Player RED, make your move: \n"
        + "Not a valid number: The column is out of bounds\n", out.toString());
  }

  /**
   * Test the controller throws an IllegalArgumentException
   * when the model is null. 3.7
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() {
    Readable in = new StringReader("y\n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(null);
  }
}
