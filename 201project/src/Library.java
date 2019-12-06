import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class Library {
	final static int MAX_BOOK_NUMBER = 100;
	
	private static JFrame jf;
	private JPanel subjectPanel;
	private JLabel frontPage, icon, descripTitle, background, bookName;
	private JButton search, login, signup, borrow, ret, viewProfile;
	private ImageIcon bookImg, backgroundImg;
	private JTextField text;
	private JTextArea area, description;
	private JList<String> list;
	public static ArrayList<Book> lib;
	public static ArrayList<User> users;
	public static String searchBuffer;
    public User currentUser;
    public String subject = "";
    
	public Library() {
		jf = new JFrame();
		subjectPanel = new JPanel();
		search = new JButton();
		login = new JButton();
		borrow = new JButton();
		ret = new JButton();
		viewProfile = new JButton();
		signup = new JButton();
		area = new JTextArea();
		description = new JTextArea();
		list = new JList<String>();
		text = new JTextField();
		currentUser = new User();
		init();
	}
	
	/**
	 * load the book information
	 */
	public static void loadBooks() {
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
				    //                title   author   year     subject  description
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
	public static void loadUsers() {
		Scanner in;
		String line;
		try {
			in = new Scanner(new File("database/users.txt"));
			// remove the first line
			in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				if (!line.isEmpty() && line.length() != 0) {
					User user = new User();
					ArrayList<String> borrowedBooks = new ArrayList<String>();
			 	    String[] info = line.split("/");
			 	   if (info == null || info.length == 0) {
			           break;
			        } else {
					    // load the user date to the users
					    user.setUsername(info[0]);
					    user.setPassword(info[1]);
					    for (int i = 2; i <= info.length; i++) {
					    		if (i >= info.length) {
					    			break;
					    		} else {
					    			borrowedBooks.add(info[i]);
					    		}
					    }
					    user.setBooksCheckedOut(borrowedBooks);
					    	users.add(user);
			        }
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
		if (lib == null) return -1;
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
	public Book getBookInfo(String key) {
		if (lib == null) return null;
		for (Book b : lib) {
			if (key.compareTo(b.getTitle()) == 0) {
				return b;	
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void search(String searchBuffer) {
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
			// if match given subject
			if (!lib.get(index).getSubject().equals(subject) && subject != "") {
				JFrame errWindow = new JFrame();
				errWindow.setTitle("Error");
				errWindow.setLayout(null);
				JLabel errMsg = new JLabel("Sorry, we didn't find your book under this subject!");
				errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
				errMsg.setBounds(40, 0, 450, 100);
				errWindow.add(errMsg);
				errWindow.setSize(500, 125);
				errWindow.setLocationRelativeTo(null);
				errWindow.setResizable(false);
				errWindow.setVisible(true);
				errWindow.toFront();
			} else {
				bookName.setText(lib.get(index).getTitle());
				bookName.setFont(new Font("Serif", Font.PLAIN, 20));
				// change description for found book
				description.setText(lib.get(index).getDescription());
//				descripTitle.setBounds(350, 300, 150, 50);
//				description.setBounds(210, 350, 390, 150);
				// change front page for found book
				bookImg = new ImageIcon(lib.get(index).getFrontPage());
				Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
				frontPage.setIcon(new ImageIcon(img));
			}
		}
	}
	
	public boolean isBorrowed(Book book) {
		for (User u : users) {
			if (u.getBooksCheckedOut() == null || u.getBooksCheckedOut().size() == 0) {
			} else {
				ArrayList<String> borrowedBooks = u.getBooksCheckedOut();
				for (String s : borrowedBooks) {
					if (s.equals(book.getTitle())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * update the user information after borrowing one or more books
	 */
	public void updateUser(User user, String appendMsg) {
		for (User u : users) {
			if (u.getUsername().length() <= 0) {
				break;
			} else {
				// if the name matches, update the user information
				if (u.getUsername().equals(user.getUsername())) {
					int index = users.indexOf(u);
					ArrayList<String> temp = new ArrayList<String>();
					ArrayList<String> orig = u.getBooksCheckedOut();
					for (String s : orig) {
						temp.add(s);
					}
					temp.add(appendMsg);
					users.get(index).setBooksCheckedOut(temp);
				}
			}
		}
			try {
				FileWriter fw = new FileWriter("database/users.txt", false);
				fw.write("username            password                borrowed-books\n");
				for (User a : users) {
					if (a.getUsername().length() <= 0) {
						break;
					} else {
						if(a.getBooksCheckedOut() == null || a.getBooksCheckedOut().size() == 0) {
							fw.write(a.getUsername() + "/" + a.getPassword() + "/\n");
						} else {
							String books = "";
							for (String str : a.getBooksCheckedOut()) {
								books = books + "/" + str;
							}
							fw.write(a.getUsername() + "/" + a.getPassword() + books + "\n");
						}
					}
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	/**
	 * Borrowing, set the book unavailable
	 * @param book
	 * @return
	 */
	public boolean borrowBook(Book book) {
		users.add(new User(CreateAccount.username, CreateAccount.password, new ArrayList<String>()));
		String username = Login_System.username;
		String password = Login_System.password;
		boolean isLogged = Login_System.isLogged;
		
		currentUser.setUsername(username);
		currentUser.setPassword(password);
		
		// if user has logged in
		if (isLogged) {
			// if the book has been borrowed
			if (isBorrowed(book)) {
				JFrame errWindow = new JFrame();
				errWindow.setTitle("Error");
				errWindow.setLayout(null);
				JLabel errMsg = new JLabel("Sorry, this book is unavailable right now!");
				errMsg.setFont(new Font("Serif", Font.PLAIN, 20));
				errMsg.setBounds(40, 0, 400, 100);
				errWindow.add(errMsg);
				errWindow.setSize(450, 125);
				errWindow.setLocationRelativeTo(null);
				errWindow.setResizable(false);
				errWindow.setVisible(true);
				errWindow.toFront();
				return false;
			} else { // if the book hasn't been borrowed
				// mark book as unavailable and add the book to user's borrowlist
				book.setCheckedOut(true);
				book.setCheckedOutBy(currentUser.getUsername());
				// append the new book user borrowed to its profile
				updateUser(currentUser, book.getTitle());
				// pop up new window
				JFrame newWindow = new JFrame();
				newWindow.setTitle("Successs");
				newWindow.setLayout(null);
				JLabel newMsg = new JLabel("Okay, this book is yours now!");
				newMsg.setFont(new Font("Serif", Font.PLAIN, 20));
				newMsg.setBounds(40, 0, 300, 100);
				newWindow.add(newMsg);
				newWindow.setSize(350, 125);
				newWindow.setLocationRelativeTo(null);
				newWindow.setResizable(false);
				newWindow.setVisible(true);
				newWindow.toFront();
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
		String username = Login_System.username;
		String password = Login_System.password;
		boolean isLogged = Login_System.isLogged;
		if (!isLogged) {
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
		} else {
		int index = 0;
		ArrayList<String> temp = null;
		// loop through the users list
		for (User u : users) {
			if (u.getUsername().length() <= 0) {
				break;
			} else {
				if (u.getBooksCheckedOut() == null) return false;
				ArrayList<String> borrowedBooks = u.getBooksCheckedOut();
				// if find the book been checked out, remove it from the 
				// checked list, save the index of that user.
				if ( borrowedBooks.contains(book.getTitle()) ) {
					index = users.indexOf(u);
					borrowedBooks.remove(book.getTitle());
					temp = borrowedBooks;
				}
			}
		}
		users.get(index).setBooksCheckedOut(temp);
		int bookIndex = inLibrary(book.getTitle());
		lib.get(bookIndex).setCheckedOut(false);
		lib.get(bookIndex).setCheckedOutBy(users.get(index).getUsername());
		
		try {
			FileWriter fw = new FileWriter("database/users.txt", false);
			fw.write("username            password                borrowed-books\n");
			for (User a : users) {
				if (a.getUsername().length() <= 0) {
					break;
				} else {
					if(a.getBooksCheckedOut() == null || a.getBooksCheckedOut().size() == 0) {
						fw.write(a.getUsername() + "/" + a.getPassword() + "/\n");
					} else {
						String books = "";
						for (String str : a.getBooksCheckedOut()) {
							books = books + "/" + str;
						}
						fw.write(a.getUsername() + "/" + a.getPassword() + books + "\n");
					}
				}
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
		}
	}
	
//	/**
//	 * Add the book with its related information
//	 * @param info
//	 * @return
//	 */
	public boolean addBook(String info) {
//		// if it is the librarian logged in
//		if (Login_System.username.equals("admin")) {
//			
//		}
		String[] infos = new String[6];
		// seperate the info to 6 parts
	    infos = info.split("/");
	    Book newbook = new Book(infos[0], infos[1], infos[2], infos[3], infos[4], false, " ", infos[5]);
	    // if the book is already exists in the library
	    if (inLibrary(newbook.getTitle()) != -1) {
	    		return false;
	    } else {
	    		lib.add(newbook);
	    		try {
					FileWriter fw = new FileWriter("database/BookInfo.txt", true);
					fw.write(info + "\n");
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return true;
	    }
	}
//	
//	/**
//	 * Remove the book from the library
//	 * @param book
//	 * @return
//	 */
//	public boolean removeBook(Book book) {
//		lib.remove(book);
//		return true;
//	}
//	
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
//		ArrayList<String> books = new ArrayList<String>();
		text.setText("");
		// buttons
		search.setText("Search");
		// after press the search button, the text the user has typed would be
		// stored in searchBuffer to future use.
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchBuffer = text.getText();
				search(searchBuffer);
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
				// trigger the create account class and ask user to create an account
				CreateAccount.main();
			}
		});
		
		borrow.setText("Borrow");
		borrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = inLibrary(searchBuffer);
				Book borrowed = lib.get(index);
				borrowBook(borrowed);
			}
		});
		ret.setText("Return");
		ret.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = inLibrary(searchBuffer);
				Book returned = lib.get(index);
				returnBook(returned);
				
			}
		});
		viewProfile.setText("Profile");
		viewProfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame profileWindow = new JFrame();
				profileWindow.setTitle("Profile");
				profileWindow.setLayout(null);
				JLabel username = new JLabel("User name: " + Login_System.username);
				username.setFont(new Font("Serif", Font.PLAIN, 20));
				username.setBounds(40, 0, 460, 100);
				ArrayList<String> temp = new ArrayList<String>();
				for (User u : users) {
					if (u.getUsername().equals(Login_System.username) ) {
						temp = u.getBooksCheckedOut();
					}
				}
				JLabel title = new JLabel("Books you borrowed: ");
				String booksName = "";
				for (String s : temp) {
					booksName += s + "\n";
				}
				JTextArea bookBorrowed = new JTextArea("" + booksName);
				title.setFont(new Font("Serif", Font.PLAIN, 20));
				bookBorrowed.setBounds(40, 120, 400, 100);
				title.setBounds(40, 60, 460, 100);
				profileWindow.add(title);
				profileWindow.add(username);
				profileWindow.add(bookBorrowed);
				profileWindow.setSize(500, 300);
				profileWindow.setLocationRelativeTo(null);
				profileWindow.setResizable(false);
				profileWindow.setVisible(true);
				profileWindow.toFront();
				
			}
		});
		// description part
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setEditable(false);
		
		// buttons
		JRadioButton rButton1 = new JRadioButton("Chemistry       ");
		rButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton1.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton2 = new JRadioButton("Alchemy         ");
		rButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton2.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton3 = new JRadioButton("Physics         ");
		rButton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton3.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton4 = new JRadioButton("Economy          ");
		rButton4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton4.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton5 = new JRadioButton("English         ");
		rButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton5.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton6 = new JRadioButton("Spanish         ");
		rButton6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton6.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton7 = new JRadioButton("Chinese         ");
		rButton7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton7.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton8 = new JRadioButton("Computer Science");
		rButton8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton8.getText();
				subject = subject.trim();
			}
		});
		JRadioButton rButton9 = new JRadioButton("Biology         ");
		rButton9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				subject = rButton9.getText();
				subject = subject.trim();
			}
		});
		// put the button in one group so that only one can be 
		// choose at the same time.
		ButtonGroup bg = new ButtonGroup();
		bg.add(rButton1);
		bg.add(rButton2);
		bg.add(rButton3);
		bg.add(rButton4);
		bg.add(rButton5);
		bg.add(rButton6);
		bg.add(rButton7);
		bg.add(rButton8);
		bg.add(rButton9);
		
		// add icon
		ImageIcon iconImg = new ImageIcon("imgs/icon.jpeg");
		icon = new JLabel(iconImg);
		//add background
		backgroundImg = new ImageIcon("imgs/background.jpg");
		Image img1 = backgroundImg.getImage().getScaledInstance(1100, 600, Image.SCALE_SMOOTH);
		background = new JLabel(new ImageIcon(img1));
		// initial book's front page
		bookImg = new ImageIcon("imgs/default1.jpg");
		Image img = bookImg.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
		frontPage = new JLabel(new ImageIcon(img));
		// add description part
		descripTitle = new JLabel("Description");
		// add book name
		bookName = new JLabel();
		
		icon.setBounds(30, 5, iconImg.getIconWidth(), iconImg.getIconHeight());
		icon.setSize(300, 45);
		background.setBounds(0, 0, 1100, 600);
		frontPage.setBounds(650, 60, 400, 500);
		search.setBounds(30, 50, 170, 50);
		login.setBounds(720, 20, 80, 30);
		ret.setBounds(900, 20, 80, 30);
		viewProfile.setBounds(990, 20, 80, 30);
		signup.setBounds(630, 20, 80, 30);
		borrow.setBounds(810, 20, 80, 30);
		text.setBounds(210, 50, 390, 50);
		bookName.setBounds(210, 100, 700, 50);
		area.setBounds(30, 150, 470, 450);
		list.setBounds(215, 100, 380, 150);
		subjectPanel.setBounds(30, 150, 170, 350);
		descripTitle.setBounds(350, 300, 150, 50);
		description.setBounds(210, 350, 390, 150);
		
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
		jf.add(borrow);
		jf.add(ret);
		jf.add(viewProfile);
		jf.add(text);
//		jf.add(list);
		jf.add(frontPage);
		jf.add(icon);
		jf.add(bookName);
		jf.add(description);
		jf.add(descripTitle);
//		jf.add(background);
		
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
