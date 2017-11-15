import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Set {

    private ArrayList<ArrayList<String>> table;

    public Set() {
        table = new ArrayList<ArrayList<String>>();
    }

    public void addTuple(ArrayList<String> tuple)
    {
        table.add(tuple);
    }

    public Set getCopy(){
        Set copy = new Set();
        for (ArrayList<String> current : table){
            copy.addTuple(current);
        }
        return copy;
    }

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
    }

    public int getSize()
    {
        return table.size();
    }

    public String getRowCol(int row, int col)
    {
        ArrayList<String> tuple = table.get(row);
        return tuple.get(col);
    }

    public void clear()
    {
        table.clear();
    }

    public boolean isEmpty(){
        if (table.isEmpty())
            return true;
        else
            return false;
    }

    public ArrayList<String> getRow(int index) {
        return table.get(index);
    }

    public ArrayList<String> getCol(int index) {
        ArrayList<String> returnTuple = new ArrayList<>();
        for (int i=0; i<table.size(); i++) {
            ArrayList<String> rowTuple = table.get(i);
            String temp = rowTuple.get(index);
            returnTuple.add(temp);
        }
        return returnTuple;
    }

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
        //System.out.println();
    }
}
