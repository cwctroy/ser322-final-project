package ser322;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Scanner;

public class DBDriver {

  public static String url;
  public static String user;
  public static String password;
  public static String driver;
  public static String[] query;

  public static final String[] TABLES = { "CITY", "TEAM", "PLAYER", "GAME", "SCORE" };

  public static Scanner in;

  public static void main(String[] args) {
    if (args.length < 4) {
      System.out.println("Please supply the following args: <url> <username> <password> <driver>");
      System.exit(1);
    }
    System.out.println("main called with the following args: ");
    for (String arg : args) {
      System.out.println(arg);
    }
    url = args[0];
    user = args[1];
    password = args[2];
    driver = args[3];

    in = new Scanner(System.in);

    // Main control loop
    do {
      System.out.println("Please input your query (q to exit)");
      query = in.nextLine().split(" ");

      query[0] = query[0].toUpperCase();

      switch (query[0]) {
        case ("Q"): {
          System.out.println("Exiting");
        }
          break;

        case ("TEST"): {
          TestQuery();
        }
          break;

        case ("HELP"):
        case ("USAGE"): {
          PrintUsage();
        }
          break;

        case ("LIST"): {
          if (query.length != 2) {
            System.out.println("Incorrect format, please enter: list <tableName>");
          } else {
            ListTable(query[1]);
          }
        }
          break;

        case ("ADD"): {
          if (query.length != 2) {
            System.out.println("Incorrect format, please enter: add <tableName>");
          } else {
            if (Arrays.asList(TABLES).contains(query[1].toUpperCase())) {
              AddValues(query[1]);
            } else {
              System.out.println("Table name not found. TABLES: " + Arrays.toString(TABLES));
            }
          }
        }
          break;

        case ("SQL"): {
          System.out.println("Please input your query");
          String queryString = in.nextLine();
          CustomQuery(queryString);
        }
        break;

        case ("DELETE"): {
          if (query.length != 2) {
            System.out.println("Incorrect format, please enher: delete <tableName>");
          } else {
            if (Arrays.asList(TABLES).contains(query[1].toUpperCase())) {
              DeleteValues(query[1]);
            } else {
              System.out.println("Table name not found. TABLES: " + Arrays.toString(TABLES));
            }
          }
        }
        break;

        default: {
          System.out.println("Unrecognized query, please try again, or type help to see a list of accepted commands");
        }
          break;
      }
    } while (!query[0].equalsIgnoreCase("Q"));
    System.exit(0);
  }

