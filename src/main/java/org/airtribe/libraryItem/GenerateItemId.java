package org.airtribe.libraryItem;

class GenerateItemId {
    private static int itemNumber = 100;
    public static synchronized String generateBookId() {
        String bookId = "LI14"+itemNumber;
        itemNumber++;
        return bookId;
    }
}
