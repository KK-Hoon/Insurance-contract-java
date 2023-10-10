package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.ConnectDB;

public class Login extends JFrame{

	private JLabel label[] = new JLabel[2];
	private JTextField id = new JTextField(5);
	private JPasswordField pass = new JPasswordField(10);
	private JButton btn[] = new JButton[2];
	Connection con = ConnectDB.makeConnection("customer");
	PreparedStatement psmt = null;
	
	class NorthPanel extends JPanel{
		
		private JLabel label = new JLabel("������ �α���");
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			label.setFont(new Font("����", Font.PLAIN, 30));
			add(label);
		}
	}
	
	class CenterPanel extends JPanel{
		
		private String slabel[] = {"�̸�", "��й�ȣ"};
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(2, 1, 0, 10));
			
			for(int i=0; i<label.length; i++) {
				label[i] = new JLabel(slabel[i]);
				label[i].setHorizontalAlignment(JLabel.CENTER);
				add(label[i]);
				if(i == 0) {
					add(id);
				}
				else {
					add(pass);
				}
			}
		}
	}
	
	class SouthPanel extends JPanel{
		
		private String sbtn[] = {"Ȯ��", "����"};
		
		public void Enter(){
			
			String name = id.getText();
			char pw[] = pass.getPassword();
			String password = new String(pw); 
			
			try {
				psmt = con.prepareStatement("select name from admin where name = ? and passwd = ?");
				psmt.setString(1, name);
				psmt.setString(2, password);
				ResultSet rs = psmt.executeQuery();
				if(rs.next()) {
					new Insmanage();
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "������ ��ġ���� �ʽ��ϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public SouthPanel() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(sbtn[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String s = e.getActionCommand();
						switch(s) {
							case "Ȯ��":
								Enter();
								break;
							case "����":
								dispose();
								break;
						}
					}
				});
				add(btn[i]);
			}
		}
	}
	
	public Login() {
		// TODO Auto-generated constructor stub
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		ct.add(new NorthPanel(), BorderLayout.NORTH);
		ct.add(new CenterPanel(), BorderLayout.CENTER);
		ct.add(new SouthPanel(), BorderLayout.SOUTH);
		
		setSize(350, 200);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();
	}

}
