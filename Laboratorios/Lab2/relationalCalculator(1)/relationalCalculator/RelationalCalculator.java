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

    public RelationalCalculator(){
        variables=new TreeMap<String,Relation>();
    }


    //Assign a empty relation to a variable
    public void assign(String name, String attributes[] ){
        Relation relation= new Relation(attributes);
        variables.put(name,relation);
        
    }    
    
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
    private void insertTuple(String [] tuple, String a){
        if (variables.containsKey(a)){
            Relation relation=variables.get(a);
            relation.insert(tuple);
            
        }
    }
    
    private void deleteTuple(String [] tuple, String a){
        if (variables.containsKey(a)){
            Relation relation=variables.get(a);
            relation.delete(tuple);
        }
    }
    
    //Assigns the result of an operation to a variable
    // a := b operator c
    //The operators are: p(roject), s(elect), m(ultiply)
    //To project, the relation b is projected with the attributes of the relation c. 
    //To select, the tuples of relation b are selected using as condition the expression that corresponds to matching the attributes of c with the values of his first tuple
    //To multiply, the relation a is multiplied with the relation b. 
    //If this is not possible, the variable a does not change
    public void assign(String a, String b, char operator, String c){
        if(operator=='p'){
            proyect(b,c);
        }else if(operator=='s'){
            
        }else if(operator=='m'){
            
        }
    }   
    private void proyect(String b,String c){
        Relation relationOne= variables.get(b);
        Relation relationTwo= variables.get(c);
        ArrayList<String []> tuplesOne =  relationOne.getTuples();
        ArrayList<String []> tuplesTwo =  relationTwo.getTuples();
        String [] attributes = takeAttributesCommon(relationOne,relationTwo).toArray(new String[0]);
        Relation newRelation = new Relation(attributes);
        if (attributes!=null){
            for (int i=0; i< relationOne.tuples();i++){
                for(int j=0; i<attributes.length;j++){
                    if (tuplesOne.get(i).get(j).equalsIgnoreCase(tuplesTwo.get(i).get(j)){
                        
                    }
                }
            }
        }
    }
    private ArrayList<String> takeAttributesCommon(Relation relationOne, Relation relationTwo){
        ArrayList<String> attributes= new ArrayList<>();
        for (int i=0; i< relationOne.tuples();i++){
            if (relationOne.attributes()[i]==relationTwo.attributes()[i]){
                attributes.add(relationOne.attributes()[i]);
            }
        }
        return attributes ;
    }
    //Consult the variables of a calculator
    public String[] variables(){
        Set<String> keySet = variables.keySet();
        String[] keyArray = keySet.toArray(new String[0]);
        return keyArray;
    }    
           
    //Returns the string represention of a relation. Columns must be aligned.
    public String toString(String variable){
        return variables.get(variable).toString();
        
    }
    
    //If the last operation was successful
    public boolean ok(){
        return false;
    }
}
    



