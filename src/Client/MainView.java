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

public class MainView extends JFrame { // ���� �� Ŭ����
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel();
	private JButton addBtn = new JButton("ģ�� �߰�");
	private JButton removeBtn = new JButton("ģ�� ����");
	private JButton msgBtn = new JButton("���� ������");
	private JButton chatBtn = new JButton("��ȭ �ϱ�");
	private JButton exitBtn = new JButton("����");
	private JLabel friendListLabel = new JLabel("�������� ģ�����");
	private JTable friendTable;
	private DefaultTableModel model;
	private JScrollPane js;
	private Font font = new Font("Serif", Font.BOLD, 30);
	private Font font2 = new Font("Times", Font.BOLD, 20);
	
	public MainView(String myId) {
		JPanel mainPanel = new JPanel(); // ��ü�� ���δ� �г�
		JPanel northPanel = new JPanel(); // ���� �г�
		northPanel.setLayout(new GridLayout(3, 1));
		titleLabel.setText(myId + "�� ȯ���մϴ�."); // �Է��� ���̵� �ؽ�Ʈ�� ����
		titleLabel.setFont(font); // ��Ʈ ����
		titleLabel.setHorizontalAlignment(JLabel.CENTER); // ��� ����
		northPanel.add(titleLabel); // Ÿ��Ʋ���̺��� ����
		JPanel btnPanel = new JPanel(); // 5���� ��ư�� ���δ� �г�
		btnPanel.setLayout(new GridLayout(1, 5));
		btnPanel.add(addBtn); // ģ���߰� ��ư
		btnPanel.add(removeBtn); // ģ������ ��ư
		btnPanel.add(msgBtn); // ���������� ��ư
		btnPanel.add(chatBtn); // ��ȭ�ϱ� ��ư
		btnPanel.add(exitBtn); // ���� ��ư
		northPanel.add(btnPanel); // ��ư�г��� ���
		friendListLabel.setFont(font2); // ��Ʈ ����
		friendListLabel.setHorizontalAlignment(JLabel.CENTER); // �������
		northPanel.add(friendListLabel); // ģ����Ϸ��̺��� �Ʒ���
		mainPanel.add(northPanel); // �����гο� �޾��ش�
		JPanel centerPanel = new JPanel(); // ��� �г�
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // �������� ���������� CENTER
		// �뱹 ��� ���̺� ����
		String[] col = { "���̵�", "����" }; // ù�࿡ ǥ�õ� �迭
		String[][] row = new String[0][2];

		model = new DefaultTableModel(row, col) { // ���̺� �� ����
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { // �� ���� �Ұ�
				return false;
			}
		};
		friendTable = new JTable(model); // ���̺� ����
		model = (DefaultTableModel) friendTable.getModel();
		js = new JScrollPane(friendTable); // ��ũ�� �޾��ֱ�
		friendTable.getTableHeader().setReorderingAllowed(false); // ���� ���̺� ������Ʈ ����
		friendTable.getTableHeader().setResizingAllowed(false); // ũ�� ���� �Ұ�
		TableColumnModel tcm = friendTable.getColumnModel(); // ������ ���̺��� �÷����� ������
		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		centerPanel.add(js); // ģ��������̺� ���
		mainPanel.add(centerPanel); // �����гο� �޾��ش�
		this.add(mainPanel); // ��ü�� ���δ� �г� �߰�
		this.setBounds(500, 200, 600, 650);
		this.setTitle("Main View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ģ����� ���̺��� �����ϴ� �޼ҵ�
	public JTable getFriendTabel() {
		return this.friendTable;
	}
	// ģ����� ���̺���� �����ϴ� �޼ҵ�
	public DefaultTableModel getFriendTabelModel() {
		return this.model;
	}
	// ģ���߰� ��ư �����ʸ� �߰��ϴ� �޼ҵ�
	public void addAddListener(ActionListener aal) {
		addBtn.addActionListener(aal);
	}
	// ģ������ ��ư �����ʸ� �߰��ϴ� �޼ҵ�
	public void addRemoveListener(ActionListener ral) {
		removeBtn.addActionListener(ral);
	}
	// ���������� ��ư �����ʸ� �߰��ϴ� �޼ҵ�
	public void addMsgListener(ActionListener mal) {
		msgBtn.addActionListener(mal);
	}
	// ��ȭ�ϱ� ��ư �����ʸ� �߰��ϴ� �޼ҵ�
	public void addChatListener(ActionListener cal) {
		chatBtn.addActionListener(cal);
	}
	// ���� ��ư �����ʸ� �߰��ϴ� �޼ҵ�
	public void addExitListener(ActionListener eal) {
		exitBtn.addActionListener(eal);
	}
}
