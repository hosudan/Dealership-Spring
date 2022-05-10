package Domain;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * DriveTest object to be used in the program
 */
public class DriveTest extends BaseEntity<Long>{
    private Long clientID;
    private Long carID;
    private String cnp;
    private String VINNumber;
    private int rating;


    /**
     * Empty constructor for the DriveTest object.
     */
    public DriveTest(){
        this.clientID=-999L;
        this.carID=-999L;
        this.rating=0;
    }

    /**
     * Constructor for the DriveTest object.
     * @param cnp
     *      represents the CNP of the client, must not be null.
     * @param VINNumber
     *      represents the VINNumber of the tested car, must not be null.
     */
    public DriveTest(Long clientID,Long carID,String cnp,String VINNumber,int rating){
        this.clientID = clientID;
        this.carID = carID;
        this.cnp = cnp;
        this.VINNumber = VINNumber;
        this.rating=rating;
    }

    public DriveTest(Long ID,Long clientID,Long carID,String cnp,String VINNumber,int rating){
        super.setId(ID);
        this.clientID = clientID;
        this.carID = carID;
        this.cnp = cnp;
        this.VINNumber = VINNumber;
        this.rating=rating;
    }

    /**
     * Gets the ID of the Client object.
     * @return
     *      the ID of the Client.
     */
    public Long getClientID() {
        return clientID;
    }

    /**
     * Sets the ID of the Client object.
     * @param clientID
     *      the id to be set, must not be null.
     */
    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    /**
     * Gets the ID of the CAR object.
     * @return
     *      the ID of the Car.
     */
    public Long getCarID() {
        return carID;
    }

    /**
     * Sets the ID of the CAR object.
     * @param carID
     *      the ID to be set, must not be null.
     */
    public void setCarID(Long carID) {
        this.carID = carID;
    }

    /**
     * Gets the CNP of the DriveTest object.
     * @return
     *      the CNP of the Client.
     */
    public String getCnp() {
        return cnp;
    }

    /**
     * Sets the CNP of the DriveTest object.
     * @param cnp
     *      the CNP to be set, must not be null.
     */
    public void setCnp(String cnp) {
        this.cnp = cnp;
    }


    /**
     * Gets the VINNumber of the DriveTest object.
     * @return
     *      the VINNumber of the Car.
     */
    public String getVINNumber() {
        return VINNumber;
    }

    /**
     * Sets the VINNumber of the DriveTest object.
     * @param VINNumber
     *      the VINNumber to be set, must not be null.
     */
    public void setVINNumber(String VINNumber) {
        this.VINNumber = VINNumber;
    }

    /**
     * Gets the rating of the DriveTest object.
     * @return
     *      the rating of the DriveTest.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the DriveTest object.
     * @param rating
     *      the rating to be set.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets a hash code for the DriveTest object.
     * @return
     *      the hash code generated based on the Client attributes.
     */
    @Override
    public int hashCode() {
        int result = cnp.hashCode();
        result = 31 * result + VINNumber.hashCode();
        result = 31 * result + Integer.valueOf(rating).hashCode();
        return result;
    }

    /**
     * Checks if the given Object is equal to the DriveTest object.
     * @param obj
     *      the Object to be checked, must not be null.
     * @return
     *      the truth value of the equality of the two Objects.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || (DriveTest.class != obj.getClass())){
            return false;
        }


        DriveTest driveTest = (DriveTest) obj;

        if(!driveTest.clientID.equals(this.clientID)) {
            return false;
        }
        if(!driveTest.carID.equals(this.carID)) {
            return false;
        }
        if(!driveTest.cnp.equals(this.cnp)) {
            return false;
        }
        if(!driveTest.getVINNumber().equals(this.VINNumber)) {
            return false;
        }
        return driveTest.getRating() == this.rating;
    }

    /**
     * Transforms the DriveTest object into a readable String.
     * @return
     *      a readable String of the DriveTest.
     */
    @Override
    public String toString() {
        return "DriveTest{" +
                "clientID=" + clientID +
                ", carID=" + carID +
                ", cnp='" + cnp + '\'' +
                ", VINNumber='" + VINNumber + '\'' +
                ", rating=" + rating +
                '}' + super.toString();
    }

