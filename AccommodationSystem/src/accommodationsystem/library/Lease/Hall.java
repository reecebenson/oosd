/*
 * Reece Benson - Bristol UWE
 * BSc Computer Science
 */
package accommodationsystem.library.Lease;

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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRoom_count() {
        return roomCount;
    }

    public void setRoom_count(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
