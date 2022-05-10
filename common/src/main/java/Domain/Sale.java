package Domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Car object to be used in the program.
 */
public class Sale extends BaseEntity<Long>{
    private String CNP;
    private String Model;
    private String Manufacturer;
    private double Price;

    /**
     * Empty constructor for the Car object.
     */
    public Sale(){}

    /**
     * Constructor for the Car object.
     * @param cnp
     *          represents the VIN number of the car, must not be null.
     * @param Model
     *          represents the model of the car, must not be null.
     * @param Manufacturer
     *          represents the manufacturer of the car, must not be null.
     */
    public Sale(String cnp,String Model,String Manufacturer,double price){
        this.CNP = cnp;
        this.Model = Model;
        this.Manufacturer = Manufacturer;
        this.Price = price;
    }

    /**
     *Gets the VIN number of the Car object.
     * @return
     *      the VINNumber of the Car.
     */
    public String getCNP(){
        return CNP;
    }

    /**
     * Sets the VIN number of the Car object.
     * @param cnp
     *      the VINNumber to be set, must not be null.
     */
    public void setCNP(String cnp){
        this.CNP = cnp;
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
     * Gets the manufacturer of the Car object.
     * @return
     *      the manufacturer of the Car.
     */
    public double getPrice(){
        return this.Price;
    }

    /**
     * Sets the manufacturer of the Car object.
     * @param price
     *      the manufacturer to be set, must not be null.
     */
    public void setPrice(double price){
        this.Price = price;
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


        Sale sale = (Sale)obj;

        if(!sale.getManufacturer().equals(Manufacturer)) {
            return false;
        }
        if(!sale.getCNP().equals(CNP)) {
            return false;
        }
        return sale.getModel().equals(Model);
    }

    /**
     * Gets a hash code for the Car object.
     * @return
     *      the hash code generated based on the Car attributes.
     */
    @Override
    public int hashCode(){
        int result = CNP.hashCode();
        result = 31 * result + Model.hashCode();
        result = 31 * result + Manufacturer.hashCode();
        result = 31 * result + Double.valueOf(Price).hashCode();
        return result;
    }

    /**
     * Transforms the Car object into a readable String.
     * @return
     *      a readable String of the Car.
     */
    @Override
    public String toString() {
        return "Sale{" +
                "CNP='" + CNP + '\'' +
                ", Model='" + Model + '\'' +
                ", Manufacturer='" + Manufacturer + '\'' +
                ", price ='" + Price +
                "'} " + super.toString();
    }

    /**
     * Transforms the Car object into a String that can be used to store data.
     * @return
     *      a String used in storage.
     */
    @Override
    public String toTxt() {
        return super.getId()+","+this.getCNP()+","+this.getModel()+","+this.getManufacturer()+","+getPrice();
    }

    /**
     * Transforms the Car object into a XML-formatted String that can be used to store data.
     * @return
     *      a XML-formatted String used in storage.
     */
    @Override
    public Element toXml(Document doc) {

        Element sale=doc.createElement("Sale");
        Element id=doc.createElement("id");
        id.appendChild(doc.createTextNode(this.getId().toString()));
        Element cnp=doc.createElement("cnp");
        cnp.appendChild(doc.createTextNode(this.getCNP()));
        Element carModel=doc.createElement("Model");
        carModel.appendChild(doc.createTextNode(this.getModel()));
        Element carManufacturer=doc.createElement("Manufacturer");
        carManufacturer.appendChild(doc.createTextNode(this.getManufacturer()));
        Element price=doc.createElement("price");
        price.appendChild(doc.createTextNode(Double.toString(this.getPrice())));
        sale.appendChild(id);
        sale.appendChild(cnp);
        sale.appendChild(carModel);
        sale.appendChild(carManufacturer);
        sale.appendChild(price);
        return sale;
    }

    /**
     * Sets the attributes of the Car object based on the given storage String.
     * @param txt
     *      a storage String.
     */
    @Override
    public void fromTxt(String txt){
        //ID,CNP,Model,Manufacturer,Price
        String[] txtArray = txt.split(",");
        this.setId(Long.parseLong(txtArray[0].strip()));
        this.setCNP(txtArray[1].strip());
        this.setModel(txtArray[2].strip());
        this.setManufacturer(txtArray[3].strip());
        this.setPrice(Double.parseDouble(txtArray[4].strip()));
    }

    /**
     * Sets the attributes of the Car object based on the given storage XML-formatted String.
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
            this.setCNP(element.getElementsByTagName("cnp").item(0).getTextContent());
            this.setModel(element.getElementsByTagName("Model").item(0).getTextContent());
            this.setManufacturer(element.getElementsByTagName("Manufacturer").item(0).getTextContent());
            this.setPrice(Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
        }
    }
}
