package GUI.Admin;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import GUI.InitGUI;
import GUI.Admin.FrmStudent.AccountRenderer;
import GUI.Admin.FrmStudent.FacultyRenderer;
import GUI.Admin.FrmStudent.Gender;
import Model.Account;
import Model.Course_Class;
import Model.Faculty;
import Model.Student;
import Model.Transcript;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

public class FrmTranscript extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection conn = null;

	private String FONT_TYPE;
	private int FONT;
	private int FONT_SIZE;
	private int COMPONENTS_HEIGHT;
	private int BUTTON_HEIGHT;
	private int BUTTON_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCREEN_WIDTH;

	private static JComboBox cbbCCID;
	private static DefaultComboBoxModel cbbCCIDModel;
	private static JComboBox cbbSID;
	private static DefaultComboBoxModel cbbSIDModel;
	private static JTextField txtScore;
	private static ArrayList<Transcript> lisTranscript = new ArrayList<Transcript>();
	private static String[] columnName = { "Lớp Học", "Sinh Viên", "Điểm" };
	private static DefaultTableModel model = new DefaultTableModel(columnName, 0);
	private static JTable tabTranscript = new JTable(model);
	private static JButton btnCancel = new JButton("Hủy");
	private static JButton btnSave = new JButton("Lưu");
	private static JButton btnDelete = new JButton("Xóa");
	private static JButton btnFilterofCCID = new JButton("Lọc theo lớp học");
	private static JButton btnFilterofSID = new JButton("Lọc theo sinh viên");
	private static JButton btnFilterofBoth = new JButton("Lọc theo lớp học và sinh viên");
	private static JButton btnCreate = new JButton("Thêm");
	private static JButton btnEdit = new JButton("Sửa");
	private static int flag = 0;
	private final JLabel lblQLBD = new JLabel("QUẢN LÝ BẢNG ĐIỂM");
	private final JLabel lblTTBD = new JLabel("Thông Tin Bảng Điểm");

	public void Init() {
		InitGUI init = new InitGUI();
		this.FONT_TYPE = init.getFONT_TYPE();
		this.FONT = init.getFONT();
		this.FONT_SIZE = init.getFONT_SIZE();
		this.COMPONENTS_HEIGHT = init.getCOMPONENTS_HEIGHT();
		this.BUTTON_HEIGHT = init.getBUTTON_HEIGHT();
		this.BUTTON_WIDTH = init.getBUTTON_WIDTH();
		this.SCREEN_WIDTH = init.getSCREEN_WIDTH();
		this.SCREEN_HEIGHT = init.getSCREEN_HEIGHT();
	}

	public FrmTranscript(Connection conn) {
		setBorder(null);
		((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
		FrmTranscript.conn = conn;
		Init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1178, 713);
		contentPane = new JPanel();
		contentPane.setForeground(SystemColor.text);
		contentPane.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabTranscript.setFont(new Font("Arial", Font.PLAIN, 14));
		tabTranscript.setBounds(10, 168, 870, 305);
		tabTranscript.setRowHeight(30);
		tabTranscript.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabTranscript.getSelectedRow();
				if (row != -1) {
					String ccid = tabTranscript.getValueAt(row, 0).toString();
					int index = -1;
					for (int i = 0; i < cbbCCIDModel.getSize(); i++) {
						Course_Class css = (Course_Class) cbbCCIDModel.getElementAt(i);
						if (css.getCcid().equals(ccid)) {
							index = i;
							break;
						}
					}
					cbbCCID.setSelectedIndex(index);
					String sid = tabTranscript.getValueAt(row, 1).toString();
					index = -1;
					for (int i = 0; i < cbbSIDModel.getSize(); i++) {
						Student sd = (Student) cbbSIDModel.getElementAt(i);
						if (sd.getId().equals(sid)) {
							index = i;
							break;
						}
					}
					cbbSID.setSelectedIndex(index);

					txtScore.setText(tabTranscript.getValueAt(row, 2).toString());
				}
			}

		});
		JScrollPane scrollPane = new JScrollPane(tabTranscript);
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(2, 50, 819, 635);
		contentPane.add(scrollPane);

		JLabel lblCCID = new JLabel("Tên Lớp :");
		lblCCID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblCCID.setBounds(827, 44, 69, 38);
		contentPane.add(lblCCID);

		JLabel lblSID = new JLabel("MSSV :");
		lblSID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblSID.setBounds(827, 96, 86, 38);
		contentPane.add(lblSID);

		JLabel lblScore = new JLabel("Điểm :");
		lblScore.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		lblScore.setBounds(827, 144, 69, 38);
		contentPane.add(lblScore);

		ArrayList<Course_Class> course_Classs = new ArrayList<Course_Class>();
		try {
			course_Classs = Course_Class.load(conn);
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		cbbCCIDModel = new DefaultComboBoxModel();
		for (Course_Class css : course_Classs) {
			cbbCCIDModel.addElement(css);
		}

		cbbCCID = new JComboBox();
		cbbCCID.setBackground(Color.WHITE);
		cbbCCID.setBorder(null);
		cbbCCID.setModel(cbbCCIDModel);
		cbbCCID.setFont(new Font("Arial", Font.PLAIN, 14));
		cbbCCID.setBounds(904, 52, 266, 27);
		cbbCCID.setRenderer(new CCIDRenderer());
		contentPane.add(cbbCCID);

		ArrayList<Student> students = new ArrayList<Student>();
		try {
			students = Student.load(conn);
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		cbbSIDModel = new DefaultComboBoxModel();
		for (Student sd : students) {
			cbbSIDModel.addElement(sd);
		}

		cbbSID = new JComboBox();
		cbbSID.setBackground(Color.WHITE);
		cbbSID.setBorder(null);
		cbbSID.setModel(cbbSIDModel);
		cbbSID.setFont(new Font("Arial", Font.PLAIN, 14));
		cbbSID.setBounds(904, 101, 266, 27);
		cbbSID.setRenderer(new SIDRenderer());
		contentPane.add(cbbSID);

		txtScore = new JTextField();
		txtScore.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtScore.setFont(new Font("Arial", Font.PLAIN, 14));
		txtScore.setBounds(904, 150, 266, 27);
		contentPane.add(txtScore);

		JLabel lblFilter = new JLabel("Lọc :");
		lblFilter.setHorizontalAlignment(SwingConstants.LEFT);
		lblFilter.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblFilter.setBounds(827, 193, 53, 38);
		btnFilterofCCID.setFocusPainted(false);
		btnFilterofCCID.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnFilterofCCID.setBackground(Color.WHITE);
		contentPane.add(lblFilter);

		btnFilterofCCID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setEnabled(true);
				FilterofCCID();
			}
		});
		btnFilterofCCID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFilterofSID.setFocusPainted(false);
		btnFilterofSID.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnFilterofSID.setBackground(Color.WHITE);
		btnFilterofCCID.setBounds(904, 197, 267, 30);
		contentPane.add(btnFilterofCCID);

		btnFilterofSID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setEnabled(true);
				FilterofSID();
			}
		});
		btnFilterofSID.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFilterofSID.setBounds(904, 292, 267, 30);
		contentPane.add(btnFilterofSID);
		btnFilterofBoth.setFocusPainted(false);
		btnFilterofBoth.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnFilterofBoth.setBackground(Color.WHITE);

		btnFilterofBoth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setEnabled(true);
				FilterofBoth();
			}
		});
		btnFilterofBoth.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnFilterofBoth.setBounds(904, 244, 267, 30);
		contentPane.add(btnFilterofBoth);

		btnCancel.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setFocusPainted(false);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFilterofSID.setEnabled(true);
				btnFilterofCCID.setEnabled(true);
				btnFilterofBoth.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
				load();
			}
		});
		btnCancel.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCancel.setBounds(1019, 563, BUTTON_WIDTH, BUTTON_HEIGHT);
		// btnCancel.setIcon(new ImageIcon("resources/cancel.png"));
		ImageIcon iconcancel = new ImageIcon(FrmManHinhChinh.class.getResource("/res/iconclose.png"));
		Image iccancel = iconcancel.getImage();
		Image newiconcancel = iccancel.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconcancel = new ImageIcon(newiconcancel);
		btnCancel.setIcon(iconcancel);
		contentPane.add(btnCancel);

		btnSave.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnSave.setBackground(Color.WHITE);
		btnSave.setFocusPainted(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (flag == 0)
					Add();
				else if (flag == 1)
					Edit();

				btnFilterofSID.setEnabled(true);
				btnFilterofCCID.setEnabled(true);
				btnFilterofBoth.setEnabled(true);
				btnCreate.setEnabled(true);
				btnEdit.setEnabled(true);
				btnCancel.setEnabled(false);
				btnSave.setEnabled(false);
				btnDelete.setEnabled(true);
			}
		});
		btnSave.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnSave.setBounds(1019, 498, BUTTON_WIDTH, BUTTON_HEIGHT);
		// btnSave.setIcon(new ImageIcon("resources/save.jpg"));
		ImageIcon iconsave = new ImageIcon(FrmManHinhChinh.class.getResource("/res/iconsave.png"));
		Image icsave = iconsave.getImage();
		Image newiconsave = icsave.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconsave = new ImageIcon(newiconsave);
		btnSave.setIcon(iconsave);
		contentPane.add(btnSave);

		btnDelete.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setFocusPainted(false);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Del();
			}
		});
		btnDelete.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnDelete.setBounds(1019, 625, BUTTON_WIDTH, BUTTON_HEIGHT);
		// btnDelete.setIcon(new ImageIcon("resources/delete.png"));
		ImageIcon icondelete = new ImageIcon(FrmManHinhChinh.class.getResource("/res/icondelete.png"));
		Image icdelete = icondelete.getImage();
		Image newicondelete = icdelete.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		icondelete = new ImageIcon(newicondelete);
		btnDelete.setIcon(icondelete);
		contentPane.add(btnDelete);

		btnEdit.setBackground(Color.WHITE);
		btnEdit.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnEdit.setFocusPainted(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = 1;
				btnFilterofSID.setEnabled(false);
				btnFilterofCCID.setEnabled(false);
				btnFilterofBoth.setEnabled(false);
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(false);
				btnCancel.setEnabled(true);
				btnSave.setEnabled(true);
				btnCreate.setEnabled(false);

				cbbSID.setEnabled(false);
				cbbCCID.setEnabled(false);
			}
		});
		btnEdit.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnEdit.setBounds(856, 563, BUTTON_WIDTH, BUTTON_HEIGHT);
		// btnEdit.setIcon(new ImageIcon("resources/edit.png"));
		ImageIcon iconedit = new ImageIcon(FrmManHinhChinh.class.getResource("/res/iconfix.png"));
		Image icedit = iconedit.getImage();
		Image newiconedit = icedit.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconedit = new ImageIcon(newiconedit);
		btnEdit.setIcon(iconedit);
		contentPane.add(btnEdit);

		btnCreate.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCreate.setBackground(Color.WHITE);
		btnCreate.setFocusPainted(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = 0;
				btnFilterofSID.setEnabled(false);
				btnFilterofCCID.setEnabled(false);
				btnFilterofBoth.setEnabled(false);
				btnEdit.setEnabled(false);
				btnDelete.setEnabled(false);
				btnCancel.setEnabled(true);
				btnSave.setEnabled(true);
				btnCreate.setEnabled(false);
				clear();
			}
		});
		btnCreate.setFont(new Font(FONT_TYPE, FONT, FONT_SIZE));
		btnCreate.setBounds(857, 498, BUTTON_WIDTH, BUTTON_HEIGHT);
		// btnCreate.setIcon(new ImageIcon("resources/create.png"));
		ImageIcon iconcreate = new ImageIcon(FrmManHinhChinh.class.getResource("/res/iconadd.png"));
		Image iccreate = iconcreate.getImage();
		Image newiconcreate = iccreate.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconcreate = new ImageIcon(newiconcreate);
		btnCreate.setIcon(iconcreate);
		contentPane.add(btnCreate);

		lblQLBD.setHorizontalAlignment(SwingConstants.CENTER);
		lblQLBD.setFont(new Font("Arial", Font.BOLD, 28));
		lblQLBD.setBounds(0, 0, 819, 47);

		contentPane.add(lblQLBD);
		lblTTBD.setHorizontalAlignment(SwingConstants.CENTER);
		lblTTBD.setFont(new Font("Arial", Font.BOLD, 18));
		lblTTBD.setBounds(819, 0, 359, 47);

		contentPane.add(lblTTBD);

		load();
	}

	public static void load() {
		cbbCCID.setEnabled(true);
		cbbSID.setEnabled(true);
		ArrayList<Transcript> lisTranscript = new ArrayList<Transcript>();
		try {
			lisTranscript = Transcript.load(conn);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel model = (DefaultTableModel) tabTranscript.getModel();
		if (model.getRowCount() > 0) {
			model.setRowCount(0);
		}
		Object[] rows = new Object[4];
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
		for (int i = 0; i < lisTranscript.size(); i++) {
			rows[0] = (lisTranscript.get(i).getCcid());
			rows[1] = (lisTranscript.get(i).getSid());
			float score = lisTranscript.get(i).getScore();
			if (score == -1) {
				rows[2] = "Chưa có điểm";
			} else {
				rows[2] = score;
			}

			model.addRow(rows);
		}
	}

	public static void Add() {
		Transcript ts = new Transcript();
		String ccid = ((Course_Class) cbbCCID.getSelectedItem()).getCcid();
		String sid = ((Student) cbbSID.getSelectedItem()).getId();
		String score_org = txtScore.getText().trim();
		float score;
		try {
			if (score_org.equals("") || score_org.equalsIgnoreCase("chưa có điểm")) {
				score = -1;
			} else {
				score = Float.valueOf(score_org);
			}
			ts.setCcid(ccid);
			ts.setSid(sid);
			ts.setScore(score);
			try {
				if (Transcript.Insert(ts, conn) == 1) {
					JOptionPane.showMessageDialog(tabTranscript, "Thêm Thành Công", "Thông Báo",
							JOptionPane.INFORMATION_MESSAGE);
//					lisTranscript.add(ts);
//					load();
				} else
					JOptionPane.showMessageDialog(tabTranscript, "Thêm thất bại", "Thông Báo",
							JOptionPane.INFORMATION_MESSAGE);

			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			load();
		} catch (Exception ex) {
			ex.printStackTrace();
			score = -1;
		}
	}

	public static void Edit() {
		Transcript ts = new Transcript();
		String ccid = ((Course_Class) cbbCCID.getSelectedItem()).getCcid();
		String sid = ((Student) cbbSID.getSelectedItem()).getId();
		String score_org = txtScore.getText().trim();
		float score;
		try {
			if (score_org.equals("") || score_org.equalsIgnoreCase("chưa có điểm")) {
				score = -1;
			} else {
				score = Float.valueOf(score_org);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			score = -1;
		}
		ts.setCcid(ccid);
		ts.setSid(sid);
		ts.setScore(score);
		try {
			if (Transcript.Edit(ts, conn) == 1) {
				JOptionPane.showMessageDialog(tabTranscript, "Sửa Thành Công", "Thông Báo",
						JOptionPane.INFORMATION_MESSAGE);
				for (int i = 0; i < lisTranscript.size(); i++) {
					if (lisTranscript.get(i).getCcid().equals(ccid) && lisTranscript.get(i).getSid().equals(sid)) {
						lisTranscript.get(i).setScore(score);
						break;
					}

				}
				DefaultTableModel model = (DefaultTableModel) tabTranscript.getModel();
				model.setRowCount(0);
				load();
			} else
				JOptionPane.showMessageDialog(tabTranscript, "Sửa thất bại", "Thông Báo",
						JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	public static void Del() {
		String ccid = ((Course_Class) cbbCCID.getSelectedItem()).getCcid();
		String sid = ((Student) cbbSID.getSelectedItem()).getId();
		try {
			if (Transcript.Del(ccid, sid, conn) == 1) {
				JOptionPane.showMessageDialog(tabTranscript, "Xoa thanh cong!", "Thong Bao",
						JOptionPane.INFORMATION_MESSAGE);
				load();
			} else
				JOptionPane.showMessageDialog(tabTranscript, "Xay ra loi", "Thong Bao",
						JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void FilterofCCID() {
		ArrayList<Transcript> lisTranscript = new ArrayList<Transcript>();
		String ccid = ((Course_Class) cbbCCID.getSelectedItem()).getCcid();
		try {
			lisTranscript = Transcript.findTranscriptoOfCCID(ccid, conn);
			DefaultTableModel model = (DefaultTableModel) tabTranscript.getModel();
			model.setRowCount(0);
			Object[] rows = new Object[4];
			for (int i = 0; i < lisTranscript.size(); i++) {
				rows[0] = (lisTranscript.get(i).getCcid());
				rows[1] = (lisTranscript.get(i).getSid());
				float score = lisTranscript.get(i).getScore();
				if (score == -1) {
					rows[2] = "Chưa có điểm";
				} else {
					rows[2] = score;
				}

				model.addRow(rows);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void FilterofSID() {
		ArrayList<Transcript> lisTranscript = new ArrayList<Transcript>();
		String sid = ((Student) cbbSID.getSelectedItem()).getId();
		try {
			lisTranscript = Transcript.findTranscriptOfSID(sid, conn);
			DefaultTableModel model = (DefaultTableModel) tabTranscript.getModel();
			model.setRowCount(0);
			Object[] rows = new Object[4];
			for (int i = 0; i < lisTranscript.size(); i++) {
				rows[0] = (lisTranscript.get(i).getCcid());
				rows[1] = (lisTranscript.get(i).getSid());
				float score = lisTranscript.get(i).getScore();
				if (score == -1) {
					rows[2] = "Chưa có điểm";
				} else {
					rows[2] = score;
				}

				model.addRow(rows);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void FilterofBoth() {
		Transcript transcript = new Transcript();
		String ccid = ((Course_Class) cbbCCID.getSelectedItem()).getCcid();
		String sid = ((Student) cbbSID.getSelectedItem()).getId();
		try {
			transcript = Transcript.findTranscript(ccid, sid, conn);
			if (transcript != null) {
				DefaultTableModel model = (DefaultTableModel) tabTranscript.getModel();
				model.setRowCount(0);
				Object[] rows = new Object[4];
				rows[0] = transcript.getCcid();
				rows[1] = transcript.getSid();
				float score = transcript.getScore();
				if (score == -1) {
					rows[2] = "Chưa có điểm";
				} else {
					rows[2] = score;
				}

				model.addRow(rows);
			} else {
				JOptionPane.showConfirmDialog(tabTranscript, "Không Tìm Thấy!!", "Thông Báo", JOptionPane.OK_OPTION);

			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void clear() {
		cbbCCID.setSelectedIndex(-1);
		cbbSID.setSelectedIndex(-1);
		txtScore.setText("");
	}

	class CCIDRenderer extends BasicComboBoxRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof Course_Class) {
				Course_Class val = (Course_Class) value;
				String name = "";
				try {
					name = Course_Class.getNameCourseClass(val, conn);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setText(name);
			}
			return this;
		}
	}

	class SIDRenderer extends BasicComboBoxRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof Student) {
				Student val = (Student) value;
				setText(val.getName());
			}
			return this;
		}
	}
}
