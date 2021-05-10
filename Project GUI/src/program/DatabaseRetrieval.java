package program;

import java.sql.*;

public class DatabaseRetrieval {

	private static Connection con;

	static class TableClass {
		public String tableName;
		public String[] columnNames;
		public String[][] data;

		public TableClass(String tableName, String[] columnNames, String[][] data) {
			this.tableName = tableName;
			this.columnNames = columnNames;
			this.data = data;
		}
	}

	public static ResultSet executeQuery(String sql) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// clothesshop is the db name, username, pass
			con = DriverManager.getConnection("jdbc:mysql://localhost/clothesshop", "root", "");

			Statement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			return stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static int executeUpdate(String sql) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// clothesshop is the db name, username, pass
			con = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
			
			Statement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			return stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}

	public static TableClass display(String sql) {
		try {
			ResultSet rs = DatabaseRetrieval.executeQuery(sql);

			// get column names
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			String[] columnNames = new String[rsmd.getColumnCount()];
			// adding column names to the array
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				columnNames[i] = rsmd.getColumnName(i + 1);
			}

			// get the number of rows
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
			}

			// getting the records
			String[][] data = new String[rowCount][rsmd.getColumnCount()];
			int count = 0;
			rs.beforeFirst();
			while (rs.next()) {
				// getting the data of each record
				String[] row = new String[rsmd.getColumnCount()];
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					row[i] = rs.getString(i + 1);
				}
				data[count] = row;
				count++;
			}
			con.close();

			return new TableClass(rsmd.getTableName(1), columnNames, data);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
