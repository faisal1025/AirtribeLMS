package org.airtribe.libraryItem;

public abstract class LibraryItem {
    protected final String itemId;
    protected double price;
    LibraryItem(String itemId, double price) {
        this.price = price;
        this.itemId = itemId.isEmpty() ? GenerateItemId.generateBookId() : itemId;
    }
    LibraryItem(double price) {
        this.price = price;
        this.itemId = GenerateItemId.generateBookId();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }
}
