package ejerciciopedidos.ejerciciopedidos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String idOrder;
    private LocalDate actualDate;
    private List<Product> products = new ArrayList<>();
    private double totalPrice;

    public Order(String idOrder, LocalDate actualDate, List<Product> products) {
        this.idOrder = idOrder;
        this.actualDate = actualDate;
        this.products = products;
        this.totalPrice = toCalculateTotalPrice();
    }

    private double toCalculateTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public String toFileString(){
        return idOrder + "," + actualDate + "," + totalPrice + "," + toGetProductsList();
    }

    private StringBuilder toGetProductsList() {
        StringBuilder msj = new StringBuilder(" ");
        StringBuilder alert = new StringBuilder("Error al agregar los productos del pedido.");
        int i = 0;
        for(Product product : products){
            msj.append(product.toString()).append("#");
            i++;
            if(i == products.size()){
                return msj;
            }
        }
        return alert;
    }

    public static Order fromFileString(String fileString){
        String[] parts = fileString.split(",", 4);
        if (parts.length != 4) {
            throw new IllegalArgumentException("El formato del archivo no es válido, se esperaban 5 elementos pero se encontraron " + parts.length);
        }
        ArrayList<Product> productsList = convertStringToProductList(parts[3]);
        return new Order(parts[0], LocalDate.parse(parts[1]), productsList);
    }

    private static ArrayList<Product> convertStringToProductList(String productsString) {
        String[] parts = productsString.split("#");
        ArrayList<Product> productList = new ArrayList<>();
        for(String part : parts){
            Product product = new Product(part);
            productList.add(product);
        }
        return productList;
    }
}
