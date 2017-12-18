package Client;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InputView extends JFrame { // 입력 뷰 클래스(친구 추가, 친구 삭제에 사용)
	private static final long serialVersionUID = 1L;
	private JLabel label =  new JLabel("아이디를 입력하세요.");
	private JTextField txtField = new JTextField();
	private JButton enter = new JButton();
	private JButton cancel = new JButton();
	private String code; // 친구추가인지 삭제인지 구분을하기 위한 변수
	
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
	
	public String getText() { // 텍스트 필드값 가져오기
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
