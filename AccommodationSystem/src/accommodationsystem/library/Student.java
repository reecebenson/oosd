/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.library;

public class Student {
    /**
     * Variables
     */
    private int studentId;
    private String firstName, lastName;

    /**
     * @name    Student
     * @desc    Default Constructor
     * @param   studentId
     * @param   firstName
     * @param   lastName 
     */
    public Student(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @name    getStudentId
     * @desc    Retrieve Student ID
     * @return  int
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @name    setStudentId
     * @desc    Set Student ID
     * @param   studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * @name    getFirstName
     * @desc    Retrieve First Name
     * @return  String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @name    setFirstName
     * @desc    Set First Name
     * @param   firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @name    getLastName
     * @desc    Retrieve Last Name
     * @return  String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @name    setLastName
     * @desc    Set Last Name
     * @param   lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
