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

public class RegisterView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("회원 가입");
	private JLabel idLabel = new JLabel("아이디");
	private JLabel pwdLabel = new JLabel("비밀번호");
	private JLabel nameLabel = new JLabel("이름");
	private JLabel phoneLabel = new JLabel("전화번호");
	private JLabel addrLabel = new JLabel("주소");
	private JTextField idField = new JTextField();
	private JTextField pwdField = new JTextField();
	private JTextField nameField = new JTextField();
	private JTextField phoneField = new JTextField();
	private JTextField addrField = new JTextField();
	private JButton enterBtn = new JButton("확인");
	private JButton cancelBtn = new JButton("취소");
	private Font font = new Font("Serif", Font.BOLD, 40);
	
	public RegisterView() {
		JPanel title = new JPanel();
		titleLabel.setFont(font);
		title.add(titleLabel);
		this.add(title, BorderLayout.NORTH);
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(0, 2));
		content.add(idLabel);
		content.add(idField);
		content.add(pwdLabel);
		content.add(pwdField);
		content.add(nameLabel);
		content.add(nameField);
		content.add(phoneLabel);
		content.add(phoneField);
		content.add(addrLabel);
		content.add(addrField);
		content.add(enterBtn);
		content.add(cancelBtn);
		this.setBounds(500, 300, 300, 300);
		this.add(content, BorderLayout.CENTER);
		this.setTitle("Register View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void reset() {
		idField.setText("");
		pwdField.setText("");
		nameField.setText("");
		phoneField.setText("");
		addrField.setText("");
	}

	public String getId() {
		return idField.getText();
	}

	public String getPwd() {
		return pwdField.getText();
	}
	
	public String getName() {
		return nameField.getText();
	}

	public String getPhone() {
		return phoneField.getText();
	}
	
	public String getAddr() {
		return addrField.getText();
	}
	
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	public void addEnterListener(ActionListener eal) {
		enterBtn.addActionListener(eal);
	}

	public void addCancelListener(ActionListener cal) {
		cancelBtn.addActionListener(cal);
	}
}
