/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author simpl_000
 */
public class LeaseData {
    /**
     * Variables
     */
    private IntegerProperty hallId,
            roomNumber,
            leaseId,
            studentId,
            occupied,
            cleanStatus;
    private Student student;
    
    /**
     * @name    LeaseData
     * @desc    Default Constructor
     */
    public LeaseData() { }
    
    /**
     * @name    LeaseData
     * @desc    Override Constructor
     * 
     * @param   hallId
     * @param   roomNumber
     * @param   leaseId
     * @param   studentId
     * @param   occupied
     * @param   cleanStatus
     */
    public LeaseData(Integer hallId, Integer roomNumber, Integer leaseId, Integer studentId, Integer occupied, Integer cleanStatus) {
        this.hallId = new SimpleIntegerProperty(hallId);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.leaseId = new SimpleIntegerProperty(leaseId);
        this.studentId = new SimpleIntegerProperty(studentId);
        this.occupied = new SimpleIntegerProperty(occupied);
        this.cleanStatus = new SimpleIntegerProperty(cleanStatus);
    }
    
    /**
     * @name    getHallId
     * @desc    Retrieve Hall ID
     * @return  Integer
     */
    public Integer getHallId() {
        return hallId.getValue();
    }

    /**
     * @name    setHallId
     * @desc    Set Hall ID
     * @param   hallId
     */
    public void setHallId(IntegerProperty hallId) {
        this.hallId = hallId;
    }

    /**
     * @name    getRoomNumber
     * @desc    Retrieve Hall Number
     * @return  IntegerProperty
     */
    public Integer getRoomNumber() {
        return roomNumber.intValue();
    }

    /**
     * @name    setRoomNumber
     * @desc    Set Room Number
     * @param   roomNumber 
     */
    public void setRoomNumber(IntegerProperty roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @name    getLeaseId
     * @desc    Retrieve Lease ID
     * @return  IntegerProperty
     */
    public Integer getLeaseId() {
        return leaseId.intValue() != 0 ? leaseId.intValue() : null;
    }

    /**
     * @name    setLeaseId
     * @desc    Set Lease ID
     * @param   leaseId 
     */
    public void setLeaseId(IntegerProperty leaseId) {
        this.leaseId = leaseId;
    }

    /**
     * @name    getStudentId
     * @desc    Retrieve Student ID
     * @return  IntegerProperty
     */
    public Integer getStudentId() {
        return studentId.intValue() != 0 ? studentId.intValue() : null;
    }
    
    /**
     * @name    getStudentName
     * @desc    Retrieve Student Name
     * @return  String
     */
    public String getStudentName() {
        // Null Check
        if(this.getStudentId() == null)
            return "";
        
        // Get Student
        this.student = Database.getStudentFromId(this.getStudentId());
        return (this.student != null ? (this.student.getFirstName() + " " + this.student.getLastName()) : "");
    }

    /**
     * @name    setStudentId
     * @desc    Set Student ID
     * @param   studentId 
     */
    public void setStudentId(IntegerProperty studentId) {
        this.studentId = studentId;
    }

    /**
     * @name    getOccupied
     * @desc    Check if the lease is occupied or not
     * @return  boolean
     */
    public boolean getOccupied() {
        return (occupied.intValue() == 1);
    }
    
    /**
     * @name    getOccupiedStatus
     * @desc    Get the string version of whether the lease is occupied
     * @return  String
     */
    public String getOccupiedStatus() {
        return this.getOccupied() ? "Occupied" : "Unoccupied";
    }

    /**
     * @name    setOccupied
     * @desc    Set Occupied Status
     * @param   occupied 
     */
    public void setOccupied(IntegerProperty occupied) {
        this.occupied = occupied;
    }

    /**
     * @name    getCleanStatus
     * @desc    Retrieve Clean Status
     * @return  IntegerProperty
     */
    public Integer getCleanStatus() {
        return cleanStatus.intValue();
    }
    
    /**
     * @name    getCleanStatusName
     * @desc    Retrieve Clean Status as a String
     * @return  String
     */
    public String getCleanStatusName() {
        return CleaningStatus.getStatus(this.getCleanStatus());
    }

    /**
     * @name    setCleanStatus
     * @desc    Set Clean Status
     * @param   cleanStatus 
     */
    public void setCleanStatus(IntegerProperty cleanStatus) {
        this.cleanStatus = cleanStatus;
    }
}
