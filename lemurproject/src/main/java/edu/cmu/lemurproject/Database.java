package edu.cmu.lemurproject;

import java.sql.*;

public class Database {
	private Connection conn;

	public Database() {
		connect();
		createTableWebpages();
	}

	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		}
		catch (Exception e) {
			System.err.println("Problem connecting to DB.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void disconnect() {
		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch(SQLException e) {
			System.err.println("Problem closing connection.");
			e.printStackTrace();
		}
	}

	public void createTableWebpages() {
    Statement stmt = null;

    try {
      stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS webpages (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				"url VARCHAR NOT NULL," +
				"content VARCHAR," +
//				"doc_header varchar," +
//				"warc_header varchar," +
				"content_length INTEGER," +
				"content_type VARCHAR," +
				"server VARCHAR," +
				"title VARCHAR)";

      stmt.executeUpdate(sql);
			stmt.close();
    }
		catch (Exception e) {
      System.err.println("Problem creating table WEBPAGES");
			e.printStackTrace();
			System.exit(1);
    }
  }

	public void insert(WarcDocument doc) {
		String sql = "INSERT INTO webpages (url, content, content_length," +
			"content_type, server, title) VALUES (?, ?, ?, ?, ?, ?)";

    try {
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString( 1, doc.getURL() );
			pstmt.setString( 2, doc.getContent() );
//			pstmt.setString( 3, doc.getDocHeader() );
//			pstmt.setString( 4, doc.getWarcHeader() );
			pstmt.setInt( 3, doc.getContentLength() );
			pstmt.setString( 4, doc.getContentType() );
			pstmt.setString( 5, doc.getServer() );
			pstmt.setString( 6, doc.getTitle() );

      pstmt.executeUpdate();
			pstmt.close();
    }
		catch (SQLException e) {
      System.err.println("Problem inserting into DB");
			e.printStackTrace();
			System.exit(1);
    }
  }
}
