package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Insmanage extends JFrame{

	private JButton btn[] = new JButton[4];
	private JLabel label = new JLabel();
	
	class NorthPanel extends JPanel{
		
		private String sbtn[] = {"�� ���", "�� ��ȸ", "��� ����", "����"};
		
		public NorthPanel() {
			// TODO Auto-generated constructor stub
			for(int i=0; i<btn.length; i++) {
				btn[i] = new JButton(sbtn[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String s = e.getActionCommand();
						switch(s) {
							case "�� ���":
								new Cusregist();
								break;
							case "�� ��ȸ":
								new Cuscheck();
								break;
							case "��� ����":
								new Contractman();
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
	
	class CenterPanel extends JPanel{
		ImageIcon icon = new ImageIcon("C:\\Users\\pc 1-12\\Desktop\\�����\\db_customer\\Customer\\images\\img.jpg");
		public CenterPanel() {
			// TODO Auto-generated constructor stub
			label = new JLabel(icon);
			label.setSize(icon.getIconWidth(), icon.getIconHeight());
			add(label);
		}
	}
	
	public Insmanage() {
		// TODO Auto-generated constructor stub
		setTitle("������ ����ȭ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = getContentPane();
		
		ct.add(new NorthPanel(), BorderLayout.NORTH);
		ct.add(new CenterPanel(), BorderLayout.CENTER);
		
		setSize(600, 500);
		setVisible(true);
	}
}
