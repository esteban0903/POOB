package domain;
 
/*
* Excepción personalizada para manejar errores específicos relacionados con 
* la tienda de Halloween. Esta clase ayuda a identificar problemas particulares
* que pueden ocurrir durante la gestión de productos y precios en la tienda.
*/
public class HalloweenShopException extends Exception {
 
    public static final String PRICE_UNKNOWN = "El precio es desconocido";
    public static final String PRICE_ERROR = "El precio es invalido";
    public static final String COMPLETE_EMPTY = "No se conoce algun basic";
 
    /*
     * Construye una nueva HalloweenShopException con el mensaje detallado especificado.
     * 
     * @param message Un mensaje descriptivo que proporciona detalles sobre la causa de la excepción.
     */
    public HalloweenShopException(String message) {
        super(message);
    }
}