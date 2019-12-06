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

public class ChangePassword {

	private JFrame frame;
	private JTextField textUserId;
	private JTextField textNewPassword;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword window = new ChangePassword();
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
	public ChangePassword() {
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

		JLabel lblTitle = new JLabel("Change your Password");
		lblTitle.setBounds(124, 6, 155, 16);
		frame.getContentPane().add(lblTitle);

		JLabel lblUserId = new JLabel("UserId");
		lblUserId.setBounds(36, 63, 50, 16);
		frame.getContentPane().add(lblUserId);

		textUserId = new JTextField();
		textUserId.setBounds(124, 58, 130, 26);
		frame.getContentPane().add(textUserId);
		textUserId.setColumns(10);

		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(6, 139, 100, 16);
		frame.getContentPane().add(lblNewPassword);

		textNewPassword = new JTextField();
		textNewPassword.setBounds(124, 134, 130, 26);
		frame.getContentPane().add(textNewPassword);
		textNewPassword.setColumns(10);

		JButton btnFinish = new JButton("Done!");
		btnFinish.addActionListener(new ActionListener() {
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
					addtionInfo.replace(textUserId.getText(), addtionInfo.get(textUserId.getText()), textNewPassword.getText());
					try {
						File file = new File("database/users.txt");
						FileWriter fw = new FileWriter(file,false);
						fw.write("username            password                borrowed-books");
						fw.write("\n");
						Set<String> keys = addtionInfo.keySet();
						Iterator<String> iter = keys.iterator();
						while(iter.hasNext()) {
							String str = iter.next();
							fw.write(str + "/" + addtionInfo.get(str)+"/");
							fw.write("\n");
						}
						fw.close();
					}catch(Exception e1) {
						e1.printStackTrace();
					}
					frame.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "We don't find your userId","Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnFinish.setBounds(124, 204, 117, 29);
		frame.getContentPane().add(btnFinish);
	}
}
