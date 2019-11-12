import static org.junit.Assert.*;

import org.junit.Test;

public class Tester {

	@Test
	public void testInLibrary() {
		Library.loadBooks();
//		assertEquals(0, Library.inLibrary("User Interface for Dummies"));
	}

	@Test
	public void testGetBookInfo() {
		Library lib1 = new Library(); 
		lib1.addBook(new String("Book1"));
		lib1.addBook(new String("Book2"));
		lib1.addBook(new String("Book3"));
		lib1.addBook("Book1Info");
		assertEquals("Book1Info", lib1.getBookInfo("Book1"));
	}

	@Test
	public void testBorrowBook() {
		Library lib1 = new Library(); 
		lib1.addBook(new String("Book1"));
		lib1.addBook(new String("Book2"));
		boolean success;
		success = lib1.borrowBook(new String("Book1"));
		assertTrue(success);
	}

	@Test
	public void testReturnBook() {
		Library lib1 = new Library(); 
		lib1.addBook(new String("Book1"));
		lib1.addBook(new String("Book2"));
		boolean success;
		success = lib1.returnBook(new String("Book1"));
		assertTrue(success);
	}

	@Test
	public void testAddBook() {
		Library lib1 = new Library(); 
		lib1.addBook(new String("Book1"));
		lib1.addBook(new String("Book2"));
		boolean success;	
		success = lib1.addBook(new String("Book1"));
		assertTrue(success);
	}

}