  public static void TestQuery() {
    ResultSet rs = null;
    Statement stmt = null;
    Connection conn = null;
    int rCount = 0;

    try {
      Class.forName(driver);

      conn = DriverManager.getConnection(url, user, password);

      stmt = conn.createStatement();

      rs = stmt.executeQuery("Select * from Player");

      // Get meta data from the results set for column numbers and names
      ResultSetMetaData rsmd = rs.getMetaData();
      int rsColumns = rsmd.getColumnCount();
      // Print the column headers, starting at 1 because it is not 0-indexed
      int colWidth;
      // We are looping through each column to get width and label then using printf
      // to ensure we space each column for longest possible attribute
      for (int i = 1; i <= rsColumns; i++) {
        colWidth = rsmd.getColumnDisplaySize(i);
        System.out.printf("%-" + colWidth + "s\t", rsmd.getColumnLabel(i));
      }
      System.out.println();
      while (rs.next()) {
        // int i starts at 1 because the rs is not 0-indexed
        for (int i = 1; i <= rsColumns; i++) {
          colWidth = rsmd.getColumnDisplaySize(i);
          // Once again using the printf to ensure there is space in the columns
          System.out.printf("%-" + colWidth + "s\t", rs.getString(i));
        }
        System.out.println();
        rCount++;
      }
      System.out.println("Returned " + rCount + " rows");
      // while (rs.next()) {
      // System.out.print(rs.getString(1) + "\t ");
      // System.out.print(rs.getDate(2) + "\t ");
      // System.out.print(rs.getDate(3) + "\t ");
      // System.out.print(rs.getDate(4) + "\t ");
      // System.out.println(rs.getString(5) + "\t ");
      // }
    } catch (Exception exc) {
      exc.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
  }

  public static void PrintUsage() {
    System.out.println("This database has the following tables: ");
    for (String table : TABLES) {
      System.out.println(table);
    }

    System.out.println("You can use the following commands: \n" + "list, add, delete\n"
        + "followed by the table you wish to access.\n" + "Type sql to input your own query.");
  }

  public static void AddValues(String tableName) {
    tableName = tableName.toLowerCase();
    if (tableName.equals("city")) {
      InsertCity();
    } else if (tableName.equals("team")) {
      InsertTeam();
    } else if (tableName.equals("game")) {
      InsertGame();
    } else if (tableName.equals("player")) {
      InsertPlayer();
    } else if (tableName.equals("score")) {
      InsertScore();
    } else {
      System.out.println("Error. No valid table found for " + tableName + ". Please try again.");
    }
  }

  public static void DeleteValues(String tableName) {
    switch (tableName.toUpperCase()) {
      case ("CITY"): {
        DeleteCity();
      }
      break;

      case ("TEAM"): {
        DeleteTeam();
      }
      break;

      case ("GAME"): {
        DeleteGame();
      }
      break;

      case ("PLAYER"): {
        DeletePlayer();
      }
      break;

      case ("SCORE"): {
        DeleteScore();
      }
      break;

      default : {
        System.out.println("Error. No valid table found for " + tableName + ". Please try again");
      }
    }
  }

  // INSERT FUNCTIONS
  public static void InsertCity() {
    System.out.println("Please input name of city");
    String city = in.nextLine();
    System.out.println("Please input state as 2 chars (e.g. WA, OK, etc.)");
    String state = in.nextLine();
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("insert into city values ('" + city + "', '" + state + "');");
      if (result > 0) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void InsertTeam() {
    System.out.println("Please input name of team");
    String teamName = in.nextLine();
    System.out.println("Please input team home city");
    String teamCity = in.nextLine();
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("insert into team values ('" + teamName + "', '" + teamCity + "');");
      if (result > 0) {
        conn.commit();
        System.out.println("SUCCESS");
      } else {
        System.out.println("FAILURE");
      }
    } catch (SQLIntegrityConstraintViolationException e) {
      if (e.getMessage().contains("FOREIGN KEY (`home_city`)")) {
        System.out.println("Team city " + teamCity + " does not exist in CITY table. Please try again.");
      } else {
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null)
          stmt.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void InsertPlayer() {
    System.out.println("Please input player name");
    String playerName = in.nextLine();
    System.out.println("Please input player birth date as YYYY-MM-DD");
    String playerDOB = in.nextLine();
    System.out.println("Please input player team start date as YYYY-MM-DD");
    String teamStart = in.nextLine();
    System.out.println("Please input player team end date as YYYY-MM-DD or null if still active");
    String teamEnd = in.nextLine();
    System.out.println("Please input name of team");
    String teamName = in.nextLine();
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      if (teamEnd.equalsIgnoreCase("null") || teamEnd.trim().isEmpty()) {
        // null team end
        result = stmt.executeUpdate("insert into player values ('" + playerName + "', '" + playerDOB + "', '"
            + teamStart + "', null, '" + teamName + "');");
      } else {
        result = stmt.executeUpdate("insert into player values ('" + playerName + "', '" + playerDOB + "', '"
            + teamStart + "', '" + teamEnd + "', '" + teamName + "');");
      }
      if (result > 0) {
        conn.commit();
        System.out.println("SUCCESS");
      } else {
        System.out.println("FAILURE");
      }
    } catch (SQLIntegrityConstraintViolationException e) {
      if (e.getMessage().contains("FOREIGN KEY (`team_name`)")) {
        System.out.println("Team name " + teamName + " does not exist in TEAM table. Please try again.");
      } else {
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void InsertGame() {
    System.out.println("Please input city where game occured");
    String gameCity = in.nextLine();
    System.out.println("Please input game start date as YYYY-MM-DD");
    String gameStart = in.nextLine();
    System.out.println("Please input home team name");
    String homeTeam = in.nextLine();
    System.out.println("Please input away team name");
    String awayTeam = in.nextLine();
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate(
          "insert into game values ('" + gameCity + "', '" + gameStart + "', '" + homeTeam + "', '" + awayTeam + "');");
      if (result > 0) {
        conn.commit();
        System.out.println("SUCCESS");
      } else {
        System.out.println("FAILURE");
      }
    } catch (SQLIntegrityConstraintViolationException e) {
      System.out.println(e.getMessage());
      if (e.getMessage().contains("FOREIGN KEY (`home_team`)")) {
        System.out.println("Team name " + homeTeam + " does not exist in TEAM table. Please try again.");
      } else if (e.getMessage().contains("FOREIGN KEY (`away_team`)")) {
        System.out.println("Team name " + awayTeam + " does not exist in TEAM table. Please try again.");
      } else if (e.getMessage().contains("FOREGIN KEY (`city`)")) {
        System.out.println("City " + gameCity + " does not exist in CITY table. Please try again.");
      } else {
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null)
          stmt.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void InsertScore() {
    System.out.println("Please input game start date as YYYY-MM-DD");
    String scoreDate = in.nextLine();
    System.out.println("Please input city where game occured");
    String scoreCity = in.nextLine();
    System.out.println("Please input winning team name");
    String winTeam = in.nextLine();
    System.out.println("Please input losing team name");
    String loseTeam = in.nextLine();
    System.out.println("Please input winning team score");
    String winScore = in.nextLine();
    System.out.println("Please input losing team score");
    String loseScore = in.nextLine();
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("insert into score values ('" + scoreDate + "', '" + scoreCity + "', '" + winTeam
          + "', '" + loseTeam + "', '" + winScore + "', '" + loseScore + "');");
      if (result > 0) {
        conn.commit();
        System.out.println("SUCCESS");
      } else {
        System.out.println("FAILURE");
      }
    } catch (SQLIntegrityConstraintViolationException e) {
      System.out.println(e.getMessage());
      if (e.getMessage().contains("FOREIGN KEY (`date`)")) {
        System.out.println("Date " + scoreDate + " does not exist in GAME table. Please try again.");
      } else if (e.getMessage().contains("FOREIGN KEY (`city`)")) {
        System.out.println("City name " + scoreCity + " does not exist in CITY table. Please try again.");
      } else if (e.getMessage().contains("FOREGIN KEY (`winning_team`)")) {
        System.out.println("Team name " + winTeam + " does not exist in TEAM table. Please try again.");
      } else if (e.getMessage().contains("FOREGIN KEY (`losing_team`)")) {
        System.out.println("Team name " + loseTeam + " does not exist in TEAM table. Please try again.");
      } else {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      if (e.getMessage().contains("Incorrect integer")) {
        System.out.println("Winning and losing scores must be integers. Please try again.");
      } else {
        System.out.println(e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null)
          stmt.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // DELETE FUNCTIONS
  public static void DeleteCity() {
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    String cityName;
    String state;
    System.out.println("Please input name of city");
    cityName = in.nextLine();
    System.out.println("Please input state as 2 chars (e.g. WA, OK, etc.)");
    state = in.nextLine();
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("DELETE FROM CITY WHERE CITY.CITY_NAME = \"" + cityName + "\" AND CITY.STATE = \"" + state + "\";");
      if (result > 0) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } 
  }

  public static void DeleteTeam() {
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    String teamName;
    System.out.println("Please input name of team");
    teamName = in.nextLine();
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("DELETE FROM TEAM WHERE TEAM.TEAM_NAME = \"" + teamName.toUpperCase() + 
      "\";");
      if (result > 0) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } 
  }

  public static void DeletePlayer() {
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    String playerDOB;
    String playerName;
    String team;
    System.out.println("Please input player name");
    playerName = in.nextLine();
    System.out.println("Please input player birth date as YYYY-MM-DD");
    playerDOB = in.nextLine();
    System.out.println("Please input team name");
    team = in.nextLine();
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("DELETE FROM PLAYER WHERE PLAYER.PLAYER_NAME = \"" +playerName.toUpperCase() + 
      "\" AND PLAYER.DOB = \"" + playerDOB + 
      "\" AND PLAYER.TEAM_NAME = \"" + team.toUpperCase() +
      "\";");
      if (result > 0) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } 
  }

  public static void DeleteGame() {
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    String date;
    String cityName;
    System.out.println("Please input game start date as YYYY-MM-DD");
    date = in.nextLine();
    System.out.println("Please input city where game occured");
    cityName = in.nextLine();
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("DELETE FROM GAME WHERE GAME.DATE = \"" + date.toUpperCase() + 
      "\" AND GAME.CITY = \"" + cityName.toUpperCase() + 
      "\";");
      if (result > 0) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } 
  }

  public static void DeleteScore() {
    Connection conn = null;
    Statement stmt = null;
    int result = 0;
    String date;
    String cityName;
    System.out.println("Please input game start date as YYYY-MM-DD");
    date = in.nextLine();
    System.out.println("Please input city where game occured");
    cityName = in.nextLine();
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      result = stmt.executeUpdate("DELETE FROM SCORE WHERE SCORE.DATE = \"" + date.toUpperCase() + 
      "\" AND SCORE.CITY = \"" + cityName.toUpperCase() + 
      "\";");
      if (result > 0) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } 
  }

  // LIST ALL IN TABLE
  public static void ListTable(String table) {
    ResultSet rs = null;
    Statement stmt = null;
    Connection conn = null;

    int rCount = 0;

    try {
      Class.forName(driver);

      conn = DriverManager.getConnection(url, user, password);

      stmt = conn.createStatement();

      rs = stmt.executeQuery("Select * from " + table + ";");
      // Get meta data from the results set for column numbers and names
      ResultSetMetaData rsmd = rs.getMetaData();
      int rsColumns = rsmd.getColumnCount();
      // Print the column headers, starting at 1 because it is not 0-indexed
      int colWidth;
      // We are looping through each column to get width and label then using printf
      // to ensure we space each column for longest possible attribute
      for (int i = 1; i <= rsColumns; i++) {
        colWidth = rsmd.getColumnDisplaySize(i);
        System.out.printf("%-" + colWidth + "s\t", rsmd.getColumnLabel(i));
      }
      System.out.println();
      while (rs.next()) {
        // int i starts at 1 because the rs is not 0-indexed
        for (int i = 1; i <= rsColumns; i++) {
          colWidth = rsmd.getColumnDisplaySize(i);
          // Once again using the printf to ensure there is space in the columns
          System.out.printf("%-" + colWidth + "s\t", rs.getString(i));
        }
        System.out.println();
        rCount++;
      }
      System.out.println("Returned " + rCount + " rows");
      // md = rs.getMetaData();
      // numColumns = md.getColumnCount();
      //
      // while (rs.next()) {
      // for (int i = 1; i <= numColumns; i++) {
      // System.out.print(rs.getString(i) + "\t");
      // }
      // System.out.println();
      // }
    } catch (Exception exc) {
      exc.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        if (conn != null)
          conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      }
    }
  }

  private static void CustomQuery(String queryString){
    Connection conn = null;
    Statement stmt = null;
    boolean success;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      success = stmt.execute(queryString);
      if (success) {
        System.out.println("SUCCESS");
        conn.commit();
      } else {
        System.out.println("FAILURE");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.rollback();
          conn.close();
        }
        if (stmt != null) {
          stmt.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
