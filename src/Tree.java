import org.javatuples.Quartet;

import java.util.ArrayList;

/**
 * Created by jcdia on 11/13/2017.
 */
public class Tree {
    private Node root;
    private ArrayList<Attribute> attrList;

    public Tree(Set trainingSet, ArrayList<Attribute> attributeList ){
        this.root=new Node(trainingSet);
        this.attrList = attributeList;
    }

    public void partitionTree(int classIndex) {

        //gets the best splitting attribute
        Attribute splitting = attributeSelection(attrList, root.getPayload(), classIndex);

        //label the root node with the best splitting attribute
        root.setLabel(splitting.getName());

        //for each of the possible values of the splitting attribute
        for (Quartet currentQuartet : splitting.getAllowedValues()) {
            String value = (String) currentQuartet.getValue0();
            Set subset = new Set();
            //check if a tuple has the splitting attribute value
            for (int i = 0; i < root.getPayload().getSize(); i++) {
                ArrayList<String> tuple = root.getPayload().getRow(i);
                for (int j = 0; j < tuple.size(); j++) {
                    if (tuple.get(j).equalsIgnoreCase(value)) {
                        subset.addTuple(tuple); //if it does, move it to a subset
                        break;
                    }
                }
            }
            Node node = new Node(subset);
            node.setLabel(value);
            root.addChild(node, root);
        }
        root.getPayload().clear();
    }

    public void printChildren(){
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

    public String getClass(ArrayList<String> tuple, ArrayList<Attribute> attributeList) {
        String matchingAttribute;
        for(Attribute attrCurrent: attributeList)
        {
            //if attribute name matches root's label
            if( (matchingAttribute=attrCurrent.getName()).equalsIgnoreCase(root.getLabel()))
            {
                //check each child of root
                for(Node nodeCurrent: root.getChildren())
                {
                    //for each value in the tuple
                    for (String currentString: tuple)
                    {
                        //if one matches the label of the child node
                        if ( currentString.equals(nodeCurrent.getLabel()) )
                        {
                            //return the class label that appears the most in the node;
                            return nodeCurrent.getPayload().getClassLabelFrequent();
                        }
                    }
                }
            }
        }
        return null;
    }

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
        //temporary save and remove the best attribute from the attributeList - undone
        //splitting on an attribute that has yes or no for values doesn't work - all the others do.
        //it doesn't work because there are to columns with either value.
        return attributeList.get(index);
    }
}




