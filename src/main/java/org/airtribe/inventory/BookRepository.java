package org.airtribe.inventory;

import java.util.*;
import java.util.stream.Collectors;

import org.airtribe.libraryItem.Book;
import org.airtribe.libraryItem.LibraryItem;


public class BookRepository implements InventoryBookRepository {
    private static BookRepository instance;
    HashSet<Book> bookRepository;
    private final HashMap<String, List<Book>> indexByAuthor;
    private final HashMap<String, List<Book>> indexByTitle;
    private final HashMap<String, List<Book>> indexByISBN;

    private BookRepository() {
        this.bookRepository = new HashSet<>();
        indexByTitle = new HashMap<>();
        indexByAuthor = new HashMap<>();
        indexByISBN = new HashMap<>();
    }
    public synchronized static BookRepository getInstance(){
        if(instance == null){
            instance = new BookRepository();
        }
        return instance;
    }

    private boolean addBookByTitle(Book book){
        try{
            List<Book> books = new ArrayList<>(Collections.singletonList(book));
            if(indexByTitle.containsKey(book.getTitle())){
                books = indexByTitle.get(book.getTitle());
                books.add(book);
            }
            indexByTitle.putIfAbsent(book.getTitle(), books);
            return true;
        } catch (Exception e) {
            System.out.println("Books not added on list by Title");
            return false;
        }
    }
    private boolean addBookByAuthor(Book book){
        try{
            List<Book> books = new ArrayList<>(Collections.singletonList(book));
            if(indexByAuthor.containsKey(book.getAuthor())){
                System.out.println("We find the author: " + book.getAuthor());
                books = indexByAuthor.get(book.getAuthor());
                books.add(book);
                return true;
            }
            indexByAuthor.putIfAbsent(book.getAuthor(), books);
            return true;
        } catch (Exception e) {
            System.out.println("Books not added on list by Author"+e.getMessage());
            return false;
        }
    }
    private boolean addBookByISBN(Book book){
        try{
            List<Book> books = new ArrayList<>(Collections.singletonList(book));
            if(indexByISBN.containsKey(book.getISBN())){
                books = indexByISBN.get(book.getISBN());
                books.add(book);
            }
            indexByISBN.putIfAbsent(book.getISBN(), books);
            return true;
        } catch (Exception e) {
            System.out.println("Books not added on list by ISBN");
            return false;
        }
    }

    private boolean removeBookByTitle(Book book){
        try{
            boolean isRemoved = false;
            if(indexByTitle.containsKey(book.getTitle())){
                List<Book> books = indexByTitle.get(book.getTitle());
                isRemoved = books.removeIf(x -> x.getItemId().equals(book.getItemId()));
                indexByTitle.put(book.getTitle(), books);
            }
            return isRemoved;
        } catch (Exception e) {
            System.out.println("Book is not removed from the list by Title");
            return false;
        }
    }
    private boolean removeBookByAuthor(Book book){
        try{
            boolean isRemoved = false;
            if(indexByAuthor.containsKey(book.getAuthor())){
                List<Book> books = indexByAuthor.get(book.getAuthor());
                isRemoved = books.removeIf(x -> x.getItemId().equals(book.getItemId()));
                indexByAuthor.put(book.getAuthor(), books);
            }
            return isRemoved;
        } catch (Exception e) {
            System.out.println("Book is not removed from the list by Author");
            return false;
        }
    }
    private boolean removeBookByISBN(Book book){
        try{
            boolean isRemoved = false;
            if(indexByISBN.containsKey(book.getISBN())){
                List<Book> books = indexByISBN.get(book.getISBN());
                isRemoved = books.removeIf(x -> x.getItemId().equals(book.getItemId()));
                indexByISBN.put(book.getISBN(), books);
            }
            return isRemoved;
        } catch (Exception e) {
            System.out.println("Book is not removed from the list by ISBN");
            return false;
        }
    }

    @Override
    public synchronized boolean addItem(Book item) {
        if(bookRepository.contains(item)) {
            System.out.println("Book already present");
            return false;
        }
        try{
            bookRepository.add(item);
            return addBookByTitle(item) && addBookByAuthor(item) && addBookByISBN(item) ;
        }catch (Exception e){
            bookRepository.removeIf(book -> book.getItemId().equals(item.getItemId()));
            removeBookByAuthor(item);
            removeBookByTitle(item);
            removeBookByISBN(item);
            System.out.println("Book not added in Inventory");
            return false;
        }
    }

    @Override
    public synchronized Book removeItem(Book item) {
        if(!bookRepository.contains(item)) {
            System.out.println("Book doesn't available");
            return null;
        }
        try{
            bookRepository.removeIf(book -> book.getItemId().equals(item.getItemId()));
            if(removeBookByTitle(item) && removeBookByAuthor(item) && removeBookByISBN(item)){
                System.out.println("Book is removed Successfully");
                return item;
            }else{
                throw new RuntimeException("Something went wrong");
            }
        }catch (Exception e){
            bookRepository.add(item);
            addBookByTitle(item);
            addBookByAuthor(item);
            addBookByISBN(item);
            System.out.println("Book not removed from Inventory");
            return null;
        }
    }

    @Override
    public Book findItem(Book item) {
        for (Book obj : bookRepository) {
            if (obj.equals(item)) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public Book findById(String itemId){
        Optional<Book> bookOptional = bookRepository.stream().filter(
                book -> book.getItemId().equals(itemId)).findFirst();
        return bookOptional.orElse(null);
    }

    @Override
    public List<Book> findByTitle(String key) {
        return indexByTitle.get(key);
    }

    @Override
    public List<Book> findByAuthor(String key) {
        return indexByAuthor.get(key);
    }

    @Override
    public List<Book> findByISBN(String key) {
        return indexByISBN.get(key);
    }

    @Override
    public Book updateItem(String itemId, Book newBook){
        Book oldBook = findById(itemId);
        if(oldBook == null) {
            System.out.println("Item not found");
            return null;
        }
        oldBook.setPrice(newBook.getPrice());
        oldBook.setPublicationYear(newBook.getPublicationYear());
        oldBook.setBorrowed(newBook.isBorrowed());
        oldBook.setBorrowedBy(newBook.getBorrowedBy());
        if(!oldBook.getTitle().equals(newBook.getTitle())) {
            removeBookByTitle(oldBook);
            oldBook.setTitle(newBook.getTitle());
            addBookByTitle(oldBook);
        }
        if(!oldBook.getAuthor().equals(newBook.getAuthor())) {
            removeBookByAuthor(oldBook);
            oldBook.setAuthor(newBook.getAuthor());
            addBookByAuthor(oldBook);
        }
        if(!oldBook.getISBN().equals(newBook.getISBN())) {
            removeBookByISBN(oldBook);
            oldBook.setISBN(newBook.getISBN());
            addBookByISBN(oldBook);
        }
        return oldBook;
    }

    @Override
    public List<Book> allAvailableBooks() {
        return bookRepository.stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    @Override
    public List<Book> allBorrowedBooks() {
        return bookRepository.stream().filter(book -> !book.isAvailable()).collect(Collectors.toList());
    }
}
