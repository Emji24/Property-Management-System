import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JTextField pass;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel backgroundLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 622);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(0, 139, 139)); // Set contentPane background to match
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Background Label with properly scaled image
		backgroundLabel = new JLabel("");
		backgroundLabel.setBounds(0, 0, 892, 622);
		ImageIcon bgIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/loginbg.png");
		Image scaledBg = bgIcon.getImage().getScaledInstance(892, 622, Image.SCALE_SMOOTH);
		backgroundLabel.setIcon(new ImageIcon(scaledBg));
		backgroundLabel.setBackground(new Color(0, 139, 139));
		backgroundLabel.setOpaque(true);
		contentPane.add(backgroundLabel);
		
		lblNewLabel_3 = new JLabel("x");
		lblNewLabel_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(220, 20, 60));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 31));
		lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StartPanel SP = new StartPanel();
				SP.setVisible(true);
				dispose();
			}
		});
		ImageIcon exitIcon = new ImageIcon("/Users/edmarsalido/Documents/Images/exit.png");
		Image scaledExit = exitIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		lblNewLabel_4.setIcon(new ImageIcon(scaledExit));
		lblNewLabel_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(new Color(220, 20, 60));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 31));
		lblNewLabel_4.setBounds(10, 11, 45, 45);
		backgroundLabel.add(lblNewLabel_4);
		lblNewLabel_3.setBounds(845, 0, 35, 34);
		backgroundLabel.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addKeyListener(new KeyAdapter() {
		
		});
		btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loginUser();
                    }
                });
		btnNewButton.setBackground(new Color(0, 139, 139));
		btnNewButton.setForeground(new Color(255, 245, 238));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 17));
		btnNewButton.setBounds(375, 432, 169, 46);
		btnNewButton.setOpaque(true);
		btnNewButton.setContentAreaFilled(true);
		// Add a white border to the button
		btnNewButton.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		backgroundLabel.add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(new Color(255, 245, 238));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewLabel_2.setAlignmentY(1.0f);
		lblNewLabel_2.setBounds(285, 323, 121, 21);
		backgroundLabel.add(lblNewLabel_2);
		
		lblNewLabel_1 = new JLabel("USERNAME");
		lblNewLabel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblNewLabel_1.setForeground(new Color(255, 245, 238));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewLabel_1.setBounds(285, 201, 121, 21);
		backgroundLabel.add(lblNewLabel_1);
		
		pass = new JTextField();
		pass.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    loginUser();
                                }
                        
			}
		});
		pass.setForeground(new Color(255, 248, 220));
		pass.setFont(new Font("Tahoma", Font.PLAIN, 21));
		pass.setColumns(10);
		pass.setBackground(new Color(0, 139, 139));
		pass.setCaretColor(Color.WHITE);
		pass.setBounds(285, 355, 346, 46);
		backgroundLabel.add(pass);
		
		user = new JTextField();
		user.setForeground(new Color(255, 245, 238));
		user.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		user.setBackground(new Color(0, 139, 139));
		user.setCaretColor(Color.WHITE);
		user.setBounds(285, 229, 346, 46);
		user.setColumns(10);
		backgroundLabel.add(user);
	}
        
        private void loginUser() {
            String username = user.getText().trim();
            String password = pass.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.");
                return;
            }

            try {
                boolean loggedIn = ApiClient.login(username, password);

                if (loggedIn) {
                    JOptionPane.showMessageDialog(this, "Login successful!");

                    PrpertyManagementSystem pms = new PrpertyManagementSystem();
                    pms.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect Username or Password");
                    user.setText("");
                    pass.setText("");
                }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "API connection error: " + ex.getMessage());
                }
            }   
}