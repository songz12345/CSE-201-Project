import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class Library {
	final static int MAX_BOOK_NUMBER = 100;
	
	private static JFrame jf;
	private JPanel subjectPanel, background;
	private JLabel frontPage, icon, descripTitle;
	private JButton search, login, signup;
	private ImageIcon bookImg;
	private JTextField text;
	private JTextArea area, description;
	private JList<String> list;
	public static ArrayList<Book> lib;
	public static String searchBuffer;
	
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
		text = new JTextField();
		init();
	}
	
	/**
	 * load the book information
	 */
	private static void loadBooks() {
		Scanner in;
		String line;
		int index = 0;
		try {
			in = new Scanner(new File("BookInfo.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
			 	    String[] info = new String[6];
				    info = line.split("/");
				    // load the date to the lib
				    lib.add(new Book(info[0], info[1], info[2], info[3], info[4], false, " ", info[5]));
			        index++;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if the search key is in the library. If it exist, return the index
	 * otherwise return -1
	 * @param key
	 * @return
	 */
	public static int inLibrary(String key) {
		int i = 0;
		// search for the book by the title
		for (Book s : lib) {
			if (s.getTitle().contains(key)) {
				return i;
			} else {
				i++;
			}
		}
		return -1;
	}
	
	/**
	 * Retrive the book information from the library as a string based on the key
	 * e.g.
	 * "Ancient Greek philosophical school/Errin Crutsinger/1975/philosophy
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
	 */
	public void search() {
		searchBuffer = text.getText();
		// if didn't find the book in the library
		// pop up a error window for user
		int index = inLibrary(searchBuffer);
		if (index < 0) {
			JFrame errWindow = new JFrame();
			errWindow.setTitle("Error");
			errWindow.setLayout(null);
			JLabel errMsg = new JLabel("Sorry, we didn't find your book!");
			errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
			errMsg.setBounds(40, 0, 300, 100);
			errWindow.add(errMsg);
			errWindow.setSize(350, 125);
			errWindow.setLocationRelativeTo(null);
			errWindow.setResizable(false);
			errWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			errWindow.setVisible(true);
			errWindow.toFront();
		} else {
			// change description for found book
			description.setText(lib.get(index).getDescription());
			// change front page for found book
			bookImg = new ImageIcon(lib.get(index).getFrontPage());
			Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
			frontPage.setIcon(new ImageIcon(img));
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
	 * Remove the book from the library
	 * @param book
	 * @return
	 */
	public boolean removeBook(String book) {
		return true;
	}
	
	/**
	 * Issue a fine to a user with its login id, return the fine amount
	 * @param id
	 * @return
	 */
	public int issueAFine(String id) {
		int fineAmount = 0;
		return fineAmount;
	}
	
	/**
	 * Ban a user if s/he didn't return the book over a month.
	 * @param id
	 * @return
	 */
	public boolean banAUser(String id) {
		return true;
	}
	
	/**
	 * initiation of GUI
	 */
	private void init() {
		jf.setTitle("Name");
		jf.setLayout(null);
		// book list for drop-down list
		ArrayList<String> books = new ArrayList<String>();
		text.setText("");
		// buttons
		search.setText("Search");
		// after press the search button, the text the user has typed would be
		// stored in searchBuffer to future use.
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		login.setText("Log in");
		signup.setText("Sign up");
		
		
		// description part
		description.setText("Nicholas Flamel appeared in J.K. Rowling’s Harry Potter—but did"
				+ " you know he really lived? And his secrets aren't safe! Discover the truth in book one of "
				+ "the New York Times bestselling series the Secrets of the Immortal Nicholas Flamel.");
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setEditable(false);
		
		JRadioButton rButton1 = new JRadioButton("Chemestry       ");
		JRadioButton rButton2 = new JRadioButton("Alchemy         ");
		JRadioButton rButton3 = new JRadioButton("Physics         ");
		JRadioButton rButton4 = new JRadioButton("Math            ");
		JRadioButton rButton5 = new JRadioButton("English         ");
		JRadioButton rButton6 = new JRadioButton("Spanish         ");
		JRadioButton rButton7 = new JRadioButton("Chinese         ");
		JRadioButton rButton8 = new JRadioButton("Computer Science");
		JRadioButton rButton9 = new JRadioButton("Biology         ");
		
		// add icon
		ImageIcon iconImg = new ImageIcon("icon.jpeg");
		icon = new JLabel(iconImg);
		// initial book's front page
		bookImg = new ImageIcon("default.jpg");
		Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
		frontPage = new JLabel(new ImageIcon(img));
		// add description part
		descripTitle = new JLabel("Description");
		
		icon.setBounds(5, 5, iconImg.getIconWidth(), iconImg.getIconHeight());
		icon.setSize(300, 45);
		frontPage.setBounds(650, 60, 400, 500);
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
		jf.setSize(1100, 600);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		lib = new ArrayList<Book>();
		searchBuffer = new String();
		new Library();
		jf.setVisible(true);
		loadBooks();
		Scanner in = new Scanner(System.in);
		System.out.print("plaes enter: ");
		String input = in.nextLine();
		System.out.println(input);
		System.out.println(lib.get(inLibrary(input)).getFrontPage());
	}
}
