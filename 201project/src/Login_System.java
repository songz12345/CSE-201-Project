import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Login_System {

	private JFrame frame;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JLabel lblUser;
	public static String username = "";
	public static String password = "";
	public static boolean isLogged = false;
//	public static String[] newUserInfo;
	
	/**
	 * Launch the application.
	 */
	static Login_System window = new Login_System();
	
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

				char[] temp = textPassword.getPassword();
				String thisPassword = "";
				for (char c : temp) {
					thisPassword += c;
				}
				password = thisPassword;
				username = textUserName.getText();


				if(checkUser(username,password)) {
					frame.dispose();
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
	
	public boolean checkUser(String userName, String password) {
		HashMap<String,String> addtionInfo = new HashMap<String,String>();
		Scanner in;
		String line;
		try {
			in = new Scanner(new File("database/users.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
			 	    String[] info = line.split("/");
				    // load the user date
			 	    addtionInfo.put(info[0], info[1]);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(addtionInfo.get(userName).equals(password)) {
			isLogged = true;
			return true;
		}
		return false;
	}
//	public static String[] addNewUser() {
//		return newUserInfo;
//	}
}
