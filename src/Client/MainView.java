package Client;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class MainView extends JFrame { // 메인 뷰 클래스
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel();
	private JButton addBtn = new JButton("친구 추가");
	private JButton removeBtn = new JButton("친구 삭제");
	private JButton msgBtn = new JButton("쪽지 보내기");
	private JButton chatBtn = new JButton("대화 하기");
	private JButton exitBtn = new JButton("종료");
	private JLabel friendListLabel = new JLabel("접속중인 친구목록");
	private JTable friendTable;
	private DefaultTableModel model;
	private JScrollPane js;
	private Font font = new Font("Serif", Font.BOLD, 30);
	private Font font2 = new Font("Times", Font.BOLD, 20);
	
	public MainView(String myId) {
		JPanel mainPanel = new JPanel(); // 전체를 감싸는 패널
		JPanel northPanel = new JPanel(); // 위쪽 패널
		northPanel.setLayout(new GridLayout(3, 1));
		titleLabel.setText(myId + "님 환영합니다."); // 입력한 아이디를 텍스트에 삽입
		titleLabel.setFont(font); // 폰트 적용
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		northPanel.add(titleLabel); // 타이틀레이블은 위쪽
		JPanel btnPanel = new JPanel(); // 5개의 버튼을 감싸는 패널
		btnPanel.setLayout(new GridLayout(1, 5));
		btnPanel.add(addBtn); // 친구추가 버튼
		btnPanel.add(removeBtn); // 친구삭제 버튼
		btnPanel.add(msgBtn); // 쪽지보내기 버튼
		btnPanel.add(chatBtn); // 대화하기 버튼
		btnPanel.add(exitBtn); // 종료 버튼
		northPanel.add(btnPanel); // 버튼패널은 가운데
		friendListLabel.setFont(font2); // 폰트 적용
		friendListLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데정렬
		northPanel.add(friendListLabel); // 친구목록레이블은 아래쪽
		mainPanel.add(northPanel); // 메인패널에 달아준다
		JPanel centerPanel = new JPanel(); // 가운데 패널
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER
		// 대국 목록 테이블 정의
		String[] col = { "아이디", "상태" }; // 첫행에 표시될 배열
		String[][] row = new String[0][2];

		model = new DefaultTableModel(row, col) { // 테이블 모델 생성
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { // 셀 수정 불가
				return false;
			}
		};
		friendTable = new JTable(model); // 테이블 생성
		model = (DefaultTableModel) friendTable.getModel();
		js = new JScrollPane(friendTable); // 스크롤 달아주기
		friendTable.getTableHeader().setReorderingAllowed(false); // 방목록 테이블 컴포넌트 고정
		friendTable.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
		TableColumnModel tcm = friendTable.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴
		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		centerPanel.add(js); // 친구목록테이블 가운데
		mainPanel.add(centerPanel); // 메인패널에 달아준다
		this.add(mainPanel); // 전체를 감싸는 패널 추가
		this.setBounds(500, 200, 600, 650);
		this.setTitle("Main View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// 친구목록 테이블을 리턴하는 메소드
	public JTable getFriendTabel() {
		return this.friendTable;
	}
	// 친구목록 테이블모델을 리턴하는 메소드
	public DefaultTableModel getFriendTabelModel() {
		return this.model;
	}
	// 친구추가 버튼 리스너를 추가하는 메소드
	public void addAddListener(ActionListener aal) {
		addBtn.addActionListener(aal);
	}
	// 친구삭제 버튼 리스너를 추가하는 메소드
	public void addRemoveListener(ActionListener ral) {
		removeBtn.addActionListener(ral);
	}
	// 쪽지보내기 버튼 리스너를 추가하는 메소드
	public void addMsgListener(ActionListener mal) {
		msgBtn.addActionListener(mal);
	}
	// 대화하기 버튼 리스너를 추가하는 메소드
	public void addChatListener(ActionListener cal) {
		chatBtn.addActionListener(cal);
	}
	// 종료 버튼 리스너를 추가하는 메소드
	public void addExitListener(ActionListener eal) {
		exitBtn.addActionListener(eal);
	}
}
