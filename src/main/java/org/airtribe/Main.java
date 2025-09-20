package org.airtribe;


import org.airtribe.libraryItem.Book;
import org.airtribe.librarycounter.Counter;
import org.airtribe.patron.Patron;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
   private final static Counter counter = new Counter();
   private static Patron patron;
   static Scanner scanner = new Scanner(System.in);
   public static void registerNewUser() {
      System.out.println("Enter your name: ");
      String name = scanner.nextLine();
      patron = new Patron(name);
      patron.register(counter);
   }
   public static void adminOperations() {
      System.out.println("Hello Admin, What do you want to perform:- ");
      boolean run = true;
      do{
         System.out.println("What's Next: ");
         System.out.println("1) Add a book");
         System.out.println("2) Remove a book");
         System.out.println("3) Update a book");
         System.out.println("4) Get all available book");
         System.out.println("5) Get all borrowed book");
         System.out.println("6) Return back to main menu");
         int choice = Byte.parseByte(scanner.nextLine());
         switch (choice){
            case 1:
               System.out.println("Enter book Details: ");
               System.out.print("Title: ");
               String title = scanner.nextLine();
               System.out.print("Author: ");
               String author = scanner.nextLine();
               System.out.print("ISBN: ");
               String ISBN = scanner.nextLine();
               System.out.print("Publication Year: ");
               int publicationYear = Integer.parseInt(scanner.nextLine());
               System.out.print("Price: ");
               double price = Double.parseDouble(scanner.nextLine());
               if(counter.addBook(title, author, ISBN, publicationYear, price))
                  System.out.println("Book Successfully Added");
               else
                  System.out.println("Something went wrong, Book not added");
               break;
            case 2:
               System.out.println("Provide Book Id to Remove: ");
               String removeBookId = scanner.nextLine().toUpperCase();
               Book book = counter.removeBook(removeBookId);
               if(book != null) book.printBook();
               break;
            case 3:
               System.out.println("Provide Book Id to Update: ");
               String updateBookId = scanner.nextLine().toUpperCase();
               System.out.println("Enter Details to Update: ");
               System.out.print("Title: ");
               String titleUpdate = scanner.nextLine();
               System.out.print("Author: ");
               String authorUpdate = scanner.nextLine();
               System.out.print("ISBN: ");
               String ISBNUpdate = scanner.nextLine();
               System.out.print("Publication Year: ");
               int publicationYearUpdate = Integer.parseInt(scanner.nextLine());
               System.out.print("Price: ");
               double priceUpdate = Double.parseDouble(scanner.nextLine());
               Book updatedBook = counter.updateBook(updateBookId, titleUpdate,
                       authorUpdate, ISBNUpdate, publicationYearUpdate, priceUpdate);
               if(updatedBook != null) updatedBook.printBook();
               break;
            case 4:
               System.out.println("List of All available Book: ");
               List<Book> availableBooks = counter.getAllAvailableBooks();
               counter.displayBooks(availableBooks);
               break;
            case 5:
               System.out.println("List of All Borrowed Book: ");
               List<Book> borrowedBooks = counter.getAllBorrowedBooks();
               counter.displayBooks(borrowedBooks);
               break;
            case 6:
               run = false;
               break;
         }
      }while(run);
   }

   public static void patronOperations() {
      System.out.println("Hello "+patron.getName()+", What do you want to perform:- ");
      boolean run = true;
      do{
         System.out.println("Choose Activity: ");
         System.out.println("1) Search a book");
         System.out.println("2) Borrow a book");
         System.out.println("3) Return a book");
         System.out.println("4) Show Borrowing History");
         System.out.println("5) Return Back");
         int choice = Byte.parseByte(scanner.nextLine());
         List<Book> bookList = new ArrayList<>();
         switch (choice) {
            case 1:
               System.out.println("Search Book By:- ");
               System.out.println("1) Title");
               System.out.println("2) Author");
               System.out.println("3) ISBN");
               int option = Byte.parseByte(scanner.nextLine());
               switch (option){
                  case 1:
                     System.out.print("Enter book Title: ");
                     String titleKey = scanner.nextLine();
                     bookList = patron.searchBookByTitle(titleKey, counter);
                     break;
                  case 2:
                     System.out.print("Enter book Author: ");
                     String authorKey = scanner.nextLine();
                     bookList = patron.searchBookByAuthor(authorKey, counter);
                     break;
                  case 3:
                     System.out.print("Enter book ISBN: ");
                     String ISBNKey = scanner.nextLine();
                     bookList = patron.searchBookByISBN(ISBNKey, counter);
                     break;
               }
               break;
            case 2:
               System.out.print("Enter the Book ID: ");
               String borrowBookId = scanner.nextLine().toUpperCase();
               Book book = patron.borrowBook(borrowBookId, counter);
               break;
            case 3:
               System.out.print("Enter the Book ID: ");
               String returnBookId = scanner.nextLine().toUpperCase();
               if(patron.returnBook(returnBookId, counter)){
                  System.out.println("Book returned successfully, Hope you like our service");
               }else{
                  System.out.println("Book not returned successfully");
               }
               break;
            case 4:
               patron.displayBorrowingHistory();
               break;
            case 5:
               run = false;
               break;
         }
      }while(run);
   }
   public static void main(String[] args) {

      System.out.println("------------------------WeLcOmE To Library Management-----------------------");
      boolean run = true;
      do{
         System.out.println("Choose who are you:");
         System.out.println("1) New User");
         System.out.println("2) Existing User");
         System.out.println("3) Admin");
         System.out.println("4) Exit from Application");
         int choice = Byte.parseByte(scanner.nextLine());
         switch (choice){
            case 1:
               System.out.println("Enter your name to get register:- ");
               registerNewUser();
               patronOperations();
               break;
            case 2:
               System.out.println("Enter User Number to get the Session");
               String id = scanner.nextLine().toUpperCase();
               patron = counter.getPatronById(id);
               if(patron == null) {
                  System.out.println("User not found");
                  break;
               }
               patronOperations();
               break;
            case 3:
               adminOperations();
               break;
            case 4:
               run = false;
               break;
         }
      }while(run);

   }
}