import static org.junit.Assert.assertEquals;

import connect.ConnectFourConsoleController;
import connect.ConnectFourView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import connect.ConnectFourModel;
import connect.ConnectFourModelMock;


public class ConnectFourControllerTest {
  private ConnectFourConsoleController controller;


  /**
   * Test the controller throws an IllegalArgumentException when the model is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() {
    Readable in = new StringReader("y\n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(null);
  }

  /**
   * Test the controller sends the correct information to the view.
   * When the game ends in a tie.
   */
  @Test
  public void testPlayGameTie() {
    boolean[] gameOverResponses = {true, true};
    String toStringResponse = "tie";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("k\n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("tie\n" + "Game over! It's a tie!\n" +
        "Do you want to play again? (yes/no)\n", out.toString());
  }

  /**
   * Test the controller sends the correct information to the view.
   * When the game ends with a winner.
   */
  @Test
  public void testPlayGameWinner() {
    boolean[] gameOverResponses = {true, true};
    String toStringResponse = "winner";
    ConnectFourModel model = new ConnectFourModelMock(gameOverResponses, toStringResponse);
    Readable in = new StringReader("n");
    Appendable out = new StringBuilder();
    ConnectFourView view = new ConnectFourView(out);
    this.controller = new ConnectFourConsoleController(in, view);
    controller.playGame(model);
    assertEquals("winner\n" + "Game over! RED is the winner!\n" +
        "Do you want to play again? (yes/no)\n", out.toString());
  }

  /**
   * Test the controller throws an IllegalArgumentException when the column is out of bounds.
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
    assertEquals("outOFBounds\n" +
        "Player RED, make your move: \n" +
        "Not a valid number: Invalid move\n" +
        "outOFBounds\n" +
        "Game over! It's a tie!\n" +
        "Do you want to play again? (yes/no)\n", out.toString());

  }
}
