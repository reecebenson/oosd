/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Reece
 */
public class HallRow {
    private final IntegerProperty hallId;
    private final StringProperty hallName;
    private final StringProperty hallShortName;
    private final StringProperty hallAddress;
    private final StringProperty hallPostcode;
    private final StringProperty hallPhone;
    private final IntegerProperty hallRoomCount;

    public HallRow(Integer hallId, String hallName, String hallShortName, String hallAddress, String hallPostcode, String hallPhone, int hallRoomCount) {
        this.hallId = new SimpleIntegerProperty(hallId);
        this.hallName = new SimpleStringProperty(hallName);
        this.hallShortName = new SimpleStringProperty(hallShortName);
        this.hallAddress = new SimpleStringProperty(hallAddress);
        this.hallPostcode = new SimpleStringProperty(hallPostcode);
        this.hallPhone = new SimpleStringProperty(hallPhone);
        this.hallRoomCount = new SimpleIntegerProperty(hallRoomCount);
    }

    public Integer getHallId() {
        return hallId.intValue();
    }

    public String getHallName() {
        return hallName.getValue();
    }

    public String getHallShortName() {
        return hallShortName.getValue();
    }

    public String getHallAddress() {
        return hallAddress.getValue();
    }

    public String getHallPostcode() {
        return hallPostcode.getValue();
    }

    public String getHallPhone() {
        return hallPhone.getValue();
    }

    public Integer getHallRoomCount() {
        return hallRoomCount.intValue();
    }
    
    
}
