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

public class InputView extends JFrame { // 입력 뷰 클래스(친구 추가, 친구 삭제에 사용)
	private static final long serialVersionUID = 1L;
	private JLabel label =  new JLabel("아이디를 입력하세요.");
	private JTextField txtField = new JTextField();
	private JButton enter = new JButton("입력");
	private JButton cancel = new JButton("취소");
	private Font font = new Font("Times", Font.BOLD, 20);
	
	public InputView() {
		setLayout(new GridLayout(3, 1));
		label.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		label.setFont(font);
		add(label, BorderLayout.NORTH);
		add(txtField, BorderLayout.CENTER);
		JPanel btnPanel = new JPanel(); // 버튼 패널
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(enter);
		btnPanel.add(cancel);
		add(btnPanel,BorderLayout.SOUTH);
		setTitle("Input View");
		setBounds(300, 300, 300, 200);
		setVisible(true);
	}
	
	public String getText() { // 텍스트 필드값 가져오기
		return txtField.getText();
	}

	public void addEnterListener(ActionListener eal) {
		enter.addActionListener(eal);
	}
	
	public void addCancelListener(ActionListener cal) {
		cancel.addActionListener(cal);
	}
}
