package Domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.sound.midi.SysexMessage;

/**
 * Car object to be used in the program.
 */
public class Car extends BaseEntity<Long>{
    private String VINNumber;
    private String Model;
    private String Manufacturer;

    /**
     * Empty constructor for the Car object.
     */
    public Car(){ }

    /**
     * Constructor for the Car object.
     * @param VINNumber
     *          represents the VIN number of the car, must not be null.
     * @param Model
     *          represents the model of the car, must not be null.
     * @param Manufacturer
     *          represents the manufacturer of the car, must not be null.
     */
    public Car(String VINNumber, String Model, String Manufacturer){
        this.VINNumber = VINNumber;
        this.Model = Model;
        this.Manufacturer = Manufacturer;
    }

    public Car(Long ID,String VINNumber, String Model, String Manufacturer){
        super.setId(ID);
        this.VINNumber = VINNumber;
        this.Model = Model;
        this.Manufacturer = Manufacturer;
    }

    /**
     *Gets the VIN number of the Car object.
     * @return
     *      the VINNumber of the Car.
     */
    public String getVINNumber(){
        return VINNumber;
    }

    /**
     * Sets the VIN number of the Car object.
     * @param VINNumber
     *      the VINNumber to be set, must not be null.
     */
    public void setVINNumber(String VINNumber){
        this.VINNumber = VINNumber;
    }

    /**
     * Gets the model of the Car object.
     * @return
     *      the model of the Car.
     */
    public String getModel(){
        return Model;
    }

    /**
     * Sets the model of the Car object.
     * @param Model
     *      the model to be set, must not be null.
     */
    public void setModel(String Model){
        this.Model = Model;
    }

    /**
     * Gets the manufacturer of the Car object.
     * @return
     *      the manufacturer of the Car.
     */
    public String getManufacturer(){
        return Manufacturer;
    }

    /**
     * Sets the manufacturer of the Car object.
     * @param Manufacturer
     *      the manufacturer to be set, must not be null.
     */
    public void setManufacturer(String Manufacturer){
        this.Manufacturer = Manufacturer;
    }

    /**
     * Checks if the given Object is equal to the Car object.
     * @param obj
     *      the Object to be checked, must not be null.
     * @return
     *      the truth value of the equality of the two Objects.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) {
            return true;
        }
        if(obj == null || (Car.class != obj.getClass())){
            return false;
        }


        Car car = (Car)obj;

        if(!car.getManufacturer().equals(Manufacturer)) {
            return false;
        }
        if(!car.getVINNumber().equals(VINNumber)) {
            return false;
        }
        return car.getModel().equals(Model);
    }

    /**
     * Gets a hash code for the Car object.
     * @return
     *      the hash code generated based on the Car attributes.
     */
    @Override
    public int hashCode(){
        int result = VINNumber.hashCode();
        result = 31 * result + Model.hashCode();
        result = 31 * result + Manufacturer.hashCode();
        return result;
    }

    /**
     * Transforms the Car object into a readable String.
     * @return
     *      a readable String of the Car.
     */
    @Override
    public String toString() {
        return "Car{" +
                "VINNumber='" + VINNumber + '\'' +
                ", Model='" + Model + '\'' +
                ", Manufacturer='" + Manufacturer +
                "'} " + super.toString();
    }

    /**
     * Transforms the Car object into a String that can be used to store data.
     * @return
     *      a String used in storage.
     */
    @Override
    public String toTxt() {
        return super.getId()+","+this.getVINNumber()+","+this.getModel()+","+this.getManufacturer();
    }

    /**
     * Transforms the Car object into a XML Element that can be used to store data.
     * @return
     *      a XML-formatted String used in storage.
     */
    @Override
    public Element toXml(Document doc)
    {
        Element car=doc.createElement("Car");
        Element carID=doc.createElement("id");
        carID.appendChild(doc.createTextNode(this.getId().toString()));
        Element carVIN=doc.createElement("VINNumber");
        carVIN.appendChild(doc.createTextNode(this.getVINNumber()));
        Element carModel=doc.createElement("Model");
        carModel.appendChild(doc.createTextNode(this.getModel()));
        Element carManufacturer=doc.createElement("Manufacturer");
        carManufacturer.appendChild(doc.createTextNode(this.getManufacturer()));
        car.appendChild(carID);
        car.appendChild(carVIN);
        car.appendChild(carModel);
        car.appendChild(carManufacturer);
        return car;
    }

    /**
     * Sets the attributes of the Car object based on the given storage String.
     * @param txt
     *      a storage String.
     */
    @Override
    public void fromTxt(String txt){
        //ID,VIN,Model,Manufacturer
        String[] txtArray = txt.split(",");
        this.setId(Long.parseLong(txtArray[0].strip()));
        this.setVINNumber(txtArray[1].strip());
        this.setModel(txtArray[2].strip());
        this.setManufacturer(txtArray[3].strip());
    }

    public static Car CarfromMessage(String txt){
        Car car = new Car();
        String[] txtArray = txt.split(",");
        car.setId(Long.parseLong(txtArray[0].strip()));
        car.setVINNumber(txtArray[1].strip());
        car.setModel(txtArray[2].strip());
        car.setManufacturer(txtArray[3].strip());
        return car;
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
            this.setVINNumber(element.getElementsByTagName("VINNumber").item(0).getTextContent());
            this.setModel(element.getElementsByTagName("Model").item(0).getTextContent());
            this.setManufacturer(element.getElementsByTagName("Manufacturer").item(0).getTextContent());
        }
    }
}
