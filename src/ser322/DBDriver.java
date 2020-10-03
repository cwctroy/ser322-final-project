package ser322;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DBDriver {

	public static String url;
	public static String user;
	public static String password;
	public static String driver;

	public static void main(String[] args) {
		System.out.println("main called with the following args: ");
		for (String arg : args) {
			System.out.println(arg);
		}
		url = args[0];
		user = args[1];
		password = args[2];
		driver = args[3];
		TestQuery();
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

}
