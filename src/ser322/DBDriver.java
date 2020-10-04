package ser322;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;


public class DBDriver {

	public static String url;
	public static String user;
	public static String password;
	public static String driver;
	public static String query;

	public static final String[] TABLES = {"CITY", "TEAM", "PLAYER", "GAME", "SCORE"};

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
			query = in.nextLine();

			query = query.toUpperCase();

			switch (query) {
				case ("Q") :{ 
					System.out.println("Exiting");
				}
				break;

				case ("TEST") : {
					TestQuery();
				} break;

				case ("HELP") : 
				case ("USAGE") :{
					PrintUsage();
				}
				break;

				default: {
					System.out.println("Unrecognized query, please try again");
				} break;
			}
		} while (! query.equalsIgnoreCase("Q"));
		System.exit(0);
	}

	public static void TestQuery() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;

		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);

			stmt = conn.createStatement();

			rs = stmt.executeQuery("Select * from Player");

			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t ");
				System.out.print(rs.getDate(2) + "\t ");
				System.out.print(rs.getDate(3) + "\t ");
				System.out.print(rs.getDate(4) + "\t ");
				System.out.println(rs.getString(5) + "\t ");
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public static void PrintUsage() {
		System.out.println("This database has the following tables: ");
		for (String table : TABLES) {
			System.out.println(table);
		}

		System.out.println(
			"You can use the following commands: \n "
			+ "list, add, delete\n"
			+ "followed by the table you wish to access.\n"
			+ "You can also type your own SQL query"
		);
	}

	// INSERT FUNCTIONS
	public static void InsertCity() {
		System.out.println("Function not yet implemented");
	}
	
	public static void InsertTeam() {
		System.out.println("Function not yet implemented");
	}


	public static void InsertPlayer() {
		System.out.println("Function not yet implemented");
	}

	public static void InsertGame() {
		System.out.println("Function not yet implemented");
	}

	public static void InsertScore() {
		System.out.println("Function not yet implemented");
	}

	//DELETE FUNCTIONS
	public static void DeleteCity() {
		System.out.println("Function not yet implemented");
	}

	public static void DeleteTeam() {
		System.out.println("Function not yet implemented");
	}

	public static void DeletePlayer() {
		System.out.println("Function not yet implemented");
	}


	public static void DeleteGame() {
		System.out.println("Function not yet implemented");
	}

	public static void DeleteScore() {
		System.out.println("Function not yet implemented");
	}

	// LIST ALL IN TABLE
	public static void ListCity() {
		System.out.println("Function not yet implemented");
	}

	public static void ListTeam() {
		System.out.println("Function not yet implemented");
	}

	public static void ListPlayer() {
		System.out.println("Function not yet implemented");
	}

	public static void ListGame() {
		System.out.println("Function not yet implemented");
	}

	public static void ListScore() {
		System.out.println("Function not yet implemented");
	}

}
