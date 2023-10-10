package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db.ConnectDB;

public class Contractman extends JFrame{
	
	private JLabel label1[] = new JLabel[4];
	private JLabel label2[] = new JLabel[3];
	private JLabel label3 = new JLabel("담당자");
	private JLabel label4 = new JLabel("<고객 보험 계약현황>");
 	private JButton btn[] = new JButton[4];
	private JTextField jt1[] = new JTextField[3];
	private JTextField jt2[] = new JTextField[2];
	private Connection con = ConnectDB.makeConnection("customer");
	private Statement st = null;
	private PreparedStatement psmt = null;
	private Vector<String> name = new Vector<String>();
	private Vector<String> goods = new Vector<String>();
	private Vector<String> admin = new Vector<String>();
	private Vector<String> colData = new Vector<String>();
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private JComboBox<String> combo1;
	private JComboBox<String> combo2;
	private JComboBox<String> combo3;
	private JTable table;
	
	class NorthPanel extends JPanel{
		
		class Left extends JPanel{
			
			String left[] = {"고객코드:", "고 객 명:", "생년월일:", "연 락 처"};
			
			public Left() {
				// TODO Auto-generated constructor stub
				setLayout(new GridLayout(4, 1));
				try {
					st = con.createStatement();
					ResultSet rs = st.executeQuery("select name from customer");
					
					while(rs.next()) {
						name.add(rs.getString("name"));
					}
				}
				catch(Exception e) {
					
				}
				int j = 0;
				for(int i=0; i<label1.length; i++) {
					label1[i] = new JLabel(left[i]);
					add(label1[i]);
					jt1[j] = new JTextField(20);
					if(i == 1) {
						combo1 = new JComboBox<String>(name);
						add(combo1);
						combo1.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								JComboBox<String> cb = (JComboBox<String>)e.getSource();
								String name1 = name.get(cb.getSelectedIndex());
								Gettable(name1);
								Getjt1(name1);
							}
						});
						continue;
					}
					add(jt1[j]);
					j++;
				}
			}
		}
		class Right extends JPanel{
			
			String right[] = {"보험상품:", "가입금액:", "월보험료:"};
			
			public Right() {
				// TODO Auto-generated constructor stub
				setLayout(new GridLayout(3, 2));
				int j=0;
				for(int i=0; i<label2.length; i++) {
					label2[i] = new JLabel(right[i]);
					add(label2[i]);
					if(i == 0) {
						try {
							st = con.createStatement();
							ResultSet rs = st.executeQuery("select distinct contractName from contract");
							
							while(rs.next()) {
								goods.add(rs.getString("contractName"));
							}
						}
						catch(Exception e) {
							
						}
						combo2 = new JComboBox(goods);
						add(combo2);
						continue;
					}
					jt2[j] = new JTextField(20); 
					add(jt2[j]);
					j++;
				}
			}
		}
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(1, 2, 50 , 50));
			add(new Left());
			add(new Right());
		}
	}
	
	class CenterPanel extends JPanel{
		
		String s[] = {"가입", "삭제", "파일로저장", "닫기"};
		
		class Center1 extends JPanel{
			public Center1() {
				// TODO Auto-generated constructor stub
				add(label3);
				
				try {
					st = con.createStatement();
					ResultSet rs = st.executeQuery("select name from admin");
					
					while(rs.next()) {
						admin.add(rs.getString("name"));
					}
				}
				catch(Exception e) {
					
				}
				combo3 = new JComboBox<String>(admin);
				add(combo3);
				
				for(int i=0; i<btn.length; i++) {
					btn[i] = new JButton(s[i]);
					btn[i].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String s = e.getActionCommand();
							switch(s) {
								case "가입":
									
									insert();
									break;
								case "삭제":
									delete();
									break;
								case "파일로저장":
									file();
									break;
								case "닫기":
									dispose();
									break;
							}
						}
					});
					add(btn[i]);
				}
			}
		}
		public void file() {
			FileDialog fd = new FileDialog(UI.Contractman.this, "텍스트 파일로 저장하기", FileDialog.SAVE);
			fd.setVisible(true);
			
			String path = fd.getDirectory();
			String name = fd.getFile();
			
			BufferedWriter br = null;
			
			try {
				String cusname = combo1.getSelectedItem().toString();
				String code = jt1[0].getText();
				StringBuffer sb = new StringBuffer();
				
				br = new BufferedWriter(new FileWriter(path + "/" + name + ".txt"));
				sb.append("고객명 : " + cusname + "(" + code + ")\r\n\r\n");
				sb.append("보험상품\t\t가입금액\t\t가입일\t\t월보험료\t담당자\r\n");
				
				
				for(int i=0; i<rowData.size(); i++) {
					Vector<String> v = rowData.get(i);
					for(int ii=1; ii<v.size(); ii++) {
						if(ii == v.size()-1) {
							sb.append(v.get(ii) + "\r\n");
						}
						else {
							if(v.get(ii).length() <= 4) {
								sb.append(v.get(ii)+"\t\t");
							}
							else{
								sb.append(v.get(ii)+"\t");
							}
						}
					}
				}
				String s = sb.toString();
				br.write(s);
				br.close();
			}
			catch(Exception e) {
				
			}
		}
		public void insert() {
			try {
				String name = combo1.getSelectedItem().toString();
				Calendar cal = Calendar.getInstance();
				String s = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE);
				System.out.println(s);
				psmt = con.prepareStatement("insert into contract values(?, ?, ?, ?, ?, ?)");
				
				psmt.setString(1, jt1[0].getText());
				psmt.setString(2, combo2.getSelectedItem().toString());
				psmt.setString(3, jt2[0].getText());
				psmt.setString(4, s);
				psmt.setString(5, jt2[1].getText());
				psmt.setString(6, combo3.getSelectedItem().toString());
				
				int re = psmt.executeUpdate();
				
				if(re == 1) {
					Gettable(name);
				}
				else {
				}
				for(int i=0; i<jt2.length; i++) {
					jt2[i].setText("");
				}
			}
			catch(Exception e) {
				
			}
		}
		public void delete() {
			try {
				String name = combo1.getSelectedItem().toString();
				int selectrow = table.getSelectedRow();
				Vector<String> v = rowData.get(selectrow);
				
				int result = JOptionPane.showConfirmDialog(null, v.get(0)+"("+v.get(1)+")"+"을 삭제하시겠습니까?", "계약정보 삭제", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					psmt = con.prepareStatement("delete from contract where customerCode = ? and contractName = ? and regPrice = ? and regDate = ? and monthPrice = ? and adminName = ?");
					
					for(int i=0; i<v.size(); i++) {
						psmt.setString(i+1, v.get(i));
					}
					psmt.executeUpdate();
				}
				Gettable(name);
			}
			catch(Exception e) {
				
			}
		}
		
		class Center2 extends JPanel{
			public Center2() {
				// TODO Auto-generated constructor stub
				setLayout(new BorderLayout(0, 10));
				
				label4.setHorizontalAlignment(JLabel.CENTER);
				add(label4, BorderLayout.NORTH);
				add(new Center3(), BorderLayout.CENTER);
			}
		}
		
		class Center3 extends JPanel{
			
			String col[] = {"customerCode", "contractName", "regPrice", "regDate", "monthPrice", "adminName"};
			public Center3() {
				// TODO Auto-generated constructor stub
				setLayout(new GridLayout(2,1));
				
				
				for(int i=0; i<col.length; i++) {
					colData.add(col[i]);
				}
				
				table = new JTable(rowData, colData);
				add(new JScrollPane(table));
			}
		}
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new BorderLayout(0, 10));
			add(new Center1(), BorderLayout.NORTH);
			add(new Center2(), BorderLayout.CENTER);
			
		}
	}

	public Contractman() {
		// TODO Auto-generated constructor stub
		setTitle("보험 계약");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
	
		ct.setLayout(new BorderLayout(30, 30));
		ct.add(new NorthPanel(), BorderLayout.NORTH);
		ct.add(new CenterPanel(), BorderLayout.CENTER);
		
		setSize(800, 600);
		setVisible(true);
	}

	public void Getjt1(String name) {
		String name2 = name;
	
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select code, birth, tel from customer where name = '" + name2 + "'");
			while(rs.next()) {
				for(int i=0; i<jt1.length; i++) {
					jt1[i].setText(rs.getString(i+1));
				}
			}
		}
		catch(Exception e1) {
			
		}
	}
	
	public void Gettable(String name) {
		String name2 = name;
		
		try {
			rowData.clear();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select customerCode, contractName, "
					+ "regPrice, regDate, monthPrice, adminName from contract, "
					+ "customer where contract.customerCode = "
					+ "customer.code and customer.name = '"
					+ name2 + "' order by regDate desc");
			while(rs.next()) {
				Vector<String> v = new Vector<String>();
				for(int i=0; i<6; i++) {
					v.add(rs.getString(i+1));
				}
				rowData.add(v);
			}
			table.updateUI();
			rs.close();
			st.close();
		}
		catch(Exception e1) {
			
		}
		
	}
}
