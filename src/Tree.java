import org.javatuples.Quartet;
import java.util.ArrayList;

/**
 * Created by jcdia on 11/13/2017.
 */
public class Tree {
    private Node root;
    private ArrayList<Attribute> attrList;

    /**
     * Creates the tree by creating a root node that contains the training Set.
     * @param trainingSet Set of tuples that will be used as the training set
     * @param attributeList the attributes that define the tuples in the training set
     */
    public Tree(Set trainingSet, ArrayList<Attribute> attributeList ){
        this.root=new Node(trainingSet);
        this.attrList = attributeList;
    }

    /**
     * Partitions the root node by dividing training set and
     * placing the paritions in children nodes
     * @param classIndex the row number that contains the tuple label
     */
    public void partitionTree(int classIndex) {

        //gets the best splitting attribute
        Attribute splitting = attributeSelection(attrList, root.getPayload(), classIndex);

        //label the root node with the best splitting attribute
        root.setLabel(splitting.getName());

        //for each of the possible values of the splitting attribute
        //Eg. attribute age has values youth, middle_aged, and senior
        for (Quartet currentQuartet : splitting.getAllowedValues()) {

            String value = (String) currentQuartet.getValue0();
            Set subset = new Set();

            //check if a tuple has the splitting attribute value
            for (int i = 0; i < root.getPayload().getSize(); i++) {
                ArrayList<String> tuple = root.getPayload().getRow(i);
                for (int j = 0; j < tuple.size(); j++) {
                    if (tuple.get(j).equalsIgnoreCase(value)) {
                        tuple.remove(j);
                        //if it does, add it to a new subset
                        subset.addTuple(tuple);
                        break;
                    }
                }
            }

            //add the subset to a new node
            //label the node with attribute value
            //add as child to root node
            Node node = new Node(subset);
            node.setLabel(value);
            root.addChild(node, root);
        }
        root.getPayload().clear();
    }

    /**
     * Prints the root and children of the tree
     */
    public void printTree(){
        if (root==null)
            return;
        else if (!root.getChildren().isEmpty()){
            root.print();
            for (Node current: root.getChildren())
            {
                current.print();
            }
        }
    }

    /**
     * Given a tupple and it's attribute list, determine its class label
     * @param tuple the tuple to be tested
     * @param attributeList the list of attributes of the tuple (must match the attributes from the tree)
     * @return a String with the label of the tuple
     */
    public String getClass(ArrayList<String> tuple, ArrayList<Attribute> attributeList) {
        String matchingAttribute;
        for(Attribute attrCurrent: attributeList)
        {
            //if attribute from attribute name matches root's label
            if( (matchingAttribute=attrCurrent.getName()).equalsIgnoreCase(root.getLabel()))
            {
                //check each child of root
                for(Node nodeCurrent: root.getChildren())
                {
                    //for each value in the tuple
                    for (String currentString: tuple)
                    {
                        //if a children of root has the same label as the tuple
                        if ( currentString.equalsIgnoreCase(nodeCurrent.getLabel()) )
                        {
                            //return the class label that appears the most in the set of the child node;
                            return nodeCurrent.getPayload().getClassLabelFrequent();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Helper method used by partitionTree(int classIndex) to determine the best attribute
     * partitioning the tree
     * @param attributeList
     * @param trainingSet
     * @param classIndex
     * @return
     */
    private Attribute attributeSelection(ArrayList<Attribute> attributeList, Set trainingSet, int classIndex) {
        double lowest = 100;
        int index = -1;
        double info = 0;
        for (int i = 0; i < attributeList.size(); i++) {
            attributeList.get(i).collectStats(trainingSet, classIndex);
            info = attributeList.get(i).getInfo();
            if (info < lowest && info > 0) {
                lowest = info;
                index = i;
            }
        }
        //remove the best attribute from the attributeList - undone
        //splitting on an attribute that has yes or no for values doesn't work
        //because class column also has those values
        return attributeList.get(index);
    }
}




