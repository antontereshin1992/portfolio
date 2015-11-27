package ua.testprojectsolveast.refactor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AddressDb {


    /**
     * Load JDBC driver for Oracle Data Base
     * */
    static {
        try {
            Class.forName("oracle.jdbc.ThinDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new entry about person
     * */
    public void addPerson(Person person) {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
             PreparedStatement statement = connection.prepareStatement("insert into AddressEntry values (?, ?, ?)")) {
            statement.setLong(1, System.currentTimeMillis());
            statement.setString(2, person.getName());
            statement.setString(3, person.getPhoneNumber().getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Looks up the given person, null if not found.
     */
    public Person findPersonByName(String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
             PreparedStatement statement = connection.prepareStatement("select * from AddressEntry where name = '" + name + "'");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String foundName = resultSet.getString("name");
                PhoneNumber phoneNumber = new PhoneNumber(resultSet.getString("phoneNumber"));
                return new Person(foundName, phoneNumber);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Return all entries from data base
     * */
    public List<Person> getAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
             PreparedStatement statement = connection.prepareStatement("select * from AddressEntry");
             ResultSet resultSet = statement.executeQuery()) {
            List<Person> entries = new LinkedList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                PhoneNumber phoneNumber = new PhoneNumber(resultSet.getString("phoneNumber"));
                Person person = new Person(name, phoneNumber);
                entries.add(person);
            }
            return entries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
