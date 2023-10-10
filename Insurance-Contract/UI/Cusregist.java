package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.ConnectDB;

public class Cusregist extends JFrame{
	
	private JLabel label[] = new JLabel[6];
	private JTextField jt[] = new JTextField[6];
	private JButton btn[] = new JButton[2];
	private Connection con = ConnectDB.makeConnection("customer");
	private PreparedStatement psmt = null;
	
	class CenterPanel extends JPanel{
		
		private String s[] = {"고객 코드", "* 고객 명:", "*생년월일(YYYY-MM-DD):", "*연 락 처:", "주 소", "회 사:"};
		
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(6, 2));
			
			for(int i=0; i<label.length; i++) {
				label[i] = new JLabel(s[i]);
				add(label[i]);
				jt[i] = new JTextField(20);
				if(i == 0) {
					jt[i].setEnabled(false);
				}
				add(jt[i]);
			}
			jt[2].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR) - 2000;
					String str[] = jt[2].getText().split("-");
					int hap = Integer.valueOf(str[0]) + Integer.valueOf(str[1]) + Integer.valueOf(str[2]);
					jt[0].setText("S" + year + hap);
				}
			});
		}
	}
	
	class SouthPanel extends JPanel{
		
		private String s[] = {"추가", "닫기"};
		
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
						case "추가":
							Add();
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
	public void Add(){
		String s[] = new String[6];
		
		for(int i=0; i<s.length; i++) {
			s[i] = jt[i].getText();
		}
		if(s[1].equals("") || s[2].equals("") || s[3].equals("")) {
			JOptionPane.showMessageDialog(null, "필수 항목(*)을 모두 입력하세요", "고객 등록 에러", JOptionPane.ERROR_MESSAGE);;
		}
		else {
			try {
				psmt = con.prepareStatement("insert into customer values(?, ?, ?, ?, ?, ?)");
				for(int i=0; i<s.length; i++) {
					psmt.setString(i+1, s[i]);
				}
				int re = psmt.executeUpdate();
				if(re == 1) {
					JOptionPane.showMessageDialog(null, "고객추가가 완료되었습니다.", "메세지", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "고객추가 실패.", "메세지", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception e) {
				
			}
		}
	}
	public Cusregist() {
		// TODO Auto-generated constructor stub
		setTitle("고객 등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		ct.add(new CenterPanel(), BorderLayout.CENTER);
		ct.add(new SouthPanel(), BorderLayout.SOUTH);
		
		setSize(550, 380);
		setVisible(true);
	}
}
