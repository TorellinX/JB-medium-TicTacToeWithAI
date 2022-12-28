package tictactoe.player.ai;

import java.awt.Point;
import java.util.ArrayList;
import tictactoe.Grid;
import tictactoe.GameState;
import tictactoe.player.Player;

public class HardAI extends AI {

  public HardAI(Grid grid, char symbol) {
    super(grid, symbol);
    this.level = "hard";
  }

  @Override
  public Point getAIMove() {
    Minimax minimax = new Minimax(this);
    return minimax.getMove();
  }
}

class Minimax {

  Player player;
  Grid newGrid;
  static final int WIN_SCORE = 10;
  static final int LOSE_SCORE = -10;
  static final int DRAW_SCORE = 0;

  Minimax(Player player) {
    this.player = player;
    this.newGrid = player.getGrid().clone();
  }

  Point getMove() {
    Move move = this.minimax(player.getSymbol());
    return move.cell;
  }

  Move minimax(char currentPlayerSymbol) {
    ArrayList<Point> availableCells = newGrid.getAvailableCells();
    GameState state = newGrid.analyzeState();
    if (isWinning(state, player.getSymbol())) {
      return new Move(WIN_SCORE);
    }
    if (isLosing(state, player.getSymbol())) {
      return new Move(LOSE_SCORE);
    }
    if (availableCells.size() == 0) {
      return new Move(DRAW_SCORE);
    }

    ArrayList<Move> moves = new ArrayList<>();
    for (Point cell : availableCells) {
      Move move = new Move();
      move.cell = cell;
      newGrid.setCell(move.cell.x, move.cell.y, currentPlayerSymbol);
      Move result = minimax(Grid.getOpponentSymbol(currentPlayerSymbol));
      move.score = result.score;
      moves.add(move);
      newGrid.setCell(move.cell.x, move.cell.y, Grid.EMPTY_SYMBOL);
    }

    int bestMoveIndex = -1;
    if (currentPlayerSymbol == player.getSymbol()) {
      int bestScore = Integer.MIN_VALUE;
      for (int i = 0; i < moves.size(); i++) {
        if (moves.get(i).score > bestScore) {
          bestScore = moves.get(i).score;
          bestMoveIndex = i;
        }
      }
    } else {
      int bestScore = Integer.MAX_VALUE;
      for (int i = 0; i < moves.size(); i++) {
        if (moves.get(i).score < bestScore) {
          bestScore = moves.get(i).score;
          bestMoveIndex = i;
        }
      }
    }
    return moves.get(bestMoveIndex);
  }

  private boolean isWinning(GameState state, char playerSymbol) {
    return state == GameState.O_WINS && playerSymbol == 'O' ||
        state == GameState.X_WINS && playerSymbol == 'X';
  }

  private boolean isLosing(GameState state, char playerSymbol) {
    return state == GameState.O_WINS && playerSymbol == 'X' ||
        state == GameState.X_WINS && playerSymbol == 'O';
  }

}

class Move {

  Point cell;
  int score;

  Move() {
  }

  Move(int score) {
    this.score = score;
  }
}
