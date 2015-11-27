package ua.testprojectsolveast.refactor;

public class Person {

    private String name;
    private PhoneNumber phoneNumber;

    public Person(String name, PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
