import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Library {
	private static JFrame jf;
	private JPanel subjectPanel, background;
	private JLabel frontPage, icon, descripTitle;
	private JButton search, login, signup;
	private JTextArea area, description;
	private JList<String> list;
	
	public Library() {
		jf = new JFrame();
		subjectPanel = new JPanel();
		background = new JPanel();
		search = new JButton();
		login = new JButton();
		signup = new JButton();
		area = new JTextArea();
		description = new JTextArea();
		list = new JList<String>();
		init();
	}
	
	/**
	 * Check if the search key is in the library
	 * @param key
	 * @return
	 */
	public boolean inLibrary(String key) {
		// search for the book
		return true;
	}
	
	/**
	 * Retrive the book information from the library as a string based on the key
	 * e.g.
	 * "Ancient Greek philosophical school/1975/Errin Crutsinger/philosophy
	 * / the Ancient Greek Philosophical Schools’ opinion, concerns, and 
	 * representative.\n"
	 * 
	 * @param key
	 * @return
	 */
	public String getBookInfo(String key) {
		String info = "";
//		for (int index = 0; index < size; index++) {
//		// separate each part
//		if (key.charAt(index) == '/') {
//			title = key.substring(0, index);
//			key = key.substring(index + 1);
//		}
		return info;
	}
	
	/**
	 * 
	 * @param key
	 */
	public void search(String key) {
		int size = key.length();
		String title;
		// if the book exist in the library
		if (inLibrary(key)) {
			String info = getBookInfo(key);
		} else {
			// pop up new window for book not found.
		}
	}
	
	/**
	 * Check the user file by login ID
	 */
	public void viewProfile(String id) {
		
	}
	
	/**
	 * Borrowing, set the book unavalable
	 * @param book
	 * @return
	 */
	public boolean borrowBook(String book) {
		return true;
	}
	
	/**
	 * Return the book and set the book available again
	 * @param book
	 * @return
	 */
	public boolean returnBook(String book) {
		return true;
	}
	
	/**
	 * Add the book with its related infomation
	 * @param info
	 * @return
	 */
	public boolean addBook(String info) {
		return true;
	}
	/**
	 * initiation of GUI
	 */
	private void init() {
		jf.setTitle("Librachy");
		jf.setLayout(null);
		// set background
//		BufferedImage bg = null;
//		background = new JPanel() {
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				ImageIcon img = new ImageIcon("background.jpeg");
//				img.setImage(image);
//				img.paintIcon(this, g, 0, 0);
//			}
//		};
//		background.setOpaque(true);
		String[] books = {"The Alchemist"};
		
		// buttons
		search.setText("Search");
		login.setText("Log in");
		signup.setText("Sign up");
		
		JTextField text = new JTextField("The Alchemist");
		// description part
		description.setText("Nicholas Flamel appeared in J.K. Rowling’s Harry Potter—but did"
				+ " you know he really lived? And his secrets aren't safe! Discover the truth in book one of "
				+ "the New York Times bestselling series the Secrets of the Immortal Nicholas Flamel.");
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setEditable(false);
		
		list = new JList<String>(books);
		JRadioButton rButton1 = new JRadioButton("Chemestry       ");
		JRadioButton rButton2 = new JRadioButton("Alchemy         ");
		JRadioButton rButton3 = new JRadioButton("Physics         ");
		JRadioButton rButton4 = new JRadioButton("Math            ");
		JRadioButton rButton5 = new JRadioButton("English         ");
		JRadioButton rButton6 = new JRadioButton("Spanish         ");
		JRadioButton rButton7 = new JRadioButton("Chinese         ");
		JRadioButton rButton8 = new JRadioButton("Computer Science");
		JRadioButton rButton9 = new JRadioButton("Biology         ");
		
		// add image
		ImageIcon bookImg = new ImageIcon("alchemy.jpg");
		ImageIcon iconImg = new ImageIcon("icon.jpeg");
		frontPage = new JLabel(bookImg);
		icon = new JLabel(iconImg);
		descripTitle = new JLabel("Description");
		
		icon.setBounds(5, 5, iconImg.getIconWidth(), iconImg.getIconHeight());
		icon.setSize(300, 45);
		frontPage.setBounds(650, 60, 324, 499);
		descripTitle.setBounds(350, 300, 150, 50);
		search.setBounds(30, 50, 170, 50);
		login.setBounds(900, 20, 80, 30);
		signup.setBounds(810, 20, 80, 30);
		text.setBounds(210, 50, 390, 50);
		area.setBounds(30, 150, 470, 450);
		description.setBounds(210, 350, 390, 150);
		list.setBounds(215, 100, 380, 150);
		subjectPanel.setBounds(30, 150, 170, 350);
		
		// subject buttons
		subjectPanel.add(rButton1);
		subjectPanel.add(rButton2);
		subjectPanel.add(rButton3);
		subjectPanel.add(rButton4);
		subjectPanel.add(rButton5);
		subjectPanel.add(rButton6);
		subjectPanel.add(rButton7);
		subjectPanel.add(rButton8);
		subjectPanel.add(rButton9);
		
		// add components to jframe
		jf.add(subjectPanel);
		jf.add(search);
		jf.add(login);
		jf.add(signup);
		jf.add(text);
		jf.add(list);
		jf.add(frontPage);
		jf.add(icon);
		jf.add(description);
		jf.add(descripTitle);
		
		 // set jframe properties
		jf.setSize(1000, 600);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		new Library();
		jf.setVisible(true);
	}
}
