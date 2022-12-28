package tictactoe;

public enum GameState {
  X_WINS, O_WINS, DRAW, RUNNING, WRONG;

  @Override
  public String toString() {
    return switch (this) {
      case X_WINS -> "X wins";
      case O_WINS -> "O wins";
      case DRAW -> "Draw";
      case RUNNING -> "Game not finished";
      default -> super.toString();
    };
  }
}
