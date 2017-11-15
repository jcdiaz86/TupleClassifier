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

    public Node(Set payload) {
        this.payload = payload;
        children = new LinkedList<Node>();
        parent = null;
        label="";
    }

    public void partition(Attribute splitting) {
        for (Quartet currentQuartet : splitting.getAllowedValues()) {
            String value = (String) currentQuartet.getValue0();
            Set subset = new Set();
            for (int i = 0; i < payload.getSize(); i++) {
                ArrayList<String> tuple = payload.getRow(i);
                for (int j = 0; j < tuple.size(); j++) {
                    if (tuple.get(j).equalsIgnoreCase(value)) {
                        tuple.remove(j);
                        subset.addTuple(tuple);
                        break;
                    }
                }
            }
            addChild(subset, this);
        }
        payload.clear();
    }

    public void addChild(Set payload, Node parent) {
        this.parent = parent;
        Node child = new Node(payload);
        children.add(child);
    }

    public void addChild(Node child, Node parent)
    {
        this.parent = parent;
        children.add(child);
    }

    public void addChild(Node child) {
        this.parent=null;
        children.add(child);
    }

    public Set getPayload() {
        return payload;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLabel () { return this.label;}


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