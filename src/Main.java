import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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
        decisionTree.printTree();

        //test the decision tree
        String decision = decisionTree.getClass(getUserInput(), attributeList);
        System.out.println("Test Result -  buys: "+ decision);
    }

    public static ArrayList<String> getUserInput(){

        ArrayList<String> tuple = new ArrayList<String>();
        String RID = "";
        String age = "";
        String income = "";
        String student = "";
        String credit = "";

        Scanner scan = new Scanner(System.in);

        System.out.println("Add a tuple to test");
        System.out.println("Note that implementation of decision tree is only to 1 level");
        System.out.println("Class label returned is based on majority voting\n");
        System.out.println("Enter the age (Should be middle_aged, youth, or senior):");
        while(!(age.equals("middle_aged")) && !(age.equals("youth")) && !(age.equals("senior")))
        {
            age = scan.nextLine();
        }
        tuple.add(age);

        System.out.println("Enter the income (Should be low, medium, high):");
        while(!(income.equals("low")) && !(income.equals("medium")) && !(income.equals("high")))
        {
            income = scan.nextLine();
        }
        tuple.add(income);

        System.out.println("Enter if they are a student (Should be yes, or no):");
        while(!(student.equals("yes")) && !(student.equals("no")))
        {
            student = scan.nextLine();
        }
        tuple.add(student);

        System.out.println("Enter their credit rating (Should be fair, or excellent):");
        while(!(credit.equals("excellent")) && !(credit.equals("fair")))
        {
            credit = scan.nextLine();
        }
        tuple.add(credit);

        Set myset = new Set(); myset.addTuple(tuple); myset.print(); //did it only for printing

        return tuple;
    }
}