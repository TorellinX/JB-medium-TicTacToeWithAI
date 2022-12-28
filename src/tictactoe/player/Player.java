package tictactoe.player;

import java.awt.Point;
import tictactoe.Grid;

public abstract class Player {

  char symbol;
  Grid grid;

  protected Player(Grid grid, char symbol) {
    this.grid = grid;
    this.symbol = symbol;
  }

  public abstract Point getMove();

  public Grid getGrid() {
    return grid;
  }

  public char getSymbol() {
    return symbol;
  }
}
