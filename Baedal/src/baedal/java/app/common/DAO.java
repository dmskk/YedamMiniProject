package baedal.java.app.common;

//import java.io.FileInputStream;
//import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Properties;

public class DAO {
	// Oracle DB정보
	private String jdbcDriver="oracle.jdbc.driver.OracleDriver";
	private String oracleUrl="jdbc:oracle:thin:@localhost:1521:xe";
	private String connectedId="bd";
	private String connectedPwd="bd";

	// 모든 자식클래스에서 공통으로 사용되는 필드
	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	public DAO() {
		//dbConfig();
	}

	// DB에 접속하는 메소드
	public void connect() {
		try {
			// 1. JDBC Driver 로딩
			Class.forName(jdbcDriver);
			// 2. DB 서버 접속
			conn = DriverManager.getConnection(oracleUrl, connectedId, connectedPwd); // url, id, pwd 순서로 넣어야 함!
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver 로딩 실패");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
		}
	}

	// DB 접속을 해제하는 메소드
	public void disconnect() {
		try {
			// 경우에 따라서 사용을 안 할 수도 있으니까 null인지 확인하기
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	// DB정보를 가져오는 메소드
//	private void dbConfig() {
//		String resource = "config/bd.properties";
//		Properties properties = new Properties();
//
//		try {
//			String filePath = ClassLoader.getSystemClassLoader().getResource(resource).getPath();
//			properties.load(new FileInputStream(filePath));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		jdbcDriver = properties.getProperty("driver");
//		oracleUrl = properties.getProperty("url");
//		connectedId = properties.getProperty("id");
//		connectedPwd = properties.getProperty("pwd");
//	}
}
