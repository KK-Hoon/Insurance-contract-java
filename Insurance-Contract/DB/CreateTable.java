package db;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
	
	public CreateTable() {
		// TODO Auto-generated constructor stub
		Connection con = ConnectDB.makeConnection("customer");
		Statement st = null;
		
		String createadmin = "create table if not exists admin ("
				+ "name varchar(20) not null,"
				+ "passwd varchar(20) not null,"
				+ "position varchar(20),"
				+ "jumin char(14),"
				+ "inputDate date,"
				+ "PRIMARY KEY (name, passwd))";
		
		String createcontract = "create table if not exists contract ("
				+ "customerCode char(7) not null,"
				+ "contractName varchar(20) not null,"
				+ "regPrice int,"
				+ "regDate date not null,"
				+ "monthPrice int,"
				+ "adminName varchar(20))";
				
		String createcustomer = "create table if not exists customer("
				+ "code char(7) not null,"
				+ "name varchar(20) not null,"
				+ "birth date,"
				+ "tel varchar(20),"
				+ "address varchar(100),"
				+ "company varchar(20))";
		
		try {
			st = con.createStatement();
			st.executeUpdate(createadmin);
			st.executeUpdate(createcontract);
			st.executeUpdate(createcustomer);
			System.out.println("테이블 만들기 성공");
		}
		catch(Exception e) {
			System.out.println("테이블 만들기 실패");
		}
	}
}
