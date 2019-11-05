import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.ActionEvent;

public class Login_System {

	private JFrame frame;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JLabel lblUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_System window = new Login_System();
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
	public Login_System() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(141, 24, 61, 16);
		frame.getContentPane().add(lblLogin);

		lblUser = new JLabel("UserName");
		lblUser.setBounds(34, 81, 77, 16);
		frame.getContentPane().add(lblUser);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(34, 165, 61, 16);
		frame.getContentPane().add(lblPassword);

		textUserName = new JTextField();
		textUserName.setBounds(122, 76, 130, 26);
		frame.getContentPane().add(textUserName);
		textUserName.setColumns(10);

		textPassword = new JPasswordField();
		textPassword.setBounds(122, 160, 130, 21);
		frame.getContentPane().add(textPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String password = textPassword.getText();
				String username = textUserName.getText();

				if(password.contains("king") && username.contains("one")) {
					textPassword.setText(null);
					textUserName.setText(null);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Your password or UserName is invalid","Login error",JOptionPane.ERROR_MESSAGE);
					textPassword.setText(null);
					textUserName.setText(null);
				}
			}
		});
		btnLogin.setBounds(6, 223, 117, 29);
		frame.getContentPane().add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textUserName.setText(null);
				textPassword.setText(null);
			}
		});
		btnReset.setBounds(174, 223, 117, 29);
		frame.getContentPane().add(btnReset);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if(JOptionPane.showConfirmDialog(frame,"Exit?","Login System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION);
				System.exit(0);
			}
		});
		btnExit.setBounds(339, 223, 117, 29);
		frame.getContentPane().add(btnExit);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 199, 470, 12);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 52, 470, 12);
		frame.getContentPane().add(separator_1);

		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateAccount create = new CreateAccount();
				create.main();
			}
		});
		btnCreateAccount.setBounds(339, 19, 122, 29);
		frame.getContentPane().add(btnCreateAccount);
	}

	private boolean CheckNull(String userId,String password) {
		boolean f = false;
		if(userId.equals("")){
			f = true;
		}
		else {
			if(password.equals("")){
				f = true;
			}
		}
		return f;
	}


	private boolean checkUserAndPassword(String user, String password) {
		boolean result = false;
		try {
			FileReader file = new FileReader("D:\\Workspaces\\MyEclipse 8.5\\testGUI.txt");
			BufferedReader bre = new BufferedReader(file);
			String str = bre.readLine();

			while(str!=null) {
				String [] a1 = str.split(",");
				if(a1[0].equals(user)) {
					if(a1[1].contentEquals(password)) {
						result = true;
					}
					str = bre.readLine();
				}
				file.close();
			}
		}catch(Exception ex) {
			System.out.print("errors");

		}
		return result;
	}
	
	
}
