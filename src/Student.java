/**
 * Student class with the details present in the roaster file 
 * i.e., ID, First name, Last name, ASURITE ID
 * @author bailoni,nalapat3,rkanduk2,sparvat6,sdonga,spenme11
 *
 */

public class Student {

    /**
     * aInput attributes
     */
    private String ID , FirstName, LastName, ASURITE;
    private int time1,time2;


    /**
     * constructor
     * @param ID
     * @param firstName
     * @param lastName
     * @param ASURITE
     */
    public Student(String ID, String firstName, String lastName, String ASURITE) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
        this.ASURITE = ASURITE;
    }

    /**
     * getter and setters
     * @return string
     */
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getASURITE() {
        return ASURITE;
    }

    public void setASURITE(String ASURITE) {
        this.ASURITE = ASURITE;
    }

    public int getTime() {
        return time1;
    }

    public void setTime(int time) {
        this.time1 = time;
    }

    public int getTime2() {
        return time2;
    }

    public void setTime2(int time2) {
        this.time2 = time2;
    }
}


