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
			System.out.println("����̺� ���� ����");
			con = DriverManager.getConnection(url, id, pass);
			System.out.println("�����ͺ��̽� ���� ����");
		}
		catch(ClassNotFoundException e) {
			System.out.println("����̺� ã�� �� �����ϴ�.");
		}
		catch(SQLException e) {
			System.out.println("���� ����!!");
		}
		
		return con;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = makeConnection(null);
	}

}
