package org.airtribe.inventory;

import org.airtribe.libraryItem.Book;

import java.util.List;

public class Inventory {
    private final String inventoryId;
    private final InventoryBookRepository bookRepository;

    public Inventory(InventoryBookRepository bookRepository) {
        inventoryId = GenerateInventoryId.generateInventoryId();
        this.bookRepository = bookRepository;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    // find by Title, Author, ISBN
    public List<Book> findBookByTitle(String key) {
        return bookRepository.findByTitle(key);
    }
    public List<Book> findBookByAuthor(String key) {
        return bookRepository.findByAuthor(key);
    }
    public List<Book> findBookByISBN(String key) {
        return bookRepository.findByISBN(key);
    }

    // find an item
    public Book findBookItem(Book item){
        return bookRepository.findItem(item);
    }
    // add an item
    public boolean addBookItem(Book item){
        return bookRepository.addItem(item);
    }
    // remove an item
    public Book removeBookItem(Book item){
        return bookRepository.removeItem(item);
    }
    // update an item
    public Book updateBookItem(String itemId, Book newItem){
        return bookRepository.updateItem(itemId, newItem);
    }
    // find by id
    public Book findBookById(String itemId){
        return bookRepository.findById(itemId);
    }
    public boolean isBookAvailable(Book book) {
        Book bookGot = bookRepository.findItem(book);
        if(bookGot == null) return false;
        return bookGot.isAvailable();
    }

    public List<Book> getAllAvailableBooks(){
        return bookRepository.allAvailableBooks();
    }

    public List<Book> getAllBorrowedBooks(){
        return bookRepository.allBorrowedBooks();
    }
}
