

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import java.util.Map;
/**
 * The test class sadsad.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class relationalCalculatorTest
{
    /**
     * Default constructor for test class sadsad
     */
    public relationalCalculatorTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){
    }

    
    @Test
    public void shouldAsignAValidVariable(){
        String [] attributes={"song", "artist", "album", "year"};
        String name= "variableOne";
        RelationalCalculator variables= new RelationalCalculator();
        variables.assign(name,attributes);
        assertEquals(variables.getKeys(),1);
    }
    
    
    @Test
    public void shouldnotAsignAValidVariable(){
        String [] attributes={"song", "artist", "album", "year"};
        String [] attributes2={"song2", "artist2", "album2", "year2"};
        String name= "variableOne";
        RelationalCalculator variables= new RelationalCalculator();
        variables.assign(name,attributes);
        variables.assign(name,attributes2);
        assertEquals(variables.getKeys(),1);
    }
    
    @Test
    public void shouldInsertAValidVariable(){
        String [] attributes = {"song", "artist", "album", "year"};
        String name= "variableOne";
        RelationalCalculator variables= new RelationalCalculator();
        variables.assign(name,attributes);
        
        String [] acrostico={"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        
        variables.update("variableOne", 'i',acrostico);
        
        assertEquals(1, variables.getValues(name));
        
    }
    
    @Test
    public void shouldNotInsertAValidVariable(){
        String [] attributes = {"song", "artist", "album", "year"};
        String name= "variableOne";
        RelationalCalculator variables= new RelationalCalculator();
        variables.assign(name,attributes);
        
        String [] acrostico={"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        
        variables.update("variableOne", 'i',acrostico);
        variables.update("variableOne", 'i',acrostico);
        assertEquals(1, variables.getValues(name));
        
    }
    
    @Test
    public void shouldDeleteAValidVariable(){
        String [] attributes = {"song", "artist", "album", "year"};
        String name= "variableOne";
        RelationalCalculator variables= new RelationalCalculator();
        variables.assign(name,attributes);
        
        String [] acrostico={"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        
        variables.update("variableOne", 'i',acrostico);
        variables.update("variableOne", 'd',acrostico);
        assertEquals(0, variables.getValues(name));
        
    }
    
    @Test
    public void shouldNotDeleteAValidVariable(){
        String [] attributes = {"song", "artist", "album", "year"};
        String name= "variableOne";
        RelationalCalculator variables= new RelationalCalculator();
        variables.assign(name,attributes);
        
        String [] acrostico={"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String [] negra={"La Camisa Negra", "Juanes", "Mi Sangre"};
        
        variables.update("variableOne", 'i',acrostico);
        variables.update("variableOne", 'd',negra);
        assertEquals(1, variables.getValues(name));
        
    }
    
    @Test
    public void shouldProyect(){
        String[] attributes = {"song", "artist", "album", "year"};
        String[] attributes2 = {"song", "artist"};
    
        String[] acrostico = {"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String[] acrosticoo = {"Acrostico", "Shakira"};
    
        RelationalCalculator a = new RelationalCalculator();
        a.assign("variableTwo", attributes);
        a.update("variableTwo", 'i', acrostico);
        a.assign("variableThree", attributes2);
        a.update("variableThree", 'i', acrosticoo);
        a.assign("variableOne", "variableTwo", 'p', "variableThree");        
        assertEquals(a.getValues("variableOne"),1);
        
    }
    
    @Test
    public void shouldNotProyect(){
        String[] attributes = {"song", "artist", "album", "year"};
        String[] attributes2 = {"song", "artist"};
    
        String[] acrostico = {"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String[] negra = {"Negra", "Juanes"};
    
        RelationalCalculator a = new RelationalCalculator();
        a.assign("variableTwo", attributes);
        a.update("variableTwo", 'i', acrostico);
        a.assign("variableThree", attributes2);
        a.update("variableThree", 'i', negra);
        a.assign("variableOne", "variableTwo", 'p', "variableThree");        
        assertEquals(a.getValues("variableOne"),0);
        
    }
    
    
    @Test
    public void shouldSelect(){
         String[] attributes = {"song", "artist", "album", "year"};
        String[] attributes2 = {"song", "artist"};
    
        String[] acrostico = {"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String[] acrosticoo = {"Acrostico", "Shakira"};
    
        RelationalCalculator a = new RelationalCalculator();
        a.assign("variableTwo", attributes);
        a.update("variableTwo", 'i', acrostico);
        a.assign("variableThree", attributes2);
        a.update("variableThree", 'i', acrosticoo);
        a.assign("variableOne", "variableTwo", 's', "variableThree");        
        assertEquals(a.getValues("variableOne"),1);
        
    }
    
    
    @Test
    public void shouldNotSelect(){
        String[] attributes = {"song", "artist", "album", "year"};
        String[] attributes2 = {"song", "artist"};
    
        String[] acrostico = {"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String[] negra = {"Negra", "Juanes"};
    
        RelationalCalculator a = new RelationalCalculator();
        a.assign("variableTwo", attributes);
        a.update("variableTwo", 'i', acrostico);
        a.assign("variableThree", attributes2);
        a.update("variableThree", 'i', negra);
        a.assign("variableOne", "variableTwo", 's', "variableThree");        
        assertEquals(a.getValues("variableOne"),0);
        
    }
    
    @Test
    public void shouldMultiply(){
        String[] attributes = {"song", "artist", "album", "year"};
        String[] attributes2 = {"song2", "artist2"};
    
        String[] acrostico = {"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String[] negra = {"Negra", "Juanes"};
    
        RelationalCalculator a = new RelationalCalculator();
        a.assign("variableTwo", attributes);
        a.update("variableTwo", 'i', acrostico);
        a.assign("variableThree", attributes2);
        a.update("variableThree", 'i', negra);
        a.assign("variableOne", "variableTwo", 'm', "variableThree");     
        assertEquals(a.getValues("variableOne"),1);
        
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown(){
    }
}
