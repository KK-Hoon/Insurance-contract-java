package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.ConnectDB;

public class Cuschange extends JFrame{
	
	private JLabel label[] = new JLabel[6];
	private JTextField jt[] = new JTextField[6];
	private JButton btn[] = new JButton[2];
	private Connection con = ConnectDB.makeConnection("customer");
	private PreparedStatement psmt = null;
	Vector<String> v = new Vector<String>();
	Vector<String> ch = new Vector<String>();
	
	class CenterPanel extends JPanel{
		private String s[] = {"�� �ڵ�", "* �� ��:", "*�������(YYYY-MM-DD):", "*�� �� ó:", "�� ��", "ȸ �� ��:"};
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			
			setLayout(new GridLayout(6, 2));
			for(int i=0; i<label.length; i++) {
				label[i] = new JLabel(s[i]);
				add(label[i]);
				jt[i] = new JTextField(20);
				jt[i].setText(v.get(i));
				if(i == 0 || i == 1) {
					jt[i].setEnabled(false);
				}
				add(jt[i]);
			}
			
		}
	}
	
	class SouthPanel extends JPanel{
		
		private String s[] = {"����", "���"};
		
		public SouthPanel() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(s[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String s = e.getActionCommand();
						switch(s) {
						case "����":
							Change();
							break;
						case "���":
							dispose();
							break;
						}
					}
				});
				add(btn[i]);
			}
		}
	}
	public void Change() {
		ch.clear();
		for(int i=2; i<jt.length; i++) {
			ch.add(jt[i].getText());
		}
		
		String name = jt[1].getText();
		System.out.println(name);
		try {
			psmt = con.prepareStatement("update customer set birth = ?, tel = ?, address = ?, company = ? where name = ?");
			for(int i=0; i<ch.size(); i++) {
				psmt.setString(i+1, ch.get(i));
			}
			psmt.setString(5, name);
			
			
			int re = psmt.executeUpdate();
			
			if(re == 1) {
				JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "�޼���", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "���� ����.", "�޼���", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(Exception e) {
			
		}
		
	}
	
	public Cuschange(Vector<String> v) {
		// TODO Auto-generated constructor stub
		
		if(v.get(0).equals(null)) {
			v.clear();
		}
		
		this.v = v;
		setTitle("�� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		ct.add(new CenterPanel(), BorderLayout.CENTER);
		ct.add(new SouthPanel(), BorderLayout.SOUTH);
		
		setSize(550, 380);
		setVisible(true);
	}

}
