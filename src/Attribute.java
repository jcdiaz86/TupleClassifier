import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Tuple;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jcdia on 11/10/2017.
 */
public class Attribute {

    private String name;
    //1 quartet for possible discrete values
    //contains name, total tuples, total yes tuples, total no tuples
    private ArrayList<Quartet> allowedValues;
    private Integer columnIndex;
    private double info;

    public Attribute(String name, Integer colIndex, String valuesList) {
        this.name = name;
        columnIndex = colIndex;
        allowedValues = new ArrayList<Quartet>();
        Scanner scanner = new Scanner(valuesList);
        while (scanner.hasNext()) {
            allowedValues.add(new Quartet<String, Integer, Integer, Integer>
                    (scanner.next(), 0, 0, 0));
        }
        info = -1;
    }

    public String getName(){
        return this.name;
    }

    public double getInfo() {
        return this.info;
    }

    public ArrayList<Quartet> getAllowedValues(){
        return this.allowedValues;
    }


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

    //this works if there are only 2 class labels
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

    public void print() {
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
