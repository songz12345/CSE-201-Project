import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateAccount {

	private JFrame frame;
	private JTextField textUserID;
	private JTextField textLastName;
	private JTextField textFirstName;
	private JTextField textPassword;
	
	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount window = new CreateAccount();
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
	public CreateAccount() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCreateAccount = new JLabel("Create New Account");
		lblCreateAccount.setBounds(155, 19, 151, 16);
		frame.getContentPane().add(lblCreateAccount);
		
		JLabel lblNewUserID = new JLabel("UserID");
		lblNewUserID.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewUserID.setBounds(48, 47, 61, 16);
		frame.getContentPane().add(lblNewUserID);
		
		textUserID = new JTextField();
		textUserID.setBounds(139, 42, 130, 26);
		frame.getContentPane().add(textUserID);
		textUserID.setColumns(10);
		
		JLabel lblLastName = new JLabel("LastName");
		lblLastName.setBounds(48, 90, 74, 16);
		frame.getContentPane().add(lblLastName);
		
		textLastName = new JTextField();
		textLastName.setBounds(139, 85, 130, 26);
		frame.getContentPane().add(textLastName);
		textLastName.setColumns(10);
		
		JLabel lblFirstName = new JLabel("FirstName");
		lblFirstName.setBounds(48, 132, 74, 16);
		frame.getContentPane().add(lblFirstName);
		
		textFirstName = new JTextField();
		textFirstName.setBounds(139, 127, 130, 26);
		frame.getContentPane().add(textFirstName);
		textFirstName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(48, 179, 61, 16);
		frame.getContentPane().add(lblPassword);
		
		textPassword = new JTextField();
		textPassword.setBounds(139, 174, 130, 26);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Login");
				
			}
		});
		btnLogin.setBounds(139, 232, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		JButton btnCheckDuplicates = new JButton("Duplicates?");
		btnCheckDuplicates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCheckDuplicates.setBounds(281, 42, 117, 29);
		frame.getContentPane().add(btnCheckDuplicates);
	}

}
