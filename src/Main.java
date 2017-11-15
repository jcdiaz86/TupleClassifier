import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception{

        //fill training set from file
        Set trainingSet = new Set();
        trainingSet.addFile(new File("data.txt"));
        trainingSet.print(); System.out.println();

        //create attributes
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
        attributeList.add(new Attribute("age", 1, "youth middle_aged senior"));
        attributeList.add(new Attribute("income", 2, "low medium high"));
        attributeList.add(new Attribute("student", 3, "no yes"));
        attributeList.add(new Attribute("credit", 4, "fair excellent"));
        //attributeList.add(new Attribute("buys", 5, "no yes"));

        //Crete tree and partition by attribute
        Tree decisionTree = new Tree(trainingSet, attributeList);
        decisionTree.partitionTree(5); //classIndex is the index of the column that has the label in the training set
        decisionTree.printChildren();

        //test the decision tree
        ArrayList<String> tuple = new ArrayList<String>();
        tuple.add("15");
        tuple.add("middle_aged");
        tuple.add("low");
        tuple.add("no");
        tuple.add("fair");
        String decision = decisionTree.getClass(tuple, attributeList);
        System.out.println("Test Result: buys: "+ decision);
    }

}