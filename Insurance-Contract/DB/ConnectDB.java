package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectDB {

	public static Connection makeConnection(String s) {
		String url;
		if(s == null) {
			url = "jdbc:mysql://localhost";
		}
		else {
			url = "jdbc:mysql://localhost/" + s;
		}
		String id = "root";
		String pass = "1234";
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이브 적재 성공");
			con = DriverManager.getConnection(url, id, pass);
			System.out.println("데이터베이스 연결 성공");
		}
		catch(ClassNotFoundException e) {
			System.out.println("드라이브 찾을 수 없습니다.");
		}
		catch(SQLException e) {
			System.out.println("연결 실패!!");
		}
		
		return con;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = makeConnection(null);
	}

}
