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

import accommodationsystem.library.Lease.CleaningStatus;
import accommodationsystem.library.Lease.Room;
import accommodationsystem.library.Lease.Hall;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeaseData {
    /**
     * Variables
     */
    private IntegerProperty hallId,
            flatNumber,
            roomNumber,
            leaseId,
            studentId,
            occupied,
            cleanStatus;
    private StringProperty startDate,
            endDate;
    private Student student;
    private Hall hall;
    private Room room;
    
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
     * @param   flatNumber
     * @param   roomNumber
     * @param   leaseId
     * @param   studentId
     * @param   startDate
     */
    public LeaseData(Integer hallId, Integer flatNumber, Integer roomNumber, Integer leaseId, Integer studentId, String startDate, String endDate) {
        // Populate LeaseData
        this.hallId = new SimpleIntegerProperty(hallId);
        this.flatNumber = new SimpleIntegerProperty(flatNumber);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.leaseId = new SimpleIntegerProperty(leaseId);
        this.studentId = new SimpleIntegerProperty(studentId);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        
        // Populate Student, Hall & CleaningStatus Objects
        this.student = Database.getStudentFromId(studentId);
        this.hall = Database.getHall(hallId);
        this.room = Database.getRoom(hallId, flatNumber, roomNumber);
        this.occupied = new SimpleIntegerProperty(this.room.getOccupied());
        this.cleanStatus = new SimpleIntegerProperty(this.room.getCleanStatus());
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
     * @name    getHallName
     * @desc    Retrieve Hall Name
     * @return  String
     */
    public String getHallName() {
        return this.hall.getName();
    }
    
    /**
     * @name    getHallNameAndNumber
     * @desc    Retrieve Hall Name and Number
     * @return  String
     */
    public String getHallNameAndNumber() {
        return this.hall.getName() + " - #" + this.hall.getId();
    }

    /**
     * @name    setHallId
     * @desc    Set Hall ID
     * @param   hallId
     */
    public void setHallId(IntegerProperty hallId) {
        this.hallId = hallId;
        this.hall = Database.getHall(this.getHallId());
    }

    /**
     * @name    getFlatNumber
     * @desc    Retrieve Flat Number
     * @return  IntegerProperty
     */
    public Integer getFlatNumber() {
        return flatNumber.intValue();
    }

    /**
     * @name    setFlatNumber
     * @desc    Set Flat Number
     * @param   flatNumber 
     */
    public void setFlatNumber(IntegerProperty flatNumber) {
        this.flatNumber = flatNumber;
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
        this.room = Database.getRoom(this.getHallId(), this.getFlatNumber(), this.getRoomNumber());
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
        return (this.student != null ? (this.student.getFirstName() + " " + this.student.getLastName()) : "");
    }

    /**
     * @name    setStudentId
     * @desc    Set Student ID
     * @param   studentId 
     */
    public void setStudentId(IntegerProperty studentId) {
        this.studentId = studentId;
        this.student = Database.getStudentFromId(this.getStudentId());
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

    /**
     * @name    getStudent
     * @desc    Retrieve Student Class
     * @return  Student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @name    getHall
     * @desc    Retrieve Hall Class
     * @return  Hall
     */
    public Hall getHall() {
        return hall;
    }

    /**
     * @name    getRoom
     * @desc    Retrieve Room Class
     * @return  Room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @name    getStartDate
     * @desc    Retrieve Start Date
     * @return  String
     */
    public String getStartDate() {
        return startDate.getValue();
    }

    /**
     * @name    setStartDate
     * @desc    Set the Start Date
     * @param   startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = new SimpleStringProperty(startDate);
    }

    /**
     * @name    getEndDate
     * @desc    Retrieve End Date
     * @return  String
     */
    public String getEndDate() {
        return endDate.getValue();
    }

    /**
     * @name    setEndDate
     * @desc    Set the End Date
     * @param   endDate 
     */
    public void setEndDate(String endDate) {
        this.endDate = new SimpleStringProperty(endDate);
    }
}
