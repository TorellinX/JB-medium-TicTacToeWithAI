package tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

  public final static int SIZE = 3;

  CellState[][] field = new CellState[SIZE][SIZE];
  public final static char EMPTY_SYMBOL = '_';
  public final static char X_SYMBOL = 'X';
  public final static char O_SYMBOL = 'O';


  public final static Point[][] lines = {
      {new Point(0, 0), new Point(0, 1), new Point(0, 2)},
      {new Point(1, 0), new Point(1, 1), new Point(1, 2)},
      {new Point(2, 0), new Point(2, 1), new Point(2, 2)},
      {new Point(0, 0), new Point(1, 0), new Point(2, 0)},
      {new Point(0, 1), new Point(1, 1), new Point(2, 1)},
      {new Point(0, 2), new Point(1, 2), new Point(2, 2)},
      {new Point(0, 0), new Point(1, 1), new Point(2, 2)},
      {new Point(0, 2), new Point(1, 1), new Point(2, 0)}};

  Grid() {
    for (CellState[] row : field) {
      Arrays.fill(row, CellState.EMPTY);
    }
  }

  Grid(String initialState) {
    char c;
    int counter = 0;
    for (int i = 0; i < field.length; i++) {
      for (int j = 0; j < field[0].length; j++) {
        c = initialState.toUpperCase().charAt(counter);
        if (c != EMPTY_SYMBOL && c != X_SYMBOL && c != O_SYMBOL) {
          throw new IllegalArgumentException(
              "Error! Initial state contains unsupported characters.");
        }
        field[i][j] = CellState.toCellState(c);
        counter++;
      }
    }
  }

  public void setCell(int i, int j, char c) {
    this.field[i][j] = CellState.toCellState(c);
  }

  public CellState getCellState(int i, int j) {
    return this.field[i][j];
  }

  void print() {
    System.out.println("---------");
    for (int i = 0; i < field.length; i++) {
      System.out.print("| ");
      for (int j = 0; j < field[0].length; j++) {
        System.out.print(field[i][j] + " ");
      }
      System.out.println("|");
    }
    System.out.println("---------");
  }

  public GameState analyzeState() throws IllegalStateException {
    boolean xHasRow = false;
    boolean oHasRow = false;
    boolean hasEmptyCells = false;
    int xs = 0;
    int os = 0;

    for (CellState[] row : this.field) {
      for (CellState cell : row) {
        switch (cell) {
          case X -> xs++;
          case O -> os++;
          case EMPTY -> hasEmptyCells = true;
          default -> throw new IllegalStateException("Illegal cell state");
        }
      }
    }

    int lineCellsXs;
    int lineCellsOs;
    for (Point[] line : lines) {
      lineCellsXs = 0;
      lineCellsOs = 0;
      for (Point cell : line) {
        if (this.field[cell.x][cell.y] == CellState.X) {
          lineCellsXs++;
        }
        if (this.field[cell.x][cell.y] == CellState.O) {
          lineCellsOs++;
        }
      }
      if (lineCellsXs == 3) {
        xHasRow = true;
      }
      if (lineCellsOs == 3) {
        oHasRow = true;
      }
    }

    if (Math.abs(xs - os) > 1 || (xHasRow && oHasRow)) {
      return GameState.WRONG;
    }
    if (!xHasRow && !oHasRow && hasEmptyCells) {
      return GameState.RUNNING;
    }
    if (!xHasRow && !oHasRow && !hasEmptyCells) {
      return GameState.DRAW;
    }
    if (xHasRow) {
      return GameState.X_WINS;
    }
    if (oHasRow) {
      return GameState.O_WINS;
    }
    throw new IllegalStateException("Error! ");
  }

  public CellState[][] getField() {
    // TODO: return copy?
    return field;
  }

  public ArrayList<Point> getAvailableCells() {
    ArrayList<Point> availableCells = new ArrayList<>();
    for (int i = 0; i < Grid.SIZE; i++) {
      for (int j = 0; j < Grid.SIZE; j++) {
        if (getCellState(i, j) == CellState.EMPTY) {
          availableCells.add(new Point(i, j));
        }
      }
    }
    return availableCells;
  }

  public Grid clone() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < field.length; i++) {
      for (int j = 0; j < field[0].length; j++) {
        builder.append(field[i][j].toStateString());
      }
    }
    return new Grid(builder.toString());
  }

  public static char getOpponentSymbol(char playerSymbol) {
    return switch (playerSymbol) {
      case X_SYMBOL -> O_SYMBOL;
      case O_SYMBOL -> X_SYMBOL;
      default -> throw new IllegalArgumentException("Illegal player symbol: " + playerSymbol);
    };
  }

}
