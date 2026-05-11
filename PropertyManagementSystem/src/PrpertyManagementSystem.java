import java.awt.EventQueue;
import java.sql.Statement;

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
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;


public class PrpertyManagementSystem {
	
	JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField address;
	private JTextField owner_id;
	private JTextField owner_no;
	private JTextField rooms;
	private JComboBox<String> p_type;
        private JTextField propertyRent;
	private JTable propertytable;
        private JTextField categoryId;
        private JTextField categoryName;
        private JTable categoryTable;

	
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
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setSize(1428, 783);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(365, -26, 1063, 809);
		frame.getContentPane().add(tabbedPane);
		
		// ===== CREATE AND ADD ALL PANELS TO TABBEDPANE =====
		
		// Panel 1: Properties
		JPanel propertiespanel = new JPanel();
		propertiespanel.setBackground(new Color(0, 139, 139));
		propertiespanel.setForeground(Color.WHITE);
		propertiespanel.setLayout(null);
		tabbedPane.addTab("Properties", propertiespanel);
		
		// Panel 2: Tenants
		JPanel Tenants = new JPanel();
		Tenants.setBackground(new Color(0, 139, 139));
		Tenants.setLayout(null);
		tabbedPane.addTab("Tenants", Tenants);
		
		// Panel 3: Categories will be added after the sidebar setup

