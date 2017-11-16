import org.javatuples.Quartet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jcdia on 11/10/2017.
 */
public class Attribute {

    private String name; //attribute name (E.g age)
    private ArrayList<Quartet> allowedValues; //allowed values for age (E.g. youth, middle_aged, senior)
    private Integer columnIndex; //in what column of a set are the attribute values
    private double info; //Entropy for the attribute

    /**
     * Creates an attribute
     * @param name the name of the attribute (E.g age)
     * @param colIndex //in what column of a set are the attribute values
     * @param valuesList //allowed values for age (E.g. youth, middle_aged, senior)
     */
    public Attribute(String name, Integer colIndex, String valuesList) {
        this.name = name;
        columnIndex = colIndex;

        //Places each possible value in a quartet
        //Each quartet has space for the following:
        //1) The name of the possible value (eg for attribute age one value is senior)
        //2) the total number of times the value appears in a set
        //3) the total number of times the value appears in a set and the tuple is labeled yes
        //4) the total number of times the value appears in a set and the tuple is labeled no
        allowedValues = new ArrayList<Quartet>();
        Scanner scanner = new Scanner(valuesList);
        while (scanner.hasNext()) {
            allowedValues.add(new Quartet<String, Integer, Integer, Integer>
                    (scanner.next(), 0, 0, 0));
        }
        info = -1;
    }

    /**
     * Gets name of the attribute
     * @return String name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the Entropy for the attribute
     * Must be called after the method CollectStats()
     * @return Entropy as a double
     */
    public double getInfo() {
        return this.info;
    }

    /**
     * Returns the list of allowed values
     * @return list of allowed values
     */
    public ArrayList<Quartet> getAllowedValues(){

        return this.allowedValues;
    }

    /**
     * Collects the following statistics for each possible attribute value
     * (1) the total number of times the value appears in a set
     * (2) the total number of times the value appears in a set and the tuple is labeled yes
     * (3) the total number of times the value appears in a set and the tuple is labeled no
     *
     * @param training the training set from which the statistics are derived
     * @param classIndex the column of the set that has the class label
     */
    public void collectStats(Set training, int classIndex) {
        for (int i = 0; i < allowedValues.size(); i++) {
            String allowedValue = (String) allowedValues.get(i).getValue0();
            Integer count = (Integer) allowedValues.get(i).getValue1();
            ArrayList<String> tableColumn = training.getCol(columnIndex);
            String currentString;
            int pos = 0;
            int neg = 0;
            for (int j = 0; j < tableColumn.size(); j++) {
                currentString = tableColumn.get(j);
                if (currentString.equalsIgnoreCase(allowedValue)) {
                    count++;
                    if (training.getRowCol(j, classIndex).equalsIgnoreCase("yes"))
                        pos++;
                    if (training.getRowCol(j, classIndex).equalsIgnoreCase("no"))
                        neg++;
                }
            }
            //if (count > 0) this might not work if the count doesn't increment
            allowedValues.set(i, new Quartet<String, Integer, Integer, Integer>(allowedValue, count, pos, neg));
        }
        setInfo(training);
    }

    /**
     * Helper method for the collectStats(method)
     * Entropy is calculate here for each attribute
     * Note: this method assumes there are only two class label values
     * @param training the training set
     */
    private void setInfo(Set training) {
        double total = 0;
        double setSize = training.getSize();
        for (Quartet current : allowedValues) {
            double weight = (Integer) current.getValue1() / setSize;
            double p1 = (double) (Integer) current.getValue2() / (Integer) current.getValue1();
            double p2 = (double) (Integer) current.getValue3() / (Integer) current.getValue1();
            double info = weight * (-p1 * (Math.log(p1) / Math.log(2)) - p2 * (Math.log(p2) / Math.log(2)));
            if (info == info) //checks that info is not NaN which could be caused by division by 0
                total += info;
        }
        if (total > 0)
            this.info = total;
    }

    /**
     * Print the statistic for each attribute value
     * 1) The name of the possible value (eg for attribute age one value is senior)
     * 2) the total number of times the value appears in a set
     * 3) the total number of times the value appears in a set and the tuple is labeled yes
     * 4) the total number of times the value appears in a set and the tuple is labeled no
     */
    public void printInfo() {
        System.out.println(name);
        for (Quartet current : allowedValues) {
            System.out.println("value: " + current.getValue(0) + "\t\t"
                    + "total tuples " + current.getValue(1) + ", "
                    + "yes tules " + current.getValue(2) + ", "
                    + "no tuples " + current.getValue(3));
        }
        System.out.println("information:" + "\t" + this.info);
    }
}
