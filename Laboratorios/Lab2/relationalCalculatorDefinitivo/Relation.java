/**
 * This class allows you to create a relation with specified attributes,
 * insert tuples into the relation, delete tuples, and perform equality checks.
 * It also provides methods to get information about the relation, such as
 * the number of columns, number of tuples, and the list of attributes.
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
    * Constructs a Relation with the given attributes.
    * 
    * @param attributes The attribute names for the relation. Must be unique.
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
    /**
     * Verifies if there are any repeated elements in the given array of attributes.
     * 
     * @param attributes The array of attribute names.
     * @return true if all elements are unique, false otherwise.
    */
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
     * Inserts a new tuple into the relation if it has the correct number of elements
     * and is not already present in the relation.
     * 
     * @param tuple The tuple to be inserted.
     */
    public void insert(String [] tuple){
        if (tuple.length == attributes.length && !in(tuple)) {
            tuples.add(tuple);
        }
    }
    /**
     * Returns the number of attributes (columns) in the relation.
     * 
     * @return The number of attributes.
     */
    public int columns(){
        return attributes.length;
    }    
    /**
     * Returns the number of tuples in the relation.
     * 
     * @return The number of tuples.
     */
    public int tuples(){
        return tuples.size();
    }   
    /**
     * Returns the array of attribute names for the relation.
     * 
     * @return The attribute names.
     */
    public String[] attributes(){
        return attributes;
    }    
    /**
     * Returns the list of tuples in the relation.
     * 
     * @return The list of tuples.
     */
    public ArrayList<String []> getTuples(){
        return tuples;
    }
    /**
     * Checks if a given tuple is already present in the relation.
     * 
     * @param tuple The tuple to check.
     * @return true if the tuple is present, false otherwise.
     */
    public boolean in(String [] tuple){
        for (String[] tupleOne : tuples){
            if(compareTuples(tupleOne,tuple)){
                return true;
            }
        }
        return false;
    }
    /**
     * Compares two tuples for equality.
     * 
     * @param tupleOne The first tuple to compare.
     * @param tupleTwo The second tuple to compare.
     * @return true if both tuples are equal, false otherwise.
     */
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
    /**
     * Checks if this relation is equal to another relation.
     * 
     * @param other The other relation to compare with.
     * @return true if both relations are equal, false otherwise.
     */
    public boolean equals(Relation other) {
        if (!Arrays.equals(this.attributes, other.attributes)){
            return false;
        }
        if(tuples()!=other.tuples()){
            return false;
        }
        
        return compareListTuples(this.tuples,other.getTuples());
        
    }
    /**
     * Compares two lists of tuples for equality.
     * 
     * @param tupleOne The first list of tuples to compare.
     * @param tupleTwo The second list of tuples to compare.
     * @return true if both lists of tuples are equal, false otherwise.
     */
    private boolean compareListTuples(ArrayList<String []> tupleOne, ArrayList<String []> tupleTwo){
        for (int i=0; i<tupleOne.size(); i++){
            if (!compareTuples(tupleOne.get(i),tupleTwo.get(i))){
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if this relation is equal to another object.
     * 
     * @param other The object to compare with.
     * @return true if the other object is a Relation and is equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
            return equals((Relation)other);
    }
    /**
     * Returns a string representation of the relation, including the attributes and tuples.
     * 
     * @return The string representation of the relation.
     */
    @Override
    public String toString () {
        String result = "Attributes: " + String.join(",  ", attributes) + "\n";

        result += "Tuples:\n";
        for (String[] tuple : tuples) {
            result += Arrays.toString(tuple) + "\n";
        }

        return result;
    }
    /**
     * Deletes a specified tuple from the relation if it exists.
     * 
     * @param tuple The tuple to be deleted.
     */
    public void delete(String[] tuple ){
        if (in(tuple)){
            tuples.remove(tuple);
        }
        
    }
    }
    
   
    

 

