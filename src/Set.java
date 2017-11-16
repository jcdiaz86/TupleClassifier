import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Set {

    private ArrayList<ArrayList<String>> table;

    /**
     * Creates an empty set
     */
    public Set() {
        table = new ArrayList<ArrayList<String>>();
    }

    /**
     * Adds a tuple to the set
     * @param tuple to be added
     */
    public void addTuple(ArrayList<String> tuple) {
        table.add(tuple);
    }

    /**
     * Creates a copy of a set leaving the original
     * @return copy of set
     */
    public Set getCopy(){
        Set copy = new Set();
        for (ArrayList<String> current : table){
            copy.addTuple(current);
        }
        return copy;
    }

    /**
     * Returns the most frequent tuple labels in the set
     * Assumes the labels to always be on the last column
     * @return A String of the most frequent label
     */
    public String getClassLabelFrequent()
    {
        int classIndex = table.get(0).size() - 1;
        int pos=0;
        int neg=0;
        for (ArrayList<String> current: this.table)
        {
            if(current.get(classIndex).equalsIgnoreCase("yes"))
                pos++;
            if(current.get(classIndex).equalsIgnoreCase("no"))
                neg++;
        }
        if(pos>neg || pos==neg)
            return "yes";
        else
            return "no";
    }

    /**
     * Adds the records of a file to a set
     * @param file the data file to be imported
     * @throws Exception if cannot read file
     */
    public void addFile(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner scanner;
        String line, text;
        while ((line = br.readLine()) != null) {
            ArrayList<String> tuple = new ArrayList<String>();
            scanner = new Scanner(line);
            while (scanner.hasNext()) {
                tuple.add(scanner.next());
            }
            table.add(tuple);
        }
        br.close();
    }

    /**
     * Returns the size of the set
     * @return int of the size of the set
     */
    public int getSize()
    {
        return table.size();
    }

    /**
     * Retrieves String of set based on row x column
     * @param row the row in the set
     * @param col the column in the set
     * @return String with row x column value
     */
    public String getRowCol(int row, int col)
    {
        ArrayList<String> tuple = table.get(row);
        return tuple.get(col);
    }

    /**
     * Clears the set
     */
    public void clear()
    {
        table.clear();
    }

    /**
     * Determines if a set is empty
     * @return boolean true or false
     */
    public boolean isEmpty(){
        if (table.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Gets row of a set
     * @param index the row number
     * @return the tuple
     */
    public ArrayList<String> getRow(int index) {

        return table.get(index);
    }

    /**
     * Gets column of a set
     * @param index the colum number
     * @return the values for the column
     */
    public ArrayList<String> getCol(int index) {
        ArrayList<String> returnTuple = new ArrayList<>();
        for (int i=0; i<table.size(); i++) {
            ArrayList<String> rowTuple = table.get(i);
            String temp = rowTuple.get(index);
            returnTuple.add(temp);
        }
        return returnTuple;
    }

    /**
     * Removes a tuple from the set
     * @param index the row of the tuple to be removed
     */
    public void removeTuple(int index)
    {
        table.remove(index);
    }

    public void print() {
        for (ArrayList<String> tuple : table) {
            for (String text : tuple) {
                System.out.print(text + " ");
            }
            System.out.println();
        }
    }
}
