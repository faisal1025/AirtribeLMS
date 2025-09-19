package org.airtribe.librarycounter;

import org.airtribe.libraryItem.Book;
import org.airtribe.patron.Patron;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class PatronManagement {
    private static final Set<Patron> patronRepository = new HashSet<>();

    public static void addPatron(Patron patron) {
        patronRepository.add(patron);
    }

    public static Patron getPatronById(String patronId) {
        Optional<Patron> patron = patronRepository.stream().filter(x -> x.getPatronId().equals(patronId)).findFirst();
        return patron.orElse(null);
    }

    public static void removePatron(String patronId){
        if(patronRepository.removeIf(x -> x.getPatronId().equals(patronId))){
            System.out.println("Patron membership removed Successfully");
        }else{
            System.out.println("Patron does not exist");
        }
    }

    public static void displayPatrons() {
        for (Patron patron : patronRepository) {
            patron.display();
        }
    }

}
