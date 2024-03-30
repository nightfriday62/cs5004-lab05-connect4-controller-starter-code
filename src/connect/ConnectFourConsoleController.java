package connect;

// TODO
// Create a controller for Connect Four that allows a user to play the game.
// The controller should use the model to execute the game and the view to convey
// the game state to the user.

import java.io.IOException;
import java.util.InputMismatchException;
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
    int move;
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
            this.view.displayGameQuit(m.toString());
            break;
          }
          m.makeMove(move);
          if (m.isGameOver()) {
            this.view.displayGameState(m.toString());
            Player winner = m.getWinner();
            if (winner == null) {
              this.view.displayGameOver(null);
            } else if(winner == Player.RED) {
              this.view.displayGameOver("RED");
            } else if (winner == Player.YELLOW) {
              this.view.displayGameOver("YELLOW");
            }
            this.view.askPlayAgain();
            String playAgain = scanner.next();
            if ("y".equals(playAgain)) {
              m.resetBoard();
            }
          }
        } catch (InputMismatchException e) {
          this.view.displayErrorMessage("Is not a number!");
          scanner.nextLine();
        } catch (IllegalArgumentException e) {
          this.view.displayInvalidNumber(e.getMessage());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
