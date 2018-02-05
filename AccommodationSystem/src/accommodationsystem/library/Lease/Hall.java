/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Lease;

import accommodationsystem.library.Database;
import accommodationsystem.library.User;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author simpl_000
 */
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
        this.roomCount = roomCount;
        
        // Get Rooms linked to this Hall
        this.rooms = new ArrayList<>();
        for(Room r: Database.getRooms()) {
            if(r.getHallId() == id)
                this.rooms.add(r);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoomCount() {
        return roomCount;
    }
    public String getShortName() {
        return shortName;
    }

    public List<Room> getRooms() {
        return rooms;
    }
    
    public ObservableList<Integer> getFlatsAsCollection() {
        ObservableList<Integer> flatsCollection = FXCollections.observableArrayList();
        for(Room r: this.getRooms()) {
            if(!flatsCollection.contains(r.getFlatId()))
                flatsCollection.add(r.getFlatId());
        }
        return flatsCollection;
    }
    
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