		// ===== PROPERTIES PANEL COMPONENTS =====
		
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
					propertytable.setModel(ApiClient.getPropertiesTableModel());
					
				} catch (Exception f) {
					f.printStackTrace();
                                        JOptionPane.showMessageDialog(null, "Failed to load properties from API: " + f.getMessage());
				}
				
			}
		});
		
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
		
		p_type = new JComboBox<String>();
                p_type.setBounds(321, 525, 231, 30);
                propertiespanel.add(p_type);

                try {
                        p_type.setModel(ApiClient.getCategoriesComboBoxModel());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Failed to load categories: " + ex.getMessage());
                    }
                
                propertyRent = new JTextField();
                propertyRent.setBounds(600, 452, 231, 30);
                propertyRent.setColumns(10);
                propertiespanel.add(propertyRent);

                JLabel lblPropertyRent = new JLabel("Property Rent");
                lblPropertyRent.setBounds(604, 427, 120, 14);
                lblPropertyRent.setForeground(Color.WHITE);
                lblPropertyRent.setFont(new Font("Tahoma", Font.BOLD, 12));
                propertiespanel.add(lblPropertyRent);
		
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
		
		// ===== TENANTS PANEL COMPONENTS =====
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(0, 117, 1070, 268);
		Tenants.add(scrollPane_3);
		
		tenanttable = new JTable();
		scrollPane_3.setViewportView(tenanttable);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBounds(0, 0, 1054, 127);
		panel_3_1.setLayout(null);
		panel_3_1.setBackground(new Color(0, 139, 139));
		Tenants.add(panel_3_1);
		
		JLabel lblNewLabel_4_2_1 = new JLabel("Tenants");
		lblNewLabel_4_2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_4_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_2_1.setForeground(Color.WHITE);
		lblNewLabel_4_2_1.setFont(new Font("Bahnschrift", Font.BOLD, 26));
		lblNewLabel_4_2_1.setBounds(382, 32, 359, 59);
		panel_3_1.add(lblNewLabel_4_2_1);
		
		JButton btnNewButton_3_1 = new JButton("LOAD");
		btnNewButton_3_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3_1.setBounds(29, 670, 92, 30);
		btnNewButton_3_1.setOpaque(true);
		btnNewButton_3_1.setContentAreaFilled(true);
		btnNewButton_3_1.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tenanttable.setModel(ApiClient.getTenantsTableModel());
					
				} catch (Exception f) {
					f.printStackTrace();
                                        JOptionPane.showMessageDialog(null, "Failed to load tenants from API: " + f.getMessage());
				}
			
			}
		});
		btnNewButton_3_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3_1.setBackground(new Color(204, 255, 204));
		Tenants.add(btnNewButton_3_1);
		
		JButton add2_1 = new JButton("ADD");
		add2_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add2_1.setBounds(169, 670, 77, 30);
		add2_1.setOpaque(true);
		add2_1.setContentAreaFilled(true);
		add2_1.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		add2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveToTenant();
			}
		});
		add2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		add2_1.setBackground(new Color(204, 255, 204));
		Tenants.add(add2_1);
		
		JButton btnNewButton_2_1_1 = new JButton("UPDATE");
		btnNewButton_2_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2_1_1.setBounds(307, 670, 81, 30);
		btnNewButton_2_1_1.setOpaque(true);
		btnNewButton_2_1_1.setContentAreaFilled(true);
		btnNewButton_2_1_1.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateTenant();
			}
		});
		btnNewButton_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2_1_1.setBackground(new Color(204, 255, 204));
		Tenants.add(btnNewButton_2_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("DELETE");
		btnNewButton_1_1_1_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1_1_1_1.setBounds(444, 670, 77, 30);
		btnNewButton_1_1_1_1.setOpaque(true);
		btnNewButton_1_1_1_1.setContentAreaFilled(true);
		btnNewButton_1_1_1_1.setBorder(new LineBorder(new Color(0, 100, 0), 1, true));
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteTenant();
			}
		});
		btnNewButton_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1_1_1_1.setBackground(new Color(204, 255, 204));
		Tenants.add(btnNewButton_1_1_1_1);
		
		JLabel tenantNO = new JLabel("Tenant NO");
		tenantNO.setBounds(29, 478, 114, 16);
		tenantNO.setForeground(Color.WHITE);
		tenantNO.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(tenantNO);
		
		tenantno = new JTextField();
		tenantno.setBounds(29, 504, 246, 34);
		tenantno.setColumns(10);
		Tenants.add(tenantno);
		
		JLabel prent = new JLabel("Property Rent");
		prent.setBounds(360, 478, 114, 16);
		prent.setForeground(Color.WHITE);
		prent.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(prent);
		
		Prent = new JTextField();
		Prent.setBounds(360, 504, 246, 34);
		Prent.setColumns(10);
		Tenants.add(Prent);
		
		JLabel ownerID = new JLabel("Owner ID");
		ownerID.setBounds(29, 565, 114, 16);
		ownerID.setForeground(Color.WHITE);
		ownerID.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(ownerID);
		
		ownerid = new JTextField();
		ownerid.setBounds(29, 591, 246, 34);
		ownerid.setColumns(10);
		Tenants.add(ownerid);
		
		JLabel tenantname = new JLabel("Tenant Name");
		tenantname.setBounds(360, 565, 114, 16);
		tenantname.setForeground(Color.WHITE);
		tenantname.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(tenantname);
		
		tenantName = new JTextField();
		tenantName.setBounds(360, 591, 246, 34);
		tenantName.setColumns(10);
		Tenants.add(tenantName);
		
		JLabel enddate = new JLabel("End Date");
		enddate.setBounds(692, 565, 114, 16);
		enddate.setForeground(Color.WHITE);
		enddate.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(enddate);
		
		endDate = new JTextField();
		endDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				SaveToTenant();
			}}
		});
		endDate.setBounds(692, 591, 246, 34);
		endDate.setColumns(10);
		Tenants.add(endDate);
		
		JLabel tenantID = new JLabel("Tenant ID");
		tenantID.setBounds(28, 395, 114, 16);
		tenantID.setForeground(Color.WHITE);
		tenantID.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(tenantID);
		
		tenantid = new JTextField();
		tenantid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_DELETE) {
					DeleteTenant();
			}
			}
		});
		tenantid.setBounds(28, 421, 246, 34);
		tenantid.setColumns(10);
		Tenants.add(tenantid);
		
		JLabel startdate = new JLabel("Start Date");
		startdate.setBounds(692, 478, 114, 16);
		startdate.setForeground(Color.WHITE);
		startdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		Tenants.add(startdate);
		
		startDate = new JTextField();
		startDate.setBounds(692, 504, 246, 34);
		startDate.setColumns(10);
		Tenants.add(startDate);
		
		// ===== SIDEPANEL =====
		
		Panel sidepanel = new Panel();
		sidepanel.setBounds(0, 0, 372, 783);
		frame.getContentPane().add(sidepanel);
		sidepanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		sidepanel.setBackground(new Color(95, 158, 160));
		sidepanel.setForeground(new Color(204, 51, 102));
		sidepanel.setLayout(null);
		
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
		ImageIcon tenantIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/tenants.png");
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
		
		// Categories Button
		JButton btnCategories = new JButton("Categories");
		btnCategories.setBounds(80, 409, 292, 63);
		btnCategories.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnCategories.setIconTextGap(15);
		btnCategories.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCategories.setForeground(Color.WHITE);
		btnCategories.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnCategories.setBackground(new Color(0, 139, 139));
		btnCategories.setOpaque(true);
		btnCategories.setContentAreaFilled(true);
		btnCategories.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
                ImageIcon categoryIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/categories.png");
                Image scaledCategory = categoryIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                btnCategories.setIcon(new ImageIcon(scaledCategory));
		btnCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		btnCategories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCategories.setBackground(new Color(95,158,160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCategories.setBackground(new Color(0,139,139));
			}
		});
		sidepanel.add(btnCategories);
		

		// LOGOUT Button
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(80, 555, 292, 63);
		btnLogout.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnLogout.setIconTextGap(15);
		btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnLogout.setBackground(new Color(0, 139, 139));
		btnLogout.setOpaque(true);
		btnLogout.setContentAreaFilled(true);
		btnLogout.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		ImageIcon logoutIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/logout.png");
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
		btnExit.setBounds(80, 635, 292, 63);
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
                
                //Categories tab
                JPanel Categories = new JPanel();
                Categories.setBackground(new Color(0, 139, 139));
                tabbedPane.addTab("Categories", null, Categories, null);
                Categories.setLayout(null);

                JLabel lblCategoryTitle = new JLabel("Categories / House Types");
                lblCategoryTitle.setHorizontalAlignment(SwingConstants.CENTER);
                lblCategoryTitle.setForeground(Color.WHITE);
                lblCategoryTitle.setFont(new Font("Bahnschrift", Font.BOLD, 26));
                lblCategoryTitle.setBounds(300, 30, 450, 40);
                Categories.add(lblCategoryTitle);

                JScrollPane categoryScroll = new JScrollPane();
                categoryScroll.setBounds(30, 100, 990, 300);
                Categories.add(categoryScroll);

                categoryTable = new JTable();
                categoryScroll.setViewportView(categoryTable);

                JLabel lblCategoryId = new JLabel("Category ID");
                lblCategoryId.setForeground(Color.WHITE);
                lblCategoryId.setFont(new Font("Tahoma", Font.BOLD, 12));
                lblCategoryId.setBounds(30, 430, 120, 20);
                Categories.add(lblCategoryId);

                categoryId = new JTextField();
                categoryId.setBounds(30, 455, 230, 34);
                Categories.add(categoryId);

                JLabel lblCategoryName = new JLabel("Category Name");
                lblCategoryName.setForeground(Color.WHITE);
                lblCategoryName.setFont(new Font("Tahoma", Font.BOLD, 12));
                lblCategoryName.setBounds(300, 430, 120, 20);
                Categories.add(lblCategoryName);

                categoryName = new JTextField();
                categoryName.setBounds(300, 455, 300, 34);
                Categories.add(categoryName);

                JButton btnLoadCategories = new JButton("LOAD");
                btnLoadCategories.setBounds(30, 530, 90, 30);
                Categories.add(btnLoadCategories);

                JButton btnAddCategory = new JButton("ADD");
                btnAddCategory.setBounds(150, 530, 90, 30);
                Categories.add(btnAddCategory);

                JButton btnUpdateCategory = new JButton("UPDATE");
                btnUpdateCategory.setBounds(270, 530, 100, 30);
                Categories.add(btnUpdateCategory);

                JButton btnDeleteCategory = new JButton("DELETE");
                btnDeleteCategory.setBounds(400, 530, 100, 30);
                Categories.add(btnDeleteCategory);
                
                btnLoadCategories.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            categoryTable.setModel(ApiClient.getCategoriesTableModel());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to load categories: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
});

