package Client;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InputView extends JFrame { // �Է� �� Ŭ����(ģ�� �߰�, ģ�� ������ ���)
	private static final long serialVersionUID = 1L;
	private JLabel label =  new JLabel("���̵� �Է��ϼ���.");
	private JTextField txtField = new JTextField();
	private JButton enter = new JButton();
	private JButton cancel = new JButton();
	private String code; // ģ���߰����� �������� �������ϱ� ���� ����
	
	public InputView(String code) {
		this.code = code;
		add(label);
		add(txtField);
		add(enter);
		add(cancel);
		setTitle("Input View");
		setBounds(300, 300, 200, 100);
		setVisible(true);
	}
	
	public String getText() { // �ؽ�Ʈ �ʵ尪 ��������
		return txtField.getText();
	}
	
	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	public void addEnterListener(ActionListener eal) {
		enter.addActionListener(eal);
	}
	
	public void addCancelListener(ActionListener cal) {
		cancel.addActionListener(cal);
	}
}
