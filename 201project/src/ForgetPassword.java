import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.awt.event.ActionEvent;

public class ForgetPassword {

	private JFrame frame;
	private JTextField textUserId;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgetPassword window = new ForgetPassword();
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
	public ForgetPassword() {
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
		
		JLabel lblTitle = new JLabel("Find your Password");
		lblTitle.setBounds(148, 18, 142, 16);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblTypeIn = new JLabel("Type your UserID");
		lblTypeIn.setBounds(54, 66, 108, 16);
		frame.getContentPane().add(lblTypeIn);
		
		textUserId = new JTextField();
		textUserId.setBounds(254, 61, 130, 26);
		frame.getContentPane().add(textUserId);
		textUserId.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				if(addtionInfo.containsKey(textUserId.getText())) {
					JOptionPane.showMessageDialog(null,addtionInfo.get(textUserId.getText()),
							"Find your password",JOptionPane.INFORMATION_MESSAGE);

				}
				else {
					JOptionPane.showMessageDialog(null, "We don't find your userId","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnFind.setBounds(148, 203, 117, 29);
		frame.getContentPane().add(btnFind);
	}
	

}
