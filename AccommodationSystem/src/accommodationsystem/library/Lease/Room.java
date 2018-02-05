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
    private int roomId,
            flatId,
            hallId,
            occupied,
            cleanStatus;

    public Room(int roomId, int flatId, int hallId, int occupied, int cleanStatus) {
        this.roomId = roomId;
        this.flatId = flatId;
        this.hallId = hallId;
        this.occupied = occupied;
        this.cleanStatus = cleanStatus;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getFlatId() {
        return flatId;
    }

    public void setFlatId(int flatId) {
        this.flatId = flatId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public int getCleanStatus() {
        return cleanStatus;
    }

    public void setCleanStatus(int cleanStatus) {
        this.cleanStatus = cleanStatus;
    }
}
