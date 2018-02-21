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
    /**
     * Variables
     */
    private final IntegerProperty hallId;
    private final StringProperty hallName;
    private final StringProperty hallShortName;
    private final StringProperty hallAddress;
    private final StringProperty hallPostcode;
    private final StringProperty hallPhone;
    private final IntegerProperty hallRoomCount;

    /**
     * @name    HallRow
     * @desc    Default Constructor
     * @param   hallId
     * @param   hallName
     * @param   hallShortName
     * @param   hallAddress
     * @param   hallPostcode
     * @param   hallPhone
     * @param   hallRoomCount 
     */
    public HallRow(Integer hallId, String hallName, String hallShortName, String hallAddress, String hallPostcode, String hallPhone, int hallRoomCount) {
        this.hallId = new SimpleIntegerProperty(hallId);
        this.hallName = new SimpleStringProperty(hallName);
        this.hallShortName = new SimpleStringProperty(hallShortName);
        this.hallAddress = new SimpleStringProperty(hallAddress);
        this.hallPostcode = new SimpleStringProperty(hallPostcode);
        this.hallPhone = new SimpleStringProperty(hallPhone);
        this.hallRoomCount = new SimpleIntegerProperty(hallRoomCount);
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
     * @name    getHallName
     * @desc    Retrieve Hall Name
     * 
     * @return  String
     */
    public String getHallName() {
        return hallName.getValue();
    }

    /**
     * @name    getHallShortName
     * @desc    Retrieve Hall Short Name
     * 
     * @return  String
     */
    public String getHallShortName() {
        return hallShortName.getValue();
    }

    /**
     * @name    getHallAddress
     * @desc    Retrieve Hall Address
     * 
     * @return  String
     */
    public String getHallAddress() {
        return hallAddress.getValue();
    }

    /**
     * @name    getHallPostcode
     * @desc    Retrieve Hall Postcode
     * 
     * @return  String
     */
    public String getHallPostcode() {
        return hallPostcode.getValue();
    }

    /**
     * @name    getHallPhone
     * @desc    Retrieve Hall Phone Number
     * 
     * @return  String
     */
    public String getHallPhone() {
        return hallPhone.getValue();
    }

    /**
     * @name    getHallRoomCount
     * @desc    Retrieve Hall Room Count
     * 
     * @return  Integer
     */
    public Integer getHallRoomCount() {
        return hallRoomCount.intValue();
    }
}
