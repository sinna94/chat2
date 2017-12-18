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

public class LoginView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("보디보디");
	private JLabel idLabel = new JLabel("ID");
	private JLabel pwdLabel = new JLabel("Password");
	private JTextField idField = new JTextField();
	private JTextField pwdField = new JTextField();
	private JButton loginBtn = new JButton("로그인");
	private JButton registerBtn = new JButton("회원가입");
	private Font font = new Font("Serif", Font.BOLD, 30);
	
	public LoginView() {
		JPanel title = new JPanel();
		titleLabel.setFont(font);
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		title.add(titleLabel);
		this.add(title, BorderLayout.NORTH);
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(3, 2));
		content.add(idLabel);
		content.add(idField);
		content.add(pwdLabel);
		content.add(pwdField);
		content.add(loginBtn);
		content.add(registerBtn);
		this.setBounds(500, 300, 300, 200);
		this.add(content, BorderLayout.CENTER);
		this.setTitle("Login View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void reset() {
		idField.setText("");
		pwdField.setText("");
	}

	public String getId() {
		return idField.getText();
	}

	public String getPwd() {
		return pwdField.getText();
	}
	
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	public void addLoginListener(ActionListener lal) {
		loginBtn.addActionListener(lal);
	}

	public void addRegisterListener(ActionListener ral) {
		registerBtn.addActionListener(ral);
	}
}