    /**
     * Transforms the DriveTest object into a String that can be used to store data.
     * @return
     *      a String used in storage.
     */
    @Override
    public String toTxt() {
        return super.getId()+","+this.getClientID()+","+this.getCarID()+","+this.getCnp()+","+this.getVINNumber()+","+this.getRating();
    }

    /**
     * Sets the attributes of the DriveTest object based on the given storage String.
     * @param txt
     *      a storage String.
     */
    @Override
    public void fromTxt(String txt) {
        //CNP, VINNumber, rating
        String[] txtArray = txt.split(",");
        this.setId(Long.parseLong(txtArray[0]));
        this.setClientID(Long.parseLong(txtArray[1]));
        this.setCarID(Long.parseLong(txtArray[2]));
        this.setCnp(txtArray[3]);
        this.setVINNumber(txtArray[4]);
        this.setRating(Integer.parseInt(txtArray[5]));
    }

    public static DriveTest DriveTestfromMessage(String message){
        DriveTest dt = new DriveTest();
        String[] txtArray = message.split(",");
        dt.setId(Long.parseLong(txtArray[0]));
        dt.setClientID(Long.parseLong(txtArray[1]));
        dt.setCarID(Long.parseLong(txtArray[2]));
        dt.setCnp(txtArray[3]);
        dt.setVINNumber(txtArray[4]);
        dt.setRating(Integer.parseInt(txtArray[5]));
        return dt;
    }

    /**
     * Transforms the DriveTest object into a XML-formatted String that can be used to store data.
     * @return
     *      a XML-formatted String used in storage.
     */
    @Override
    public Element toXml(Document doc)
    {
        Element driveTest=doc.createElement("DriveTest");
        Element driveTestID=doc.createElement("id");
        driveTestID.appendChild(doc.createTextNode(this.getId().toString()));

        Element driveTestClientId = doc.createElement("ClientId");
        driveTestClientId.appendChild(doc.createTextNode(this.getClientID().toString()));

        Element driveTestCarId = doc.createElement("CarId");
        driveTestCarId.appendChild(doc.createTextNode(this.getCarID().toString()));

        Element driveTestVIN=doc.createElement("VINNumber");
        driveTestVIN.appendChild(doc.createTextNode(this.getVINNumber()));


        Element driveTestCNP=doc.createElement("CNP");
        driveTestCNP.appendChild(doc.createTextNode(this.getCnp()));

        Element driveTestRating=doc.createElement("Rating");
        driveTestRating.appendChild(doc.createTextNode(String.valueOf(this.getRating())));

        driveTest.appendChild(driveTestID);
        driveTest.appendChild(driveTestCNP);
        driveTest.appendChild(driveTestVIN);
        driveTest.appendChild(driveTestRating);
        return driveTest;
    }

    /**
     * Sets the attributes of the Car object based on the given storage XML Node.
     * @param node
     *      a storage XML-formatted String.
     */
    @Override
    public void fromXml(Node node)
    {
        if(node.getNodeType()==Node.ELEMENT_NODE)
        {
            Element element=(Element)node;
            this.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
            this.setClientID(Long.parseLong(element.getElementsByTagName("ClientId").item(0).getTextContent()));
            this.setCarID(Long.parseLong(element.getElementsByTagName("CarId").item(0).getTextContent()));
            this.setCnp(element.getElementsByTagName("CNP").item(0).getTextContent());
            this.setVINNumber(element.getElementsByTagName("VINNumber").item(0).getTextContent());
            this.setRating(Integer.parseInt(element.getElementsByTagName("Rating").item(0).getTextContent()));
        }
    }


}