btnAddCategory.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            if (categoryName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter category name.");
                return;
            }

            ApiClient.addCategory(categoryName.getText().trim());

            JOptionPane.showMessageDialog(null, "Category added successfully.");
            categoryName.setText("");
            categoryTable.setModel(ApiClient.getCategoriesTableModel());

            p_type.setModel(ApiClient.getCategoriesComboBoxModel());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to add category: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
});

btnUpdateCategory.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            if (categoryId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter category ID.");
                return;
            }

            if (categoryName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter category name.");
                return;
            }

            ApiClient.updateCategory(
                categoryId.getText().trim(),
                categoryName.getText().trim()
            );

            JOptionPane.showMessageDialog(null, "Category updated successfully.");
            categoryTable.setModel(ApiClient.getCategoriesTableModel());

            p_type.setModel(ApiClient.getCategoriesComboBoxModel());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to update category: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
});

btnDeleteCategory.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        try {
            if (categoryId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter category ID.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this category?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            ApiClient.deleteCategory(categoryId.getText().trim());

            JOptionPane.showMessageDialog(null, "Category deleted successfully.");
            categoryId.setText("");
            categoryName.setText("");
            categoryTable.setModel(ApiClient.getCategoriesTableModel());

            p_type.setModel(ApiClient.getCategoriesComboBoxModel());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to delete category: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
});

