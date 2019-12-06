import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LibraryTest {
	Library lib1 = new Library();
	Book book1 = new Book("Book1", "author1", "2000", "Computer Science", "null", false, "null", "null");
	User user = new User();
	Login_System ls = new Login_System();

	@Test
	void testInLibrary() {
		lib1.main(null);
		// Make sure Library have not Book1
		assertFalse(lib1.inLibrary("Book1") == -1);
		assertTrue(lib1.inLibrary("Book3") == -1);

		lib1.addBook("Book1/author1/2000/Computer Science/null/false/ /null");

		// Book1 in 0, Book2 in 1, Book3 in 2
		assertFalse(lib1.inLibrary("Book1") == 0);
		assertTrue(lib1.inLibrary("Book1") == 23);
		assertFalse(lib1.inLibrary("Book3") == 1);
	}

	@Test
	void testGetBookInfo() {
		lib1.main(null);
		lib1.getBookInfo("Book1/author1/2000/Computer Science/null/false/ /null");
		lib1.getBookInfo("Book1");
		lib1.search("Book1");
	}

	@Test
	void testIsBorrowed() {
		assertFalse(lib1.isBorrowed(book1));
		assertFalse(lib1.borrowBook(book1));
		assertFalse(lib1.isBorrowed(book1));
	}

	@Test
	void testBorrowBook() {
		ls.main();
		boolean success;
		success = lib1.borrowBook(book1);
		assertFalse(success);
		ls.isLogged = true;
		success = lib1.borrowBook(book1);
		assertTrue(success);

	}

	@Test
	void testReturnBook() {
		lib1.main(null);
		Book book1 = new Book("Book1", "author1", "2000", "Computer Science", "null", false, "null", "null");
		boolean success;
		lib1.borrowBook(book1);
		success = lib1.returnBook(book1);
		assertFalse(success);
		Login_System.isLogged = true;
		success = lib1.returnBook(book1);
		assertTrue(success);
	}

	Book booktest1 = new Book();
	Book booktest2 = new Book("book2", "author2", "1900", "Economy", "null", true, "123", "null");

	void setBook() {
		booktest1.setAuthor("author1");
		booktest1.setCheckedOut(false);
		booktest1.setCheckedOutBy(null);
		booktest1.setDate("2000");
		booktest1.setDescription("null");
		booktest1.setSubject("Computer Science");
		booktest1.setTitle("Book1");
		booktest1.setFrontPage("null");

	}

	@Test
	void testToString() {
		assertTrue(booktest1.toString().equals("null, by null"));
		setBook();
		assertTrue(booktest1.toString().equals("Book1, by author1"));
		assertTrue(booktest2.toString().equals("book2, by author2"));
	}

	@Test
	void testPrintAllInfo() {
		assertTrue(booktest1.printAllInfo().equals("null, null, null, null, null, false,null"));
		setBook();
		assertTrue(booktest1.printAllInfo().equals("Book1, author1, 2000, Computer Science, null, false,null"));
		assertTrue(booktest2.printAllInfo().equals("book2, author2, 1900, Economy, null, true,123"));
		assertTrue(booktest1.getFrontPage().equals("null"));
	}

	@Test
	void testCheckUser() {
		ls.main();
		CreateAccount ca = new CreateAccount();
		ca.main();
		ca.writeToFile("test", "test");
		assertTrue(ca.checkDuplicates("test"));
		assertFalse(ls.checkUser("123", "123"));
		assertTrue(ls.checkUser("123", "1234"));
		ForgetPassword fp = new ForgetPassword();
		fp.main();
		ChangePassword cp = new ChangePassword();
		cp.main();
	}

}
