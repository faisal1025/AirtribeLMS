package org.airtribe.inventory;

import org.airtribe.libraryItem.Book;

import java.util.List;

public interface InventoryBookRepository extends InventoryRepository<Book> {
    List<Book> findByTitle(String key);
    List<Book> findByAuthor(String key);
    List<Book> findByISBN(String key);
    List<Book> allAvailableBooks();
    List<Book> allBorrowedBooks();
}
