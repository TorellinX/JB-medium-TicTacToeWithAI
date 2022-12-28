package tictactoe.player.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import tictactoe.Grid;
import tictactoe.player.Player;

/**
 * The template class for AI-opponent
 */
public abstract class AI extends Player {

  String level;

  public AI(Grid field, char symbol) {
    super(field, symbol);
  }

  @Override
  public Point getMove() {
    System.out.printf("Making move level \"%s\"%n", getLevel());
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return getAIMove();
  }

  abstract Point getAIMove();

  /**
   * Returns a random free cell.
   *
   * @return random free cell
   */
  Point getRandomMove() {
    ArrayList<Point> availableCells = getGrid().getAvailableCells();
    Random random = new Random();
    int randomIndex = random.nextInt(availableCells.size());
    return availableCells.get(randomIndex);
  }


  public String getLevel() {
    return level;
  }

}
