package com.qa.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.qa.util.DatabaseConfig;

public class DBConnector {

	static boolean looper = true;
	private Statement statement;
	private Connection connect;

	public DBConnector() throws SQLException {
		// This is going to open our connection!
		// (jdbc:mysql:localhost;etc, root, root)
		connect = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
		this.statement = connect.createStatement();
		// statements.executeUpdate("sting of sql");
		// statements.executeQUery(sql);
	}

	// Create
	public void createPerson(String first, String last) throws SQLException {
		String sql = String.format("INSERT INTO Customers(`first_name`, `surname`) VALUES ('%s', '%s');", first, last);
		statement.executeUpdate(sql);
	}

	// Read all
	public void readPeople() throws SQLException {
		String sql = "SELECT * FROM Customers";
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			System.out.println(String.format("ID: %d, Firstname: %s, Lastname: %s", rs.getInt("id"),
					rs.getString("first_name"), rs.getString("surname")));
		}
	}

	// Update
	public void updatePerson(int id, String fname, String lname) throws SQLException {
		String sql = String.format("UPDATE Customers SET `first_name`= '%s', `surname`='%s' WHERE ID='%d';", fname,
				lname, id);
		statement.executeUpdate(sql);
	}

	// Delete
	public void deletePerson(int id) throws SQLException {
		String sql = String.format("DELETE FROM Customers where id = '%d';", id);
		statement.executeUpdate(sql);
	}

	// Quit
	public void quitDatabase() {
		looper = false;
	}

	// Read One
	public void readOne(int id) throws SQLException {
		String sql = String.format("SELECT * FROM customers where id = '%d';", id);
		ResultSet rs = statement.executeQuery(sql);

		if (!rs.next()) {
			System.out.println("No matches found");
		} else {
			do {
				System.out.println(rs.getString("first_name") + " " + rs.getString("surname"));
			} while (rs.next());
		}
	}
	
	public void tearDown() throws SQLException {
		connect.close();
	}
	
}
