import java.awt.Image;
import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.awt.Cursor;
import java.awt.Desktop;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PrpertyManagementSystem {
	
	JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField address;
	private JTextField owner_id;
	private JTextField owner_no;
	private JTextField rooms;
	private JTextField p_type;
	private JTextField app_id;
	private JTextField client_id;
	private JTextField date;
	private JTextField time;
	private JTextField p_id;
	private JTable propertytable;
	private JTable table;

	
	public static void main(String[] args) {
		Statement mystatObj = null;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrpertyManagementSystem window = new PrpertyManagementSystem();
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
	public PrpertyManagementSystem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	Connection connection = null;
	private JTextField clientid;
	private JTextField pid;
	private JTextField datE;
	private JTextField comment;
	private JTable table_1;
	private JTextField tenantno;
	private JTextField Prent;
	private JTextField ownerid;
	private JTextField tenantName;
	private JTextField endDate;
	private JTextField tenantid;
	private JTextField startDate;
	private JTable tenanttable;
	private JTextField propertyID;
	
	private void initialize() {
		
		connection = DBconnection.getConnection();
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setSize(1428, 783);
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(365, -26, 1063, 809);
		frame.getContentPane().add(tabbedPane);
		
		JPanel propertiespanel = new JPanel();
		propertiespanel.setBackground(new Color(0, 139, 139));
		propertiespanel.setForeground(Color.WHITE);
		tabbedPane.addTab("New tab", null, propertiespanel, null);
		
		// ===== FIXED BUTTONS WITH PROPER STYLING =====
		JButton btnNewButton = new JButton("LOAD");
		btnNewButton.setBounds(52, 684, 89, 30);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(new Color(204, 255, 204));
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setOpaque(true);
		btnNewButton.setContentAreaFilled(true);
		btnNewButton.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String load = "select * from property;";
					PreparedStatement pst = DBconnection.getConnection().prepareStatement(load);
					ResultSet rs1 = pst.executeQuery();
					propertytable.setModel(DbUtils.resultSetToTableModel(rs1));
					
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		propertiespanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1, 126, 1060, 274);
		scrollPane_1.setFocusTraversalKeysEnabled(false);
		scrollPane_1.setBorder(null);
		scrollPane_1.setFocusable(false);
		scrollPane_1.setForeground(new Color(102, 204, 102));
		scrollPane_1.setFont(new Font("Segoe UI", Font.PLAIN, 19));
		scrollPane_1.setBackground(new Color(0, 0, 128));
		propertiespanel.add(scrollPane_1);
		
		propertytable = new JTable();
		scrollPane_1.setViewportView(propertytable);
		propertiespanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("ADD");
		btnNewButton_1.setBounds(180, 684, 89, 30);
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(new Color(204, 255, 204));
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setOpaque(true);
		btnNewButton_1.setContentAreaFilled(true);
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveToDatabase();
			}
		});
		propertiespanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("UPDATE");
		btnNewButton_2.setBounds(327, 684, 89, 30);
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setBackground(new Color(204, 255, 204));
		btnNewButton_2.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setOpaque(true);
		btnNewButton_2.setContentAreaFilled(true);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			UpdateDatabase();
			}
		});
		propertiespanel.add(btnNewButton_2);
		
		JButton btnNewButton_1_1 = new JButton("DELETE");
		btnNewButton_1_1.setBounds(469, 684, 89, 30);
		btnNewButton_1_1.setForeground(Color.BLACK);
		btnNewButton_1_1.setBackground(new Color(204, 255, 204));
		btnNewButton_1_1.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1_1.setOpaque(true);
		btnNewButton_1_1.setContentAreaFilled(true);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteDatabase();
			}
		});
		propertiespanel.add(btnNewButton_1_1);
		
		// Rest of your existing code for text fields and labels...
		address = new JTextField();
		address.setBounds(42, 601, 231, 30);
		propertiespanel.add(address);
		address.setColumns(10);
		
		owner_id = new JTextField();
		owner_id.setBounds(321, 452, 231, 30);
		owner_id.setColumns(10);
		propertiespanel.add(owner_id);
		
		owner_no = new JTextField();
		owner_no.setBounds(42, 525, 231, 30);
		owner_no.setColumns(10);
		propertiespanel.add(owner_no);
		
		rooms = new JTextField();
		rooms.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			SaveToDatabase();
		}
			}
		});
		rooms.setBounds(321, 601, 231, 30);
		rooms.setColumns(10);
		propertiespanel.add(rooms);
		
		p_type = new JTextField();
		p_type.setBounds(321, 525, 231, 30);
		p_type.setColumns(10);
		propertiespanel.add(p_type);
		
		JLabel lblNewLabel_5 = new JLabel("Address");
		lblNewLabel_5.setBounds(48, 581, 75, 14);
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		propertiespanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Owner ID");
		lblNewLabel_5_1.setBounds(325, 427, 75, 14);
		lblNewLabel_5_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		propertiespanel.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_5_3 = new JLabel("Owner No");
		lblNewLabel_5_3.setBounds(44, 502, 75, 14);
		lblNewLabel_5_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_5_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		propertiespanel.add(lblNewLabel_5_3);
		
		JLabel lblNewLabel_5_4 = new JLabel("Rooms");
		lblNewLabel_5_4.setBounds(323, 581, 75, 14);
		lblNewLabel_5_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_5_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		propertiespanel.add(lblNewLabel_5_4);
		
		JLabel lblNewLabel_5_5 = new JLabel("Property Type");
		lblNewLabel_5_5.setBounds(321, 502, 91, 14);
		lblNewLabel_5_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		propertiespanel.add(lblNewLabel_5_5);
		
		JPanel panel = new JPanel();
		panel.setBounds(1, 0, 1080, 126);
		propertiespanel.add(panel);
		panel.setForeground(Color.WHITE);
		panel.setBackground(new Color(0, 139, 139));
		panel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Properties");
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Bahnschrift", Font.BOLD, 26));
		lblNewLabel_4.setBounds(382, 32, 359, 59);
		panel.add(lblNewLabel_4);
		
		propertyID = new JTextField();
		propertyID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_DELETE) {
					DeleteDatabase();
				}
			}
		});
		propertyID.setColumns(10);
		propertyID.setBounds(42, 452, 231, 30);
		propertiespanel.add(propertyID);
		
		JLabel lblNewLabel_5_1_2 = new JLabel("Property ID");
		lblNewLabel_5_1_2.setForeground(Color.WHITE);
		lblNewLabel_5_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5_1_2.setBounds(46, 427, 75, 14);
		propertiespanel.add(lblNewLabel_5_1_2);
		
		// Continue with Tenants, Appointment, Comment panels (similar button fixes needed)
		// For brevity, I'll show the key fix for sidepanel icons and buttons
		
		Panel sidepanel = new Panel();
		sidepanel.setBounds(0, 0, 372, 783);
		frame.getContentPane().add(sidepanel);
		sidepanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		sidepanel.setBackground(new Color(95, 158, 160));
		sidepanel.setForeground(new Color(204, 51, 102));
		sidepanel.setLayout(null);
		
		// ===== FIXED SIDEPANEL BUTTONS WITH PROPER ICON SIZING =====
		
		// Properties Button
		JButton btnProperties = new JButton("Properties");
		btnProperties.setBounds(80, 263, 292, 63);
		btnProperties.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnProperties.setIconTextGap(15);
		btnProperties.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProperties.setForeground(Color.WHITE);
		btnProperties.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnProperties.setBackground(new Color(0, 139, 139));
		btnProperties.setOpaque(true);
		btnProperties.setContentAreaFilled(true);
		btnProperties.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		// Resize icon
		ImageIcon propIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/properties.png");
		Image scaledProp = propIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnProperties.setIcon(new ImageIcon(scaledProp));
		btnProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
			}
		});
		btnProperties.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnProperties.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnProperties.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnProperties);
		
		// Tenants Button
		JButton btnTenants = new JButton("Tenants");
		btnTenants.setBounds(80, 336, 292, 63);
		btnTenants.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnTenants.setIconTextGap(15);
		btnTenants.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTenants.setForeground(Color.WHITE);
		btnTenants.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnTenants.setBackground(new Color(0, 139, 139));
		btnTenants.setOpaque(true);
		btnTenants.setContentAreaFilled(true);
		btnTenants.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		ImageIcon tenantIcon = new ImageIcon("C:\\Users\\EURO COMPUTERS\\Downloads\\icons8-person-at-home-60.png");
		Image scaledTenant = tenantIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnTenants.setIcon(new ImageIcon(scaledTenant));
		btnTenants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnTenants.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnTenants.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnTenants.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnTenants);
		
		// Appointment Button
		JButton btnAppointment = new JButton("Appointment");
		btnAppointment.setBounds(80, 409, 292, 63);
		btnAppointment.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnAppointment.setIconTextGap(15);
		btnAppointment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAppointment.setForeground(Color.WHITE);
		btnAppointment.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnAppointment.setBackground(new Color(0, 139, 139));
		btnAppointment.setOpaque(true);
		btnAppointment.setContentAreaFilled(true);
		btnAppointment.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		ImageIcon appIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/appointment.png");
		Image scaledApp = appIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnAppointment.setIcon(new ImageIcon(scaledApp));
		btnAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		btnAppointment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAppointment.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAppointment.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnAppointment);
		
		// Comments Button
		JButton btnComments = new JButton("Comments");
		btnComments.setBounds(80, 482, 292, 63);
		btnComments.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnComments.setIconTextGap(15);
		btnComments.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnComments.setForeground(Color.WHITE);
		btnComments.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnComments.setBackground(new Color(0, 139, 139));
		btnComments.setOpaque(true);
		btnComments.setContentAreaFilled(true);
		btnComments.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		ImageIcon commentIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/comments.png");
		Image scaledComment = commentIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnComments.setIcon(new ImageIcon(scaledComment));
		btnComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		btnComments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnComments.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnComments.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnComments);
		
		// LOGOUT Button
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(80, 635, 292, 63);
		btnLogout.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnLogout.setIconTextGap(15);
		btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnLogout.setBackground(new Color(0, 139, 139));
		btnLogout.setOpaque(true);
		btnLogout.setContentAreaFilled(true);
		btnLogout.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		ImageIcon logoutIcon = new ImageIcon("C:\\Users\\EURO COMPUTERS\\Downloads\\icons8-logout-rounded-down-64.png");
		Image scaledLogout = logoutIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnLogout.setIcon(new ImageIcon(scaledLogout));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPanel SP = new StartPanel();
				SP.setVisible(true);
				frame.dispose();
			}
		});
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogout.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogout.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnLogout);
		
		// EXIT Button
		JButton btnExit = new JButton("EXIT");
		btnExit.setBounds(80, 709, 292, 63);
		btnExit.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnExit.setIconTextGap(15);
		btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnExit.setBackground(new Color(0, 139, 139));
		btnExit.setOpaque(true);
		btnExit.setContentAreaFilled(true);
		btnExit.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		ImageIcon exitIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/exit.png");
		Image scaledExit = exitIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnExit.setIcon(new ImageIcon(scaledExit));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnExit);
		
		// Title Label
		JLabel lblNewLabel_6 = new JLabel("Property Management System");
		lblNewLabel_6.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setForeground(new Color(255, 255, 255));
		lblNewLabel_6.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblNewLabel_6.setBounds(37, 170, 292, 28);
		sidepanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("____________________________________");
		lblNewLabel_6_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
		lblNewLabel_6_1.setBounds(31, 185, 306, 28);
		sidepanel.add(lblNewLabel_6_1);
		
		// Logo
		JLabel lblLogo = new JLabel("");
		ImageIcon logoIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/PropertyManagementSystem.png");
		Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(scaledLogo));
		lblLogo.setBounds(126, 40, 120, 100);
		sidepanel.add(lblLogo);
	}
	
	// Keep all your existing SaveToTenant, UpdateTenant, DeleteTenant, etc. methods here
	// ... (rest of your methods remain the same)
	
	private void SaveToTenant() {
		connection = DBconnection.getConnection();
		try {
			String query = "insert into tenant  values (?,?,?,?,?,?,?);";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(query);
			ps.setNString(1, tenantid.getText());
			ps.setNString(2, tenantno.getText());
			ps.setNString(3, ownerid.getText());
			ps.setNString(4, Prent.getText());		
			ps.setNString(5, startDate.getText());
			ps.setNString(6, endDate.getText());
			ps.setNString(7, tenantName.getText());
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Saved");
		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1062 )
				JOptionPane.showMessageDialog(null, "Duplicate Entry");
				e.printStackTrace();
			}
	}
	
	private void UpdateTenant() {
		connection = DBconnection.getConnection();
		try {
			String update = "update propertymanagementsystem.tenant set tenant_no = ?, owner_id = ?, property_rent = ?, start_date = ?, end_date = ?, tenant_name = ? where tenant_id = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(update);
			ps.setNString(7, tenantid.getText());
			ps.setNString(1, tenantno.getText());
			ps.setNString(2, ownerid.getText());
			ps.setNString(3, Prent.getText());		
			ps.setNString(4, startDate.getText());
			ps.setNString(5, endDate.getText());
			ps.setNString(6, tenantName.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Record Updated");
		}
		catch (Exception O) {
			JOptionPane.showMessageDialog(null, "ERROR!\n\tValues Missing");
			O.printStackTrace();
		}
	}
	
	private void DeleteTenant() {
		connection = DBconnection.getConnection();
		try {
			String Delete = "delete from propertymanagementsystem.tenant where tenant_id = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(Delete);
			ps.setString(1, tenantid.getText());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Record Deleted!");
		}
		catch (Exception O) {
			if(tenantid.getText().equals(null))
			JOptionPane.showMessageDialog(null, "Enter Tenant ID");
			O.printStackTrace();
		}
	}
	
	private void SaveToComment() {
		connection = DBconnection.getConnection();
		try {
			String query = "insert into comments  values (?,?,?,?);";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(query);
			ps.setNString(1, clientid.getText());
			ps.setNString(2, pid.getText());
			ps.setNString(3, datE.getText());
			ps.setNString(4, comment.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Saved");
		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1062 )
				JOptionPane.showMessageDialog(null, "Duplicate Entry");
			}
	}
	
	private void UpdateComment() {
		connection = DBconnection.getConnection();
		try {
			String update = "update propertymanagementsystem.comments set PropertyID = ?, date = ?, comment = ? where clientid = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(update);
			ps.setString(4, clientid.getText());
			ps.setNString(1, pid.getText());
			ps.setNString(2, date.getText());
			ps.setNString(3, comment.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Record Updated");
		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1062 )
				JOptionPane.showMessageDialog(null, "Duplicate Entry");
			}
	}
	
	private void DeleteComment() {
		connection = DBconnection.getConnection();
		try {
			String Delete = "delete from propertymanagementsystem.comments where clientid = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(Delete);
			ps.setString(1, clientid.getText());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Record Deleted!");
		}
		catch (Exception O) {
			if(clientid.getText().equals(null))
			JOptionPane.showMessageDialog(null, "Enter Client ID");
			O.printStackTrace();
		}
	}
	
	private void SaveToAppointment() {
		connection = DBconnection.getConnection();
		try {
			String query = "insert into appointment values (?,?,?,?,?)";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(query);
			ps.setNString(1, app_id.getText());
			ps.setNString(2, date.getText());
			ps.setNString(3, time.getText());
			ps.setNString(4, client_id.getText());
			ps.setNString(5, p_id.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Saved");
		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1062 )
				JOptionPane.showMessageDialog(null, "Duplicate Entry");
				e.printStackTrace();
			}
	}
	
	private void UpdateAppointment() {
		connection = DBconnection.getConnection();
		try {
			String update = "update propertymanagementsystem.appointment set date = ?, time = ?, client_id = ?, p_id = ? where Appointment_id = ?";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(update);
			ps.setNString(1, date.getText());
			ps.setNString(2, time.getText());
			ps.setNString(3, client_id.getText());
			ps.setNString(4, p_id.getText());
			ps.setString(5, app_id.getText());
		
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Record Updated");
		}
		catch (Exception O) {
			if(app_id == null) {
				JOptionPane.showMessageDialog(null,  "Enter Appointment ID");
			}
			JOptionPane.showMessageDialog(null, "ERROR!\n\tValues Missing");
			O.printStackTrace();
		}
	}
	
	private void DeleteAppointment() {
		connection = DBconnection.getConnection();
		try {
			String Delete = "delete from propertymanagementsystem.appointment where appointment_id = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(Delete);
			ps.setString(1, app_id.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Record Deleted!");
		}
		catch (Exception O) {
			if(app_id.getText().equals(null))
			JOptionPane.showMessageDialog(null, "Enter Appointment ID");
			O.printStackTrace();
		}
	}
	
	private void SaveToDatabase() {
		connection = DBconnection.getConnection();
		try {
			String query = "insert into property values (?,?,?,?,?,?)";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(query);
			ps.setNString(1, propertyID.getText());
			ps.setNString(2, address.getText());
			ps.setNString(4, owner_id.getText());
			ps.setNString(6, rooms.getText());
			ps.setNString(5, p_type.getText());
			ps.setNString(3, owner_no.getText());
		
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Saved");
		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1062 )
				JOptionPane.showMessageDialog(null, "Duplicate Entry");
			e.printStackTrace();
			}
	}
	
	private void UpdateDatabase() {
		connection = DBconnection.getConnection();
		try {
			String update = "update propertymanagementsystem.property set address = ?, rooms = ?, p_type = ?, owner_no = ? where owner_ID = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(update);
			ps.setString(5, owner_id.getText());
			ps.setNString(1, address.getText());
			ps.setNString(2, rooms.getText());
			ps.setNString(3, p_type.getText());
			ps.setNString(4, owner_no.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Record Updated");
		}
		catch (Exception O) {
			JOptionPane.showMessageDialog(null, "ERROR!\n\tValues Missing");
			O.printStackTrace();
		}
	}
	
	private void DeleteDatabase() {
		connection = DBconnection.getConnection();
		try {
			String Delete = "delete from propertymanagementsystem.property where property_id = ?;";
			PreparedStatement ps = DBconnection.getConnection().prepareStatement(Delete);
			ps.setString(1, propertyID.getText());
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Record Deleted!");
		}
		catch (Exception O) {
			if(propertyID.getText().equals(null)) {
			JOptionPane.showMessageDialog(null, "Enter Property ID");
			O.printStackTrace();
			}
		}
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(true);
	}
}