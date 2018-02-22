/*
 * UWE Accommodation System
 * BSc Computer Science
 * 
 * Developers:
 * - Reece Benson
 * - Chris Tapply
 * - Jonas Arud
 */
package accommodationsystem.library.Lease;

import accommodationsystem.library.Database;
import accommodationsystem.library.User;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Hall {
    /**
     * Variables
     */
    private int id,
            roomCount;
    private String name,
            shortName,
            address,
            postcode,
            phone;
    private List<Room> rooms;

    /**
     * @name    Hall
     * @desc    Default Constructor
     * 
     * @param   id
     * @param   roomCount
     * @param   name
     * @param   shortName
     * @param   address
     * @param   postcode
     * @param   phone
     */
    public Hall(int id, String name, String shortName, String address, String postcode, String phone, int roomCount) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.postcode = postcode;
        this.phone = phone;
        this.roomCount = 0;
        
        // Get Rooms linked to this Hall
        this.rooms = new ArrayList<>();
        for(Room r: Database.getRooms()) {
            if(r.getHallId() == id) {
                this.roomCount++;
                this.rooms.add(r);
            }
        }
    }

    /**
     * @name    getId
     * @desc    Retrieve ID
     * @return  int
     */
    public int getId() {
        return id;
    }

    /**
     * @name    getName
     * @desc    Retrieve Name
     * @return  String
     */
    public String getName() {
        return name;
    }

    /**
     * @name    getShortName
     * @desc    Retrieve Short Name
     * @return  String
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @name    getAddress
     * @desc    Retrieve Address
     * @return  String
     */
    public String getAddress() {
        return address;
    }

    /**
     * @name    getPostcode
     * @desc    Retrieve Postcode
     * @return  String
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @name    getPhone
     * @desc    Retrieve Phone
     * @return  String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @name    setPhone
     * @desc    Set Phone
     * @param   phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @name    getRoomCount
     * @desc    Retrieve Room Count
     * @return  int
     */
    public int getRoomCount() {
        return roomCount;
    }

    /**
     * @name    getRooms
     * @desc    Retrieve Rooms
     * @return  List<Room>
     */
    public List<Room> getRooms() {
        return rooms;
    }
    
    /**
     * @name    getFlatsAsCollection
     * @desc    Retrieve the Flats linked to this Hall ID
     * 
     * @return  ObservableList<Integer>
     */
    public ObservableList<Integer> getFlatsAsCollection() {
        ObservableList<Integer> flatsCollection = FXCollections.observableArrayList();
        for(Room r: this.getRooms()) {
            if(!flatsCollection.contains(r.getFlatId()))
                flatsCollection.add(r.getFlatId());
        }
        return flatsCollection;
    }
    
    
    /**
     * @name    getFlatsAsCollection
     * @desc    Retrieve the Room linked to this Hall ID & Flat ID
     * 
     * @param   flatId
     * @return  ObservableList<Integer>
     */
    public ObservableList<Integer> getRoomsAsCollection(int flatId) {
        ObservableList<Integer> roomsCollection = FXCollections.observableArrayList();
        for(Room r: this.getRooms()) {
            if(r.getFlatId() != flatId) continue;
            if(!roomsCollection.contains(r.getRoomId()))
                roomsCollection.add(r.getRoomId());
        }
        return roomsCollection;
    }
}
