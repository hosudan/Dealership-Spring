package Domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Client object to be used in the program
 */
public class Client extends BaseEntity<Long>{
    private String cnp;
    private String firstName;
    private String lastName;
    private Integer Age;


    /**
     * Empty constructor for the Client object.
     */
    public Client(){
        this.Age=-1;
    }

    /**
     * Constructor for the Client object.
     * @param cnp
     *          represents the CNP of the client, must not be null.
     * @param firstName
     *          represents the first name of the Client.
     * @param lastName
     *          represents the last name of the Client.
     * @param age
     *          represents the age of the Client.
     */
    public Client(String cnp,String firstName,String lastName,Integer age){
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Age = age;
    }

    public Client(Long ID,String cnp,String firstName,String lastName,Integer age){
        super.setId(ID);
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Age = age;
    }


    /**
     * Gets the CNP of the Client object.
     * @return
     *      the CNP of the Client.
     */
    public String getCnp() {
        return cnp;
    }

    /**
     * Sets the CNP of the Client object.
     * @param cnp
     *      the CNP to be set, must not be null.
     */
    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    /**
     * Gets the first name of the Client object.
     * @return
     *      the first name of the Client.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the Client object.
     * @param firstName
     *      the first name to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the Client object.
     * @return
     *      the last name of the Client.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the Client object.
     * @param lastName
     *      the last name to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the birth date of the Client object.
     * @return
     *      the birth date of the Client.
     */
    public Integer getAge() {
        return Age;
    }

    /**
     * Sets the birth date of the Client object.
     * @param age
     *      the birth date to be set.
     */
    public void setAge(Integer age) {
        this.Age = age;
    }

    /**
     * Gets a hash code for the Client object.
     * @return
     *      the hash code generated based on the Client attributes.
     */
    @Override
    public int hashCode() {
        int result = cnp.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + Age.hashCode();
        return result;
    }

    /**
     * Checks if the given Object is equal to the Client object.
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
        if(obj == null || (Client.class != obj.getClass())){
            return false;
        }


        Client client = (Client)obj;

        if(!client.getCnp().equals(this.cnp)) {
            return false;
        }
        if(!client.getFirstName().equals(this.firstName)) {
            return false;
        }
        if(!client.getLastName().equals(this.lastName)) {
            return false;
        }
        return client.getAge().equals(this.Age);
    }

    /**
     * Transforms the Client object into a readable String.
     * @return
     *      a readable String of the Client.
     */
    @Override
    public String toString() {
        return "Client{" +
                "CNP='" + cnp + '\'' +
                ", First name='" + firstName + '\'' +
                ", Last name='" + lastName +'\'' +
                ", age='" + Age+
                "'} " + super.toString();
    }

    /**
     * Transforms the Client object into a String that can be used to store data.
     * @return
     *      a String used in storage.
     */
    @Override
    public String toTxt() {
        return super.getId()+","+this.getCnp()+","+this.getFirstName()+","+this.getLastName()+","+this.getAge();
    }

    /**
     * Sets the attributes of the Client object based on the given storage String.
     * @param txt
     *      a storage String.
     */
    @Override
    public void fromTxt(String txt) {
        //CNP, firstName, lastName, DoB
        String[] txtArray = txt.split(",");
        this.setId(Long.parseLong(txtArray[0]));
        this.setCnp(txtArray[1]);
        this.setFirstName(txtArray[2]);
        this.setLastName(txtArray[3]);
        this.setAge(Integer.parseInt(txtArray[4]));
    }

    public static Client ClientfromMessage(String message){
        Client cl = new Client();
        String[] txtArray = message.split(",");
        cl.setId(Long.parseLong(txtArray[0]));
        cl.setCnp(txtArray[1]);
        cl.setFirstName(txtArray[2]);
        cl.setLastName(txtArray[3]);
        cl.setAge(Integer.parseInt(txtArray[4]));
        return cl;
    }

    /**
     * Transforms the Client object into a XML-formatted String that can be used to store data.
     * @return
     *      a XML-formatted String used in storage.
     */
    @Override
    public Element toXml(Document doc) {
        Element client=doc.createElement("Client");
        Element id=doc.createElement("id");
        id.appendChild(doc.createTextNode(this.getId().toString()));
        Element cnp=doc.createElement("cnp");
        cnp.appendChild(doc.createTextNode(this.getCnp()));
        Element frist=doc.createElement("firstName");
        frist.appendChild(doc.createTextNode(this.getFirstName()));
        Element last=doc.createElement("lastName");
        last.appendChild(doc.createTextNode(this.getLastName()));
        Element age=doc.createElement("age");
        age.appendChild(doc.createTextNode(this.getAge().toString()));
        client.appendChild(id);
        client.appendChild(cnp);
        client.appendChild(frist);
        client.appendChild(last);
        client.appendChild(age);
        return client;
    }

    /**
     * Sets the attributes of the Client object based on the given storage XML-formatted String.
     * @param node
     *      a storage XML-formatted String.
     */
    @Override
    public void fromXml(Node node) {
        if(node.getNodeType()==Node.ELEMENT_NODE)
        {
            Element element=(Element)node;
            this.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
            this.setCnp(element.getElementsByTagName("cnp").item(0).getTextContent());
            this.setFirstName(element.getElementsByTagName("firstName").item(0).getTextContent());
            this.setLastName(element.getElementsByTagName("lastName").item(0).getTextContent());
            this.setAge(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));
        }
    }
}
