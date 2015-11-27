package ua.testprojectsolveast.refactor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBook {

    private AddressDb db = new AddressDb();

    static {
        new Checker().start();
    }

    /**
     * @param name
     * @return availability of records with names that has the Swedish phone number
     */
    public boolean hasSwedenMobile(String name) {
        String number = getMobile(name);
        return number != null ? number.startsWith("070") : false;
    }

    /**
     * return count of entries in data base
     * */
    public int getSize() {
        return db.getAll().size();
    }

    /**
     * Gets the given user's mobile phone number,
     * or null if he doesn't have one.
     */
    public String getMobile(String name) {
        Person person = db.findPersonByName(name);
        return person != null ? person.getPhoneNumber().getNumber() : null;
    }

    /**
     * Returns all names in the book truncated to the given length.
     */
    public List getNames(int maxLength) {
        return db.getAll().stream()
                .filter(person -> person.getName().length() > maxLength)
                .map(person -> person.getName().substring(0, maxLength))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Returns all people who have mobile phone numbers.
     */
    public List getSwedenList() {
        return db.getAll().stream()
                .filter(person -> person.getPhoneNumber().getNumber().startsWith("070"))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    static class Checker extends Thread {

        public void run() {
            new AddressBook().getSwedenList();
        }
    }

}
