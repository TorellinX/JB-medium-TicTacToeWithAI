package tictactoe.player.ai;

import java.awt.Point;
import tictactoe.Grid;

public class EasyAI extends AI {

  public EasyAI(Grid field, char symbol) {
    super(field, symbol);
    this.level = "easy";
  }

  @Override
  public Point getAIMove() {
    return getRandomMove();
  }

}
