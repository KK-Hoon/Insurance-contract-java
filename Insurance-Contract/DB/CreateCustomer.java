package db;

import java.sql.Connection;
import java.sql.Statement;

public class CreateCustomer {

	public CreateCustomer() {
		// TODO Auto-generated constructor stub
		Statement st = null;
		String dropdb = "drop database if exists customer";
		String createdb = "create database customer";
		
		try {
			Connection con = ConnectDB.makeConnection(null);
			st = con.createStatement();
			st.executeUpdate(dropdb);
			st.executeUpdate(createdb);
			System.out.println("DB 积己肯丰");
		}
		catch(Exception e) {
			System.out.println("DB 积己角菩");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CreateCustomer();
		new CreateTable();
		new InsertTable();
	}
}
