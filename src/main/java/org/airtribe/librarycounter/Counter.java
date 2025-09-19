package org.airtribe.librarycounter;

import org.airtribe.inventory.BookRepository;
import org.airtribe.inventory.Inventory;
import org.airtribe.libraryItem.Book;
import org.airtribe.patron.Action;
import org.airtribe.patron.BorrowingHistory;
import org.airtribe.patron.Patron;

import java.util.List;

public class Counter {
    private static Counter instance;
    private final String counterId;
    private final Inventory inventory;
    private Counter() {
        this.counterId = GenerateCounterId.generateCounterId();
        this.inventory = new Inventory(BookRepository.getInstance());
    }
    public synchronized static Counter getInstance(){
        if(instance == null){
            instance = new Counter();
        }
        return instance;
    }

    public String getCounterId() {
        return counterId;
    }
    public List<Book> findBookByTitle(String key) {
        return inventory.findBookByTitle(key.toLowerCase());
    }
    public List<Book> findBookByAuthor(String key) {
        return inventory.findBookByAuthor(key.toLowerCase());
    }
    public List<Book> findBookByISBN(String key) {
        return inventory.findBookByISBN(key.toLowerCase());
    }

    public void displayBooks(List<Book> books) {
        if(books == null || books.isEmpty()){
            System.out.println("No any Books found");
            return;
        }
        for(int i = 0; i < books.size(); i++){
            System.out.print(i+")");
            books.get(i).printBook();
        }
    }

    public Book borrowBook(Patron user, String bookId){
        Book originalBook = inventory.findBookById(bookId);
        if(originalBook != null && originalBook.isAvailable()){
            originalBook.setBorrowed(true);
            originalBook.setBorrowedBy(user);
            user.logInHistory(new BorrowingHistory(originalBook, Action.BORROWED));
            return originalBook;
        }else{
            System.out.println("Book is not Available");
        }
        return null;
    }

    public Book getBookById(String itemId) {
        return inventory.findBookById(itemId);
    }

    public boolean returnBook(Patron user, String itemId) {
        Book book = inventory.findBookById(itemId);
        if(book == null){
            System.out.println("Book is not registered in Library");
        }else{
            if(user.equals(book.getBorrowedBy()) && (!book.isAvailable())){
                book.setBorrowed(false);
                book.setBorrowedBy(null);
                user.logInHistory(new BorrowingHistory(book, Action.RETURN));
                return true;
            }
            System.out.println("Author is not authorised or book is already present");
        }
        return false;
    }

    public void addPatron(Patron patron) {
        PatronManagement.addPatron(patron);
    }

    public Patron getPatronById(String patronId) {
        return PatronManagement.getPatronById(patronId);
    }

    public boolean addBook(String title, String author, String ISBN, int publicationYear, double price) {
        Book book = new Book.BookBuilder(title.toLowerCase(), price)
                .setAuthor(author.toLowerCase())
                .setISBN(ISBN.toLowerCase())
                .setPubYear(publicationYear)
                .build();
        return inventory.addBookItem(book);
    }

    public Book removeBook(String bookId){
        Book getBook = inventory.findBookById(bookId);
        return inventory.removeBookItem(getBook);
    }

    public Book updateBook(String bookId, String titleUpdate,
                           String authorUpdate, String ISBNUpdate,
                           int publicationYearUpdate, double priceUpdate) {
        Book newBook = new Book.BookBuilder(titleUpdate.toLowerCase(), priceUpdate)
                .setAuthor(authorUpdate.toLowerCase())
                .setISBN(ISBNUpdate.toLowerCase())
                .setPubYear(publicationYearUpdate)
                .build();
        return inventory.updateBookItem(bookId, newBook);
    }

    public List<Book> getAllAvailableBooks() {
        return inventory.getAllAvailableBooks();
    }

    public List<Book> getAllBorrowedBooks() {
        return inventory.getAllBorrowedBooks();
    }
}
