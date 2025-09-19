package org.airtribe.inventory;

import org.airtribe.libraryItem.Book;
import org.airtribe.libraryItem.LibraryItem;

public interface InventoryRepository<T extends LibraryItem> {
    boolean addItem(T item);
    T removeItem(T item);
    T findItem(T item);
    T updateItem(String itemId, T newItem);
    T findById(String itemId);
}
