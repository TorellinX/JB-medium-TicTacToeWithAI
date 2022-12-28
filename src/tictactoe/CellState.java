package tictactoe;

public enum CellState {
  X, O, EMPTY;

  @Override
  public String toString() {
    return switch (this) {
      case O -> "O";
      case X -> "X";
      case EMPTY -> " ";
    };
  }

  public String toStateString() {
    return switch (this) {
      case O -> "O";
      case X -> "X";
      case EMPTY -> "_";
    };
  }

  public static CellState toCellState(char name) {
    switch (name) {
      case 'O' -> {
        return CellState.O;
      }
      case 'X' -> {
        return CellState.X;
      }
      case '_' -> {
        return CellState.EMPTY;
      }
      default -> throw new IllegalArgumentException(
          "Error! Initial state contains unsupported characters.");
    }
  }

}
