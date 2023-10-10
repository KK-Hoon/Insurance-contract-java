package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class InsertTable {
	static Vector<String> v = new Vector<String>();
	static String admin = "insert into admin values (?,?,?,?,?)";
	static String contract = "insert into contract values (?,?,?,?,?,?)";
	static String customer = "insert into customer values (?,?,?,?,?,?)";
	static Connection con = ConnectDB.makeConnection("customer");
	static PreparedStatement psmt = null;
	
	public static void Insert(String s) {
		
		String sql = null;
		switch(s) {
			case "admin":
				sql = admin;
				break;
			case "contract":
				sql = contract;
				break;
			case "customer":
				sql = customer;
				break;
		}
		
		try {
			Scanner fscanner = new Scanner(new FileInputStream("C:\\Users\\pc 1-12\\Desktop\\±Ë∞Ê»∆\\db_customer\\Customer\\" + s + ".txt"));
			fscanner.nextLine();
			while(fscanner.hasNext()) {
				int i=0;
				StringTokenizer st = new StringTokenizer(fscanner.nextLine(), "\t");
				while(st.hasMoreTokens()) {
					v.add(st.nextToken());
					i++;
				}
				
				try {
					psmt = con.prepareStatement(sql);
					for(int ii=0; ii<v.size(); ii++) {
						psmt.setString(ii+1, v.get(ii));
					}
					
					v.clear();
					int re = psmt.executeUpdate();
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch(Exception e) {
			
		}
	}
	public InsertTable() {
		// TODO Auto-generated constructor stub
		InsertTable.Insert("admin");
		InsertTable.Insert("contract");
		InsertTable.Insert("customer");
	}
}
