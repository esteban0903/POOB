package domain;  
 
import java.util.ArrayList;

public class Complete extends Costume{
   

    private int makeUp;
    private ArrayList<Basic> pieces;
    
    /**
     * Constructs a new complete custom
     * @param name 
     * @param makeUp
     * @param discount 
     */
    public Complete(String name, int makeUp, int discount){
        super(name,discount);
        this.makeUp=makeUp;
        pieces= new ArrayList<Basic>();
    }


     /**
     * Add a new basic piece
     * @param b
     */   
    public void addBasic(Basic b){
        pieces.add(b);
    }
       
 
    
    @Override
    public int price() throws HalloweenShopException{

        if (pieces.isEmpty()){
            throw new HalloweenShopException(HalloweenShopException.COMPLETE_EMPTY);
        }
        int totalPrice=0;
        for( Basic piece : pieces){
            totalPrice+=piece.price();
        }
        
        totalPrice+=makeUp;
        totalPrice= (totalPrice * (100-discount))/100;
        if(totalPrice<0){
            totalPrice=0;
        }
        return totalPrice;
    }
    
    
     /**
     * Calculates an estimate price
     * For basics where the price cannot be known or has error, the unknown or the error value is assumed
     * @param unknown
     * @param error
     * @return 
     * @throws HalloweenException COMPLETE_EMPTY, if it don't have basics. PRICE_ERROR, if the unknown or error value has error
     */
    public int price(int unknown, int error) throws HalloweenShopException {
        if (pieces.isEmpty()) {
            throw new HalloweenShopException(HalloweenShopException.COMPLETE_EMPTY);
        }
        if (unknown < 0 || error < 0) {
            throw new HalloweenShopException(HalloweenShopException.PRICE_ERROR);
        }
        int totalPrice = 0;
        for (Basic piece : pieces) {
            totalPrice += calculatePiecePrice(piece, unknown, error);
        }
        totalPrice += makeUp;
        totalPrice = (totalPrice * (100 - discount)) / 100;
        if (totalPrice < 0) {
            totalPrice = 0;
        }
    
        return totalPrice;
    }
    
    private int calculatePiecePrice(Basic piece, int unknown, int error) throws HalloweenShopException {
        try {
            return piece.price();
        }catch (HalloweenShopException e) {
            if (e.getMessage().equals(HalloweenShopException.PRICE_UNKNOWN)) {
                return unknown;
            }else if (e.getMessage().equals(HalloweenShopException.PRICE_ERROR)) {
                return error;
            } else {
                throw e; 
            }
        }
    }

    
    
     /**
     * Calculates an estimate price
     * For basics where the price cannot be known, the maximum or the minimum value of the other basics is assumed
     * @param maximum
     * @return 
     * @throws CostumeShopException COMPLETE_EMPTY, if it don't have basics. PRICE_ERROR, if some basic has error
     */
    public int price(boolean maximum) throws HalloweenShopException {
        if (pieces.isEmpty()) {
            throw new HalloweenShopException(HalloweenShopException.COMPLETE_EMPTY);
        }
        int knownTotal = 0;
        Integer minPrice = null;
        Integer maxPrice = null;
        for (Basic piece : pieces) {
            try {
                int piecePrice = piece.price();
                knownTotal += piecePrice;
                if (minPrice == null || piecePrice < minPrice) {
                    minPrice = piecePrice;
                }
                if (maxPrice == null || piecePrice > maxPrice) {
                    maxPrice = piecePrice;
                }
            } catch (HalloweenShopException e) {
                if (e.getMessage().equals(HalloweenShopException.PRICE_ERROR)) {
                    throw e;
                }
            }
        }
        int assumedPrice = determineAssumedPrice(maximum, minPrice, maxPrice);
        int adjustment = calculateAdjustmentForUnknownPrices(assumedPrice);
        return calculateFinalPrice(knownTotal, adjustment);
    }
    
    private int determineAssumedPrice(boolean maximum, Integer minPrice, Integer maxPrice) {
        int assumedPrice = 0;
        if (maximum) {
            if (maxPrice != null) {
                assumedPrice = maxPrice;
            }
        } else {
            if (minPrice != null) {
                assumedPrice = minPrice;
            }
        }
        return assumedPrice;
    }
    
    private int calculateAdjustmentForUnknownPrices(int assumedPrice) {
        int adjustment = 0;
        for (Basic piece : pieces) {
            try {
                piece.price();
            } catch (HalloweenShopException e) {
                if (e.getMessage().equals(HalloweenShopException.PRICE_UNKNOWN)) {
                    adjustment += assumedPrice;
                }
            }
        }
        return adjustment;
    }
    
    private int calculateFinalPrice(int knownTotal, int adjustment) {
        int totalPrice = knownTotal + adjustment + makeUp;
        totalPrice = (totalPrice * (100 - discount)) / 100;
        if (totalPrice < 0) {
            return 0;
        } else {
            return totalPrice;
        }
    }
    @Override
    public String data() throws HalloweenShopException{
        StringBuffer answer=new StringBuffer();
        answer.append(name+". Maquillaje "+ makeUp+". Descuento: "+ discount);
        for(Basic b: pieces) {
            answer.append("\n\t"+b.data());
        }
        return answer.toString();
    } 
    

}
