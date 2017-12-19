package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MessageView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("���� ������");
	private JTextField textField = new JTextField();
	private JButton enterBtn = new JButton("�Է�");
	private JButton exitBtn = new JButton("����");
	private Font font = new Font("Serif", Font.BOLD, 30);
	private String selectId;
	
	public MessageView(String selectId) {
		this.selectId = selectId;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		titleLabel.setFont(font);
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // ��� ����
		mainPanel.add(titleLabel, BorderLayout.NORTH);  // ���̺��� ����
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(enterBtn);
		btnPanel.add(exitBtn);
		mainPanel.add(textField, BorderLayout.CENTER); // �ؽ�Ʈ�ʵ�� �߾�
		mainPanel.add(btnPanel, BorderLayout.SOUTH); // ��ư�г��� ����
		this.add(mainPanel); // �����г� ��ġ
		this.setBounds(500, 300, 300, 200);
		this.setTitle("Message View");
	}

	public String getSelectId() {
		return selectId;
	}
	
	public String getMsg() {
		return textField.getText();
	}
	
	public void addEnterListener(ActionListener lal) {
		enterBtn.addActionListener(lal);
	}

	public void addExitListener(ActionListener ral) {
		exitBtn.addActionListener(ral);
	}
}
