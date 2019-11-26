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
	private JPanel subjectPanel;
	private JLabel frontPage, icon, descripTitle, background;
	private JButton search, login, signup;
	private ImageIcon bookImg, backgroundImg;
	private JTextField text;
	private JTextArea area, description;
	private JList<String> list;
	public static ArrayList<Book> lib;
	public static ArrayList<User> users;
	public static String searchBuffer;
    public static String[] userInfo;
    
	public Library() {
		jf = new JFrame();
		subjectPanel = new JPanel();
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
			in = new Scanner(new File("database/BookInfo.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
			 	    String[] info = new String[6];
				    info = line.split("/");
				    // load the date to the lib
				    lib.add(new Book(info[0], info[1], info[2], info[3], info[4], false, " ", "imgs/"+info[5]));
			        index++;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load the user information
	 */
	private static void loadUsers() {
		Scanner in;
		String line;
		try {
			in = new Scanner(new File("database/users.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty()) {
					User user = new User();
					ArrayList<String> addtionInfo = new ArrayList<String>();
			 	    String[] info = line.split("/");
				    // load the user date to the users
				    user.setUsername(info[0]);
				    user.setPassword(info[1]);
				    for (int i = 2; i < info.length; i++) {
				    	addtionInfo.add(info[i]);
				    }
				    user.setBooksCheckedOut(addtionInfo);
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
	 * / the Ancient Greek Philosophical Schools
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
	public void verifyUser(User user) {
		if (user.getUsername())
	}
	
	/**
	 * Borrowing, set the book unavailable
	 * @param book
	 * @return
	 */
	public boolean borrowBook(Book book) {
		// if user has logged in
		if (userInfo[0]) {
			// if the book has been borrowed
			if (book.isCheckedOut()) { 
				JFrame errWindow = new JFrame();
				errWindow.setTitle("Error");
				errWindow.setLayout(null);
				JLabel errMsg = new JLabel("Sorry, this book is unavailable right now!");
				errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
				errMsg.setBounds(40, 0, 300, 100);
				errWindow.add(errMsg);
				errWindow.setSize(350, 125);
				errWindow.setLocationRelativeTo(null);
				errWindow.setResizable(false);
				errWindow.setVisible(true);
				errWindow.toFront();
				return false;
			} else {
				// mark book as unavailable and add the book to user's borrowlist
				book.setCheckedOut(true);
				book.setCheckedOutBy(user.getUsername);
				user.getBooksCheckedOut().add(book);
				return true;
			}
		} else {  // if the user hasn't logged in
			JFrame errWindow = new JFrame();
			errWindow.setTitle("Error");
			errWindow.setLayout(null);
			JLabel errMsg = new JLabel("Sorry, please login first!");
			errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
			errMsg.setBounds(40, 0, 300, 100);
			errWindow.add(errMsg);
			errWindow.setSize(350, 125);
			errWindow.setLocationRelativeTo(null);
			errWindow.setResizable(false);
			errWindow.setVisible(true);
			errWindow.toFront();
			return false;
		}
	}
	
	/**
	 * Return the book and set the book available again
	 * @param book
	 * @return
	 */
	public boolean returnBook(Book book) {
		book.setCheckedOut(false);
		book.setCheckedOutBy(null);
		user.getBooksCheckedOut().remove(book);
		return true;
	}
	
	/**
	 * Add the book with its related information
	 * @param info
	 * @return
	 */
	public boolean addBook(String info) {
		String[] infos = new String[6];
		// seperate the info to 6 parts
	    infos = info.split("/");
	    Book newbook = new Book(infos[0], infos[1], infos[2], infos[3], infos[4], false, " ", infos[5]);
	    // if the book is already exists in the library
	    if (inLibrary(newbook.getTitle()) != -1) {
	    	return false;
	    } else {
	    	lib.add(newbook);
			return true;
	    }
	}
	
	/**
	 * Remove the book from the library
	 * @param book
	 * @return
	 */
	public boolean removeBook(Book book) {
		lib.remove(book);
		return true;
	}
	
//	/**
//	 * Issue a fine to a user with its login id, return the fine amount
//	 * @param id
//	 * @return
//	 */
//	public int issueAFine(String id) {
//		int fineAmount = 0;
//		
//		return fineAmount;
//	}
//	
//	/**
//	 * Ban a user if s/he didn't return the book over a month.
//	 * @param id
//	 * @return
//	 */
//	public boolean banAUser(String id) {
//		return true;
//	}
	
	/**
	 * initiation of GUI
	 */
	private void init() {
		jf.setTitle("Librademic");
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
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// load the user information
				Login_System.main();
			}
		});
		signup.setText("Sign up");
		signup.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateAccount.main();
			}
		});
		
		// description part
		description.setText("Nicholas Flamel appeared in J.K. Rowling Harry Potter did"
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
		ImageIcon iconImg = new ImageIcon("imgs/icon.jpeg");
		icon = new JLabel(iconImg);
		//add background
		backgroundImg = new ImageIcon("imgs/background.jpg");
		Image img1 = backgroundImg.getImage().getScaledInstance(1100, 600, Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(img1));
		// initial book's front page
		bookImg = new ImageIcon("imgs/default.jpg");
		Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
		frontPage = new JLabel(new ImageIcon(img));
		// add description part
		descripTitle = new JLabel("Description");
		
		icon.setBounds(5, 5, iconImg.getIconWidth(), iconImg.getIconHeight());
		icon.setSize(300, 45);
		background.setBounds(0, 0, 1100, 600);
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
//		jf.add(background);
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
		users = new ArrayList<User>();
		searchBuffer = new String();
		new Library();
		loadBooks();
		loadUsers();
		jf.setVisible(true);
	}
}
