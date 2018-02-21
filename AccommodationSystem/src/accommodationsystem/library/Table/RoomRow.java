/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Reece
 */
public class RoomRow {
    private final IntegerProperty roomId,
            flatId,
            hallId,
            occupied,
            cleanStatus,
            monthlyPrice;

    public RoomRow(Integer roomId, Integer flatId, Integer hallId, Integer occupied, Integer cleanStatus, Integer monthlyPrice) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.flatId = new SimpleIntegerProperty(flatId);
        this.hallId = new SimpleIntegerProperty(hallId);
        this.occupied = new SimpleIntegerProperty(occupied);
        this.cleanStatus = new SimpleIntegerProperty(cleanStatus);
        this.monthlyPrice = new SimpleIntegerProperty(monthlyPrice);
    }

    public Integer getRoomId() {
        return roomId.intValue();
    }

    public Integer getFlatId() {
        return flatId.intValue();
    }

    public Integer getHallId() {
        return hallId.intValue();
    }

    public Integer getOccupied() {
        return occupied.intValue();
    }

    public Integer getCleanStatus() {
        return cleanStatus.intValue();
    }

    public Integer getMonthlyPrice() {
        return monthlyPrice.intValue();
    }
}
