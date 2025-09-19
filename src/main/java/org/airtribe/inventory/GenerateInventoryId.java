package org.airtribe.inventory;

public class GenerateInventoryId {
    private static int inventoryNumber = 100;
    public static synchronized String generateInventoryId() {
        String inventoryId = "IN14"+inventoryNumber;
        inventoryNumber++;
        return inventoryId;
    }
}
