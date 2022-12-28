/*
Input command: > start user medium
Input command: > start user user
Input command: > start easy easy
Input command: > start hard user
Input command: > exit
 */

package tictactoe;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import tictactoe.player.User;
import tictactoe.player.ai.EasyAI;
import tictactoe.player.Player;
import tictactoe.player.ai.HardAI;
import tictactoe.player.ai.MediumAI;

/**
 * CLI Tic-Tac-Toe game.
 * <p>
 * Two menu commands:
 * <li>
 *   <ul> - start <player> <player>;
 *   <ul> - exit;
 * </li>
 * <p>
 * "user" parameter for user-player.
 * One of three types of AI for AI-player:
 * <li>
 *   <ul> - easy;
 *   <ul> - medium;
 *   <ul> - hard;
 * </li>
 * <p>
 * The easy difficulty level makes random moves.
 * The medium difficulty level looks one move ahead to see an immediate win or prevent an immediate loss.
 * The hard difficulty level uses the minimax algorithm (calculates all possible moves that might be
 * played during the game, and choose the best one based on the assumption that its opponent will also
 * play perfectly).
 */
public class Main {

  Player player1;
  Player player2;
  Player activePlayer;
  private GameState gameState = GameState.RUNNING;
  private boolean finished = false;

  private boolean exit = false;
  private Grid field;
  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {
    Main game = new Main();
    while (!game.exit) {
      game.chooseMenuAction();
    }
  }

  private void chooseMenuAction() {
    System.out.print("Input command: ");
    String[] tokens = getMenuInput();
    if (tokens.length < 1) {
      System.out.println("Bad parameters!");
      return;
    }
    String command = tokens[0];
    try {
      switch (command) {
        case "exit" -> {
          exit = true;
          return;
        }
        case "start" -> {
          if (tokens.length < 3) {
            throw new IllegalArgumentException("Bad parameters!");
          }
          start(tokens[1], tokens[2]);
        }
        default -> throw new IllegalArgumentException("Bad parameters!");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  private String[] getMenuInput() {
    String input = "";
    try {
      input = reader.readLine();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return input.strip().toLowerCase().split("\\s+");
  }

  private void start(String typePlayer1, String typePlayer2) throws IllegalArgumentException {
    initializeField();
    initializePlayers(typePlayer1, typePlayer2);
    finished = false;

    while (!finished) {
      printField();
      makeMove();
      analyseGameState();
      changeActivePlayer();
    }

    printField();
    printGameState();
  }

  private void initializeField() {
    field = new Grid();
  }

  private void initializePlayers(String type1, String type2) {
    player1 = createPlayer(type1, Grid.X_SYMBOL);
    player2 = createPlayer(type2, Grid.O_SYMBOL);
    activePlayer = player1;
  }

  Player createPlayer(String type, char symbol) {
    return switch (type) {
      case "user" -> new User(field, symbol);
      case "easy" -> new EasyAI(field, symbol);
      case "medium" -> new MediumAI(field, symbol);
      case "hard" -> new HardAI(field, symbol);
      default -> throw new IllegalArgumentException("Error! Wrong player type: " + type);
    };
  }

  private void printField() {
    field.print();
  }

  private void makeMove() throws IllegalArgumentException {
    Player player = activePlayer;
    Point move = player.getMove();
    field.setCell(move.x, move.y, player.getSymbol());
  }

  private void analyseGameState() {
    gameState = field.analyzeState();
    finished = isFinished(gameState);
  }

  private boolean isFinished(GameState state) {
    return state != GameState.RUNNING;
  }

  private void changeActivePlayer() {
    activePlayer = activePlayer == player1 ? player2 : player1;
  }

  private void printGameState() {
    System.out.println(gameState);
    System.out.println();
  }
}




