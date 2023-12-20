
/**
 * Attendance class to implement getters and setters to access the data from the data file
 * @author bailoni,nalapat3,rkanduk2,sparvat6,sdonga,spenme11
 *
 */
public class Attendance {

    //Input attributes
    private String ASURITE;
    private int times;

    /**
     * Constructor of the Attendance class
     * @param ASURITE ASU Id of the student
     * @param times Number of times student attended the class
     */
    public Attendance(String ASURITE, int times) {
        this.ASURITE = ASURITE;
        this.times = times;
    }
    
    //Getters and Setters

    public String getASURITE() {
        return ASURITE;
    }

    public void setASURITE(String ASURITE) {
        this.ASURITE = ASURITE;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}


