package ru.ecom.riamsproc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:default:connection");
	}
}
