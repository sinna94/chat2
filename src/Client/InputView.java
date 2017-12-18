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

public class InputView extends JFrame { // �Է� �� Ŭ����(ģ�� �߰�, ģ�� ������ ���)
	private static final long serialVersionUID = 1L;
	private JLabel label =  new JLabel("���̵� �Է��ϼ���.");
	private JTextField txtField = new JTextField();
	private JButton enter = new JButton("�Է�");
	private JButton cancel = new JButton("���");
	private Font font = new Font("Times", Font.BOLD, 20);
	
	public InputView() {
		setLayout(new GridLayout(3, 1));
		label.setHorizontalAlignment(JLabel.CENTER); // ��� ����
		label.setFont(font);
		add(label, BorderLayout.NORTH);
		add(txtField, BorderLayout.CENTER);
		JPanel btnPanel = new JPanel(); // ��ư �г�
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(enter);
		btnPanel.add(cancel);
		add(btnPanel,BorderLayout.SOUTH);
		setTitle("Input View");
		setBounds(300, 300, 300, 200);
		setVisible(true);
	}
	
	public String getText() { // �ؽ�Ʈ �ʵ尪 ��������
		return txtField.getText();
	}

	public void addEnterListener(ActionListener eal) {
		enter.addActionListener(eal);
	}
	
	public void addCancelListener(ActionListener cal) {
		cancel.addActionListener(cal);
	}
}
