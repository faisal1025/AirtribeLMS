package org.airtribe.libraryItem;

import org.airtribe.patron.Patron;

public class Book extends LibraryItem {
    private String title;
    private String author;
    private String ISBN;
    private int publicationYear;
    private boolean isBorrowed;
    private Patron borrowedBy;

    private Book(BookBuilder builder) {
        super(builder.price);
        this.title = builder.title;
        this.author = builder.author;
        this.ISBN = builder.ISBN;
        this.publicationYear = builder.publicationYear;
        this.isBorrowed = false;
        this.borrowedBy = null;
    }

    public static class BookBuilder{
        private final String title;
        private String author;
        private String ISBN;
        private int publicationYear;
        private final double price;

        public BookBuilder(String title, double price) {
            this.price = price;
            this.title = title;
        }

        public BookBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }
        public BookBuilder setISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }
        public BookBuilder setPubYear(int year) {
            this.publicationYear = year;
            return this;
        }
        public Book build() {
            return new Book(this);
        }

    }

    public boolean isAvailable() {
        return !this.isBorrowed && borrowedBy == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book myObject = (Book) o;
        return itemId.equals(myObject.itemId);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    public void printBook() {
        System.out.println("Book ID: "+this.itemId);
        System.out.println("Title: "+this.title.toUpperCase());
        System.out.println("Author: "+this.author.toUpperCase());
        System.out.println("Author: "+this.ISBN.toUpperCase());
        System.out.println("Publication Year: "+this.publicationYear);
        System.out.println("Is Borrowed: "+this.isBorrowed);
        if(this.isBorrowed){
            System.out.println("Borrowed By Name: "+this.borrowedBy.getPatronId());
            System.out.println("Borrowed By Name: "+this.borrowedBy.getName());
        }

    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Patron getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(Patron borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
