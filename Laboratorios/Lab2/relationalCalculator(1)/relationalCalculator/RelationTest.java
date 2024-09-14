

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RelationTest.
 *
 * @author  CIS
 * @version 2024-2
 */
public class RelationTest
{
    /**
     * Default constructor for test class RelationTest
     */
    public RelationTest(){
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp(){
    }


    @Test
    public void shouldCreateAValidRelation(){
        String [] attributes={"song", "artist", "album", "year"};
        Relation songs= new Relation(attributes);
        assertEquals(songs.columns(),4);
        assertEquals(songs.tuples(),0);
    }    
    
    @Test
    public void shouldCreateAEmptyRelationIfError(){
        String [] attributes={"song", "artist", "song", "year"};
        Relation songs= new Relation(attributes);
        assertEquals(songs.columns(),0);
        assertEquals(songs.tuples(),0);
    }     
    

    @Test
    public void shouldInsertTuples(){
        String [] attributes={"song", "artist", "album", "year"};
        String [] acrostico={"Acróstico", "Shakira", "Las mujeres ya no lloran", "2023"};
        String [] negra={"La Camisa Negra", "Juanes", "Mi Sangre", "2004"};   
        Relation songs= new Relation(attributes);
        songs.insert(acrostico);
        songs.insert(negra);
        assertEquals(songs.columns(),4);
        assertEquals(songs.tuples(),2);
    } 
    
    @Test
    public void shouldNotInsertInvalidTuples(){
        String [] attributes={"song", "artist", "album", "year"};
        String [] acrostico={"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String [] negra={"La Camisa Negra", "Juanes", "Mi Sangre"};
        Relation songs= new Relation(attributes);
        songs.insert(acrostico);
        songs.insert(negra);
        assertEquals(songs.columns(),4);
        assertEquals(songs.tuples(),1);
    }     
    

    @Test
    public void shouldNotInsertRepeatedTuples(){
        String [] attributes={"song", "artist", "album", "year"};
        String [] acrostico={"Acrostico", "Shakira", "Las Mujeres Ya No Lloran", "2023"};
        String [] ocrostico={"ACROSTICO", "SHAKIRA", "Las Mujeres Ya No Lloran", "2023"};
        Relation songs= new Relation(attributes);
        songs.insert(acrostico);
        songs.insert(ocrostico);        
        assertEquals(songs.columns(),4);
        assertEquals(songs.tuples(),1);
    }    
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){
    }
    
}
