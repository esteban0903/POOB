package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CompleteTest{
   
 
    @Test
    public void shouldCalculateTheCostOfACompleteCostume(){
        Complete c = new Complete("Blanca Nieves", 5000, 0);
        c.addBasic(new Basic("Falda Amarilla", 20000, 0));
        c.addBasic(new Basic("Camiza Azul", 10000, 0));
        c.addBasic(new Basic("Capa roja", 30000, 0));
        try{
           assertEquals(65000,c.price());
        } catch (HalloweenShopException e){
            fail("Threw a exception");
        }    
    }    
    
    @Test
    public void shouldCalculateTheCostOfACompleteCostumeWithDiscount(){
        Complete c = new Complete("Blanca Nieves", 5000, 20);
        c.addBasic(new Basic("Falda Amarilla", 20000, 10));
        c.addBasic(new Basic("Camiza Azul", 10000, 10));
        c.addBasic(new Basic("Capa roja", 30000, 10));
        try{
           assertEquals(47200,c.price());
        }catch(HalloweenShopException e){
            fail("Threw a exception");
        }    
    }  
    
    @Test
    public void shouldThrowExceptionIfCostumeHasNoBasicCustom(){
        Complete c = new Complete("Blanca Nieves", 5000, 20);
        try{ 
           int price=c.price();
           fail("Did not throw exception");
        }catch(HalloweenShopException e) {
            assertEquals(HalloweenShopException.COMPLETE_EMPTY,e.getMessage());
        }    
    }    
    
    
   @Test
    public void shouldThrowExceptionIfThereIsErrorInPrice(){
        Complete c = new Complete("Blanca Nieves", 5000, 20);
        c.addBasic(new Basic("Falda Amarilla", 20000, 10));
        c.addBasic(new Basic("Camiza Azul", -10000, 10));
        c.addBasic(new Basic("Capa roja", 30000, 10));
        try{ 
           int price=c.price();
           fail("Did not throw exception");
        }catch(HalloweenShopException e) {
            assertEquals(HalloweenShopException.PRICE_ERROR,e.getMessage());
        }    
    }     
    
   @Test
    public void shouldThrowExceptionIfPriceIsNotKnown(){
        Complete c = new Complete("Blanca Nieves", 5000, 20);
        c.addBasic(new Basic("Falda Amarilla", 20000, 10));
        c.addBasic(new Basic("Camiza Azul",null, 10));
        c.addBasic(new Basic("Capa roja", 30000, 10));
        try{ 
           int price=c.price();
           fail("Threw a exception");
        }catch(HalloweenShopException e ) {
            assertEquals(HalloweenShopException.PRICE_UNKNOWN,e.getMessage());
        }    
    }
    
   @Test
    public void shouldCalculateTheCostOfCompleteCostumeWithoutErrors() {
        Complete c = new Complete("Pirata", 4000, 0);
        c.addBasic(new Basic("Sombrero", 10000, 0));
        c.addBasic(new Basic("Camisa", 15000, 0));
        c.addBasic(new Basic("Pantalón", 20000, 0));

        try{
            assertEquals(49000, c.price(0, 0));
        }catch(HalloweenShopException e) {
            fail("Threw a exception");
        }
    }

   @Test
   public void shouldEstimateCostUsingUnknownValue() {
        Complete c = new Complete("Pirata", 4000, 0);
        c.addBasic(new Basic("Sombrero", null, 0)); 
        c.addBasic(new Basic("Camisa", 15000, 0));
        c.addBasic(new Basic("Pantalón", 10000, 0));

        try{
            assertEquals(39000, c.price(10000, 0)); 
        }catch(HalloweenShopException e) {
            fail("Threw a exception");
        }
   }

   @Test
   public void shouldEstimateCostUsingErrorValue() {
        Complete c = new Complete("Pirata", 4000, 0);
        c.addBasic(new Basic("Sombrero", -5000, 0)); 
        c.addBasic(new Basic("Camisa", 15000, 0));
        c.addBasic(new Basic("Pantalón", 10000, 0));

        try{
            assertEquals(34000, c.price(0, 5000)); 
        }catch (HalloweenShopException e) {
            fail("Threw a exception");
        }
   }

   @Test
   public void shouldThrowExceptionIfCostumeHasNoBasic() {
        Complete c = new Complete("Pirata", 4000, 0);
        try{
            int price = c.price(10000, 5000);
            fail("Did not throw an exception");
        }catch (HalloweenShopException e) {
            assertEquals(HalloweenShopException.COMPLETE_EMPTY, e.getMessage());
        }
   }

   @Test
   public void shouldCalculateTotalCostUsingMinimumPriceForUnknownValues() {
        Complete c = new Complete("Disfraz Pirata", 5000, 0);
        c.addBasic(new Basic("Sombrero", null, 0));
        c.addBasic(new Basic("Camisa", 15000, 0));
        c.addBasic(new Basic("Pantalón", 10000, 0));

        try{
            int totalPrice = c.price(false); 
            assertEquals(40000, totalPrice); 
        }catch (HalloweenShopException e) {
            fail("Threw an exception");
        }
   }

   @Test
   public void shouldApplyDiscountToTotalCostWithMaximumAssumedValue() {
        Complete c = new Complete("Disfraz Superhéroe", 5000, 20);
        c.addBasic(new Basic("Capa", null, 0));
        c.addBasic(new Basic("Traje", 20000, 0));
        c.addBasic(new Basic("Botas", 10000, 0));

        try{
            int totalPrice = c.price(true);
            assertEquals(44000, totalPrice); 
        }catch (HalloweenShopException e) {
            fail("Threw an exception");
        }
   }

   @Test
   public void shouldCalculateTotalCostUsingMaximumPriceForUnknownValues() {
        Complete c = new Complete("Disfraz Pirata", 5000, 0);
        c.addBasic(new Basic("Sombrero", null, 0));
        c.addBasic(new Basic("Camisa", 15000, 0));
        c.addBasic(new Basic("Pantalón", 10000, 0));

        try {
            int totalPrice = c.price(true); 
            assertEquals(45000, totalPrice); 
        } catch (HalloweenShopException e) {
            fail("Threw an exception");
        }
   }

   @Test
   public void shouldCalculateCostWithDiscountAndErrorHandling() {
        Complete c = new Complete("Disfraz Alien", 5000, 10); 
        c.addBasic(new Basic("Antenas", null, 0)); 
        c.addBasic(new Basic("Traje Espacial", 20000, 0));
        c.addBasic(new Basic("Botas", 6000, 0));

        try {
            int totalPrice = c.price(false);
            assertEquals(33300, totalPrice); 
        } catch (HalloweenShopException e) {
            fail("Threw an exception");
        }
   }
}
    
