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
	private JLabel titleLabel = new JLabel("��ȭ �ϱ�");
	private JTextArea textArea = new JTextArea(10, 30);
	private JScrollPane scroll = new JScrollPane(textArea);
	private JTable friendTable;
	private DefaultTableModel model;
	private JScrollPane js;
	private JButton enterBtn = new JButton("�Է�");
	private JButton exitBtn = new JButton("����");
	private JTextField textField = new JTextField();
	private Font font = new Font("Serif", Font.BOLD, 30);
	
	public ChattingView() {
		JPanel mainPanel = new JPanel(); // ��ü�� ���δ� �г�
		mainPanel.setLayout(new BorderLayout());
		JPanel title = new JPanel();
		titleLabel.setFont(font);
		title.add(titleLabel);
		mainPanel.add(title, BorderLayout.NORTH);
		mainPanel.add(scroll, BorderLayout.WEST);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); // �������� ���������� CENTER
		// �뱹 ��� ���̺� ����
		String[] col = {"���̵�"}; // ù�࿡ ǥ�õ� �迭
		String[][] row = new String[0][1];

		model = new DefaultTableModel(row, col) { // ���̺� �� ����
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { // �� ���� �Ұ�
				return false;
			}
		};
		friendTable = new JTable(model); // ���̺� ����
		model = (DefaultTableModel) friendTable.getModel();
		js = new JScrollPane(friendTable); // ��ũ�� �޾��ֱ�
		TableColumnModel tcm = friendTable.getColumnModel(); // ������ ���̺��� �÷����� ������
		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		friendTable.getColumnModel().getColumn(0).setPreferredWidth(5);
		mainPanel.add(js); // ������
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 1));
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(enterBtn);
		btnPanel.add(exitBtn);
		southPanel.add(textField); // �ؽ�Ʈ�ʵ� �߰�
		southPanel.add(btnPanel); // �Ʒ��гο� ��ư�г� �߰�
		mainPanel.add(southPanel, BorderLayout.SOUTH); // �����г��� �Ʒ��� ��ġ
		this.add(mainPanel); // ��ü�� ���δ� �г� �߰�
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

