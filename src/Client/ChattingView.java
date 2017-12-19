package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ChattingView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("대화 하기");
	private JTextArea textArea = new JTextArea(10, 30);
	private JScrollPane scroll = new JScrollPane(textArea);
	private JTable friendTable;
	private DefaultTableModel model;
	private JScrollPane js;
	private JButton enterBtn = new JButton("입력");
	private JButton exitBtn = new JButton("종료");
	private JTextField textField = new JTextField();
	private Font font = new Font("Serif", Font.BOLD, 30);
	
	public ChattingView() {
		JPanel mainPanel = new JPanel(); // 전체를 감싸는 패널
		mainPanel.setLayout(new BorderLayout());
		JPanel title = new JPanel();
		titleLabel.setFont(font);
		title.add(titleLabel);
		mainPanel.add(title, BorderLayout.NORTH);
		mainPanel.add(scroll, BorderLayout.WEST);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER
		// 대국 목록 테이블 정의
		String[] col = {"아이디"}; // 첫행에 표시될 배열
		String[][] row = new String[0][1];

		model = new DefaultTableModel(row, col) { // 테이블 모델 생성
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { // 셀 수정 불가
				return false;
			}
		};
		friendTable = new JTable(model); // 테이블 생성
		model = (DefaultTableModel) friendTable.getModel();
		js = new JScrollPane(friendTable); // 스크롤 달아주기
		TableColumnModel tcm = friendTable.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴
		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		friendTable.getColumnModel().getColumn(0).setPreferredWidth(5);
		mainPanel.add(js); // 오른쪽
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 1));
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(enterBtn);
		btnPanel.add(exitBtn);
		southPanel.add(textField); // 텍스트필드 추가
		southPanel.add(btnPanel); // 아래패널에 버튼패널 추가
		mainPanel.add(southPanel, BorderLayout.SOUTH); // 메인패널의 아래에 배치
		this.add(mainPanel); // 전체를 감싸는 패널 추가
		this.setBounds(500, 200, 500, 400);
		this.setTitle("Chatting View");
	}

	public void addEnterListener(ActionListener aal) {
		enterBtn.addActionListener(aal);
	}

	public void addExitListener(ActionListener ral) {
		exitBtn.addActionListener(ral);
	}
}

