package tictactoe.player.ai;

import java.awt.Point;
import tictactoe.CellState;
import tictactoe.Grid;

public class MediumAI extends AI {

  public MediumAI(Grid grid, char symbol) {
    super(grid, symbol);
    this.level = "medium";
  }

  @Override
  public Point getAIMove() {
    Point move = getMoveWithFirstRule();
    if (move != null) {
      return move;
    }
    move = getMoveWithSecondRule();
    if (move != null) {
      return move;
    }
    return getRandomMove();
  }

  /**
   * If it already has two in a row and can win with one further move, it does so. Otherwise,
   * returns null.
   *
   * @return cell
   */
  private Point getMoveWithFirstRule() {
    int lineCells;
    for (Point[] line : Grid.lines) {
      lineCells = 0;
      for (Point cell : line) {
        if (getGrid().getField()[cell.x][cell.y] == CellState.toCellState(getSymbol())) {
          lineCells++;
        }
      }
      if (lineCells == 2) {
        for (Point cell : line) {
          if (getGrid().getField()[cell.x][cell.y] == CellState.EMPTY) {
            return cell;
          }
        }
      }
    }
    return null;
  }

  /**
   * If its opponent can win with one move, it plays the move necessary to block this. Otherwise,
   * returns null.
   *
   * @return cell
   */
  private Point getMoveWithSecondRule() {
    char opponent = getSymbol() == 'X' ? 'O' : 'X';
    int lineCells;
    for (Point[] line : Grid.lines) {
      lineCells = 0;
      for (Point cell : line) {
        if (getGrid().getField()[cell.x][cell.y] == CellState.toCellState(opponent)) {
          lineCells++;
        }
      }
      if (lineCells == 2) {
        for (Point cell : line) {
          if (getGrid().getField()[cell.x][cell.y] == CellState.EMPTY) {
            return cell;
          }
        }
      }

    }
    return null;
  }
}
