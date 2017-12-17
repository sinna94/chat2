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

public class ChattingView {
	private static final long serialVersionUID = 1L;
	private JLabel titleLabel = new JLabel("ȯ���մϴ�.");
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
	
	public ChattingView() {
		JPanel mainPanel = new JPanel(); // ��ü�� ���δ� �г�
		JPanel northPanel = new JPanel(); // ���� �г�
		northPanel.setLayout(new GridLayout(3, 1));
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
		String[] col = { "���̵�", "�̸�", "����" }; // ù�࿡ ǥ�õ� �迭
		String[][] row = new String[0][3];

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

	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	public void addAddListener(ActionListener aal) {
		addBtn.addActionListener(aal);
	}

	public void addRemoveListener(ActionListener ral) {
		removeBtn.addActionListener(ral);
	}
	
	public void addMsgListener(ActionListener ral) {
		msgBtn.addActionListener(ral);
	}
	
	public void addChatListener(ActionListener ral) {
		chatBtn.addActionListener(ral);
	}
	
	public void addExitListener(ActionListener ral) {
		exitBtn.addActionListener(ral);
	}
}

