import java.util.TreeMap;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;

/** Relational Calculator
 * 
 * @author CIS 2024-02
 */
    
public class RelationalCalculator{
    
    private TreeMap<String, Relation> variables;
    /**
     * Constructs a RelationalCalculator with an empty set of variables.
     */
    public RelationalCalculator(){
        variables=new TreeMap<String,Relation>();
    }

    /**
     * Assigns an empty relation to a variable.
     * 
     * @param name The name of the variable.
     * @param attributes The attribute names for the new relation.
     */
    //Assign a empty relation to a variable
    public void assign(String name, String attributes[] ){
        Relation relation= new Relation(attributes);
        variables.put(name,relation);
        
    }    
    /**
     * Updates a relation by performing a unary operation (insert or delete).
     * 
     * @param a The variable to update.
     * @param unary The unary operation ('i' for insert, 'd' for delete).
     * @param tuple The tuple to insert or delete.
     */
    //Assigns the result of an operation to a variable (unary operations)
    // a := unary tuple
    // The unary operators are: i (nsert), d (elete)
    //If this is not possible, the variable a does not change
    public void update(String a, char unary, String[] tuple){
        if (unary=='i'){
            insertTuple(tuple,a);
        }else if (unary=='d'){
            deleteTuple(tuple,a);
        }
    }    
    /**
     * Inserts a tuple into a relation.
     * 
     * @param tuple The tuple to insert.
     * @param a The variable containing the relation.
     */
    private void insertTuple(String [] tuple, String a){
        if (variables.containsKey(a)){
            Relation relation=variables.get(a);
            relation.insert(tuple);
            
        }
    }
    /**
     * Deletes a tuple from a relation.
     * 
     * @param tuple The tuple to delete.
     * @param a The variable containing the relation.
     */
    private void deleteTuple(String [] tuple, String a){
        if (variables.containsKey(a)){
            Relation relation=variables.get(a);
            relation.delete(tuple);
        }
    }
    
