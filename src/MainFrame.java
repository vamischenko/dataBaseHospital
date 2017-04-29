import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainFrame {

	private JFrame frame;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_5;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private DefaultTableModel model;
	private TextArea textArea;
	private TextArea textArea_dialog_view;

	private JTextField host_ip_textField;
	private JTextField DBMS_port_textField;
	private JTextField DB_name_textField;
	private JTextField user_name_textField;
	private JTextField user_password_textField;
	private JButton connect_button;

	private Connection connection = null;
	private Statement statement;
	private JDialog dialog;
	private JDialog dialog_view;
	private JTabbedPane tabbedPane;
	private Panel panel_1;
	private Panel panel_2;
	private int selectedRow;

	private String host_ip = "127.0.0.1";
	private String DBMS_port = "5432";
	private String DB_name = "postgres";
	private String user_name = "postgres";
	private String user_password = "postgres";
	private String url = "jdbc:postgresql://" + host_ip + ":" + DBMS_port + "/" + DB_name;
	private JTextField textField_19;

	private String VERSION = "1.4";

	private static final DateFormat df = new SimpleDateFormat("yyyy/mm/dd");

	boolean writeTmpFiles;

	JFileChooser filechooser = new JFileChooser();
	JTree tree;
	DefaultTreeModel treeModel;
	DefaultMutableTreeNode rootNode;
	private JTextField textField;
	private JTextField textField_20;
	private JTextField textField_21;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Hospital");
		frame.setBounds(100, 100, 1038, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// dialog_view
		dialog_view = new JDialog(frame, "Просмотр");
		dialog_view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				frame.setEnabled(true);
			}
		});
		dialog_view.setResizable(false);
		Dimension dim_d = new Dimension(700, 600);
		Dimension dim_ta = new Dimension(700, 577);
		dialog_view.setPreferredSize(dim_d);
		dialog_view.pack();
		dialog_view.setLocationRelativeTo(frame);
		textArea_dialog_view = new TextArea();
		textArea_dialog_view.setEditable(false);
		textArea_dialog_view.setPreferredSize(dim_ta);
		dialog_view.getContentPane().add(textArea_dialog_view, BorderLayout.NORTH);
		dialog_view.setVisible(false);
		//

		// dialog
		dialog = new JDialog(frame, "Настройки БД");
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				frame.setEnabled(true);
			}
		});
		dialog.setResizable(false);
		dialog.setPreferredSize(new Dimension(350, 220));
		JLabel host_ip_label = new JLabel("IP хоста");
		JLabel DBMS_port_label = new JLabel("Порт СУБД");
		JLabel DB_name_label = new JLabel("Имя БД");
		JLabel user_name_label = new JLabel("Имя пользователя");
		JLabel user_password_label = new JLabel("Пароль пользователя");

		host_ip_label.setBounds(10, 20, 150, 20);
		DBMS_port_label.setBounds(10, 45, 150, 20);
		DB_name_label.setBounds(10, 70, 150, 20);
		user_name_label.setBounds(10, 95, 150, 20);
		user_password_label.setBounds(10, 120, 150, 20);

		dialog.getContentPane().add(host_ip_label);
		dialog.getContentPane().add(DBMS_port_label);
		dialog.getContentPane().add(DB_name_label);
		dialog.getContentPane().add(user_name_label);
		dialog.getContentPane().add(user_password_label);

		host_ip_textField = new JTextField(host_ip);
		DBMS_port_textField = new JTextField(DBMS_port);
		DB_name_textField = new JTextField(DB_name);
		user_name_textField = new JTextField(user_name);
		user_password_textField = new JTextField(user_password);

		host_ip_textField.setBounds(180, 20, 150, 20);
		DBMS_port_textField.setBounds(180, 45, 150, 20);
		DB_name_textField.setBounds(180, 70, 150, 20);
		user_name_textField.setBounds(180, 95, 150, 20);
		user_password_textField.setBounds(180, 120, 150, 20);

		dialog.getContentPane().add(host_ip_textField);
		dialog.getContentPane().add(DBMS_port_textField);
		dialog.getContentPane().add(DB_name_textField);
		dialog.getContentPane().add(user_name_textField);
		dialog.getContentPane().add(user_password_textField);

		JButton connect_button = new JButton("Подключиться");
		connect_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
			}
		});
		connect_button.setBounds(100, 150, 150, 30);
		dialog.getContentPane().add(connect_button);

		JLabel null_label = new JLabel("");
		dialog.getContentPane().add(null_label);
		JTextField null_textField = new JTextField();
		null_textField.setEnabled(false);
		dialog.getContentPane().add(null_textField);

		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(false);
		//

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 1012, 449);
		frame.getContentPane().add(tabbedPane);

		model = new DefaultTableModel();
		model.addColumn("Id пациента");
		model.addColumn("Контекст");
		model.addColumn("Имя");
		model.addColumn("Фамилия");
		model.addColumn("Отчество");
		model.addColumn("Возраст");
		model.addColumn("Пол");
		model.addColumn("Стаж");
		model.addColumn("ПАВ");
		model.addColumn("Срок ремиссии");
		model.addColumn("Лек. средства");
		model.addColumn("Инсульты");
		model.addColumn("ЧМТ");
		model.addColumn("Дата теста");

		panel_1 = new Panel();
		panel_1.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent arg0) {
				update();
			}
		});
		tabbedPane.addTab("\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440", null, panel_1, null);

		JButton button_3 = new JButton("\u041F\u043E\u0438\u0441\u043A");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		button_3.setBounds(10, 390, 199, 23);
		panel_1.setLayout(null);
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSize(770, 288);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 987, 288);
		panel_1.add(scrollPane);
		panel_1.add(button_3);

		JLabel label_17 = new JLabel("\u0424\u0430\u043C\u0438\u043B\u0438\u044F");
		label_17.setBounds(10, 310, 58, 15);
		panel_1.add(label_17);

		textField_12 = new JTextField();
		textField_12.setBounds(68, 307, 240, 20);
		textField_12.setColumns(10);
		panel_1.add(textField_12);

		JLabel label_18 = new JLabel("\u0418\u043C\u044F");
		label_18.setBounds(10, 336, 58, 15);
		panel_1.add(label_18);

		textField_13 = new JTextField();
		textField_13.setBounds(68, 333, 240, 20);
		textField_13.setColumns(10);
		panel_1.add(textField_13);

		JLabel label_19 = new JLabel("\u041E\u0442\u0447\u0435\u0441\u0442\u0432\u043E");
		label_19.setBounds(10, 362, 58, 15);
		panel_1.add(label_19);

		textField_14 = new JTextField();
		textField_14.setBounds(68, 359, 240, 20);
		textField_14.setColumns(10);
		panel_1.add(textField_14);

		JLabel label_20 = new JLabel("\u0412\u043E\u0437\u0440\u0430\u0441\u0442");
		label_20.setBounds(366, 311, 58, 15);
		panel_1.add(label_20);

		textField_15 = new JTextField();
		textField_15.setBounds(424, 308, 29, 20);
		textField_15.setColumns(10);
		panel_1.add(textField_15);

		comboBox_3 = new JComboBox();
		comboBox_3.setBounds(579, 307, 85, 22);
		comboBox_3
				.setModel(new DefaultComboBoxModel(new String[] { "", "\u043C\u0443\u0436.", "\u0436\u0435\u043D." }));
		comboBox_3.setSelectedIndex(0);
		panel_1.add(comboBox_3);

		JLabel label_21 = new JLabel("\u041F\u043E\u043B");
		label_21.setBounds(544, 310, 29, 15);
		panel_1.add(label_21);

		JLabel label_22 = new JLabel("\u0421\u0442\u0430\u0436");
		label_22.setBounds(366, 336, 58, 15);
		panel_1.add(label_22);

		textField_16 = new JTextField();
		textField_16.setBounds(424, 333, 74, 20);
		textField_16.setColumns(10);
		panel_1.add(textField_16);

		JLabel label_23 = new JLabel("\u041F\u0410\u0412");
		label_23.setBounds(366, 362, 58, 15);
		panel_1.add(label_23);

		textField_17 = new JTextField();
		textField_17.setBounds(424, 359, 240, 20);
		textField_17.setColumns(10);
		panel_1.add(textField_17);

		textField_18 = new JTextField();
		textField_18.setBounds(469, 308, 29, 20);
		textField_18.setColumns(10);
		panel_1.add(textField_18);

		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setBounds(459, 310, 14, 15);
		panel_1.add(lblNewLabel);

		JLabel label_24 = new JLabel("\u043F.\u043B.");
		label_24.setBounds(505, 310, 29, 15);
		panel_1.add(label_24);

		JButton btnNewButton_1 = new JButton(
				"\u0414\u0435\u0442\u0430\u043B\u0438\u0437\u0438\u0440\u043E\u0432\u0430\u0442\u044C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				detail();
			}
		});
		btnNewButton_1.setBounds(219, 390, 199, 23);
		panel_1.add(btnNewButton_1);

		textField_19 = new JTextField();
		textField_19.setColumns(10);
		textField_19.setBounds(515, 333, 74, 20);
		panel_1.add(textField_19);

		JLabel label_25 = new JLabel("-");
		label_25.setBounds(505, 336, 14, 15);
		panel_1.add(label_25);

		JButton button_2 = new JButton(
				"\u042D\u043A\u0441\u043F\u043E\u0440\u0442 \u0432 *.xls \u0444\u0430\u0439\u043B");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				export();
			}
		});
		button_2.setBounds(424, 390, 184, 23);
		panel_1.add(button_2);

		JLabel label_26 = new JLabel("\u0414\u0430\u0442\u0430 \u0442\u0435\u0441\u0442\u0430");
		label_26.setBounds(676, 310, 66, 19);
		panel_1.add(label_26);

		textField_20 = new JTextField();
		textField_20.setBounds(752, 310, 86, 20);
		panel_1.add(textField_20);
		textField_20.setColumns(10);

		JLabel label_27 = new JLabel("\u041A\u043E\u043D\u0442\u0435\u043A\u0441\u0442");
		label_27.setBounds(676, 336, 66, 14);
		panel_1.add(label_27);

		textField_21 = new JTextField();
		textField_21.setBounds(752, 333, 103, 20);
		panel_1.add(textField_21);
		textField_21.setColumns(10);

		panel_2 = new Panel();
		tabbedPane.addTab("\u0414\u0435\u0442\u0430\u043B\u0438", null, panel_2, null);
		tabbedPane.setBackgroundAt(1, SystemColor.inactiveCaptionBorder);
		panel_2.setLayout(null);

		JButton button = new JButton(
				"\u0418\u0437\u043C\u0435\u043D\u0438\u0442\u044C \u0442\u0435\u043A\u0443\u0449\u0443\u044E \u0437\u0430\u043F\u0438\u0441\u044C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				change();
			}
		});
		button.setBounds(8, 356, 300, 23);
		panel_2.add(button);

		JButton btnNewButton = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0442\u0435\u043A\u0443\u0449\u0443\u044E \u0437\u0430\u043F\u0438\u0441\u044C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		btnNewButton.setBounds(8, 390, 300, 23);
		panel_2.add(btnNewButton);

		JLabel label = new JLabel("\u041A\u043E\u043D\u0442\u0435\u043A\u0441\u0442");
		label.setBounds(10, 37, 58, 15);
		panel_2.add(label);

		JLabel label_1 = new JLabel("\u0424\u0430\u043C\u0438\u043B\u0438\u044F");
		label_1.setBounds(10, 63, 58, 15);
		panel_2.add(label_1);

		JLabel label_2 = new JLabel("\u0418\u043C\u044F");
		label_2.setBounds(10, 89, 58, 15);
		panel_2.add(label_2);

		JLabel label_3 = new JLabel("\u041E\u0442\u0447\u0435\u0441\u0442\u0432\u043E");
		label_3.setBounds(10, 115, 58, 15);
		panel_2.add(label_3);

		JLabel label_4 = new JLabel("\u0412\u043E\u0437\u0440\u0430\u0441\u0442");
		label_4.setBounds(10, 141, 58, 15);
		panel_2.add(label_4);

		JLabel label_5 = new JLabel("\u041F\u043E\u043B");
		label_5.setBounds(180, 141, 39, 15);
		panel_2.add(label_5);

		JLabel label_6 = new JLabel("\u0421\u0442\u0430\u0436");
		label_6.setBounds(10, 167, 58, 15);
		panel_2.add(label_6);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(68, 60, 240, 20);
		panel_2.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(68, 86, 240, 20);
		panel_2.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(68, 112, 240, 20);
		panel_2.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(68, 138, 85, 20);
		panel_2.add(textField_4);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(68, 164, 240, 20);
		panel_2.add(textField_6);

		JLabel label_7 = new JLabel("\u041F\u0410\u0412");
		label_7.setBounds(10, 196, 58, 15);
		panel_2.add(label_7);

		JLabel label_8 = new JLabel("\u2116");
		label_8.setBounds(10, 11, 58, 15);
		panel_2.add(label_8);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(68, 34, 240, 20);
		panel_2.add(textField_5);

		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setColumns(10);
		textField_7.setBounds(68, 8, 240, 20);
		panel_2.add(textField_7);

		JLabel label_9 = new JLabel("\u0421\u0440\u043E\u043A \u0440\u0435\u043C\u0438\u0441\u0441\u0438\u0438");
		label_9.setBounds(10, 223, 95, 15);
		panel_2.add(label_9);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(68, 193, 240, 20);
		panel_2.add(textField_8);

		textField_9 = new JTextField();
		textField_9.setText("0");
		textField_9.setColumns(10);
		textField_9.setBounds(100, 220, 39, 20);
		panel_2.add(textField_9);

		JButton button_1 = new JButton(
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043D\u043E\u0432\u0443\u044E \u0437\u0430\u043F\u0438\u0441\u044C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add();
			}
		});
		button_1.setBounds(8, 322, 300, 23);
		panel_2.add(button_1);

		JLabel label_10 = new JLabel("\u043B.");
		label_10.setBounds(145, 223, 22, 15);
		panel_2.add(label_10);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "\u043C\u0443\u0436.", "\u0436\u0435\u043D." }));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(223, 137, 85, 22);
		panel_2.add(comboBox);

		textField_10 = new JTextField();
		textField_10.setText("0");
		textField_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				check_remission_month();
			}
		});
		textField_10.setColumns(10);
		textField_10.setBounds(160, 220, 53, 20);
		panel_2.add(textField_10);

		JLabel label_11 = new JLabel("\u043C\u0435\u0441.");
		label_11.setBounds(215, 223, 30, 15);
		panel_2.add(label_11);

		textField_11 = new JTextField();
		textField_11.setText("1");
		textField_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				check_remission_days();
				check_remission_month();
			}
		});
		textField_11.setColumns(10);
		textField_11.setBounds(242, 220, 39, 20);
		panel_2.add(textField_11);

		JLabel label_12 = new JLabel("\u0434\u043D.");
		label_12.setBounds(288, 223, 22, 15);
		panel_2.add(label_12);

		JLabel label_13 = new JLabel(
				"\u041B\u0435\u043A\u0430\u0440\u0441\u0442\u0432\u0435\u043D\u043D\u044B\u0435 \u0441\u0440\u0435\u0434\u0441\u0442\u0432\u0430");
		label_13.setBounds(328, 11, 177, 15);
		panel_2.add(label_13);

		textArea = new TextArea();
		textArea.setBounds(328, 32, 669, 124);
		panel_2.add(textArea);

		JLabel label_14 = new JLabel("\u0418\u043D\u0441\u0443\u043B\u044C\u0442\u044B");
		label_14.setBounds(10, 255, 66, 15);
		panel_2.add(label_14);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "", "\u0434\u0430", "\u043D\u0435\u0442" }));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(78, 251, 85, 22);
		panel_2.add(comboBox_1);

		JLabel label_15 = new JLabel("\u0427\u041C\u0422");
		label_15.setBounds(190, 255, 29, 15);
		panel_2.add(label_15);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "", "\u0434\u0430", "\u043D\u0435\u0442" }));
		comboBox_2.setSelectedIndex(0);
		comboBox_2.setBounds(223, 251, 85, 22);
		panel_2.add(comboBox_2);

		JButton btnAddFile = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0444\u0430\u0439\u043B");
		btnAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add_file();
			}
		});
		btnAddFile.setBounds(813, 356, 184, 23);
		panel_2.add(btnAddFile);

		JButton btnDeleteFile = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0444\u0430\u0439\u043B");
		btnDeleteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rm_file();
			}
		});
		btnDeleteFile.setBounds(813, 390, 184, 23);
		panel_2.add(btnDeleteFile);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(328, 167, 475, 246);
		panel_2.add(scrollPane_1);

		tree = new JTree();
		rootNode = new DefaultMutableTreeNode("Результаты тестов");
		treeModel = new DefaultTreeModel(rootNode);
		tree.setModel(treeModel);

		tree.setLocation(534, 0);
		tree.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane_1.setViewportView(tree);

		JButton button_4 = new JButton("\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view_file();
			}
		});
		button_4.setBounds(813, 322, 184, 23);
		panel_2.add(button_4);
		JLabel label_16 = new JLabel("\u0414\u0430\u0442\u0430 \u0442\u0435\u0441\u0442\u0430");
		label_16.setBounds(8, 281, 68, 23);
		panel_2.add(label_16);

		textField = new JTextField();
		textField.setBounds(88, 284, 220, 20);
		panel_2.add(textField);
		textField.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("\u0424\u0430\u0439\u043B");
		menuBar.add(mnFile);

		JMenuItem mntmOptions = new JMenuItem("\u041D\u0430\u0441\u0442\u0440\u043E\u0439\u043A\u0438 \u0411\u0414");
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setEnabled(false);
				dialog.setVisible(true);
			}
		});
		mnFile.add(mntmOptions);

		JMenuItem mntmExit = new JMenuItem("\u0412\u044B\u0445\u043E\u0434");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		final JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(
				"\u0412\u0440\u0435\u043C\u0435\u043D\u043D\u044B\u0435 \u0444\u0430\u0439\u043B\u044B");
		writeTmpFiles = checkBoxMenuItem.getState();
		checkBoxMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeTmpFiles = checkBoxMenuItem.getState();
			}
		});
		mnFile.add(checkBoxMenuItem);
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("\u041F\u043E\u043C\u043E\u0449\u044C");
		menuBar.add(mnHelp);

		JMenuItem mntmHelp = new JMenuItem("\u0421\u043F\u0440\u0430\u0432\u043A\u0430");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String about = "Для подключения:\n1. Файл -> Настройки БД -> Ввести необходимые данные -> Подключиться\n"
						+ "2. На вкладке Просмотр отобразится содержимое базы данных\n"
						+ "3. На вкладке Детали отобразится детальная информация выбранной записи БД";
				JOptionPane.showMessageDialog(frame, about);
			}
		});
		mnHelp.add(mntmHelp);

		JMenuItem mntmAbout = new JMenuItem("\u041E \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0435");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String about = "Проблемно-ориентированная база данных\nВерсия " + VERSION + "\n2015 год";
				JOptionPane.showMessageDialog(frame, about);
			}
		});
		mnHelp.add(mntmAbout);
	}

	public static void recursiveDelete(File file) {
		if (!file.exists())
			return;
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				recursiveDelete(f);
			}
		}
		file.delete();
		System.out.println("Deleted file/folder: " + file.getAbsolutePath());
	}

	public void update() {
		if (connection == null) {
			JOptionPane.showMessageDialog(frame, "Подключение к БД отсутствует !");
			return;
		}

		ResultSet rs;
		ResultSetMetaData meta;
		int numberOfColumns;

		String selectStat = "SELECT * FROM patients";

		String selectStatAdd = "";

		// name
		if (textField_12.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("surname='%s'", textField_12.getText().toString().trim());
		}
		// surname
		if (textField_13.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("name='%s'", textField_13.getText().toString().trim());
		}
		// patronymic
		if (textField_14.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("patronymic='%s'", textField_14.getText().toString().trim());
		}
		// pav
		if (textField_17.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("pav='%s'", textField_17.getText().toString().trim());
		}
		// gender
		if (comboBox_3.getSelectedIndex() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("gender='%s'", comboBox_3.getSelectedItem().toString().trim());
		}
		// experience from ...
		if (textField_16.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("experience>%s", Double.valueOf(textField_16.getText().toString().trim()));
		}
		// experience to ...
		if (textField_19.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("experience<%s", Double.valueOf(textField_19.getText().toString().trim()));
		}
		// age from ...
		if (textField_15.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("age>%s", Integer.valueOf(textField_15.getText().toString().trim()));
		}
		// age to ...
		if (textField_18.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("age<%s", Integer.valueOf(textField_18.getText().toString().trim()));
		}
		// data
		if (textField_20.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("vremya=%s", Integer.valueOf(textField_20.getText().toString().trim()));
		}
		// context
		if (textField_21.getText().length() != 0) {
			selectStatAdd += (selectStatAdd != "") ? " AND " : "";
			selectStatAdd += String.format("context=%s", Integer.valueOf(textField_21.getText().toString().trim()));
		}
		// checking for empty
		if (selectStatAdd != "") {
			selectStat += " WHERE ";
			selectStat += selectStatAdd;
		}

		// JOptionPane.showMessageDialog(frame, selectStat);

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(selectStat);
			meta = rs.getMetaData();
			numberOfColumns = meta.getColumnCount();
			model.setRowCount(0);
			while (rs.next()) {
				Object[] rowData = new Object[numberOfColumns];
				for (int i = 0; i < rowData.length; ++i) {
					rowData[i] = rs.getObject(i + 1);
				}
				model.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void detail() {
		selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(frame, "Выделите строку !");
			return;
		}

		tabbedPane.setSelectedComponent(panel_2);
		textField_7.setText(model.getValueAt(selectedRow, 0).toString().trim()); // id
		textField_5.setText(model.getValueAt(selectedRow, 1).toString().trim()); // context
		textField_2.setText(model.getValueAt(selectedRow, 2).toString().trim()); // name
		textField_1.setText(model.getValueAt(selectedRow, 3).toString().trim()); // surname
		textField_3.setText(model.getValueAt(selectedRow, 4).toString().trim()); // patronymic
		textField_4.setText(model.getValueAt(selectedRow, 5).toString().trim()); // age

		// gender
		String gender = model.getValueAt(selectedRow, 6).toString().trim();
		for (int i = 1; i < comboBox.getItemCount(); i++) {
			if (comboBox.getItemAt(i).equals(gender)) {
				comboBox.setSelectedIndex(i);
				break;
			}
		}

		textField_6.setText(model.getValueAt(selectedRow, 7).toString().trim()); // experience
		textField_8.setText(model.getValueAt(selectedRow, 8).toString().trim()); // pav

		// remission_period
		String[] remission_period = model.getValueAt(selectedRow, 9).toString().trim().split("\\.");
		textField_9.setText(remission_period[0]);
		textField_10.setText(remission_period[1]);
		textField_11.setText(remission_period[2]);

		textArea.setText(model.getValueAt(selectedRow, 10).toString().trim()); // medicaments

		// insult
		String insult = model.getValueAt(selectedRow, 11).toString().trim();
		for (int i = 1; i < comboBox_1.getItemCount(); i++) {
			if (comboBox_1.getItemAt(i).equals(insult)) {
				comboBox_1.setSelectedIndex(i);
				break;
			}
		}

		// tbi
		String tbi = model.getValueAt(selectedRow, 12).toString().trim();
		for (int i = 1; i < comboBox_2.getItemCount(); i++) {
			if (comboBox_2.getItemAt(i).equals(tbi)) {
				comboBox_2.setSelectedIndex(i);
				break;
			}
		}
		// String vremya = model.getValueAt(selectedRow, 13).toString().trim();
		// formattedTextField

		// clear files list in Tree
		rootNode.removeAllChildren();
		treeModel.setRoot(rootNode);

		// load files list to Tree
		String selectFileStat = String.format("SELECT filename FROM tests WHERE patient_id=%s", textField_7.getText());
		ResultSet rs;

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(selectFileStat);
			while (rs.next()) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode((String) rs.getObject(1));
				rootNode.add(node);
			}
			treeModel.setRoot(rootNode);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка получения списка тестов !");
			return;
		}
	}

	public void export() {
		// to xls
		selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(frame, "Выделите строку !");
			return;
		}

		String splitChar = "\t";
		String id = model.getValueAt(selectedRow, 0).toString().trim();

		File dir = new File("tmp");
		// recursiveDelete(dir);
		dir.mkdir();

		// load files list to Tree
		try {
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook();
			Sheet mainSheet = workbook.createSheet("Основная информация");

			for (int i = 0; i < model.getColumnCount(); i++) {
				Row msRow = mainSheet.createRow(i);
				Cell hdrCell = msRow.createCell(0);
				hdrCell.setCellValue(model.getColumnName(i));
				Cell mainCell = msRow.createCell(1);
				mainCell.setCellValue(model.getValueAt(selectedRow, i).toString().trim());
			}

			String selectFileStat = String.format("SELECT filename,filecontent FROM tests WHERE patient_id=%s", id);

			ResultSet rs;
			statement = connection.createStatement();
			rs = statement.executeQuery(selectFileStat);

			while (rs.next()) {
				String filename = rs.getObject(1).toString();
				filename = filename.substring(filename.lastIndexOf("\\") + 1, filename.lastIndexOf("."));
				byte[] decodedBuffer = Base64.getMimeDecoder().decode(rs.getObject(2).toString());
				String filecontent = new String(decodedBuffer, "UTF-8");

				if (writeTmpFiles) {
					File file = new File(String.format("tmp\\~%s.dat", filename));
					DataOutputStream dos;
					dos = new DataOutputStream(new FileOutputStream(file));
					dos.write(decodedBuffer);
					dos.close();
				}

				Sheet additionalSheet = workbook.createSheet(filename);
				String[] lines = filecontent.split("\n");
				for (int i = 0; i < lines.length; i++) {
					Row asRow = additionalSheet.createRow(i);
					String[] words = lines[i].split(splitChar);
					for (int j = 0; j < words.length; j++) {
						Cell asCell = asRow.createCell(j);
						asCell.setCellValue(words[j]);
					}
				}
			}

			FileOutputStream out = new FileOutputStream(String.format("tmp\\%s.xlsx", id));
			workbook.write(out);
			out.close();

		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка получения списка тестов !");
			return;
		}
	}

	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			if (connection != null) {
				connection.close();
			}

			url = "jdbc:postgresql://" + host_ip_textField.getText() + ":" + DBMS_port_textField.getText() + "/"
					+ DB_name_textField.getText();
			user_name = user_name_textField.getText();
			user_password = user_password_textField.getText();

			connection = DriverManager.getConnection(url, user_name, user_password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка подключения !");
			return;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка подключения !");
			return;
		}

		JOptionPane.showMessageDialog(frame, "Успешно подключено !");
		dialog.setVisible(false);
		frame.setEnabled(true);
		update();
	}

	public void add() {
		if (connection == null) {
			JOptionPane.showMessageDialog(frame, "Подключение к БД отсутствует !");
			return;
		}

		if (textField_5.getText().length() == 0 || textField_1.getText().length() == 0
				|| textField_2.getText().length() == 0 || textField_3.getText().length() == 0
				|| textField_4.getText().length() == 0 || comboBox.getSelectedItem().toString().length() == 0) {
			JOptionPane.showMessageDialog(frame, "Заполните основные данные !");
			return;
		}

		String insertStat = String.format(
				"INSERT INTO patients (context,name,surname,patronymic,age,gender,experience,pav,remission_period,insult,tbi,medicaments,vremya) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s.%s.%s','%s','%s','%s','%s','%s') ",
				textField_5.getText(), textField_2.getText(), textField_1.getText(), textField_3.getText(),
				textField_4.getText(), comboBox.getSelectedItem().toString(), textField_6.getText(),
				textField_8.getText(), textField_9.getText(), textField_10.getText(), textField_11.getText(),
				comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString(), textArea.getText(),
				textField.getText(), textField_7.getText());

		try {
			statement = connection.createStatement();
			statement.executeUpdate(insertStat);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка выполнения запроса !");
			return;
		}

		String selectStat = String.format("SELECT MAX(id) FROM patients");
		ResultSet rs;
		int maxID = -1;

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(selectStat);
			rs.next();
			maxID = (int) rs.getObject(1);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка получения максимального индекса !");
			return;
		}

		int childCount = rootNode.getChildCount();
		if (childCount == 0) {
			JOptionPane.showMessageDialog(frame, "Тесты не добавлены !");
			return;
		}

		for (int i = 0; i < childCount; i++) {
			String filePath = rootNode.getChildAt(i).toString();
			File file = new File(filePath);
			byte[] fileData = new byte[(int) file.length()];
			try {
				DataInputStream dis;
				dis = new DataInputStream(new FileInputStream(file));
				dis.readFully(fileData);
				dis.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String encodedBuffer = Base64.getMimeEncoder().encodeToString(fileData);
			String insertFileStat = String.format(
					"INSERT INTO tests (patient_id,filename,filecontent) VALUES ('%s','%s', '%s')", maxID, filePath,
					encodedBuffer);

			try {
				statement = connection.createStatement();
				statement.executeUpdate(insertFileStat);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(frame, "Ошибка выполнения запроса !");
				e.printStackTrace();
				return;
			}
		}
	}

	public void change() {
		if (connection == null) {
			JOptionPane.showMessageDialog(frame, "Подключение к БД отсутствует !");
			return;
		}

		String changeStat = String.format(
				"UPDATE patients SET context='%s',name='%s',surname='%s',patronymic='%s',age='%s',gender='%s',experience=%s,pav='%s',remission_period='%s.%s.%s',insult='%s',tbi='%s',medicaments='%s',vremya='%s'  WHERE id=%s",
				textField_5.getText(), textField_2.getText(), textField_1.getText(), textField_3.getText(),
				textField_4.getText(), comboBox.getSelectedItem().toString(), textField_6.getText(),
				textField_8.getText(), textField_9.getText(), textField_10.getText(), textField_11.getText(),
				comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString(), textArea.getText(),
				textField.getText(), textField_7.getText());

		try {
			statement = connection.createStatement();
			statement.executeUpdate(changeStat);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка выполнения запроса !");
		}

		int k = 0;
		ResultSet rs;
		int size = -1;
		int idArray[];
		ResultSetMetaData meta;
		String fileNameArray[];
		String selectFileStat = String.format("SELECT id,filename FROM tests WHERE patient_id=%s",
				textField_7.getText());

		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(selectFileStat);

			rs.last();
			size = rs.getRow();
			rs.beforeFirst();

			idArray = new int[size];
			fileNameArray = new String[size];

			while (rs.next()) {
				idArray[k] = (int) rs.getObject(1);
				fileNameArray[k] = (String) rs.getObject(2);
				k++;
			}

			int rootNodeChilds = rootNode.getChildCount();

			// 1st loop for DELETE sql request
			for (int bd = 0; bd < size; bd++) {
				//
				if (rootNodeChilds == 0) {
					String selectDeleteStat = String.format("DELETE FROM tests WHERE id=%s", idArray[bd]);
					statement = connection.createStatement();
					statement.executeUpdate(selectDeleteStat);
				}
				//
				for (int tr = 0; tr < rootNodeChilds; tr++) {
					String rootNodeChild = rootNode.getChildAt(tr).toString();
					if (fileNameArray[bd].compareTo(rootNodeChild) == 0) {
						break;
					}
					if (tr == rootNodeChilds - 1) {
						String selectDeleteStat = String.format("DELETE FROM tests WHERE id=%s", idArray[bd]);
						statement = connection.createStatement();
						statement.executeUpdate(selectDeleteStat);
					}
				}
				//
			}

			// 2st loop for INSERT sql request
			for (int tr = 0; tr < rootNodeChilds; tr++) {
				//
				String rootNodeChild = rootNode.getChildAt(tr).toString();
				//
				if (size == 0) {
					File file = new File(rootNodeChild);
					byte[] fileData = new byte[(int) file.length()];
					try {
						DataInputStream dis;
						dis = new DataInputStream(new FileInputStream(file));
						dis.readFully(fileData);
						dis.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					String encodedBuffer = Base64.getMimeEncoder().encodeToString(fileData);
					String insertFileStat = String.format(
							"INSERT INTO tests (patient_id,filename,filecontent) VALUES ('%s','%s', '%s')",
							textField_7.getText(), rootNodeChild, encodedBuffer);

					try {
						statement = connection.createStatement();
						statement.executeUpdate(insertFileStat);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(frame, "Ошибка выполнения запроса !");
						e.printStackTrace();
						return;
					}
				}
				//
				for (int bd = 0; bd < size; bd++) {
					if (rootNodeChild.compareTo(fileNameArray[bd]) == 0) {
						break;
					}
					if (bd == size - 1) {
						File file = new File(rootNodeChild);
						byte[] fileData = new byte[(int) file.length()];
						try {
							DataInputStream dis;
							dis = new DataInputStream(new FileInputStream(file));
							dis.readFully(fileData);
							dis.close();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

						String encodedBuffer = Base64.getMimeEncoder().encodeToString(fileData);
						String insertFileStat = String.format(
								"INSERT INTO tests (patient_id,filename,filecontent) VALUES ('%s','%s', '%s')",
								textField_7.getText(), rootNodeChild, encodedBuffer);

						try {
							statement = connection.createStatement();
							statement.executeUpdate(insertFileStat);
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(frame, "Ошибка выполнения запроса !");
							e.printStackTrace();
							return;
						}
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		if (connection == null) {
			JOptionPane.showMessageDialog(frame, "Подключение к БД отсутствует !");
			return;
		}

		String deleteFileStat = "DELETE FROM tests WHERE patient_id=" + textField_7.getText();
		String deleteStat = "DELETE FROM patients WHERE id=" + textField_7.getText();

		try {
			statement = connection.createStatement();
			statement.executeUpdate(deleteFileStat);
			statement.executeUpdate(deleteStat);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Ошибка выполнения запроса !");
			e.printStackTrace();
		}
	}

	public void view_file() {
		DefaultMutableTreeNode selectedTreeItem = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedTreeItem != null) {
			if (!selectedTreeItem.isRoot()) {
				String id = model.getValueAt(selectedRow, 0).toString().trim();
				String selectFileStat = String.format(
						"SELECT filename,filecontent FROM tests WHERE patient_id=%s AND filename='%s'", id,
						selectedTreeItem.toString());
				ResultSet rs;
				try {
					statement = connection.createStatement();
					rs = statement.executeQuery(selectFileStat);
					rs.next();
					// String filename = rs.getObject(1).toString();
					// filename =
					// filename.substring(filename.lastIndexOf("\\")+1,
					// filename.lastIndexOf("."));
					byte[] decodedBuffer = Base64.getMimeDecoder().decode(rs.getObject(2).toString());
					String filecontent = new String(decodedBuffer, "UTF-8");
					frame.setEnabled(false);
					dialog_view.setVisible(true);
					textArea_dialog_view.setText(filecontent);
				} catch (SQLException | UnsupportedEncodingException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Ошибка просмотра файла !");
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Это корень !");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Необходимо выделить файл !");
		}
	}

	public void add_file() {
		int ret = filechooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			String FullFilePath = file.getPath();
			DefaultMutableTreeNode currentNode = new DefaultMutableTreeNode(FullFilePath);
			treeModel.insertNodeInto(currentNode, rootNode, rootNode.getChildCount());
		}
		tree.expandRow(0);
	}

	public void rm_file() {
		DefaultMutableTreeNode selectedTreeItem = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedTreeItem != null) {
			if (!selectedTreeItem.isRoot()) {
				treeModel.removeNodeFromParent(selectedTreeItem);
			} else {
				JOptionPane.showMessageDialog(frame, "Нельзя удалять корень !");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Необходимо выделить файл !");
		}
	}

	public void check_remission_days() {
		int days = Integer.parseInt(textField_11.getText());
		if (days > 30) {
			int months = days / 30;
			days = days % 30;
			int months_prev = Integer.valueOf(textField_10.getText().trim());
			textField_10.setText(String.valueOf(months + months_prev));
			textField_11.setText(String.valueOf(days));
		}
	}

	public void check_remission_month() {
		int months = Integer.parseInt(textField_10.getText());
		if (months >= 12) {
			int years = months / 12;
			months = months % 12;
			int years_prev = Integer.valueOf(textField_9.getText().trim());
			textField_9.setText(String.valueOf(years + years_prev));
			textField_10.setText(String.valueOf(months));
		}
	}
}
