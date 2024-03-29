import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class CreateAccount {

	private JFrame frame;
	private JTextField textUserID;
	private JTextField textLastName;
	private JTextField textFirstName;
	private JTextField textPassword;
	public static String username = "";
	public static String password = "";
//	public static String[] newUserInfo;
	
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
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
//		JLabel lblLastName = new JLabel("LastName");
//		lblLastName.setBounds(48, 90, 74, 16);
//		frame.getContentPane().add(lblLastName);
//		
//		textLastName = new JTextField();
//		textLastName.setBounds(139, 85, 130, 26);
//		frame.getContentPane().add(textLastName);
//		textLastName.setColumns(10);
		
//		JLabel lblUserName = new JLabel("UserName");
//		lblUserName.setBounds(48, 90, 74, 16);
//		frame.getContentPane().add(lblUserName);
//		
//		textUserName = new JTextField();
//		textFirstName.setBounds(139, 85, 130, 26);
//		frame.getContentPane().add(textFirstName);
//		textFirstName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(48, 132, 74, 16);
		frame.getContentPane().add(lblPassword);
		
		textPassword = new JTextField();
		textPassword.setBounds(139, 127, 130, 26);
		frame.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		
		JButton btnCompleted = new JButton("Completed");
		btnCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToFile(textUserID.getText(),textPassword.getText());
				username = textUserID.getText();
				password = textPassword.getText();
				frame.dispose();
				
			}
		});
		btnCompleted.setBounds(139, 232, 117, 29);
		frame.getContentPane().add(btnCompleted);
		
		JButton btnCheckDuplicates = new JButton("Duplicates?");
		btnCheckDuplicates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkDuplicates(textUserID.getText())) {
					JOptionPane.showMessageDialog(null, "Your userName is duplicates","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCheckDuplicates.setBounds(281, 42, 117, 29);
		frame.getContentPane().add(btnCheckDuplicates);
	}
	
	public boolean checkDuplicates(String userName) {
		Scanner in;
		String line;
		ArrayList<String> addtionInfo = new ArrayList<String>();
		try {
			in = new Scanner(new File("database/users.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
			 	    String[] info = line.split("/");
				    // load the user date
			 	    addtionInfo.add(info[0]);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(addtionInfo.contains(userName)) {
			return true;
		}
		return false;
	}
	
	public void writeToFile(String userName,String password) {
		try {
			File file = new File("database/users.txt");
			FileWriter fw = new FileWriter(file,true);
			fw.write(userName + "/" + password +"/");
			fw.write("\n");
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
