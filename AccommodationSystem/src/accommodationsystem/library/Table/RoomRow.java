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

public class RoomRow {
    /**
     * Variables
     */
    private final IntegerProperty roomId,
            flatId,
            hallId,
            occupied,
            cleanStatus,
            monthlyPrice;

    /**
     * @name    RoomRow
     * @desc    Default Constructor
     * @param   roomId
     * @param   flatId
     * @param   hallId
     * @param   occupied
     * @param   cleanStatus
     * @param   monthlyPrice 
     */
    public RoomRow(Integer roomId, Integer flatId, Integer hallId, Integer occupied, Integer cleanStatus, Integer monthlyPrice) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.flatId = new SimpleIntegerProperty(flatId);
        this.hallId = new SimpleIntegerProperty(hallId);
        this.occupied = new SimpleIntegerProperty(occupied);
        this.cleanStatus = new SimpleIntegerProperty(cleanStatus);
        this.monthlyPrice = new SimpleIntegerProperty(monthlyPrice);
    }

    /**
     * @name    getRoomId
     * @desc    Retrieve Room ID
     * 
     * @return  Integer
     */
    public Integer getRoomId() {
        return roomId.intValue();
    }

    /**
     * @name    getFlatId
     * @desc    Retrieve Flat ID
     * 
     * @return  Integer
     */
    public Integer getFlatId() {
        return flatId.intValue();
    }

    /**
     * @name    getHallId
     * @desc    Retrieve Hall ID
     * 
     * @return  Integer
     */
    public Integer getHallId() {
        return hallId.intValue();
    }

    /**
     * @name    getOccupied
     * @desc    Retrieve Room Occupancy State
     * 
     * @return  Integer
     */
    public Integer getOccupied() {
        return occupied.intValue();
    }

    /**
     * @name    getCleanStatus
     * @desc    Retrieve Room Cleaning Status State
     * 
     * @return  Integer
     */
    public Integer getCleanStatus() {
        return cleanStatus.intValue();
    }

    /**
     * @name    getMonthlyPrice
     * @desc    Retrieve Room Monthly Price
     * 
     * @return  Integer
     */
    public Integer getMonthlyPrice() {
        return monthlyPrice.intValue();
    }
}
