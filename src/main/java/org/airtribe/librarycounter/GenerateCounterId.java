package org.airtribe.librarycounter;

public class GenerateCounterId {
    private static int counterNumber = 100;
    public static synchronized String generateCounterId() {
        String counterId = "IN14"+counterNumber;
        counterNumber++;
        return counterId;
    }
}
