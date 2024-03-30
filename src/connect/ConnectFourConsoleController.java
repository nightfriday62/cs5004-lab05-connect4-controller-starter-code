package connect;

// TODO
// Create a controller for Connect Four that allows a user to play the game.
// The controller should use the model to execute the game and the view to convey
// the game state to the user.

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a Controller for Connect Four: handle user moves by executing them
 * using the model; Communicate between the model and the view.
 */
public class ConnectFourConsoleController implements ConnectFourController {
  private Readable in;
  private ConnectFourView view;

  /**
   * Constructs a new ConnectFourConsoleController object with the specified input and view.
   *
   * @param in   the input source for user moves
   * @param view the view to display the game state
   */
  public ConnectFourConsoleController(Readable in, ConnectFourView view) {
    this.in = in;
    this.view = view;
  }


  @Override
  public void playGame(ConnectFourModel m) throws IllegalArgumentException {
    Scanner scanner = new Scanner(in);
    int move = 99;
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      while (!m.isGameOver()) {
        try {
          this.view.displayGameState(m.toString());
          this.view.displayPlayerTurn(m.getTurn().getDisplayName());
          move = scanner.nextInt();
          if (move == 0) {
            break;
          }
          m.makeMove(move);
        } catch (InputMismatchException e) {
          this.view.displayErrorMessage("Is not a number!");
          scanner.nextLine();
        } catch (IllegalArgumentException e) {
          this.view.displayInvalidNumber(e.getMessage());
        }
      }
      if (m.isGameOver()) {
        this.view.displayGameState(m.toString());
        switch (m.getWinner()) {
          case RED:
            this.view.displayGameOver("RED");
            break;
          case YELLOW:
            this.view.displayGameOver("YELLOW");
            break;
          case null:
            this.view.displayGameOver(null);
            break;
        }
        this.view.askPlayAgain();
        String playAgain = scanner.next();
        if ("y".equals(playAgain)) {
          m.resetBoard();
          this.playGame(m);
        }
      }
      if (move == 0) {
        this.view.displayGameQuit(m.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