    /**
     * Assigns the result of a binary operation to a variable.
     * 
     * @param a The variable to assign the result to.
     * @param b The first variable in the operation.
     * @param operator The operation to perform ('p' for project, 's' for select, 'm' for multiply).
     * @param c The second variable in the operation.
     */
    //Assigns the result of an operation to a variable
    // a := b operator c
    //The operators are: p(roject), s(elect), m(ultiply)
    //To project, the relation b is projected with the attributes of the relation c. 
    //To select, the tuples of relation b are selected using as condition the expression that corresponds to matching the attributes of c with the values of his first tuple
    //To multiply, the relation a is multiplied with the relation b. 
    //If this is not possible, the variable a does not change
    public void assign(String a, String b, char operator, String c){
        if(operator=='p'){
            proyect(a,b,c);
        }else if(operator=='s'){
            select(a,b,c);
        }else if(operator=='m'){
            multiply(a,b,c);
        }
    }   
    /**
     * Projects the relation b onto the attributes of relation c.
     * 
     * @param a The variable to assign the result to.
     * @param b The variable containing the relation to project.
     * @param c The variable containing the relation with the attributes to project onto.
     */
    private void proyect(String a,String b,String c){
        Relation relationOne= variables.get(b);
        Relation relationTwo= variables.get(c);
        ArrayList<Integer> positions=new ArrayList<>();
        String [] attributes = takeAttributesCommon(relationOne,relationTwo,positions).toArray(new String[0]);
        Relation newRelation = new Relation(attributes);
        if (attributes!=null){
            addTuplesCommon(newRelation,relationOne,relationTwo,positions);
            variables.put(a,newRelation);
        }
    
    }
    /**
     * Finds common attributes between two relations.
     * 
     * @param relationOne The first relation.
     * @param relationTwo The second relation.
     * @param positions The positions of the common attributes in the first relation.
     * @return The list of common attributes.
     */
    private ArrayList<String> takeAttributesCommon(Relation relationOne, Relation relationTwo, ArrayList<Integer> positions){
        ArrayList<String> attributes= new ArrayList<>();
        String[] attributesOne = relationOne.attributes();
        String[] attributesTwo = relationTwo.attributes();
        for (int i = 0; i < attributesOne.length; i++) {
            for (int j = 0; j < attributesTwo.length; j++) {
                if (attributesOne[i].equals(attributesTwo[j])) {
                    attributes.add(attributesOne[i]);
                    positions.add(i); 
                }
        }
    }
        return attributes ;
    }
    /**
     * Adds tuples that have common attributes to a new relation.
     * 
     * @param newRelation The new relation to which tuples are added.
     * @param relationOne The first relation.
     * @param relationTwo The second relation.
     * @param positions The positions of the common attributes.
     */
    private void addTuplesCommon(Relation newRelation,Relation relationOne,Relation relationTwo,ArrayList<Integer> positions){
        ArrayList<String []> tuplesOne =  relationOne.getTuples();
        ArrayList<String []> tuplesTwo =  relationTwo.getTuples();
        for (String[] tupleOne : tuplesOne) {
            for (String[] tupleTwo : tuplesTwo) {
                boolean tupleEqual=true;
                ArrayList<String> tuplesCommon=new ArrayList<>();
                for (int i = 0; i < positions.size(); i++) {
                    int pos = positions.get(i);
                    if (!tupleOne[pos].equalsIgnoreCase(tupleTwo[pos])){
                        tupleEqual=false;
                    }
                    tuplesCommon.add(tupleOne[pos]);
                if (tupleEqual) {
                    newRelation.insert(tuplesCommon.toArray(new String[0]));
                }
                }
            }   
        }
    }
    /**
     * Selects tuples from relation b where attributes match the first tuple of relation c.
     * 
     * @param a The variable to assign the result to.
     * @param b The variable containing the relation to select from.
     * @param c The variable containing the relation with the selection criteria.
     */
    private void select(String a, String b, String c){
        Relation relationOne= variables.get(b);
        Relation relationTwo= variables.get(c);
        ArrayList<Integer> positions=new ArrayList<>();
        String [] attributes = takeAttributesCommon(relationOne,relationTwo,positions).toArray(new String[0]);
        Relation newRelation = new Relation(attributes);
        if (attributes!=null){
            addFirtsTuplesCommon(newRelation,relationOne,relationTwo,positions);
            variables.put(a,newRelation);
        }
    
    }
    /**
     * Adds tuples that match the first tuple of the second relation to a new relation.
     * 
     * @param newRelation The new relation to which tuples are added.
     * @param relationOne The relation from which tuples are selected.
     * @param relationTwo The relation containing the selection criteria.
     * @param positions The positions of the common attributes.
     */
    private void addFirtsTuplesCommon(Relation newRelation,Relation relationOne,Relation relationTwo,ArrayList<Integer> positions){
        ArrayList<String []> tuplesOne =  relationOne.getTuples();
        ArrayList<String []> tuplesTwo =  relationTwo.getTuples();
        for (String[] tupleOne : tuplesOne) {
            boolean tupleEqual=true;
            ArrayList<String> tuplesCommon=new ArrayList<>();
            String [] tupleTwo= tuplesTwo.get(0);
            for (int i = 0; i < positions.size(); i++) {
                int pos = positions.get(i);
                if (!tupleOne[pos].equalsIgnoreCase(tupleTwo[pos])){
                        tupleEqual=false;
                }
                tuplesCommon.add(tupleOne[pos]);
            if (tupleEqual) {
                newRelation.insert(tuplesCommon.toArray(new String[0]));
            }
            }
        }   
    }
    /**
     * Multiplies two relations and assigns the result to a variable.
     * 
     * @param a The variable to assign the result to.
     * @param b The first variable in the operation.
     * @param c The second variable in the operation.
     */
    private void multiply(String a, String b, String c){
        Relation relationOne= variables.get(b);
        Relation relationTwo= variables.get(c);
        String [] attributes = combineArrays(relationOne.attributes(),relationTwo.attributes());
        System.out.println("Array: " + Arrays.toString(attributes));
        Relation newRelation = new Relation(attributes);
        if (attributes!=null){
            addTuplesMultiplyng(newRelation,relationOne,relationTwo);
            variables.put(a,newRelation);
        }
    
    }
    /**
     * Combines two arrays of attribute names.
     * 
     * @param array1 The first array.
     * @param array2 The second array.
     * @return The combined array.
     */
    private String[] combineArrays(String [] array1,String [] array2){
        String[] result = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
    /**
     * Adds tuples resulting from the Cartesian product of two relations.
     * 
     * @param newRelation The new relation to which tuples are added.
     * @param relationOne The first relation.
     * @param relationTwo The second relation.
     */
    private void addTuplesMultiplyng(Relation newRelation, Relation relationOne,Relation relationTwo){
        ArrayList<String []> tuplesOne =  relationOne.getTuples();
        ArrayList<String []> tuplesTwo =  relationTwo.getTuples();
        ArrayList<String []> tuplesMultiply=new ArrayList<>();
        for (int i = 0; i < tuplesOne.size(); i++) {
            for (int j = 0; j < tuplesTwo.size(); j++) {
                String[] combinedTuple = combineArrays(tuplesOne.get(i), tuplesTwo.get(j));
                newRelation.insert(combinedTuple); 
            }
        }
    }
    /**
     * Returns the names of all variables in the calculator.
     * 
     * @return An array of variable names.
     */
    //Consult the variables of a calculator
    public String[] variables(){
        Set<String> keySet = variables.keySet();
        String[] keyArray = keySet.toArray(new String[0]);
        return keyArray;
    }    
    /**
     * Returns the number of variables in the calculator.
     * 
     * @return The number of variables.
     */       
    public int getKeys(){
        return variables.size();
    }
    /**
     * Returns the number of tuples in the relation associated with the given key.
     * 
     * @param key The key for which to get the number of tuples.
     * @return The number of tuples in the relation associated with the key.
     */
    public int getValues(String key){
        return variables.get(key).tuples();
    }
    /**
     * Prints the names and values of all variables in the calculator.
     */
    public void print(){
        for (String key : variables.keySet()) {
            System.out.println("Clave: " + key + ", Valor: " + variables.get(key));
        }
    }
}
    



