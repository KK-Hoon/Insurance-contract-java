package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db.ConnectDB;

public class Cuscheck extends JFrame{

	private JLabel label = new JLabel();
	private JTextField jt = new JTextField(10);
	private JButton btn[] = new JButton[5];
	private Vector<Vector<String>> rowData = new Vector<Vector<String>>();
	private Vector<String> colData = new Vector<String>();
	private JTable table;
	private Connection con = ConnectDB.makeConnection("customer");
	private Statement st = null;
	private PreparedStatement psmt = null;
	Vector<String> vc = new Vector<String>();
	
	class NorthPanel extends JPanel{
		
		private String button[] = {"조회", "전체보기", "수정", "삭제", "닫기"};
		private String name = "성명";
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			label = new JLabel(name);
			add(label);
			add(jt);
			
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(button[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String s = e.getActionCommand();
						String n = null;
						String s1 = jt.getText();
						switch(s) {
							case "조회":
								n = "select * from customer where name like '%" + s1 + "%'";
								List(n);
								break;
							case "전체보기":
								n = "select * from customer";
								List(n);
								break;
							case "수정":
								new Cuschange(vc);
								break;
							case "삭제":
								Delete();
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
	public void List(String n) {
		
		jt.setText("");
		
		try {
			rowData.clear();
			st = con.createStatement();
			ResultSet rs = st.executeQuery(n);
			
			while(rs.next()) {
				Vector<String> v = new Vector<String>();
				for(int i=0; i<6; i++) {
					v.add(rs.getString(i+1));
				}
				rowData.add(v);
			}
			table.updateUI();
		}
		catch(Exception e) {
			
		}
	}
	
	public void Delete(){
		
		int seletrow = table.getSelectedRow();
		Vector<String> dv = new Vector<String>();
		
		dv = rowData.get(seletrow);
		String name = dv.get(1);
		
		int result = JOptionPane.showConfirmDialog(null, name + "님을 정말 삭제하시겠습니까?", "고객정보 삭제", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			try {
				psmt = con.prepareStatement("delete from customer where code = ?");
				psmt.setString(1, dv.get(0));
				int re = psmt.executeUpdate();
				String n = "select * from customer";
				List(n);
				
				if(re == 1) {
					JOptionPane.showMessageDialog(null, "삭제되었습니다.", "메세지", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "삭제 실패.", "메세지", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception e) {
				
			}
		}
	}
	
	class CenterPanel extends JPanel{
		String col[] = {"code", "name", "birth", "tel", "address", "company"};
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<col.length; i++) {
				colData.add(col[i]);
			}
			table = new JTable(rowData, colData);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					int selection = table.getSelectedRow();
					vc = rowData.get(selection);
				}
			});
			add(new JScrollPane(table));
		}
	}
	
	public Cuscheck() {
		// TODO Auto-generated constructor stub
		setTitle("고객조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		add(new NorthPanel(), BorderLayout.NORTH);
		add(new CenterPanel(), BorderLayout.CENTER);
		
		setSize(800, 500);
		setVisible(true);
	}
}