categoryTable.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = categoryTable.getSelectedRow();

        if (row >= 0) {
            categoryId.setText(categoryTable.getValueAt(row, 0).toString());
            categoryName.setText(categoryTable.getValueAt(row, 1).toString());
        }
    }
});


	}
	
	// ===== ALL YOUR EXISTING METHODS GO HERE =====
	// Keep all your existing database methods unchanged (SaveToTenant, UpdateTenant, etc.)
	
	private void SaveToTenant() {
            try {
                ApiClient.addTenant(tenantno.getText(), ownerid.getText(), Prent.getText(), startDate.getText(), endDate.getText(), tenantName.getText());
                tenanttable.setModel(ApiClient.getTenantsTableModel());
                JOptionPane.showMessageDialog(null, "Tenant saved through API");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "API Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
	
	private void UpdateTenant() {
            try {
                    ApiClient.updateTenant(tenantid.getText(), tenantno.getText(), ownerid.getText(), Prent.getText(), startDate.getText(), endDate.getText(), tenantName.getText());
                    tenanttable.setModel(ApiClient.getTenantsTableModel());
                    JOptionPane.showMessageDialog(null, "Tenant updated through API");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "API Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
	
	private void DeleteTenant() {
            try {
                    ApiClient.deleteTenant(tenantid.getText());
                    tenanttable.setModel(ApiClient.getTenantsTableModel());
                    JOptionPane.showMessageDialog(null, "Tenant deleted through API");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "API Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
	
	private void SaveToDatabase() {
          try {
                ApiClient.addProperty(
                propertyID.getText(),
                address.getText(),
                owner_no.getText(),
                owner_id.getText(),
                p_type.getSelectedItem().toString(),
                rooms.getText(),
                propertyRent.getText()
                );

                propertytable.setModel(ApiClient.getPropertiesTableModel());
                JOptionPane.showMessageDialog(null, "Property saved through API");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "API Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
	
	private void UpdateDatabase() {
            try {
                ApiClient.updateProperty(
                propertyID.getText(),
                propertyID.getText(),
                address.getText(),
                owner_no.getText(),
                owner_id.getText(),
                p_type.getSelectedItem().toString(),
                rooms.getText(),
                propertyRent.getText()
                );

                propertytable.setModel(ApiClient.getPropertiesTableModel());
                JOptionPane.showMessageDialog(null, "Property updated through API");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "API Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
	
	private void DeleteDatabase() {
            try {
                ApiClient.deleteProperty(propertyID.getText());
                propertytable.setModel(ApiClient.getPropertiesTableModel());
                JOptionPane.showMessageDialog(null, "Property deleted through API");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "API Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
	
	public void setVisible(boolean b) {
            frame.setVisible(b);
        }
}