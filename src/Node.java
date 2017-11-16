import org.javatuples.Quartet;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jcdia on 11/11/2017.
 */
public class Node {

    private Node parent;
    private LinkedList<Node> children;
    private Set payload;
    private String label;

    /**
     * Create a node based on the payload (set) passed
     * @param payload the set passed
     */
    public Node(Set payload) {
        this.payload = payload;
        children = new LinkedList<Node>();
        parent = null;
        label="";
    }

    /**
     * Adds a child to the node
     * @param payload the set to be in the node
     * @param parent the parent of the node
     */
    public void addChild(Set payload, Node parent) {
        this.parent = parent;
        Node child = new Node(payload);
        children.add(child);
    }

    /**
     * Adds a child to the node
     * @param child the child node to be added
     * @param parent the parent of the new child
     */
    public void addChild(Node child, Node parent)
    {
        this.parent = parent;
        children.add(child);
    }

    /**
     * Adds a child to the node
     * Node created by this method does not know it's parent
     * @param child the node child to be added
     */
    public void addChild(Node child) {
        this.parent=null;
        children.add(child);
    }

    /**
     * Gets set of a node
     * @return the set of the node
     */
    public Set getPayload() {
        return payload;
    }

    /**
     * Gets children of node as a linked list
     * @return the children of the node
     */
    public LinkedList<Node> getChildren() {
        return children;
    }

    /**
     * Sets the label of the node
     * @param label a String with the label
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Gets the label of the node
     * @return a String with the label
     */
    public String getLabel () {
        return this.label;
    }

    /**
     * Prints the payload (set) of the node
     */
    public void print() {

        System.out.println("payload:");
        if (payload.isEmpty())
            System.out.println("empty");
        else
            payload.print();
        System.out.println("label:");
        if (label.isEmpty())
            System.out.println("empty");
        else
            System.out.println(label);

        System.out.println();
    }

}