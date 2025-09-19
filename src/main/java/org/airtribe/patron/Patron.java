package org.airtribe.patron;

import org.airtribe.libraryItem.Book;
import org.airtribe.librarycounter.Counter;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

public class Patron {
    private final String patronId;
    private String name;
    private LocalDate registeredOn;
    private TreeSet<BorrowingHistory> borrowingHistory;

    public Patron(String name) {
        this.patronId = GeneratePatronId.generatePatronId();
        this.name = name;
        this.registeredOn = LocalDate.now();
        this.borrowingHistory = new TreeSet<>();
    }

    public List<Book> searchBookByTitle(String key, Counter counter){
        List<Book> books = counter.findBookByTitle(key);
        counter.displayBooks(books);
        return books;
    }

    public List<Book> searchBookByAuthor(String key, Counter counter){
        List<Book> books = counter.findBookByAuthor(key);
        counter.displayBooks(books);
        return books;
    }

    public List<Book> searchBookByISBN(String key, Counter counter){
        List<Book> books = counter.findBookByISBN(key);
        counter.displayBooks(books);
        return books;
    }

    public Book borrowBook(String bookId, Counter counter){
        Book allotedBook = counter.borrowBook(this, bookId);
        if(allotedBook != null)allotedBook.printBook();
        return allotedBook;
    }

    public boolean returnBook(String bookId, Counter counter) {
        return counter.returnBook(this, bookId);
    }

    public synchronized void logInHistory(BorrowingHistory history){
        this.borrowingHistory.add(history);
    }

    public String getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public TreeSet<BorrowingHistory> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void displayBorrowingHistory() {
        if(borrowingHistory.isEmpty()){
            System.out.println("No any history, No any book borrowed or returned");
            return;
        }
        for(BorrowingHistory history: borrowingHistory){
            history.display();
        }
    }

    public void setBorrowingHistory(TreeSet<BorrowingHistory> borrowingHistory) {
        this.borrowingHistory = borrowingHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron myObject = (Patron) o;
        return patronId.equals(myObject.patronId);
    }

    @Override
    public int hashCode() {
        return patronId.hashCode();
    }

    public void display() {
        System.out.println("Patron ID: "+patronId);
        System.out.println("Patron Name: "+name);
        System.out.println("Registered On: "+registeredOn);
    }

    public void register(Counter counter) {
        counter.addPatron(this);
    }
}
