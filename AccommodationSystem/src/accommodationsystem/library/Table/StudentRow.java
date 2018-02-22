/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.library.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentRow {
    /**
     * Variables
     */
    private final IntegerProperty id;
    private final StringProperty firstName, lastName;

    /**
     * @name    StudentRow
     * @desc    Default Constructor
     * @param   id
     * @param   firstName
     * @param   lastName 
     */
    public StudentRow(Integer id, String firstName, String lastName) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    /**
     * @name    getId
     * @desc    Retrieve ID
     * 
     * @return  Integer
     */
    public Integer getId() {
        return id.intValue();
    }

    /**
     * @name    getFirstName
     * @desc    Retrieve First Name
     * 
     * @return  String
     */
    public String getFirstName() {
        return firstName.getValue();
    }

    /**
     * @name    getLastName
     * @desc    Retrieve Last Name
     * 
     * @return  String
     */
    public String getLastName() {
        return lastName.getValue();
    }
}
