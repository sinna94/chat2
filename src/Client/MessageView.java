package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MessageView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("쪽지 보내기");
	private JTextField textField = new JTextField();
	private JButton enterBtn = new JButton("입력");
	private JButton exitBtn = new JButton("종료");
	private Font font = new Font("Serif", Font.BOLD, 30);
	
	public MessageView() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		titleLabel.setFont(font);
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		mainPanel.add(titleLabel, BorderLayout.NORTH);  // 레이블은 북쪽
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(enterBtn);
		btnPanel.add(exitBtn);
		mainPanel.add(textField, BorderLayout.CENTER); // 텍스트필드는 중앙
		mainPanel.add(btnPanel, BorderLayout.SOUTH); // 버튼패널은 남쪽
		this.add(mainPanel); // 메인패널 배치
		this.setBounds(500, 300, 300, 200);
		this.setTitle("Login View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	public void addEnterListener(ActionListener lal) {
		enterBtn.addActionListener(lal);
	}

	public void addExitListener(ActionListener ral) {
		exitBtn.addActionListener(ral);
	}
}
