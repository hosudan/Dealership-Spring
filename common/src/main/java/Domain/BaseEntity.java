package Domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.Serializable;

/**
 * The base for all entities in the program.
 * @param <ID>
 *      the type for the id attribute.
 */
public class BaseEntity<ID> implements Serializable {
    private ID id;

    /**
     * Gets the id of the object.
     * @return
     *      the id of the given type.
     */
    public ID getId(){
        return id;
    }

    /**
     * Sets the id of the object.
     * @param id
     *      matching id for the object type.
     */
    public void setId(ID id){
        this.id = id;
    }

    /**
     * Gets a readable String of the object.
     * @return
     *      a String based on attributes.
     */
    @Override
    public String toString(){
        return "BaseEntity{" + "id=" + id + "}";
    }

    /**
     * Turns the object into a String that can be used to store data.
     * @return
     *      empty String, should not be used on {@code BaseEntity}.
     */
    public String toTxt(){
        return "";
    }

    /**
     * Sets the attributes of the object based on the given storage String
     * @param txt
     *       storage String, should not be used on {@code BaseEntity}.
     */
    public void fromTxt(String txt){

    }

    /**
     * Turns the object into a XML Node that can be used to store data.
     * @return
     *      empty String, should not be used on {@code BaseEntity}.
     */
    public Element toXml(Document doc){
        return doc.createElement("");
    }

    /**
     * Sets the attributes of the object based on the given storage XML Node
     * @param node
     *       storage XML-formatted String, should not be used on {@code BaseEntity}.
     */
    public void fromXml(Node node){

    }
}
