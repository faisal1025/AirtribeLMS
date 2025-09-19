package org.airtribe.patron;

public class GeneratePatronId {
    private static int patronNumber = 100;
    public static synchronized String generatePatronId() {
        String patronId = "PT14"+patronNumber;
        patronNumber++;
        return patronId;
    }
}
