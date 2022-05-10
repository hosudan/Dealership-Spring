package Domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Employee extends BaseEntity<Long>{

    private String cnp;
    private String firstName;
    private String lastName;
    private String role;


    /**
     * Empty constructor for the Employee object.
     */
    public Employee(){
    }

    /**
     * Constructor for the Employee object.
     * @param cnp
     *          represents the CNP of the Employee, must not be null.
     * @param firstName
     *          represents the first name of the Employee.
     * @param lastName
     *          represents the last name of the Employee.
     * @param role
     *          represents the role of the Employee.
     */
    public Employee(String cnp,String firstName,String lastName,String role){
        this.cnp = cnp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }


    /**
     * Gets the CNP of the Employee object.
     * @return
     *      the CNP of the Employee.
     */
    public String getCnp() {
        return cnp;
    }

    /**
     * Sets the CNP of the Employee object.
     * @param cnp
     *      the CNP to be set, must not be null.
     */
    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    /**
     * Gets the first name of the Employee object.
     * @return
     *      the first name of the Employee.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the Employee object.
     * @param firstName
     *      the first name to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the Employee object.
     * @return
     *      the last name of the Employee.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the Employee object.
     * @param lastName
     *      the last name to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the role of the Employee object.
     * @return
     *      the role date of the Employee.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the birth date of the Employee object.
     * @param role
     *      the role to be set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets a hash code for the Employee object.
     * @return
     *      the hash code generated based on the Employee attributes.
     */
    @Override
    public int hashCode() {
        int result = cnp.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    /**
     * Checks if the given Object is equal to the Employee object.
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
        if(obj == null || (Employee.class != obj.getClass())){
            return false;
        }


        Employee employee = (Employee)obj;

        if(!employee.getCnp().equals(this.cnp)) {
            return false;
        }
        if(!employee.getFirstName().equals(this.firstName)) {
            return false;
        }
        if(!employee.getLastName().equals(this.lastName)) {
            return false;
        }
        return employee.getRole().equals(this.role);
    }

    /**
     * Transforms the Employee object into a readable String.
     * @return
     *      a readable String of the Employee.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "CNP='" + cnp + '\'' +
                ", First name='" + firstName + '\'' +
                ", Last name='" + lastName +'\'' +
                ", role='" + role+
                "'} " + super.toString();
    }

    /**
     * Transforms the Employee object into a String that can be used to store data.
     * @return
     *      a String used in storage.
     */
    @Override
    public String toTxt() {
        return super.getId()+","+this.getCnp()+","+this.getFirstName()+","+this.getLastName()+","+this.getRole();
    }

    /**
     * Sets the attributes of the Employee object based on the given storage String.
     * @param txt
     *      a storage String.
     */
    @Override
    public void fromTxt(String txt) {
        //CNP, firstName, lastName, DoB
        String[] txtArray = txt.split(",");
        this.setId(Long.parseLong(txtArray[0].strip()));
        this.setCnp(txtArray[1].strip());
        this.setFirstName(txtArray[2].strip());
        this.setLastName(txtArray[3].strip());
        this.setRole(txtArray[4].strip());
    }

    /**
     * Transforms the Employee object into a XML-formatted String that can be used to store data.
     * @return
     *      a XML-formatted String used in storage.
     */
    @Override
    public Element toXml(Document doc)
    {
        Element employee=doc.createElement("Employee");
        Element id=doc.createElement("id");
        id.appendChild(doc.createTextNode(this.getId().toString()));
        Element cnp=doc.createElement("cnp");
        cnp.appendChild(doc.createTextNode(this.getCnp()));
        Element first=doc.createElement("firstName");
        first.appendChild(doc.createTextNode(this.getFirstName()));
        Element last=doc.createElement("lastName");
        last.appendChild(doc.createTextNode(this.getLastName()));
        Element role=doc.createElement("role");
        role.appendChild(doc.createTextNode(this.getRole()));
        employee.appendChild(id);
        employee.appendChild(cnp);
        employee.appendChild(first);
        employee.appendChild(last);
        employee.appendChild(role);
        return employee;
    }

    /**
     * Sets the attributes of the Employee object based on the given storage XML-formatted String.
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
            this.setCnp(element.getElementsByTagName("cnp").item(0).getTextContent());
            this.setFirstName(element.getElementsByTagName("firstName").item(0).getTextContent());
            this.setLastName(element.getElementsByTagName("lastName").item(0).getTextContent());
            this.setRole(element.getElementsByTagName("role").item(0).getTextContent());
        }
    }

}
