package org.airtribe.patron;

import org.airtribe.libraryItem.Book;
import java.time.LocalDateTime;

public class BorrowingHistory implements Comparable<BorrowingHistory>{
    private final String historyId;
    private Book book;
    private LocalDateTime actionDate;
    private Action action;

    public BorrowingHistory(Book book, Action action) {
        this.historyId = GenerateHistoryId.generateHistoryId();
        this.book = book;
        this.action = action;
        this.actionDate = LocalDateTime.now();
    }

    public void display() {
        System.out.println("History Id: "+ this.historyId);
        System.out.println("Book Id: "+ this.book.getItemId());
        System.out.println("Book Title: "+ this.book.getTitle());
        System.out.println("Action Date: "+ this.actionDate.toString());
        System.out.println("Action: "+ this.action.name());
    }

    @Override
    public int compareTo(BorrowingHistory o) {
        return this.actionDate.compareTo(o.actionDate);
    }

    public String getHistoryId() {
        return historyId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
