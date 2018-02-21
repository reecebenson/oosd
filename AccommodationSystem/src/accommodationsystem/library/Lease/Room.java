/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Lease;

/**
 *
 * @author Reece
 */
public class Room {
    /**
     * Variables
     */
    private int roomId,
            flatId,
            hallId,
            occupied,
            cleanStatus,
            monthlyPrice;

    /**
     * @name    Room
     * @desc    Default Constructor
     * 
     * @param   roomId
     * @param   flatId
     * @param   hallId
     * @param   occupied
     * @param   cleanStatus
     * @param   monthlyPrice 
     */
    public Room(int roomId, int flatId, int hallId, int occupied, int cleanStatus, int monthlyPrice) {
        this.roomId = roomId;
        this.flatId = flatId;
        this.hallId = hallId;
        this.occupied = occupied;
        this.cleanStatus = cleanStatus;
        this.monthlyPrice = monthlyPrice;
    }

    /**
     * @name    getRoomId
     * @desc    Retrieve Room ID
     * 
     * @return  int
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @name    setRoomId
     * @desc    Set Room ID
     * 
     * @param   roomId
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @name    getFlatId
     * @desc    Retrieve Flat ID
     * 
     * @return  int
     */
    public int getFlatId() {
        return flatId;
    }

    /**
     * @name    setFlatId
     * @desc    Set Flat ID
     * 
     * @param   flatId
     */
    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    /**
     * @name    getHallId
     * @desc    Retrieve Hall ID
     * 
     * @return  int
     */
    public int getHallId() {
        return hallId;
    }

    /**
     * @name    setHallId
     * @desc    Set Hall ID
     * 
     * @param   hallId
     */
    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    /**
     * @name    getOccupied
     * @desc    Retrieve Occupied State
     * 
     * @return  int
     */
    public int getOccupied() {
        return occupied;
    }

    /**
     * @name    setOccupied
     * @desc    Set Occupied State
     * 
     * @param   occupied
     */
    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    /**
     * @name    getCleanStatus
     * @desc    Retrieve CleaningStatus State
     * 
     * @return  int
     */
    public int getCleanStatus() {
        return cleanStatus;
    }

    /**
     * @name    setCleanStatus
     * @desc    Set Cleaning Status State
     * 
     * @param   cleanStatus
     */
    public void setCleanStatus(int cleanStatus) {
        this.cleanStatus = cleanStatus;
    }

    /**
     * @name    getMonthlyPrice
     * @desc    Retrieve Monthly Price
     * 
     * @return  int
     */
    public int getMonthlyPrice() {
        return monthlyPrice;
    }

    /**
     * @name    setMonthlyPrice
     * @desc    Set Monthly Price
     * 
     * @param   monthlyPrice
     */
    public void setMonthlyPrice(int monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
}
