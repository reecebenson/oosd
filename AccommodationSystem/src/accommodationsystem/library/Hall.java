/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library;

/**
 *
 * @author simpl_000
 */
public class Hall {
    /**
     * Variables
     */
    private int id,
            room_count;
    private String name,
            address,
            postcode,
            phone;

    /**
     * @name    Hall
     * @desc    Default Constructor
     * @param   id
     * @param   room_count
     * @param   name
     * @param   address
     * @param   postcode
     * @param   phone 
     */
    public Hall(int id, String name, String address, String postcode, String phone, int room_count) {
        this.id = id;
        this.room_count = room_count;
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
