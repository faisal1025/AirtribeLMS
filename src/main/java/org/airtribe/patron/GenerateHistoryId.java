package org.airtribe.patron;

public class GenerateHistoryId {
    private static int historyNumber = 100;
    public static synchronized String generateHistoryId() {
        String historyId = "H14"+historyNumber;
        historyNumber++;
        return historyId;
    }
}
