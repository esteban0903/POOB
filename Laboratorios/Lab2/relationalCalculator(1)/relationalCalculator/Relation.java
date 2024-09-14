/**
 * @author CIS
 * @version 2024
 *
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Relation{
    private String [] attributes;
    private ArrayList<String []> tuples;
    
 
    
    /**
     * Creates a relation with the defined attributes
     */

    public Relation(String [] attributes) {
        if (verifyElementsRepeated(attributes)){
            this.attributes = attributes;
            this.tuples= new ArrayList<>();
        }else{
            this.attributes=new String[0];
            this.tuples= new ArrayList<>();
        }
    }
    
    private boolean verifyElementsRepeated(String [] attributes ){
        List<String> elements=new ArrayList<>();
        for (String element: attributes){
            if (elements.contains(element)){
                return false;
            }
            elements.add(element);
        }
        return true;
    }
    
    /**
     * Insert a tuple in the relation
     */
    public void insert(String [] tuple){
        if (tuple.length == attributes.length && !in(tuple)) {
            tuples.add(tuple);
        }
    }

    public int columns(){
        return attributes.length;
    }    

    public int tuples(){
        return tuples.size();
    }   
    
    public String[] attributes(){
        return attributes;
    }    
    
    public ArrayList<String []> getTuples(){
        return tuples;
    }
    
    public boolean in(String [] tuple){
        for (String[] tupleOne : tuples){
            if(compareTuples(tupleOne,tuple)){
                return true;
            }
        }
        return false;
    }
    
    private boolean compareTuples(String [] tupleOne, String [] tupleTwo){
        if(tupleOne.length != tupleTwo.length){
            return false;
        }
        for (int i=0; i<tupleOne.length;i++){
            if (!tupleOne[i].equalsIgnoreCase(tupleTwo[i])){
                return false;
            }
        }
        return true;
    }
    
    public boolean equals(Relation other) {
        if (!Arrays.equals(this.attributes, other.attributes)){
            return false;
        }
        if(tuples()!=other.tuples()){
            return false;
        }
        
        return compareListTuples(this.tuples,other.getTuples());
        
    }
    private boolean compareListTuples(ArrayList<String []> tupleOne, ArrayList<String []> tupleTwo){
        for (int i=0; i<tupleOne.size(); i++){
            if (!compareTuples(tupleOne.get(i),tupleTwo.get(i))){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(Object other) {
            return equals((Relation)other);
    }
    
    @Override
    public String toString () {
        String result = "Attributes: " + String.join(",  ", attributes) + "\n";

        result += "Tuples:\n";
        for (String[] tuple : tuples) {
            result += Arrays.toString(tuple) + "\n";
        }

        return result;
    }
    
    public void delete(String[] tuple ){
        if (in(tuple)){
            tuples.remove(tuple);
        }
        
    }
    }
    
   
    

 

