package tictactoe.player;

import java.awt.Point;
import java.util.Scanner;
import tictactoe.CellState;
import tictactoe.Grid;

public class User extends Player {

  final Scanner scanner = new Scanner(System.in);

  public User(Grid field, char symbol) {
    super(field, symbol);
  }

  @Override
  public Point getMove() {
    return getCoordinateInput();
  }

  public Point getCoordinateInput() {
    int i;
    int j;
    while (true) {
      System.out.print("Enter the coordinates: ");
      String iInput = scanner.next();
      if (!isCoordinateInputCorrect(iInput)) {
        scanner.nextLine();
        continue;
      }
      String jInput = scanner.next();
      if (!isCoordinateInputCorrect(jInput)) {
        continue;
      }
      i = Integer.parseInt(iInput) - 1;
      j = Integer.parseInt(jInput) - 1;
      if (isTurnCorrect(i, j)) {
        return new Point(i, j);
      }
    }
  }


  private boolean isCoordinateInputCorrect(String input) {
    int coordinate;
    try {
      coordinate = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      System.out.println("You should enter numbers!");
      return false;
    }
    if (coordinate > 3 || coordinate < 1) {
      System.out.println("Coordinates should be from 1 to 3!");
      return false;
    }
    return true;
  }

  private boolean isTurnCorrect(int i, int j) {
    CellState current = grid.getCellState(i, j);
    if (current == CellState.X || current == CellState.O) {
      System.out.println("This cell is occupied! Choose another one!");
      return false;
    }
    return true;
  }


}
