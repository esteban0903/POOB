package domain;  

public class Basic extends Costume{
    
    private Integer price;
    
    public Basic(String name, Integer price, int discount){
        super(name,discount);
        this.price=price;
    }    
    

    @Override
    public int price() throws HalloweenShopException{
       if (price == null) throw new HalloweenShopException(HalloweenShopException.PRICE_UNKNOWN);
       if (price < 1) throw new HalloweenShopException(HalloweenShopException.PRICE_ERROR);
       return (price * (100-discount))/100;
    }    
    
    @Override
    public String data(){
        return name+". Precio:" +price+".Descuento:"+discount;
    }
    
}
